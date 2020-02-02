package com.example.sofaexpert;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.in;
import static java.lang.System.out;

public class MainActivity extends AppCompatActivity {
    private final String baseUrl = "http://image.tmdb.org/t/p/w154/";
    private final String TAG = "sofaExpert";
    private AssetManager manager;
    private GridView gridView;
    private List<Movie> movieList = null;

    /*
    Bilder in Thread laden
    Fortschritt mit ProgressDialog anzeigen
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bilder laden
        Thread t = new Thread(() -> {
            //ProgressDialog dialog = ProgressDialog.show(this, "Bilder werden geladen", "", true);
            movieList = parseMovies();
            //dialog.dismiss();
        });

        t.start();

        manager = getAssets();
        gridView = findViewById(R.id.gridView);

        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Ladedialog anzeigen
        final ProgressDialog dialog = ProgressDialog.show(this, "Bilder werden geladen", "dfgdgadrhgeadrheasdtrheatrhearh");
        gridView.setAdapter(new MovieAdapter(this, R.layout.movie_layout, movieList));
        dialog.dismiss();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getBaseContext(), DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("movie", movieList.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private String jsonFileToString() {
        StringBuilder fileContent = new StringBuilder();
        try {
            InputStream inputStream = manager.open("movies.json");
            BufferedReader bin = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = bin.readLine()) != null) {
                fileContent.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String output = fileContent.toString();
        return output;
    }

    // Gibt eine Liste der Dateinamen der Bilder zurueck
    private List<String> getFileNames() {
        String content = jsonFileToString();
        List<String> fileNames = new ArrayList<>();

        try {
            // Convert String to JsonArray
            JSONObject jsonObject = new JSONObject(content);
            JSONArray results = jsonObject.getJSONArray("results");

            // List der Bilddateinamen erstellen
            for (int i = 0; i < results.length(); i++) {
                String curFileName = results.getJSONObject(i).getString("poster_path");
                fileNames.add(curFileName);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return fileNames;
    }

    private List<Movie> parseMovies() {
        String content = jsonFileToString();
        List<Movie> movies = new ArrayList<>();

        try {
            // Convert String to JsonArray
            JSONObject jsonObject = new JSONObject(content);
            JSONArray results = jsonObject.getJSONArray("results");

            // List der Movies erstellen
            for (int i = 0; i < results.length(); i++) {
                String url = results.getJSONObject(i).getString("poster_path");
                String title = results.getJSONObject(i).getString("title");
                double rating = results.getJSONObject(i).getDouble("vote_average");
                LocalDate releaseDate = LocalDate.parse(results.getJSONObject(i).getString("release_date"), Movie.formatter);
                String description = results.getJSONObject(i).getString("overview");

                movies.add(new Movie(url, title, rating, releaseDate, description));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movies;
    }
}
