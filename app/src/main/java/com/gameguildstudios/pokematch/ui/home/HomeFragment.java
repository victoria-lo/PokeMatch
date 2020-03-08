package com.gameguildstudios.pokematch.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;

import com.gameguildstudios.pokematch.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeFragment extends Fragment {
    private TextView[] textViews;
    private EditText[] pokes;
    private ImageView[] sprites;
    private Button btn;

    private String url;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        int[] pokeIds = {R.id.input_home1, R.id.input_home2, R.id.input_home3, R.id.input_home4, R.id.input_home5, R.id.input_home6};
        int[] ids = {R.id.type_foe1,R.id.type_foe2,R.id.type_foe3,R.id.type_home4,R.id.type_home5,R.id.type_home6};
        int[] imgIds = {R.id.image1,R.id.image2,R.id.image3,R.id.image4,R.id.image5,R.id.image6};
        sprites = initImg(imgIds,root);
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
                        //request to get type of the Pokemon and update the textView.
                        jsonParse(url, i);
                    }
                }
            }
        });
    }


    //parse the JSON response
    private void jsonParse(String url, int index){
        RequestQueue queue = Volley.newRequestQueue(getContext());
        // Request a JSON response from the provided URL.
        final int typeIndex = index;
        final JsonObjectRequest res = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String type="Types:";
                        try {
                            JSONArray types = response.getJSONArray("types");
                            String imgUrl = response.getJSONObject("sprites").get("front_default").toString();
                            Picasso.get().load(imgUrl).into(sprites[typeIndex]);
                            for(int i = 0; i<types.length(); i++){
                                type += (" "+types.getJSONObject(i).getJSONObject("type").get("name"));
                            }
                        } catch (JSONException e) {
                            type = "Invalid Pokemon";
                        }
                        textViews[typeIndex].setText(type);
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

    //place all the imageViews into an array
    private ImageView[] initImg(int[] ids, View view){

        ImageView[] collection = new ImageView[ids.length];

        for(int i=0; i<ids.length; i++){
            ImageView currentImageView = view.findViewById(ids[i]);
            collection[i]=currentImageView;
        }

        return collection;
    }

}