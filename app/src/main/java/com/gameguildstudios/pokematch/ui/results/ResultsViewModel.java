package com.gameguildstudios.pokematch.ui.results;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ResultsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ResultsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("PokeMatch recommends the following Pokemon from your team.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}