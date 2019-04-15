package com.example.android.newsfeed;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

// derive our class from the async loader class
public class ArticleLoader extends AsyncTaskLoader<List<ArticleData>> {

    private String mUrl;

    // constructor, the url string is the json query
    public ArticleLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<ArticleData> loadInBackground() {
        // bail quickly if null url
        if (mUrl == null) {return null;}

        // get the article list using the query utils class
        List<ArticleData> articles = QueryUtils.fetchArticleData(mUrl);
        return articles;
    }
}
