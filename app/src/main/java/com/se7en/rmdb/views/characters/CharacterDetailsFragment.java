package com.se7en.rmdb.views.characters;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.se7en.rmdb.R;
import com.se7en.rmdb.data.models.characters.Character;
import com.se7en.rmdb.databinding.FragmentCharacterDetailsBinding;

public class CharacterDetailsFragment extends Fragment {

    private FragmentCharacterDetailsBinding binding;

    public CharacterDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_character_details, container, false);

        Bundle args = getArguments();
        if(args != null) {
            Character character = (Character) args.getSerializable(getString(R.string.characterBundleKey));
            binding.setCharacter(character);
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView imageView = view.findViewById(R.id.characterImageView);
        Glide.with(this).load(binding.getCharacter().getImage()).into(imageView);
    }
}
