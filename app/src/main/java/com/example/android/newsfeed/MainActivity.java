package com.example.android.newsfeed;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements LoaderCallbacks<List<ArticleData>> {

    private ArticleListAdapter articleListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: complete layout
        articleListAdapter = new ArticleListAdapter(this, 5, new ArrayList<ArticleData>());

        // TODO: complete layout
        ListView articleList = findViewById(5);
        articleList.setAdapter(articleListAdapter);

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();


        if(networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
        }
    }


    @NonNull
    @Override
    public Loader<List<ArticleData>> onCreateLoader(int i, @Nullable Bundle bundle) {
        Uri baseUri = Uri.parse("https://content.guardianapis.com/search");
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("section", "news");
        uriBuilder.appendQueryParameter("page_size", "150");
        uriBuilder.appendQueryParameter("show_tags", "contributor");
        uriBuilder.appendQueryParameter("from_date", "2019-03-01");
        uriBuilder.appendQueryParameter("to-date", "2019-04-01");
        uriBuilder.appendQueryParameter("api-key", getString(R.string.guardian_key));

        return new ArticleLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<ArticleData>> loader, List<ArticleData> articleData) {
        ;
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<ArticleData>> loader) {
        articleListAdapter.clear();
    }

}