package com.example.sofaexpert;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Referenzen
        TextView titleView = findViewById(R.id.header);
        TextView ratingView = findViewById(R.id.rating);
        TextView dateView = findViewById(R.id.releaseDate);
        TextView decriptionView = findViewById(R.id.description);

        // Movie Objekt von MainActivity empfangen
        movie = (Movie) getIntent().getSerializableExtra("movie");

        // Werte in Layout einfuegen
        titleView.setText(movie.getTitle());
        ratingView.setText(String.valueOf(movie.getRating()) + " / 10");

        // Bild laden
        Picasso.get().load(movie.getUrl()).into(
                (ImageView) findViewById(R.id.cover)
        );

        dateView.setText(movie.getReleaseDate().format(Movie.formatter));

        decriptionView.setText(movie.getDescription());
    }
}
