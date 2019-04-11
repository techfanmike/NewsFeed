package com.example.android.newsfeed;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.AsyncTaskLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
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
