package com.example.android.newsfeed;

// A class to hold the article data
public class ArticleData {
    private String mTitle;
    private String mAuthor;
    private String mDate;
    private String mUrl;

    // constructor to make a new article data object
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