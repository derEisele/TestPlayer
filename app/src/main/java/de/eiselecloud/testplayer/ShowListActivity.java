package de.eiselecloud.testplayer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by alexander on 17.10.17.
 */

public class ShowListActivity extends AppCompatActivity {
    /**
     * Views
     */

    private ListView listView;
    private View parentView;

    private ArrayList<Show> showList;
    private MyShowlistAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstancesState){
        super.onCreate(savedInstancesState);
        setContentView(R.layout.activity_showlist);

        showList = new ArrayList<Show>();

        parentView = findViewById(R.id.parentLayout);

        listView = (ListView) findViewById(R.id.ListView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Snackbar.make(parentView, showList.get(position).getTitle() + " selected!",
                              Snackbar.LENGTH_SHORT).show();
                openShow(showList.get(position).getId());
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(@NonNull final View view){
                loadShows();
            }
        });
        loadShows();

    }

    private void openShow(int id){
        Intent intent = new Intent(getApplicationContext(), ShowActivity.class);
        Bundle b = new Bundle();
        b.putInt("showID", id);
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }

    private void loadShows(){
        if(isNetworkConnected()){
            final ProgressDialog dialog;

            dialog = new ProgressDialog(ShowListActivity.this);
            dialog.setTitle(getString(R.string.getting_json_title));
            dialog.setMessage(getString(R.string.getting_json_msg));
            dialog.show();

            GlassService glass = RetroClient.getGlassService();
            Call<ShowsList> call = glass.getShows();

            call.enqueue(new Callback<ShowsList>() {
                @Override
                public void onResponse(Call<ShowsList> call, Response<ShowsList> response) {
                    dialog.dismiss();

                    if(response.isSuccessful()){
                        showList = response.body().getShows();
                        adapter = new MyShowlistAdapter(ShowListActivity.this, showList);
                        listView.setAdapter(adapter);
                    } else {
                        Snackbar.make(parentView, R.string.string_some_thing_wrong, Snackbar.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ShowsList> call, Throwable t) {
                    dialog.dismiss();
                    t.printStackTrace();
                }
            });

        } else {
            Snackbar.make(parentView, R.string.string_internet_connection_not_available, Snackbar.LENGTH_LONG).show();
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
