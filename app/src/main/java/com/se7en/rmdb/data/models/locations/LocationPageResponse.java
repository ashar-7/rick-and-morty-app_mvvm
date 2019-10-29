package com.se7en.rmdb.data.models.locations;

import com.se7en.rmdb.data.models.Info;

import java.util.ArrayList;

public class LocationPageResponse {
    private Info info;
    private ArrayList<Location> results;

    public Info getInfo() {
        return info;
    }

    public ArrayList<Location> getResults() {
        return results;
    }
}
