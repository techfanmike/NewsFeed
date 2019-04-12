package com.example.android.newsfeed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleListAdapter extends ArrayAdapter<ArticleData> {
    public ArticleListAdapter(Context context, List<ArticleData> articleList) {
        super(context, 0, articleList);
    }

    // implement class for view holder pattern
    static private class ViewHolder {
        private TextView mTitle;
        private TextView mAuthor;
        private TextView mDate;
    }

    // only call bind when null ConvertView
    @BindView(R.id.articleName)
    TextView articleName;
    @BindView(R.id.AuthorName)
    TextView authorName;
    @BindView(R.id.Date)
    TextView dateName;

    @Override
    public View getView(int position, View convertView, ViewGroup container) {

        // instantiate the view holder class
        ViewHolder holder;

        // if a new list, inflate a new list item
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.article_list_item_layout, container, false);

            // call the butterknife binding method
            ButterKnife.bind(this, convertView);

            // assign the view holder variables
            holder = new ViewHolder();
            holder.mTitle = articleName;
            holder.mAuthor = authorName;
            holder.mDate = dateName;
            convertView.setTag(holder);
        } else {
            // existing item, so grab the save view holder
            holder = (ViewHolder) convertView.getTag();
        }

        // get a handle to the item in the array indexed by position
        ArticleData entry = getItem(position);

        holder.mTitle.setText(entry.getTitle());
        holder.mAuthor.setText(entry.getAuthor());
        holder.mDate.setText(entry.getDate());

        return convertView;
    }
}
