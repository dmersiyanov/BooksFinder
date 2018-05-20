package com.mersiyanov.dmitry.booksfinder.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.mersiyanov.dmitry.booksfinder.BookFinderApp;
import com.mersiyanov.dmitry.booksfinder.R;
import com.mersiyanov.dmitry.booksfinder.domain.Entity.Item;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements com.mersiyanov.dmitry.booksfinder.presentation.View {

    @Inject BookSearchPresenter presenter;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private BooksAdapter adapter;
    private TextView screenHint;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BookFinderApp.component.injects(this);

        initUI();

        presenter.attachView(this);

        if(presenter.getCache() != null) {
            screenHint.setVisibility(View.GONE);
            adapter.setItemList(presenter.getCache());
        }


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                screenHint.setVisibility(View.GONE);
                showLoading(true);
                presenter.makeSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) { return false; }
        });
    }

    private void initUI() {
        adapter = new BooksAdapter();
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        searchView = findViewById(R.id.search_bar);
        progressBar = findViewById(R.id.progress_bar);
        screenHint = findViewById(R.id.screen_hint);
        adapter.setClickListener(clickListener);
    }

    public void showError(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading(boolean flag) {
        if(flag) {
            progressBar.setVisibility(View.VISIBLE);

        } else progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showSearchResults(List<Item> books) {
        adapter.setItemList(books);
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete_search:
                adapter.clear();
                showLoading(false);
                screenHint.setVisibility(View.VISIBLE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    BooksAdapter.OnBookClickListener clickListener = new BooksAdapter.OnBookClickListener() {
        @Override
        public void onBookClick(Item item) {
            Intent intent = new Intent(MainActivity.this, BookDetailsActivity.class);
            intent.putExtra("title", item.getVolumeInfo().getTitle());
            intent.putExtra("desc", item.getVolumeInfo().getDescription());
            intent.putExtra("image_url", item.getVolumeInfo().getImageLinks().getThumbnail());
            startActivity(intent);
        }
    };

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }




}
