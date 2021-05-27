package com.example.newsbreezer.network;

import com.example.newsbreezer.model.NewsModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NewsService {
    @GET("top-headlines?country=us&apiKey=61682d2000f449b48dcdd3f557ce5c9d")
    Call<NewsModel> getNews();

}

