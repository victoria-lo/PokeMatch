package com.gameguildstudios.pokematch;

import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<CharSequence> text = new MutableLiveData<>();

    public void setText(TextView[] textviews){
        StringBuilder old= new StringBuilder();
        for(int i=0; i<textviews.length; i++){
            String newWord = textviews[i].getText().toString().replace("Weaknesses: ","");
            old.append(newWord +" ");
        }
        text.setValue(old.toString());
    }

    public LiveData<CharSequence> getText(){
        return text;
    }

}
