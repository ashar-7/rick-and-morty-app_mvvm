package com.se7en.rmdb.data.models.episodes;

import java.io.Serializable;
import java.util.ArrayList;

public class Episode implements Serializable {
    private int id;
    private String name;
    private String air_date;
    private String episode;
    private ArrayList<String> characters;
    private String url;
    private String created;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAir_date() {
        return air_date;
    }

    public String getEpisode() {
        return episode;
    }

    public ArrayList<String> getCharacters() {
        return characters;
    }

    public String getUrl() {
        return url;
    }

    public String getCreated() {
        return created;
    }
}
