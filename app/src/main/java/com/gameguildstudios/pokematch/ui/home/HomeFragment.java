package com.gameguildstudios.pokematch.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.gameguildstudios.pokematch.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private EditText poke1;
    private EditText poke2;
    private EditText poke3;
    private EditText poke4;
    private EditText poke5;
    private EditText poke6;

    private TextView type1;
    private TextView type2;
    private TextView type3;
    private TextView type4;
    private TextView type5;
    private TextView type6;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        poke1 = getView().findViewById(R.id.input_home1);
        poke2 = getView().findViewById(R.id.input_home2);
        poke3 = getView().findViewById(R.id.input_home3);
        poke4 = getView().findViewById(R.id.input_home4);
        poke5 = getView().findViewById(R.id.input_home5);
        poke6 = getView().findViewById(R.id.input_home6);

        type1 = getView().findViewById(R.id.type_home1);
        type2 = getView().findViewById(R.id.type_home2);
        type3 = getView().findViewById(R.id.type_home3);
        type4 = getView().findViewById(R.id.type_home4);
        type5 = getView().findViewById(R.id.type_home5);
        type6 = getView().findViewById(R.id.type_home6);
        return root;
    }
}