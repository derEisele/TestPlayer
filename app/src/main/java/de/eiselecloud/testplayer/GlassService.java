package de.eiselecloud.testplayer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GlassService {
    @GET("shows")
    Call<ShowsList> getShows();

    @GET("shows/{id}")
    Call<ShowDetailed> getShow(@Path("id") int show_id);

    @GET("episodes/{id}")
    Call<Episode> getEpisode(@Path("id") int episode_id);
}
