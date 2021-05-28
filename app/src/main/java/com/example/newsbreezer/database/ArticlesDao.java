package com.example.newsbreezer.database;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.newsbreezer.model.Article;

import java.util.List;
@Dao
public interface ArticlesDao  {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Article article);

    @Query("SELECT * FROM articles" )
    LiveData<List<Article>> getAllArticles();

    @Query("DELETE FROM articles")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertArticles(List<Article> articles);



}
