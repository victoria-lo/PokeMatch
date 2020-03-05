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
import androidx.lifecycle.ViewModelProviders;

import com.gameguildstudios.pokematch.R;

public class ResultsFragment extends Fragment {

    private ResultsViewModel resultsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        resultsViewModel =
                ViewModelProviders.of(this).get(ResultsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_results, container, false);
        final TextView textView = root.findViewById(R.id.text_results);
        resultsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}