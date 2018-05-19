package com.mersiyanov.dmitry.booksfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class BookDetailsActivity extends AppCompatActivity {

    private TextView title;
    private TextView desc;
    private ImageView cover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        title = findViewById(R.id.title);
        desc = findViewById(R.id.desc);
        cover =  findViewById(R.id.book_big_cover);

        Intent intent = getIntent();
        String bookTitle = intent.getStringExtra("title");
        String bookDesc = intent.getStringExtra("desc");
        String bookImage = intent.getStringExtra("image_url");

        title.setText(bookTitle);
        desc.setText(bookDesc);
        Picasso.get().load(bookImage).fit().into(cover);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);





    }
}
