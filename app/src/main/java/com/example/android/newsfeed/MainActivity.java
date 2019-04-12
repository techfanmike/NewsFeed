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

    private static final int ARTICLE_LOADER_ID = 1;
    private ArticleListAdapter articleListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create adapter and bind to article list view
        articleListAdapter = new ArticleListAdapter(this, new ArrayList<ArticleData>());
        ListView articleList = findViewById(R.id.list_view_articles);
        articleList.setAdapter(articleListAdapter);

        // get the network connection information
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // decide if we have a network connection to continue
        if(networkInfo != null && networkInfo.isConnected()) {

            // get the loader manager and kick off the loader
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(ARTICLE_LOADER_ID, null, this);
        }
    }

    @NonNull
    @Override
    public Loader<List<ArticleData>> onCreateLoader(int i, @Nullable Bundle bundle) {

        // build the query and pass to the article loader
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