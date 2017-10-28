package de.eiselecloud.testplayer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by alexander on 28.10.17.
 */

public class ShowTabOverview extends Fragment {
    View rootview;
    String descr;
    TextView textViewDescr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("GLASS", "Create Overview View");
        rootview = inflater.inflate(R.layout.show_tab_overview, container, false);
        textViewDescr = (TextView) rootview.findViewById(R.id.show_description);
        textViewDescr.setText(descr);
        return rootview;
    }

    public void setDescription(String description){
        descr = description;
        textViewDescr.setText(descr);
    }
}
