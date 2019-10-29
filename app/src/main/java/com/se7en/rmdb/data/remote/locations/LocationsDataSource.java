package com.se7en.rmdb.data.remote.locations;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.se7en.rmdb.data.models.locations.Location;
import com.se7en.rmdb.data.models.locations.LocationPageResponse;
import com.se7en.rmdb.data.remote.RetrofitClient;
import com.se7en.rmdb.data.remote.RickAndMortyApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationsDataSource extends PageKeyedDataSource<Integer, Location>{

    public static final int PAGE_SIZE = 20;
    private static final int FIRST_PAGE = 1;

    private MutableLiveData<Throwable> initialLoadLiveData = new MutableLiveData<>();
    private String queryName;

    LocationsDataSource(String queryName) {
        this.queryName = queryName;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Location> callback) {
        RickAndMortyApi api = RetrofitClient.getInstance().getApi();
        api.searchLocation(1, queryName)
                .enqueue(new Callback<LocationPageResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<LocationPageResponse> call, @NonNull Response<LocationPageResponse> response) {
                        if(!response.isSuccessful()) {
                            Log.d("TTGGGG", "Error for query: " + queryName);
                            initialLoadLiveData.postValue(new Throwable("404"));
                        } else if(response.body() != null) {
                            if(isInvalid()) return;

                            callback.onResult(response.body().getResults(),
                                    null, FIRST_PAGE + 1);

                            initialLoadLiveData.postValue(null);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<LocationPageResponse> call, @NonNull Throwable t) {
                        if(isInvalid()) return;
                        initialLoadLiveData.postValue(t);
                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Location> callback) {
        RickAndMortyApi api = RetrofitClient.getInstance().getApi();
        api.searchLocation(params.key, queryName)
                .enqueue(new Callback<LocationPageResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<LocationPageResponse> call, @NonNull Response<LocationPageResponse> response) {
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
                    public void onFailure(@NonNull Call<LocationPageResponse> call, @NonNull Throwable t) {

                    }
                });
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Location> callback) {
        RickAndMortyApi api = RetrofitClient.getInstance().getApi();
        api.searchLocation(params.key, queryName)
                .enqueue(new Callback<LocationPageResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<LocationPageResponse> call, @NonNull Response<LocationPageResponse> response) {
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
                    public void onFailure(@NonNull Call<LocationPageResponse> call, @NonNull Throwable t) {

                    }
                });
    }

    public MutableLiveData<Throwable> getInitialLoadLiveData() {
        return initialLoadLiveData;
    }
}
