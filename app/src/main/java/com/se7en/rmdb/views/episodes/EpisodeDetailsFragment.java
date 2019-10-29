package com.se7en.rmdb.views.episodes;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.se7en.rmdb.R;
import com.se7en.rmdb.data.models.episodes.Episode;
import com.se7en.rmdb.databinding.FragmentEpisodeDetailsBinding;

public class EpisodeDetailsFragment extends Fragment {

    private FragmentEpisodeDetailsBinding binding;

    public EpisodeDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_episode_details, container, false);

        Bundle args = getArguments();
        if(args != null) {
            Episode episode = (Episode) args.getSerializable(getString(R.string.episodeBundleKey));
            binding.setEpisode(episode);
        }
        return binding.getRoot();
    }

}
