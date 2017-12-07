package com.android.cmpe277project.module.librarian;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.android.cmpe277project.R;
import com.android.cmpe277project.base.BaseActivity;
import com.android.cmpe277project.model.Book;
import com.android.cmpe277project.module.util.Bakery;
import com.android.cmpe277project.service.librarian.LibrarianPresenter;
import com.android.cmpe277project.service.librarian.LibrarianPresenterImpl;
import com.android.cmpe277project.service.librarian.LibrarianViewInteractor;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

public class SearchActivity extends BaseActivity implements LibrarianViewInteractor {

    @BindView(R.id.rv_books)
    RecyclerView recycler_search;
    @BindView(R.id.edt_search)
    EditText edtSearch;
    @BindView(R.id.btn_search)
    Button btnSearch;
    @BindView(R.id.search_progressBar)
    ProgressBar searchProgressBar;
    @BindView(R.id.rootLayout)
    LinearLayout rootLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private SearchAdapter searchAdapter;
    private Book bookDel;
    private List<Book> books;

    private LibrarianPresenter librarianPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        librarianPresenter = new LibrarianPresenterImpl(this);
        librarianPresenter.attachViewInteractor(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!edtSearch.getText().toString().isEmpty())
            librarianPresenter.search(edtSearch.getText().toString());
    }

    public void updateBook(Book book) {
        Bundle bundle = new Bundle();
        bundle.putString("book", new Gson().toJson(book));
        startActivity(UpdateBookActivity.class, bundle);
    }

    public void deleteBook(Book book) {
        this.bookDel = book;
        librarianPresenter.deleteBook(bookDel.getId());
    }

    @OnClick(R.id.btn_search)
    public void onSearchClick() {
        librarianPresenter.search(edtSearch.getText().toString());
    }

    @Override
    public void showProgress() {
        searchProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        searchProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onSuccess() {
        Bakery bakery = new Bakery(this);
        bakery.snackShort(rootLayout, "Book Deleted Successfully");
        books.remove(bookDel);
        searchAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResult(List<Book> books) {
        this.books = books;
        searchAdapter = new SearchAdapter(this, books);
        recycler_search.setLayoutManager(new LinearLayoutManager(this));
        recycler_search.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recycler_search.setItemAnimator(new DefaultItemAnimator());
        recycler_search.setAdapter(searchAdapter);

    }
}
