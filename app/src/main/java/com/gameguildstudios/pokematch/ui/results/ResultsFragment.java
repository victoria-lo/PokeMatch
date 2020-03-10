package com.gameguildstudios.pokematch.ui.results;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.gameguildstudios.pokematch.R;
import com.gameguildstudios.pokematch.SharedViewModel;
import com.gameguildstudios.pokematch.VolleyCallBack;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ResultsFragment extends Fragment {

    private TextView label1;
    private TextView label2;
    private TextView label3;
    private TextView label4;

    private ImageView img1;
    private ImageView img2;
    private ImageView img3;

    private AnyChartView anyChartView;

    private SharedViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_results, container, false);
        label1 = root.findViewById(R.id.label_1);
        label2 = root.findViewById(R.id.label_2);
        label3 = root.findViewById(R.id.label_3);
        label4 = root.findViewById(R.id.label_4);

        img1= root.findViewById(R.id.img1);
        img2= root.findViewById(R.id.img2);
        img3= root.findViewById(R.id.img3);

        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        anyChartView = view.findViewById(R.id.anyChartView);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
        viewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                setupPieChart(s);
                label2.setText(s);
            }
        });
        viewModel.getNames().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                getTopPoke(s);
            }
        });
    }

    private void setupPieChart(String s){
        String s1 = s.replace("{","").replace("}","");
        String[] arr = s1.split("=|,");
        List<DataEntry> data = new ArrayList<>();

        Pie pie = AnyChart.pie();
        for(int i=0; i<arr.length-1;i+=2){
            String type = arr[i];
            Integer number = Integer.parseInt(arr[i+1]);
            data.add(new ValueDataEntry(type, number));
        }

        pie.data(data);
        pie.title("Weaknesses");
        anyChartView.setChart(pie);

        //label2.setText(s.replace("{","").replace("}","")
                //.replaceAll("[0-9]","").replaceAll("=",""));
    }

    private void getTopPoke(String s){
        String typeStr = label2.getText().toString().replace("{","").replace("}","")
        .replaceAll("[0-9]","").replaceAll("=","");
        String[] types = typeStr.split(",");
        String str = s.replace("{","").replace("}","");
        String[] arr = str.split("=|,");
        label4.setText("");
        label3.setText("");
        label2.setText("");
        label1.setText("");

        for(int i=0;i<types.length;i++){
            String type  = types[i].trim();
            for(int j=1; j<arr.length;j+=2){
                if(arr[j].contains(type)){
                    String pokemon = arr[j-1].trim();
                    String cap = pokemon.substring(0, 1).toUpperCase() + pokemon.substring(1);
                    if(label1.getText().toString().equals("")){
                        label1.setText(cap);
                        String url = "https://pokeapi.co/api/v2/pokemon/" + label1.getText().toString().trim().toLowerCase() + "/";
                        jsonParse(url, img1);
                    }
                    else if(label3.getText().toString().equals("")){
                        label3.setText(cap);
                        String url = "https://pokeapi.co/api/v2/pokemon/" + label3.getText().toString().trim().toLowerCase() + "/";
                        jsonParse(url, img2);
                    }
                    else if(label4.getText().toString().equals("")){
                        label4.setText(cap);
                        String url = "https://pokeapi.co/api/v2/pokemon/" + label4.getText().toString().trim().toLowerCase() + "/";
                        jsonParse(url, img3);
                    }
                }
            }
        }
    }

    //parse the JSON response
    private void jsonParse(String url, final ImageView img){
        RequestQueue queue = Volley.newRequestQueue(getContext());
        // Request a JSON response from the provided URL.
        final JsonObjectRequest res = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String imgUrl = response.getJSONObject("sprites").get("front_default").toString();
                            Picasso.get().load(imgUrl).into(img);
                        } catch (JSONException e) {

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(res);
    }
}