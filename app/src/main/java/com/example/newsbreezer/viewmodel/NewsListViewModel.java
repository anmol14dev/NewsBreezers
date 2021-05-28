package com.example.newsbreezer.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.newsbreezer.database.ArticlesRepository;
import com.example.newsbreezer.model.Article;
import com.example.newsbreezer.model.NewsModel;
import com.example.newsbreezer.network.NewsService;
import com.example.newsbreezer.network.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsListViewModel extends AndroidViewModel {
    private ArticlesRepository articlesRepository;
    private MutableLiveData<List<Article>> newsList;

    public NewsListViewModel(@NonNull Application application) {
        super(application);
        articlesRepository= new ArticlesRepository(application);
        articlesRepository.makeApiCallDatabase();
        newsList= articlesRepository.getAllNews();

    }


    public MutableLiveData<List<Article>> getNewsListObserver() {
        return newsList;
    }

//    public void makeApiCall(){
//        NewsService newsService = RetrofitInstance.getRetrofitClient().create(NewsService.class);
//        Call<NewsModel> call = newsService.getNews();
//         call.enqueue(new Callback<NewsModel>() {
//             @Override
//             public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
//                 newsList.postValue(response.body().getArticles());
//             }
//             @Override
//             public void onFailure(Call<NewsModel> call, Throwable t) {
//                 newsList.postValue(null);
//
//             }
//         });
//    }
}
