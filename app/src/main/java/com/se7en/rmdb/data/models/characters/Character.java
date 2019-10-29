package com.se7en.rmdb.data.models.characters;

import java.io.Serializable;
import java.util.ArrayList;

public class Character implements Serializable {
    private int id;
    private String name;
    private String status;
    private String species;
    private String type;
    private String gender;
    private OriginSub origin;
    private LocationSub location;
    private String image;
    private ArrayList<String> episode;
    private String url;
    private String created;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getSpecies() {
        return species;
    }

    public String getType() {
        return type;
    }

    public String getGender() {
        return gender;
    }

    public OriginSub getOrigin() {
        return origin;
    }

    public LocationSub getLocation() {
        return location;
    }

    public String getImage() {
        return image;
    }

    public ArrayList<String> getEpisode() {
        return episode;
    }

    public String getUrl() {
        return url;
    }

    public String getCreated() {
        return created;
    }

    public class OriginSub {
        private String name;
        private String url;

        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }
    }

    public class LocationSub {
        private String name;
        private String url;

        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }
    }
}
