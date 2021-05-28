package com.example.newsbreezer;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newsbreezer.activity.SavedActivity;
import com.example.newsbreezer.adapter.NewsListAdapter;
import com.example.newsbreezer.model.Article;
import com.example.newsbreezer.viewmodel.NewsListViewModel;
import com.google.android.material.chip.Chip;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NewsListAdapter.setOnSaveButtonClickListener {
    private List<Article> newsModelList;
    private NewsListAdapter adapter;
    private NewsListViewModel viewModel;
    private RecyclerView recyclerView;
    private EditText searchField;
    private TextView noResult;
    private ArrayList<Article> savedArticles;
    private ImageView savedListIcon;
    private Chip chipDate;
    private Chip chipAuthor;
    private ImageView notifyIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        init();
        setListeners();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        viewModel= new ViewModelProvider(this).get(NewsListViewModel.class);
        viewModel.getNewsListObserver().observe(this, new Observer<List<Article>>() {
            @Override
            public void onChanged(List<Article> articles) {
                if(articles != null){
                    Log.e("Onchanged","NOT NULL ");
                    newsModelList=articles;
                    adapter.setNewsList(articles);
                    noResult.setVisibility(View.GONE);
                }
                else{
                    Log.e("Onchanged","NULL ");
                    noResult.setVisibility(View.VISIBLE);

                }
            }
        });

    }


    public void init(){
        recyclerView= findViewById(R.id.recycler_view);
        adapter = new NewsListAdapter(this,newsModelList,this);
        viewModel= new ViewModelProvider(this).get(NewsListViewModel.class);
        savedArticles= new ArrayList<>();
        searchField = findViewById(R.id.search_field);
        noResult= findViewById(R.id.no_result);
        savedListIcon= findViewById(R.id.saved_list);
        chipDate=findViewById(R.id.chip_date);
        chipAuthor=findViewById(R.id.chip_author);
        notifyIcon= findViewById(R.id.notify_icon);


    }

    private void setListeners() {
        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
        savedListIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SavedActivity.class);
                i.putParcelableArrayListExtra("savedList",(ArrayList<Article>)savedArticles);
                startActivity(i);
            }
        });
        chipDate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    sortByDate();
                }
                else{
                    if(chipAuthor.isChecked()){
                        sortByAuthor();
                    }
                    else {
                        adapter.setNewsList(newsModelList);
                    }
                }
            }
        });
        chipAuthor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    sortByAuthor();
                }
                else
                {   if(chipDate.isChecked()){
                    sortByDate();
                }
                else {
                    adapter.setNewsList(newsModelList);
                }
                }
            }
        });

    }

    private void sortByAuthor() {
        List<Article> temp=new ArrayList<>();
        for(Article a: newsModelList){
            temp.add(a);
        }
        Collections.sort(temp, new Comparator<Article>() {
            @Override
            public int compare(Article o1, Article o2) {
                if(o2.getAuthor()!=null && o1.getAuthor()!=null)
                return o2.getAuthor().toLowerCase().compareTo(o1.getAuthor().toLowerCase());
                else
                    return -1;
            }
        });
        adapter.setNewsList(temp);
    }
    public void sortByDate(){
        List<Article> temp=new ArrayList<>();
        for(Article a: newsModelList){
            temp.add(a);
        }
        Collections.sort(temp, new Comparator<Article>() {
            @Override
            public int compare(Article o1, Article o2) {
                return o1.getPublishedAt().compareTo(o2.getPublishedAt());
            }
        });
        adapter.setNewsList(temp);
    }
    public void filter(String search){
        List<Article> temp= new ArrayList<>();
        for(Article a: newsModelList){
            if(a.getTitle().toLowerCase().contains(search.toLowerCase()))
                temp.add(a);
        }
        if(temp.size()==0)
            noResult.setVisibility(View.VISIBLE);
        else
            noResult.setVisibility(View.GONE);
        adapter.setNewsList(temp);
    }

    @Override
    public void onSaveButtonClicked(Article article) {
        if(article.getSaveState()) {
            savedArticles.add(article);
            final Handler handler = new Handler();
            notifyIcon.setVisibility(View.GONE);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    notifyIcon.setVisibility(View.VISIBLE);
                }
            }, 10);
        }
        else{
            savedArticles.remove(article);
            notifyIcon.setVisibility(View.GONE);
            if(savedArticles.size()>0){
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        notifyIcon.setVisibility(View.VISIBLE);
                    }
                }, 10);

            }

        }



    }
}