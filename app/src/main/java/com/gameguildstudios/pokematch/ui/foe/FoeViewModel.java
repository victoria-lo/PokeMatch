package com.gameguildstudios.pokematch.ui.foe;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FoeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public FoeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Enter the foe's Pokemon Team");
    }

    public LiveData<String> getText() {
        return mText;
    }
}