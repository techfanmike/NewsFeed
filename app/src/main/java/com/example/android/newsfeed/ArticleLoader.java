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

        //try {
        List<ArticleData> articleList = null;
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.scheme(getContext().getString(R.string.http_string))
            .encodedAuthority("content.guardianapis.com")
            .appendPath("search")
            .appendQueryParameter("order-by", "newest")
                .appendQueryParameter("show-references", "author")
                .appendQueryParameter("show-tags", "contributor")
                .appendQueryParameter("q", "Android")
                .appendQueryParameter("api-key", getContext().getString(R.string.guardian_key));

        String jsonString = new Uri.Builder().toString();

        String jsonRead;
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;


        try {
            URL request = new URL(jsonString);
            urlConnection = (HttpURLConnection) request.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(5000);
            urlConnection.setConnectTimeout(5000);
            urlConnection.connect();
            if (urlConnection.getResponseCode()== 200) {
                inputStream = urlConnection.getInputStream();
                StringBuilder output = new StringBuilder();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }

        } catch(IOException e) {
            ;
        }

            try {
                JSONObject jsonResponse = new JSONObject(jsonString);
                JSONObject jsonResults = jsonResponse.getJSONObject("response");
                JSONArray resultsArray = jsonResults.getJSONArray("results");

                for(int index = 0; index < resultsArray.length(); index++) {
                    JSONObject entry = resultsArray.getJSONObject(index);
                    String webTitle = entry.getString("webTitle");
                    String webUrl = entry.getString("webUrl");
                    String date = entry.getString("webPublicationDate");
                    JSONArray tags = entry.getJSONArray("tags");

                    String author = " ";

                    if(tags.length() == 0)
                    {
                        author = " ";
                    } else {
                        for (int index2 = 0; index2 < tags.length(); index2++) {
                            JSONObject item = tags.getJSONObject(index2);
                            author += item.getString("webTitle");
                        }
                    }
                }

            } catch(JSONException e) {
                ;
            }

        return null;
    }
}
