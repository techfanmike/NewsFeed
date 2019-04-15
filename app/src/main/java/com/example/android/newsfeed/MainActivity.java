package com.example.android.newsfeed;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements LoaderCallbacks<List<ArticleData>> {

    private static final int ARTICLE_LOADER_ID = 1;
    private ArticleListAdapter articleListAdapter;

    @BindView(R.id.list_view_articles)
    ListView articleList;
    @BindView(R.id.loading_indicator)
    View loadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        // create adapter and bind to article list view
        articleListAdapter = new ArticleListAdapter(this, new ArrayList<ArticleData>());
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

        articleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                ArticleData currentItem = articleListAdapter.getItem(position);
                Uri articleUri = Uri.parse(currentItem.getUrl());

                // create an intent to launch web browser, and send the intent
                Intent webSiteIntent = new Intent(Intent.ACTION_VIEW, articleUri);
                startActivity(webSiteIntent);
            }
        });
    }

    @NonNull
    @Override
    public Loader<List<ArticleData>> onCreateLoader(int i, @Nullable Bundle bundle) {

        // build the query and pass to the article loader
        Uri baseUri = Uri.parse("https://content.guardianapis.com/search");
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("order-by", "newest");
        uriBuilder.appendQueryParameter("page-size", "20");
        uriBuilder.appendQueryParameter("show-tags", "contributor");

        uriBuilder.appendQueryParameter("api-key", getString(R.string.guardian_key));

        // kick off the async loader
        return new ArticleLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<ArticleData>> loader, List<ArticleData> articleData) {

        // done loading, so hide the spinny progress thingy
        loadingIndicator.setVisibility(View.GONE);

        // make sure we have a valid list of articles before adding to the adapter to display
        if (articleData != null && !articleData.isEmpty()) {
            articleListAdapter.addAll(articleData);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<ArticleData>> loader) {
        articleListAdapter.clear();
    }

}