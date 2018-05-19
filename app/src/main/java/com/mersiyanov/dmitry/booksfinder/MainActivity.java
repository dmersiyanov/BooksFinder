package com.mersiyanov.dmitry.booksfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.mersiyanov.dmitry.booksfinder.network.RetrofitHelper;
import com.mersiyanov.dmitry.booksfinder.pojo.BooksResponse;
import com.mersiyanov.dmitry.booksfinder.pojo.Item;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SearchView searchView;
    private BooksAdapter adapter;
    private RetrofitHelper retrofitHelper = new RetrofitHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new BooksAdapter();
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        searchView = findViewById(R.id.search_bar);
        adapter.setClickListener(clickListener);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                searchView.clearFocus();

                retrofitHelper.getApi().getBooksInfo(query).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<BooksResponse>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onSuccess(BooksResponse booksResponse) {
                                adapter.setItemList(booksResponse.getItems());
                            }

                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();

                            }
                        });

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) { return false; }
        });



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





}
