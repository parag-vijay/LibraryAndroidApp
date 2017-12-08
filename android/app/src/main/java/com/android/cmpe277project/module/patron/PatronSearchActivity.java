package com.android.cmpe277project.module.patron;

import android.os.Bundle;
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
import com.android.cmpe277project.service.patron.PatronPresenter;
import com.android.cmpe277project.service.patron.PatronPresenterImpl;
import com.android.cmpe277project.service.patron.PatronViewInteractor;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by parag on 12/7/17.
 */

public class PatronSearchActivity extends BaseActivity implements PatronViewInteractor {



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
    private PatronSearchAdapter patronSearchAdapter;
    private List<Book> books;

    private PatronPresenter patronPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        patronPresenter = new PatronPresenterImpl(this);
        patronPresenter.attachViewInteractor(this);

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
            patronPresenter.search(edtSearch.getText().toString());
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
    public void onSuccess(String msg) {

    }

    @Override
    public void onResult(List<Book> books) {

            this.books = books;
            patronSearchAdapter = new PatronSearchAdapter(this, books);
            recycler_search.setLayoutManager(new LinearLayoutManager(this));
            recycler_search.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
            recycler_search.setItemAnimator(new DefaultItemAnimator());
            recycler_search.setAdapter(patronSearchAdapter);

        }

    public void detailView(Book book) {
        Bundle bundle = new Bundle();
        bundle.putString("book", new Gson().toJson(book));
        startActivity(BookDetailActivity.class, bundle);
    }
}
