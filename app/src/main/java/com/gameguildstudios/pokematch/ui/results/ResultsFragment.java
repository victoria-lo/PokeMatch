package com.gameguildstudios.pokematch.ui.results;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.gameguildstudios.pokematch.R;
import com.gameguildstudios.pokematch.SharedViewModel;

public class ResultsFragment extends Fragment {

    private TextView poke1;

    private SharedViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_results, container, false);
        poke1 = root.findViewById(R.id.label_result1);
        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
        viewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                poke1.setText(showTopWeakneses(s));
            }
        });
    }

    private String showTopWeakneses(String s){
        String s1 = s.replace("{","").replace("}","")
                .replaceAll("[0-9]","").replaceAll("=","");
        return s1;
    }
}