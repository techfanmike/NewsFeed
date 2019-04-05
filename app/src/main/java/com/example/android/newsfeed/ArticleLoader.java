package com.example.android.newsfeed;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

public class ArticleLoader extends AsyncTaskLoader<List<ArticleData>> {

    public ArticleLoader(Context context) {
        super(context);
    }

    @Override
    public List<ArticleData> loadInBackground() {
        return null;
    }
}
