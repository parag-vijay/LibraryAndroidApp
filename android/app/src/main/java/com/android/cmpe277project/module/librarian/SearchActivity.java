package com.android.cmpe277project.module.librarian;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.cmpe277project.R;
import com.android.cmpe277project.base.BaseActivity;
import com.android.cmpe277project.model.Book;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity {

    @BindView(R.id.rv_books)
    RecyclerView recycler_search;

    private SearchAdapter searchAdapter;

    private List<Book> books;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        books.add(new Book("Head First Java", "Becky"));
        books.add(new Book("Cracking the coding Interview", "McDonald"));

        searchAdapter = new SearchAdapter(this, books);

        recycler_search.setLayoutManager(new LinearLayoutManager(this));
        recycler_search.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recycler_search.setItemAnimator(new DefaultItemAnimator());
        recycler_search.setAdapter(searchAdapter);
    }

    @OnClick(R.id.add_to_cart)
    public void onViewClicked(){

    }
}
