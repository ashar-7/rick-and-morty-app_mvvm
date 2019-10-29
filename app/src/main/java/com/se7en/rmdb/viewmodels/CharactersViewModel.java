package com.se7en.rmdb.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.se7en.rmdb.data.models.characters.Character;
import com.se7en.rmdb.data.remote.characters.CharactersDataSource;
import com.se7en.rmdb.data.remote.characters.CharactersDataSourceFactory;


public class CharactersViewModel extends ViewModel {
    private LiveData<PagedList<Character>> characters;
    private MutableLiveData<CharactersDataSource> charactersDataSourceLiveData;
    private CharactersDataSourceFactory charactersDataSourceFactory;

    private String currentQueryName;

    public CharactersViewModel() {
        charactersDataSourceFactory = new CharactersDataSourceFactory();
        charactersDataSourceLiveData = charactersDataSourceFactory.getCharactersLiveDataSource();

        PagedList.Config pagedListConfig = new PagedList.Config.Builder()
                                    .setEnablePlaceholders(true)
                                    .setPageSize(CharactersDataSource.PAGE_SIZE)
                                    .build();

        characters = (new LivePagedListBuilder<>(charactersDataSourceFactory, pagedListConfig)).build();
    }

    public LiveData<PagedList<Character>> getCharactersList() {
        return characters;
    }

    public void searchCharacter(String name) {
        currentQueryName = name;
        charactersDataSourceFactory.searchCharacter(name);
        CharactersDataSource dataSource = charactersDataSourceFactory
                .getCharactersLiveDataSource().getValue();

        if(dataSource != null) dataSource.invalidate();
    }

    public MutableLiveData<CharactersDataSource> getCharactersDataSourceLiveData() {
        return charactersDataSourceLiveData;
    }

    public String getCurrentQueryName() {
        return currentQueryName;
    }
}
