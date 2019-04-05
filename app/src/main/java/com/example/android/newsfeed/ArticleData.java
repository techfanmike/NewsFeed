package com.example.android.newsfeed;

public class ArticleData {
    String mTitle;
    String mAuthor;
    String mDate;

    public ArticleData(String title, String author, String date) {
        mTitle = title;
        mAuthor = author;
        mDate = date;
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

}