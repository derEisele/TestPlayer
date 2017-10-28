package de.eiselecloud.testplayer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

/**
 * Created by alexander on 28.10.17.
 */

public class ShowTabSeason extends Fragment {

    View rootView;
    ListView listView;
    List<Episode> episodes;
    MyEpisodelistAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.show_tab_season, container, false);
        listView = (ListView) rootView.findViewById(R.id.lv_episodes);
        adapter = new MyEpisodelistAdapter(getActivity(), episodes);
        setupListView(listView);
        return rootView;
    }

    private void setupListView(ListView listView){
        listView.setAdapter(adapter);
    }

    public void loadEpisodes(List<Episode> episodes){
        this.episodes = episodes;
    }
}
