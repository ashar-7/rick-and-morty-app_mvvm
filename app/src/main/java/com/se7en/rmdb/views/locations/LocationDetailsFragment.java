package com.se7en.rmdb.views.locations;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.se7en.rmdb.R;
import com.se7en.rmdb.data.models.locations.Location;
import com.se7en.rmdb.databinding.FragmentLocationDetailsBinding;


public class LocationDetailsFragment extends Fragment {

    private FragmentLocationDetailsBinding binding;

    public LocationDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_location_details, container, false);

        Bundle args = getArguments();
        if(args != null) {
            Location location = (Location) args.getSerializable(getString(R.string.locationBundleKey));
            binding.setLocation(location);
        }
        return binding.getRoot();
    }

}
