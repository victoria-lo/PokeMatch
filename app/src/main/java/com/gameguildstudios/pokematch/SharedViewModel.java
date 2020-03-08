package com.gameguildstudios.pokematch;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;


public class SharedViewModel extends ViewModel {
    private final MutableLiveData<String> text = new MutableLiveData<>();

    public void setMap(String s){

        text.setValue(s);
    }

    public LiveData<String> getText(){
        return text;
    }

}
