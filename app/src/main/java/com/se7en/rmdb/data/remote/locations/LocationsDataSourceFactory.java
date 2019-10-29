package com.se7en.rmdb.data.remote.locations;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.se7en.rmdb.data.models.locations.Location;

public class LocationsDataSourceFactory extends DataSource.Factory<Integer, Location> {

    private MutableLiveData<LocationsDataSource>
            locationsLiveDataSource = new MutableLiveData<>();

    private String queryName;

    @Override
    public LocationsDataSource create() {
        LocationsDataSource locationsDataSource = new LocationsDataSource(queryName);
        locationsLiveDataSource.postValue(locationsDataSource);
        return locationsDataSource;
    }

    public MutableLiveData<LocationsDataSource> getLocationsLiveDataSource() {
        return locationsLiveDataSource;
    }

    public void searchLocation(String name) {
        queryName = name;
    }
}
