package com.example.android.newsfeed;

public class ArticleData {
    String mTitle;
    String mAuthor;
    String mDate;
    String mUrl;

    public ArticleData(String title, String author, String date, String url) {
        mTitle = title;
        mAuthor = author;
        mDate = date;
        mUrl = url;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getDate() {
        return mDate;
    }

    public String getUrl() {
        return mUrl;
    }
}