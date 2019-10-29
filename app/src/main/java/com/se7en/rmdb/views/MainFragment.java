package com.se7en.rmdb.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;

import com.se7en.rmdb.R;

public class MainFragment extends Fragment implements View.OnClickListener {

    private NavController navController;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        view.findViewById(R.id.charactersListButtonLayout).setOnClickListener(this);
        view.findViewById(R.id.locationsListButtonLayout).setOnClickListener(this);
        view.findViewById(R.id.episodesListButtonLayout).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.charactersListButtonLayout:
                if(navController != null) {
                    navController.navigate(R.id.action_mainFragment_to_charactersListFragment);
                }
                break;

            case R.id.locationsListButtonLayout:
                if(navController != null) {
                    navController.navigate(R.id.action_mainFragment_to_locationsListFragment);
                }
                break;

            case R.id.episodesListButtonLayout:
                if(navController != null) {
                    navController.navigate(R.id.action_mainFragment_to_episodesListFragment);
                }
                break;
        }
    }
}
