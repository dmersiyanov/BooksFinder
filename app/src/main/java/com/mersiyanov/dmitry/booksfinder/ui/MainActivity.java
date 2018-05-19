package com.mersiyanov.dmitry.booksfinder.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.mersiyanov.dmitry.booksfinder.BookFinderApp;
import com.mersiyanov.dmitry.booksfinder.R;
import com.mersiyanov.dmitry.booksfinder.pojo.Item;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    @Inject
    MainPresenter presenter;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private BooksAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BookFinderApp.component.injects(this);

        adapter = new BooksAdapter();
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        searchView = findViewById(R.id.search_bar);
        adapter.setClickListener(clickListener);

        presenter.attachView(this);

        if(presenter.getSavedData() != null) {
            adapter.setItemList(presenter.getSavedData());
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                presenter.makeSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) { return false; }
        });
    }

    public void showError(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void showData(List<Item> itemList) {
        adapter.setItemList(itemList);
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
