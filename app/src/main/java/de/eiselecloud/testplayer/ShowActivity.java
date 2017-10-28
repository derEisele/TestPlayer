package de.eiselecloud.testplayer;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;


import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private int showID;
    private String title;
    private CollapsingToolbarLayout toolbarLayout;
    private View parentView;
    private ImageView toolbarImageView;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private MyShowPagerAdapter adapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getIntent().getExtras();
        showID = -1; // or other values
        if(b != null)
            showID = b.getInt("showID");

        setContentView(R.layout.activity_show);
        parentView = findViewById(R.id.show_parent);

        toolbarImageView = (ImageView) findViewById(R.id.show_toolbarImageView);
        toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.show_toolbar_layout);
        toolbar = (Toolbar) findViewById(R.id.show_toolbar);


        viewPager = (ViewPager) findViewById(R.id.show_viewpager);

        if(viewPager != null){
            setupViewPager(viewPager);
        }

        tabLayout = (TabLayout) findViewById(R.id.show_tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setOnTabSelectedListener(this);

        setSupportActionBar(toolbar);
        loadInfo();
    }


    private void loadInfo(){
        if(isNetworkConnected()){
            final ProgressDialog dialog;

            dialog = new ProgressDialog(ShowActivity.this);
            dialog.setTitle(getString(R.string.getting_json_title));
            dialog.setMessage(getString(R.string.getting_json_msg));
            dialog.show();

            GlassService glass = RetroClient.getGlassService();
            Call<ShowDetailed> call = glass.getShow(showID);

            call.enqueue(new Callback<ShowDetailed>() {
                @Override
                public void onResponse(Call<ShowDetailed> call, Response<ShowDetailed> response) {
                    dialog.dismiss();

                    if(response.isSuccessful()){
                        title = response.body().getTitle();
                        String banner = response.body().getBanner();
                        banner = banner.replace("http://", "https://www.");
                        //Snackbar.make(parentView, banner, Snackbar.LENGTH_LONG).show();
                        toolbarLayout.setTitle(title);
                        setToolbarBackground(response.body().getPoster());

                        ArrayList<Season> seasons = response.body().getSeasons();
                        adapter.getOverview().setDescription(response.body().getDescr());

                        for (Season s: seasons) {
                            int seasonNumber = s.getSeason();
                            ShowTabSeason tabSeason = new ShowTabSeason();
                            adapter.addItem(tabSeason, "Season " + seasonNumber);
                            tabSeason.loadEpisodes(s.getEpisodes());
                            adapter.notifyDataSetChanged();
                        }

                        //episodeList = response.body().getEpisodes();
                        //adapter = new MyEpisodelistlistAdapter(ShowActivity.this, episodeList);
                        //listView.setAdapter(adapter);
                    } else {
                        Snackbar.make(parentView, R.string.string_some_thing_wrong, Snackbar.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ShowDetailed> call, Throwable t) {
                    dialog.dismiss();
                    t.printStackTrace();
                }
            });

        } else {
            Snackbar.make(parentView, R.string.string_internet_connection_not_available, Snackbar.LENGTH_LONG).show();
        }
    }

    private void setupViewPager(ViewPager viewPager){
        Log.i("GLASS", "Setup Viewpager");
        adapter = new MyShowPagerAdapter(getSupportFragmentManager());
        adapter.addItem(new ShowTabOverview(), "Overview");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            Palette.Swatch vibrantSwatch = Palette.from(bitmap).generate().getVibrantSwatch();
            //Palette.Swatch dominantSwatch = Palette.from(bitmap).generate().getDominantSwatch();

            Palette.Swatch mySwatch;

            if (vibrantSwatch != null){
                mySwatch = vibrantSwatch;
            } else {
                Snackbar.make(parentView, "Use dominant swatch", Snackbar.LENGTH_LONG).show();
                mySwatch = Palette.from(bitmap).generate().getDominantSwatch();
            }


            if (mySwatch == null) {
                Snackbar.make(parentView, "Unable to create swatch", Snackbar.LENGTH_LONG).show();
            }else{

                getWindow().setStatusBarColor(mySwatch.getRgb());
                //toolbarLayout.setBackgroundColor(vibrantSwatch.getRgb());
                toolbarLayout.setContentScrimColor(mySwatch.getRgb());
                tabLayout.setBackgroundColor(mySwatch.getRgb());


                float lumi = mySwatch.getHsl()[2];
                Log.i("GLASS", "lumi: " + lumi);
                int textColor;

                if (lumi > 0.5){
                    textColor = Color.parseColor("#000000");
                }else{
                    textColor = Color.parseColor("#FFFFFF");
                }

                toolbarLayout.setCollapsedTitleTextColor(textColor);
                tabLayout.setTabTextColors(textColor, textColor);
                tabLayout.setSelectedTabIndicatorColor(textColor);



            }
            toolbarImageView.setBackground(new BitmapDrawable(getApplicationContext().getResources(), bitmap));
            //toolbarLayout.setBackground(new BitmapDrawable(getApplicationContext().getResources(), bitmap));
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            Snackbar.make(parentView, "Failed to load image", Snackbar.LENGTH_LONG).show();
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };


    private void setToolbarBackground(String url) {

        int x = toolbarImageView.getWidth();
        int y = toolbarImageView.getHeight();
        Picasso.with(getApplicationContext())
                .load(url).resize(x, y).centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .into(target);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }


}
