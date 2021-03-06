package com.se7en.rmdb.data.remote.episodes;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.se7en.rmdb.data.models.episodes.Episode;
import com.se7en.rmdb.data.models.episodes.EpisodePageResponse;
import com.se7en.rmdb.data.remote.RetrofitClient;
import com.se7en.rmdb.data.remote.RickAndMortyApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EpisodesDataSource extends PageKeyedDataSource<Integer, Episode>{

    public static final int PAGE_SIZE = 20;
    private static final int FIRST_PAGE = 1;

    private MutableLiveData<Throwable> initialLoadLiveData = new MutableLiveData<>();
    private String queryName;

    EpisodesDataSource(String queryName) {
        this.queryName = queryName;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Episode> callback) {
        RickAndMortyApi api = RetrofitClient.getInstance().getApi();
        api.searchEpisode(1, queryName)
                .enqueue(new Callback<EpisodePageResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<EpisodePageResponse> call, @NonNull Response<EpisodePageResponse> response) {
                        if(!response.isSuccessful()) {
                            initialLoadLiveData.postValue(new Throwable("404"));
                        } else if(response.body() != null) {
                            if(isInvalid()) return;

                            callback.onResult(response.body().getResults(),
                                    null, FIRST_PAGE + 1);

                            initialLoadLiveData.postValue(null);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<EpisodePageResponse> call, @NonNull Throwable t) {
                        if(isInvalid()) return;
                        initialLoadLiveData.postValue(t);
                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Episode> callback) {
        RickAndMortyApi api = RetrofitClient.getInstance().getApi();
        api.searchEpisode(params.key, queryName)
                .enqueue(new Callback<EpisodePageResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<EpisodePageResponse> call, @NonNull Response<EpisodePageResponse> response) {
                        if(response.body() != null) {
                            String prevURL = response.body().getInfo().getPrev();
                            Integer prevPage = null;
                            if(!prevURL.isEmpty()) {
                                // - '0' converts from ascii code to actual digit
                                prevPage = prevURL.charAt(prevURL.length() - 1) - '0';
                            }

                            callback.onResult(response.body().getResults(), prevPage);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<EpisodePageResponse> call, @NonNull Throwable t) {

                    }
                });
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Episode> callback) {
        RickAndMortyApi api = RetrofitClient.getInstance().getApi();
        api.searchEpisode(params.key, queryName)
                .enqueue(new Callback<EpisodePageResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<EpisodePageResponse> call, @NonNull Response<EpisodePageResponse> response) {
                        if(response.body() != null) {
                            String nextURL = response.body().getInfo().getNext();
                            Integer nextPage = null;
                            if(!nextURL.isEmpty()) {
                                // subtracting '0' converts from ascii code to actual digit
                                nextPage = nextURL.charAt(nextURL.length() - 1) - '0';
                            }

                            callback.onResult(response.body().getResults(), nextPage);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<EpisodePageResponse> call, @NonNull Throwable t) {

                    }
                });
    }

    public MutableLiveData<Throwable> getInitialLoadLiveData() {
        return initialLoadLiveData;
    }
}
