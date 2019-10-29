package com.se7en.rmdb.views.locations;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.se7en.rmdb.R;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.se7en.rmdb.adapters.LocationsListAdapter;
import com.se7en.rmdb.data.models.locations.Location;
import com.se7en.rmdb.data.remote.locations.LocationsDataSource;
import com.se7en.rmdb.utils.GIFUtils;
import com.se7en.rmdb.viewmodels.LocationsViewModel;

public class LocationsFragment extends Fragment {

    private LocationsViewModel viewModel;
    private LocationsListAdapter adapter;

    private LinearLayout displayingResultsLayout;
    private TextView displayingResultsTextView;
    private LinearLayout loadingLayout;
    private ImageView resetSearchButton;
    private TextView errorTextView;
    private Button retryButton;
    private ImageView gifLoadingView;
    private RecyclerView recyclerView;

    private SearchView searchView;
    private NavController navController;

    public LocationsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_locations, container, false);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.options_menu, menu);

        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setQueryHint("Search locations...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                query = query.trim();
                if(!query.isEmpty()) {
                    searchLocation(query);
                    searchView.clearFocus();
                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        navController = Navigation.findNavController(view);

        displayingResultsLayout = view.findViewById(R.id.displayingResultsLayout);
        displayingResultsTextView = view.findViewById(R.id.displayingResultsTextView);
        resetSearchButton = view.findViewById(R.id.resetSearchButton);
        loadingLayout = view.findViewById(R.id.loadingLayout);
        errorTextView = view.findViewById(R.id.loadErrorTextView);
        retryButton = view.findViewById(R.id.retryButton);
        gifLoadingView = view.findViewById(R.id.gifLoadingImage);
        recyclerView = view.findViewById(R.id.locationsRecyclerView);

        displayingResultsLayout.setVisibility(View.GONE);
        resetSearchButton.setOnClickListener(v ->
                searchLocation(null)
        );

        // initialize recyclerview
        adapter = new LocationsListAdapter(getContext());
        adapter.setOnItemClickListener(locationItemClickListener);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Create ViewModel and set up LiveData Observers
        viewModel = ViewModelProviders.of(this).get(LocationsViewModel.class);
        viewModel.getLocationsList().observe(this, pagedList ->
                adapter.submitList(pagedList)
        );

        viewModel.getLocationsDataSourceLiveData().observe(this, locationsDataSource ->
                locationsDataSource.getInitialLoadLiveData().observe(this, throwable -> {
                    // If initial Data loaded successfully
                    if(throwable == null) {
                        onLoadSuccess();
                    } else {
                        onLoadFailure(locationsDataSource, throwable.getMessage());
                    }
                })
        );

        String currentQuery = viewModel.getCurrentQueryName();
        if(currentQuery != null && !currentQuery.isEmpty()) {
            displayingResultsLayout.setVisibility(View.VISIBLE);
            String queryIndicatorText = getString(R.string.queryIndicator, currentQuery);
            displayingResultsTextView.setText(queryIndicatorText);
        }

        onLoading();
    }

    private void searchLocation(String name) {
        if(name == null || name.isEmpty()) {
            displayingResultsLayout.setVisibility(View.GONE);
        } else {
            displayingResultsLayout.setVisibility(View.VISIBLE);
            String queryIndicatorText = getString(R.string.queryIndicator, name);
            displayingResultsTextView.setText(queryIndicatorText);
        }

        viewModel.searchLocation(name);
        onLoading();
    }

    private void onLoading() {
        recyclerView.setVisibility(View.INVISIBLE);
        loadingLayout.setVisibility(View.VISIBLE);
        retryButton.setVisibility(View.GONE);
        errorTextView.setVisibility(View.GONE);

        Glide.with(this).asGif().load(GIFUtils.getRandomLoadingGIF()).into(gifLoadingView);
    }

    private void onLoadSuccess() {
        loadingLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void onLoadFailure(LocationsDataSource locationsDataSource, String error) {
        Glide.with(this).asGif()
                .load(GIFUtils.getRandomErrorGIF())
                .into(gifLoadingView);

        recyclerView.setVisibility(View.INVISIBLE);
        errorTextView.setVisibility(View.VISIBLE);

        if(error.equals(getString(R.string.notFoundErrorCode))) {
            errorTextView.setText(R.string.notFoundErrorText);
        } else {
            errorTextView.setText(R.string.somethingWentWrongErrorText);

            // Show retry button only if error is not 404
            retryButton.setVisibility(View.VISIBLE);

            retryButton.setOnClickListener(v -> {
                locationsDataSource.invalidate();
                onLoading();
            });
        }
    }

    private LocationsListAdapter.OnItemClickListener locationItemClickListener = (position, v) -> {
        if(adapter.getCurrentList() == null || navController == null) return;

        Location location = adapter.getCurrentList().get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable(getString(R.string.locationBundleKey), location);

        navController.navigate(
                R.id.action_locationsListFragment_to_locationDetailsFragment,
                bundle
        );
    };
}
