package com.se7en.rmdb.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.se7en.rmdb.data.models.episodes.Episode;
import com.se7en.rmdb.data.remote.episodes.EpisodesDataSource;
import com.se7en.rmdb.data.remote.episodes.EpisodesDataSourceFactory;

public class EpisodesViewModel extends ViewModel {
    private LiveData<PagedList<Episode>> episodes;
    private MutableLiveData<EpisodesDataSource> episodesDataSourceLiveData;
    private EpisodesDataSourceFactory episodesDataSourceFactory;

    private String currentQueryName;

    public EpisodesViewModel() {
        episodesDataSourceFactory = new EpisodesDataSourceFactory();
        episodesDataSourceLiveData = episodesDataSourceFactory.getEpisodesLiveDataSource();

        PagedList.Config pagedListConfig = new PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setPageSize(EpisodesDataSource.PAGE_SIZE)
                .build();

        episodes = (new LivePagedListBuilder<>(episodesDataSourceFactory, pagedListConfig)).build();
    }

    public LiveData<PagedList<Episode>> getEpisodesList() {
        return episodes;
    }

    public void searchEpisode(String name) {
        currentQueryName = name;
        episodesDataSourceFactory.searchEpisode(name);
        EpisodesDataSource dataSource = episodesDataSourceFactory
                .getEpisodesLiveDataSource().getValue();

        if(dataSource != null) dataSource.invalidate();
    }

    public MutableLiveData<EpisodesDataSource> getEpisodesDataSourceLiveData() {
        return episodesDataSourceLiveData;
    }

    public String getCurrentQueryName() {
        return currentQueryName;
    }
}
