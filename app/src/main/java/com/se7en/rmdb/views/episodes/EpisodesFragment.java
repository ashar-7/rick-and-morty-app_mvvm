package com.se7en.rmdb.views.episodes;


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
import com.se7en.rmdb.adapters.EpisodesListAdapter;
import com.se7en.rmdb.data.models.episodes.Episode;
import com.se7en.rmdb.data.remote.episodes.EpisodesDataSource;
import com.se7en.rmdb.utils.GIFUtils;
import com.se7en.rmdb.viewmodels.EpisodesViewModel;

public class EpisodesFragment extends Fragment {

    private EpisodesViewModel viewModel;
    private EpisodesListAdapter adapter;

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

    public EpisodesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_episodes, container, false);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.options_menu, menu);

        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setQueryHint("Search episodes...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                query = query.trim();
                if(!query.isEmpty()) {
                    searchEpisode(query);
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
        recyclerView = view.findViewById(R.id.episodesRecyclerView);

        displayingResultsLayout.setVisibility(View.GONE);
        resetSearchButton.setOnClickListener(v ->
                searchEpisode(null)
        );

        // initialize recyclerview
        adapter = new EpisodesListAdapter(getContext());
        adapter.setOnItemClickListener(episodeItemClickListener);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Create ViewModel and set up LiveData Observers
        viewModel = ViewModelProviders.of(this).get(EpisodesViewModel.class);
        viewModel.getEpisodesList().observe(this, pagedList ->
                adapter.submitList(pagedList)
        );

        viewModel.getEpisodesDataSourceLiveData().observe(this, episodesDataSource ->
                episodesDataSource.getInitialLoadLiveData().observe(this, throwable -> {
                    // If initial Data loaded successfully
                    if(throwable == null) {
                        onLoadSuccess();
                    } else {
                        onLoadFailure(episodesDataSource, throwable.getMessage());
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

    private void searchEpisode(String name) {
        if(name == null || name.isEmpty()) {
            displayingResultsLayout.setVisibility(View.GONE);
        } else {
            displayingResultsLayout.setVisibility(View.VISIBLE);
            String queryIndicatorText = getString(R.string.queryIndicator, name);
            displayingResultsTextView.setText(queryIndicatorText);
        }

        viewModel.searchEpisode(name);
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

    private void onLoadFailure(EpisodesDataSource episodesDataSource, String error) {
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
                episodesDataSource.invalidate();
                onLoading();
            });
        }
    }

    private EpisodesListAdapter.OnItemClickListener episodeItemClickListener = (position, v) -> {
        if(adapter.getCurrentList() == null || navController == null) return;

        Episode episode = adapter.getCurrentList().get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable(getString(R.string.episodeBundleKey), episode);

        navController.navigate(
                R.id.action_episodesListFragment_to_episodeDetailsFragment,
                bundle
        );
    };
}
