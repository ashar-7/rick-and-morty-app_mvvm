package com.se7en.rmdb.data.models.locations;
import java.io.Serializable;
import java.util.ArrayList;

public class Location implements Serializable {
    private int id;
    private String name;
    private String type;
    private String dimension;
    private ArrayList<String> residents;
    private String url;
    private String created;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDimension() {
        return dimension;
    }

    public ArrayList<String> getResidents() {
        return residents;
    }

    public String getUrl() {
        return url;
    }

    public String getCreated() {
        return created;
    }
}
