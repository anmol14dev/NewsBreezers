package com.example.newsbreezer.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.newsbreezer.model.Article;

import java.util.List;

public class SavedListViewModel extends ViewModel {
    private MutableLiveData<List<Article>> savedList;

    public SavedListViewModel() {
        savedList = new MutableLiveData<>();
    }

    public MutableLiveData<List<Article>> getSavedListObserver(){
        return this.savedList;
    }
    public void setSavedList(List<Article> articles){
        savedList.postValue(articles);
    }
}
