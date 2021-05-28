package com.example.newsbreezer.model;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "articles")
public class Article implements Parcelable {

    public Article(String author, String title, String description, String url, String urlToImage, String publishedAt, String content) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
        this.content = content;
    }



    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    private int uid;

    @ColumnInfo(name="author")
    private String author;
    @ColumnInfo(name="title")
    private String title;

    @ColumnInfo(name="description")
    private String description;
    @ColumnInfo(name="url")
    private String url;
    @ColumnInfo(name="urlToImage")
    private String urlToImage;

    @ColumnInfo(name="publishedAt")
    private String publishedAt;
    @ColumnInfo(name="content")
    private String content;

    @ColumnInfo(name="saveState")
    private Boolean saveState=false ;




    protected Article(Parcel in) {
        author = in.readString();
        title = in.readString();
        description = in.readString();
        url = in.readString();
        urlToImage = in.readString();
        publishedAt = in.readString();
        content = in.readString();
        byte tmpSaveState = in.readByte();
        saveState = tmpSaveState == 0 ? null : tmpSaveState == 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(author);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(url);
        dest.writeString(urlToImage);
        dest.writeString(publishedAt);
        dest.writeString(content);
        dest.writeByte((byte) (saveState == null ? 0 : saveState ? 1 : 2));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };
    public int getUid() {
        return uid;
    }
    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getSaveState() {
        return saveState;
    }

    public void setSaveState(Boolean saveState) {
        this.saveState = saveState;
    }

}