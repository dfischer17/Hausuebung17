package com.example.sofaexpert;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class MovieAdapter extends BaseAdapter {
    private Context context;
    private int layoutId;
    private List<Movie> movieList;
    private LayoutInflater inflater;

    public MovieAdapter(Context context, int layoutId, List<Movie> movieList) {
        this.context = context;
        this.layoutId = layoutId;
        this.movieList = movieList;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return movieList.size();
    }

    @Override
    public Object getItem(int position) {
        return movieList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = movieList.get(position);

        View listItem = (convertView == null) ?
                inflater.inflate(this.layoutId, null) : convertView;

        Picasso.get().load(movie.getUrl()).into(
                (ImageView) listItem.findViewById(R.id.thumbmail)
        );

        return listItem;
    }
}
