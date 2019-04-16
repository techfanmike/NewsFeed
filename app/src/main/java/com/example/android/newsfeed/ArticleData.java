package com.example.android.newsfeed;

// A class to hold the article data
public class ArticleData {
    private String mTitle;
    private String mAuthor;
    private String mSection;
    private String mDate;
    private String mUrl;

    // constructor to make a new article data object
    public ArticleData(String title, String author, String section, String date, String url) {
        mTitle = title;
        mAuthor = author;
        mSection = section;
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

    public String getSection() {
        return mSection;
    }

    public String getUrl() {
        return mUrl;
    }
}