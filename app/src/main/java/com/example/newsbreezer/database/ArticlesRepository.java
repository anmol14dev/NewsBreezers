package com.example.newsbreezer.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import com.example.newsbreezer.model.Article;
import com.example.newsbreezer.model.NewsModel;
import com.example.newsbreezer.network.NewsService;
import com.example.newsbreezer.network.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticlesRepository {
    ArticlesDatabase database;

    private MutableLiveData<List<Article>> newsList;
    public ArticlesRepository(Application application) {
        database=ArticlesDatabase.getDataBaseInstance(application);
        newsList= new MutableLiveData<>();
    }
    public MutableLiveData<List<Article>> getAllNews(){
        return newsList;
    }

    public void makeApiCallDatabase(){
        NewsService newsService = RetrofitInstance.getRetrofitClient().create(NewsService.class);
        Call<NewsModel> call = newsService.getNews();
        call.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                insert(response.body().getArticles());
                newsList.postValue(response.body().getArticles());
            }
            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {
                newsList.postValue(null);

            }
        });
    }

    public void insert(List<Article> articles){
       new InsertAsyncTask(database).execute(articles);
    }


    static class InsertAsyncTask extends AsyncTask<List<Article>,Void,Void>{
        ArticlesDao articlesDao;

        public InsertAsyncTask(ArticlesDatabase articlesDatabase) {
            articlesDao = articlesDatabase.articlesDao();
        }
        @Override
        protected Void doInBackground(List... lists) {
            articlesDao.insertArticles(lists[0]);
            return null;
        }
    }
}
