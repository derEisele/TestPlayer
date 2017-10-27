package de.eiselecloud.testplayer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by alexander on 17.10.17.
 */

class Models {
}

class ShowsList {
    private ArrayList<Show> shows = new ArrayList<>();

    public ArrayList<Show> getShows(){
        return shows;
    }

    public void setShows(ArrayList<Show> shows) {
        this.shows = shows;
    }
}

class Show {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("lang")
    @Expose
    private String lang;

    @SerializedName("banner")
    @Expose
    private String banner;

    @SerializedName("poster")
    @Expose
    private String poster;

    @SerializedName("descr")
    @Expose
    private String descr;

    @SerializedName("year")
    @Expose
    private Integer year;

    @SerializedName("imdb_id")
    @Expose
    private String imdb_id;



    public Integer getId() {
        return id;
    }

    private void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    public String getLang() {
        return lang;
    }

    private void setLang(String lang) {
        this.lang = lang;
    }

    public String getBanner() {
        return banner;
    }

    private void setBanner(String banner) {
        this.banner = banner;
    }

    public String getPoster() {
        return poster;
    }

    private void setPoster(String poster) {
        this.poster = poster;
    }

    public String getDescr() {
        return descr;
    }

    private void setDescr(String descr) {
        this.descr = descr;
    }

    public Integer getYear() {
        return year;
    }

    private void setYear(Integer year) {
        this.year = year;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    private void setImdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
    }
}




class ShowDetailed extends Show{

    private ArrayList<Season> seasons = new ArrayList<>();

    public ArrayList<Season> getSeasons(){
        return seasons;
    }

    public void setSeasons(ArrayList<Season> seasons) {
        this.seasons = seasons;
    }


}

class Season{
    private ArrayList<Episode> episodes = new ArrayList<>();

    public ArrayList<Episode> getEpisodes(){
        return episodes;
    }

    public void setEpisodes(ArrayList<Episode> episodes) {
        this.episodes = episodes;
    }

    @SerializedName("season")
    @Expose
    private Integer season;

    public Integer getSeason() {
        return season;
    }

    private void setSeason(Integer season) {
        this.season = season;
    }
}

class Episode{
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("descr")
    @Expose
    private String descr;

    @SerializedName("season")
    @Expose
    private Integer season;

    @SerializedName("episode")
    @Expose
    private Integer episode;



    public Integer getId() {
        return id;
    }

    private void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    public String getDescr() {
        return descr;
    }

    private void setDescr(String descr) {
        this.descr = descr;
    }

    public Integer getSeason() {
        return season;
    }

    private void setSeason(Integer season) {
        this.season = season;
    }

    public Integer getEpisode() {
        return episode;
    }

    private void setEpisode(Integer episode) {
        this.episode = episode;
    }
}