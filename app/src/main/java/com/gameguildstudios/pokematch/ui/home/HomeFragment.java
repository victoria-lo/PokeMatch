package com.gameguildstudios.pokematch.ui.home;


import android.os.Bundle;
import java.util.ArrayList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

    private Button btn;

    private String url;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        poke1 = root.findViewById(R.id.input_home1);
        poke2 = root.findViewById(R.id.input_home2);
        poke3 = root.findViewById(R.id.input_home3);
        poke4 = root.findViewById(R.id.input_home4);
        poke5 = root.findViewById(R.id.input_home5);
        poke6 = root.findViewById(R.id.input_home6);

        type1 = root.findViewById(R.id.type_home1);
        type2 = root.findViewById(R.id.type_home2);
        type3 = root.findViewById(R.id.type_home3);
        type4 = root.findViewById(R.id.type_home4);
        type5 = root.findViewById(R.id.type_home5);
        type6 = root.findViewById(R.id.type_home6);

        btn = root.findViewById(R.id.btn_home);

        return root;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<String> pokes = new ArrayList<>();
                addPoke(pokes);
                for(int i=0; i<pokes.size(); i++){
                    url="https://pokeapi.co/api/v2/pokemon/" + pokes.get(i).toLowerCase()  +"/";
                    jsonParse(url);
                    //request to get type of the Pokemon and update the textView.
                }

            }
        });
    }

    private void jsonParse(String url){
        Toast.makeText(getContext(), url,Toast.LENGTH_SHORT).show();
    }

    //Checks whether EditText is empty
    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return false;
        return true;
    }

    //Adds the filled EditText to an ArrayList of Pokemon names
    private ArrayList<String> addPoke(ArrayList<String> pokes){
        if(!isEmpty(poke1)){
            pokes.add(poke1.getText().toString());
        }
        if(!isEmpty(poke2)){
            pokes.add(poke2.getText().toString());
        }
        if(!isEmpty(poke3)){
            pokes.add(poke3.getText().toString());
        }
        if(!isEmpty(poke4)){
            pokes.add(poke4.getText().toString());
        }
        if(!isEmpty(poke5)){
            pokes.add(poke5.getText().toString());
        }
        if(!isEmpty(poke6)){
            pokes.add(poke6.getText().toString());
        }
        return pokes;
    }

}