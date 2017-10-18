package de.eiselecloud.testplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by alexander on 17.10.17.
 */

public class MyShowlistAdapter extends ArrayAdapter<Show> {
    List<Show> showList;
    Context context;
    private LayoutInflater mInflater;

    public MyShowlistAdapter(Context context, List<Show> objects) {
        super(context,0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        showList = objects;
    }

    public Show getShow(int position) {
        return showList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final ViewHolder vh;
        if(convertView == null){
            View view = mInflater.inflate(R.layout.row_showlist_view, parent, false);
            vh = ViewHolder.create((RelativeLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        Show item = getItem(position);

        vh.textViewTitle.setText(item.getTitle());
        String infoText = item.getLang().toUpperCase() + " • " + item.getYear().toString();
        vh.textViewInfo.setText(infoText);

        if (item.getPoster() != "") {
            Picasso.with(context).load(item.getPoster()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(vh.imageViewPoster);
        }
        return vh.rootView;
    }

    private static class ViewHolder {
        public final RelativeLayout rootView;
        public final ImageView imageViewPoster;
        public final TextView textViewTitle;
        public final TextView textViewInfo;

        private ViewHolder(RelativeLayout rootView, ImageView imageViewPoster, TextView textViewTitle, TextView textViewInfo){
            this.rootView = rootView;
            this.imageViewPoster = imageViewPoster;
            this.textViewTitle = textViewTitle;
            this.textViewInfo = textViewInfo;
        }

        public static ViewHolder create(RelativeLayout rootView) {
            ImageView imageViewPoster = (ImageView) rootView.findViewById(R.id.imageViewPoster);
            TextView textViewTitle = (TextView) rootView.findViewById(R.id.textViewTitle);
            TextView textViewInfo = (TextView) rootView.findViewById(R.id.textViewInfo);
            return new ViewHolder(rootView, imageViewPoster, textViewTitle, textViewInfo);
        }



    }
}
