package com.gameguildstudios.pokematch.ui.results;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.gameguildstudios.pokematch.R;
import com.gameguildstudios.pokematch.SharedViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResultsFragment extends Fragment {

    private TextView label1;
    private TextView label2;
    private AnyChartView anyChartView;

    private SharedViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_results, container, false);
        label1 = root.findViewById(R.id.label_1);
        label2 = root.findViewById(R.id.label_2);

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
        label2.setText("");
        label1.setText("");

        for(int i=0;i<types.length;i++){
            String type  = types[i].trim();
            for(int j=1; j<arr.length;j+=2){
                if(arr[j].contains(type)){
                    String old = label1.getText().toString();
                    String cap = arr[j-1].substring(0, 1).toUpperCase() + arr[j-1].substring(1);
                    label1.setText(old+"\n"+cap);
                }
            }
        }
        //arr={ponyta,fire,gengar,poison ghost}
        //type={poison,steel}

    }
}