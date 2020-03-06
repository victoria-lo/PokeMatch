package com.gameguildstudios.pokematch.ui.foe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;

import com.gameguildstudios.pokematch.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FoeFragment extends Fragment {
    private TextView[] textViews;
    private EditText[] pokes;
    private Button btn;

    private String url;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_foe, container, false);

        int[] pokeIds = {R.id.input_foe1, R.id.input_foe2, R.id.input_foe3, R.id.input_foe4, R.id.input_foe5, R.id.input_foe6};
        int[] ids = {R.id.type_foe1,R.id.type_foe2,R.id.type_foe3,R.id.type_foe4,R.id.type_foe5,R.id.type_foe6};
        textViews =initTextViews(ids, root);
        pokes = initEditText(pokeIds,root);
        btn = root.findViewById(R.id.btn_foe);

        return root;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i=0; i< pokes.length; i++){

                    if(!isEmpty(pokes[i])){
                        url="https://pokeapi.co/api/v2/pokemon/" + pokes[i].getText().toString().trim().toLowerCase()  +"/";
                        jsonParseType(url, i); //request to get type of the Pokemon and then its weaknesses
                    }
                }
            }
        });
    }

    private void jsonParseType(String url, int index){
        RequestQueue queue = Volley.newRequestQueue(getContext());
        final int typeIndex = index;
        final JsonObjectRequest res = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray types = response.getJSONArray("types");
                            if(types.length()<2){//if single type use API
                                Object type = types.getJSONObject(0).getJSONObject("type").get("name");
                                String weakUrl = "https://pokeapi.co/api/v2/type/" + type.toString() + "/";
                                textViews[typeIndex].setText("Weaknesses: ");
                                jsonParseWeakness(weakUrl,typeIndex);
                            }
                            else{
                                String type1 = types.getJSONObject(0).getJSONObject("type").get("name").toString();
                                String type2 = types.getJSONObject(1).getJSONObject("type").get("name").toString();
                                String type;
                                if(type1.compareTo(type2)<0){
                                    type = type1+type2;
                                    Toast.makeText(getContext(),type,Toast.LENGTH_SHORT).show();
                                }else{
                                    type = type2+type1;
                                    Toast.makeText(getContext(),type,Toast.LENGTH_SHORT).show();
                                }
                                getJSON(type, typeIndex);
                            }
                        } catch (JSONException e) {
                            String weak = "Invalid Pokemon";
                            textViews[typeIndex].setText(weak);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String type = "Invalid Pokemon";
                textViews[typeIndex].setText(type);
            }
        });
        queue.add(res);

    }

    private void jsonParseWeakness(String url, int index) {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        final int textIndex = index;
        final JsonObjectRequest res = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    String output = "";
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject dmg = response.getJSONObject("damage_relations");
                            JSONArray weak = dmg.getJSONArray("double_damage_from");

                            for(int i=0; i<weak.length(); i++){
                                if(!textViews[textIndex].getText().toString().contains(weak.getJSONObject(i).get("name").toString())) {
                                    output += (" "+ weak.getJSONObject(i).get("name").toString());

                                }
                            }

                        } catch (JSONException e) {
                            output = "Invalid Pokemon";
                            textViews[textIndex].setText(output);
                        }
                        String old = textViews[textIndex].getText().toString();
                        textViews[textIndex].setText(old+output);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String type = "Invalid Pokemon";
                textViews[textIndex].setText(type);
            }
        });
        queue.add(res);
    }


    //Checks whether EditText is empty
    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return false;
        return true;
    }

    //place all the textViews into an array
    private TextView[] initTextViews(int[] ids, View view){

        TextView[] collection = new TextView[ids.length];

        for(int i=0; i<ids.length; i++){
            TextView currentTextView = view.findViewById(ids[i]);
            collection[i]=currentTextView;
        }

        return collection;
    }

    //place all the editText into an array
    private EditText[] initEditText(int[] ids, View view){

        EditText[] collection = new EditText[ids.length];

        for(int i=0; i<ids.length; i++){
            EditText currentEditText = view.findViewById(ids[i]);
            collection[i]=currentEditText;
        }

        return collection;
    }

    private void getJSON(String type, int index){
        // Reading json file from assets folder
        StringBuffer sb = new StringBuffer();
        BufferedReader br = null;
        try{
            br = new BufferedReader(new InputStreamReader(getContext().getAssets().open("weaknesses.json")));
            String temp;
            while ((temp = br.readLine()) != null)
                sb.append(temp);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close(); // stop reading
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String jsonStr = sb.toString();


        // Try to parse JSON
        try {
            // Creating JSONObject from String
            JSONObject jsonObj = new JSONObject(jsonStr);
            //get weaknesses array
            JSONArray weaknesses = jsonObj.getJSONArray(type);

            StringBuilder output = new StringBuilder();
            output.append("Weaknesses: ");
            for(int i=0; i<weaknesses.length();i++){
                if(i != weaknesses.length()-1){
                    output.append((weaknesses.get(i)+" "));
                }else{
                    output.append(weaknesses.get(i));
                }
            }
            textViews[index].setText(output.toString());
        } catch (JSONException e) {

            e.printStackTrace();
        }
    }


}