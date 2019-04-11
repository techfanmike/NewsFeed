package com.example.android.newsfeed;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<ArticleData>> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private ArticleListAdapter articleListAdapter;

    @NonNull
    @Override
    public Loader<List<ArticleData>> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new ArticleLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<ArticleData>> loader, List<ArticleData> articleData) {
        if (articleListAdapter != null) {
            articleListAdapter.setNotifyOnChange(false);
            articleListAdapter.clear();
            articleListAdapter.setNotifyOnChange(true);
            articleListAdapter.addAll(articleData);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<ArticleData>> loader) {
    }

}