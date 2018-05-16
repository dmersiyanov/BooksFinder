package com.mersiyanov.dmitry.booksfinder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;
import android.widget.Toast;

import com.mersiyanov.dmitry.booksfinder.network.RetrofitHelper;
import com.mersiyanov.dmitry.booksfinder.pojo.BooksResponse;

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


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

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




}
