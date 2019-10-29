package com.se7en.rmdb.viewmodels;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.se7en.rmdb.data.models.locations.Location;
import com.se7en.rmdb.data.remote.locations.LocationsDataSource;
import com.se7en.rmdb.data.remote.locations.LocationsDataSourceFactory;


public class LocationsViewModel extends ViewModel {
    private LiveData<PagedList<Location>> locations;
    private MutableLiveData<LocationsDataSource> locationsDataSourceLiveData;
    private LocationsDataSourceFactory locationsDataSourceFactory;

    private String currentQueryName;

    public LocationsViewModel() {
        locationsDataSourceFactory = new LocationsDataSourceFactory();
        locationsDataSourceLiveData = locationsDataSourceFactory.getLocationsLiveDataSource();

        PagedList.Config pagedListConfig = new PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setPageSize(LocationsDataSource.PAGE_SIZE)
                .build();

        locations = (new LivePagedListBuilder<>(locationsDataSourceFactory, pagedListConfig)).build();
    }

    public LiveData<PagedList<Location>> getLocationsList() {
        return locations;
    }

    public void searchLocation(String name) {
        currentQueryName = name;
        locationsDataSourceFactory.searchLocation(name);
        LocationsDataSource dataSource = locationsDataSourceFactory
                .getLocationsLiveDataSource().getValue();

        if(dataSource != null) dataSource.invalidate();
    }

    public MutableLiveData<LocationsDataSource> getLocationsDataSourceLiveData() {
        return locationsDataSourceLiveData;
    }

    public String getCurrentQueryName() {
        return currentQueryName;
    }
}
