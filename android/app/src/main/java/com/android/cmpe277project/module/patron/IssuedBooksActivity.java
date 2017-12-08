package com.android.cmpe277project.module.patron;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.cmpe277project.R;
import com.android.cmpe277project.base.BaseActivity;
import com.android.cmpe277project.model.Book;
import com.android.cmpe277project.util.UserPreference;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by parag on 12/7/17.
 */

public class IssuedBooksActivity extends BaseActivity {
    @BindView(R.id.recycler_issued_book)
    RecyclerView recyclerIssued;

    private UserPreference userPreference;
    private IssuedBooksAdapter issuedBooksAdapter;

    private List<Book>  issuedBooks;

    @Override
    protected void  onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patron_issued_books_recycler);
        ButterKnife.bind(this);

        userPreference = new UserPreference(this);
        issuedBooks = userPreference.readUser().getBooks();

        issuedBooksAdapter = new IssuedBooksAdapter(this, issuedBooks);

        recyclerIssued.setLayoutManager(new LinearLayoutManager(this));
        recyclerIssued.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerIssued.setItemAnimator(new DefaultItemAnimator());
        recyclerIssued.setAdapter(issuedBooksAdapter);

    }

}
