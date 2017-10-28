package de.eiselecloud.testplayer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexander on 28.10.17.
 */

public class MyEpisodelistAdapter extends ArrayAdapter<Episode> {
    List<Episode> episodeList;
    Context context;
    private LayoutInflater mInflater;

    public MyEpisodelistAdapter(Context context, List<Episode> objects) {
        super(context,0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        episodeList = objects;
    }

    public Episode getEpisode(int position) {
        return episodeList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final ViewHolder vh;
        if(convertView == null){
            View view;

            if (position % 2 == 1) {
                view = mInflater.inflate(R.layout.row_episodelist_odd, parent, false);
            } else {
                view = mInflater.inflate(R.layout.row_episodelist_even, parent, false);
            }

            vh = ViewHolder.create((RelativeLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        Episode item = getItem(position);

        vh.textViewTitle.setText(item.getTitle());
        String numberText = "S" + item.getSeason() + " • E" + item.getEpisode();
        vh.textViewNumber.setText(numberText);

        return vh.rootView;
    }

    private static class ViewHolder {
        public final RelativeLayout rootView;
        public final TextView textViewNumber;
        public final TextView textViewTitle;
        public final TextView textViewLength;

        private ViewHolder(RelativeLayout rootView, TextView textViewNumber, TextView textViewTitle, TextView textViewLength){
            this.rootView = rootView;
            this.textViewNumber = textViewNumber;
            this.textViewTitle = textViewTitle;
            this.textViewLength = textViewLength;
        }

        public static ViewHolder create(RelativeLayout rootView) {
            TextView textViewNumber = (TextView) rootView.findViewById(R.id.tv_number);
            TextView textViewTitle = (TextView) rootView.findViewById(R.id.tv_title);
            TextView textViewLength = (TextView) rootView.findViewById(R.id.tv_length);
            return new ViewHolder(rootView, textViewNumber, textViewTitle, textViewLength);
        }

    }
}
