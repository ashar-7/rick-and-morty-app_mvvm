package com.se7en.rmdb.data.remote.episodes;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.se7en.rmdb.data.models.episodes.Episode;

public class EpisodesDataSourceFactory extends DataSource.Factory<Integer, Episode> {

    private MutableLiveData<EpisodesDataSource>
            episodesLiveDataSource = new MutableLiveData<>();

    private String queryName;

    @Override
    public EpisodesDataSource create() {
        EpisodesDataSource episodesDataSource = new EpisodesDataSource(queryName);
        episodesLiveDataSource.postValue(episodesDataSource);
        return episodesDataSource;
    }

    public MutableLiveData<EpisodesDataSource> getEpisodesLiveDataSource() {
        return episodesLiveDataSource;
    }

    public void searchEpisode(String name) {
        queryName = name;
    }
}
