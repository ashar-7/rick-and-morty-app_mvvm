package com.se7en.rmdb.data.models.episodes;

import com.se7en.rmdb.data.models.Info;

import java.util.ArrayList;

public class EpisodePageResponse {
    private Info info;
    private ArrayList<Episode> results;

    public ArrayList<Episode> getResults() {
        return results;
    }

    public Info getInfo() {
        return info;
    }
}
