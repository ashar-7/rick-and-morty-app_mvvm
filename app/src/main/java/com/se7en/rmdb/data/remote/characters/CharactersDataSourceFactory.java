package com.se7en.rmdb.data.remote.characters;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.se7en.rmdb.data.models.characters.Character;

public class CharactersDataSourceFactory extends DataSource.Factory<Integer, Character> {

    private MutableLiveData<CharactersDataSource>
            charactersLiveDataSource = new MutableLiveData<>();

    private String queryName;

    @Override
    public CharactersDataSource create() {
        CharactersDataSource charactersDataSource = new CharactersDataSource(queryName);
        charactersLiveDataSource.postValue(charactersDataSource);
        return charactersDataSource;
    }

    public MutableLiveData<CharactersDataSource> getCharactersLiveDataSource() {
        return charactersLiveDataSource;
    }

    public void searchCharacter(String name) {
        queryName = name;
    }
}
