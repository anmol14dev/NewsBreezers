package com.example.newsbreezer.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.PrimaryKey;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.newsbreezer.model.Article;

@Database(entities = {Article.class},version = 1)
public abstract class ArticlesDatabase extends RoomDatabase{
    public abstract ArticlesDao articlesDao();
    private static  ArticlesDatabase INSTANCE;

    static ArticlesDatabase getDataBaseInstance(Context context){
        if(INSTANCE==null){
            synchronized (ArticlesDatabase.class){
                if(INSTANCE== null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(), ArticlesDatabase.class,"articles-database")
                            .addCallback(callback)
                            .build();
                }
                            }
        }
        return INSTANCE;
    }
    static Callback callback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDBAsyncTask(INSTANCE).execute();
        }
    };

    static class PopulateDBAsyncTask extends AsyncTask<Void,Void,Void>{
        private ArticlesDao articlesDao;

        PopulateDBAsyncTask(ArticlesDatabase articlesDatabase){
            articlesDao=articlesDatabase.articlesDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            articlesDao.deleteAll();
            return null;
        }
    }

}
