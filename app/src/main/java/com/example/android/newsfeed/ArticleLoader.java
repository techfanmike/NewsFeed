package com.example.android.newsfeed;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import java.io.IOException;
import java.net.URL;
import java.util.List;



public class ArticleLoader extends AsyncTaskLoader<List<ArticleData>> {

    public ArticleLoader(Context context) {
        super(context);
    }

    @Override
    public List<ArticleData> loadInBackground() {

        //try {
        List<ArticleData> articleList = null;
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.scheme(getContext().getString(R.string.http_string))
            .encodedAuthority("content.guardianapis.com")
            .appendPath("search")
            .appendQueryParameter("order-by", "newest");

        String jsonString = uriBuilder.toString();
        

       // } catch (IOException e) {
       //     ;
       // }


        return null;
    }
}
