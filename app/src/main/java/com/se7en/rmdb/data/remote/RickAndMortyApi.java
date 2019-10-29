package com.se7en.rmdb.data.remote;

import com.se7en.rmdb.data.models.characters.CharacterPageResponse;
import com.se7en.rmdb.data.models.episodes.EpisodePageResponse;
import com.se7en.rmdb.data.models.locations.LocationPageResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RickAndMortyApi {
    @GET("character")
    Call<CharacterPageResponse> searchCharacter(
            @Query("page") int page,
            @Query("name") String name
        );

    @GET("location")
    Call<LocationPageResponse> searchLocation(
            @Query("page") int page,
            @Query("name") String name
    );

    @GET("episode")
    Call<EpisodePageResponse> searchEpisode(
            @Query("page") int page,
            @Query("name") String name
    );
}
