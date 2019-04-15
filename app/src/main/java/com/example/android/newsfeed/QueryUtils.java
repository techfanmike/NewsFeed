package com.example.android.newsfeed;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class QueryUtils {

    // string for loggin purposes
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    // private constructor, should not be called
    private QueryUtils() {;}

    public static List<ArticleData> fetchArticleData(String requestUrl) {

        // query is passed in
        URL url = createUrl(requestUrl);

        // make the request
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // return the list of articles
        return extractFeatureFromJson(jsonResponse);
    }

    // helper to return a URL object from a string
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);

        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    // open the http connection, use input stream to read
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if(url == null){return jsonResponse;}

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if(urlConnection.getResponseCode()  == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }

        } catch (IOException e) {
            Log.e(LOG_TAG, "problem retrieving the earthquake JSON results.", e);
        } finally {
            if(urlConnection != null) {
                urlConnection.disconnect();
            }
            if(inputStream != null) {
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    // given an input stream, read in a line
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader =
                    new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    // interpret the JSON response, return a list of article data
    private static List<ArticleData> extractFeatureFromJson(String articleJson) {

        if(TextUtils.isEmpty(articleJson)) { return null;}

        List<ArticleData> articles = new ArrayList<>();

        try {

            JSONObject baseJsonResponse = new JSONObject(articleJson);
            JSONArray articleArray = baseJsonResponse.getJSONObject("response").getJSONArray("results");

            for (int index1 = 0; index1 < articleArray.length(); index1++) {
                JSONObject currentArticle = articleArray.getJSONObject(index1);
                String webTitle = currentArticle.getString("webTitle");
                String date = currentArticle.getString("webPublicationDate");
                String url = currentArticle.getString("webUrl");

                String authorName = "";

                try {
                    JSONArray tags = currentArticle.getJSONArray("tags");
                    for(int index2 = 0; index2 < tags.length(); index2++) {
                        JSONObject authorNameObj = tags.getJSONObject(index2);
                        authorName = authorNameObj.getString("webTitle");
                    }
                } catch (Exception e) {
                    Log.e(LOG_TAG, "No author Name");
                }

                if(authorName.equals(""))authorName = "No author given";
                articles.add(new ArticleData(webTitle, authorName, date, url));
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the article JSON results", e);
        }

        return articles;
    }
}