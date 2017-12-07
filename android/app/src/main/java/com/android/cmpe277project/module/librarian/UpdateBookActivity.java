package com.android.cmpe277project.module.librarian;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.android.cmpe277project.R;
import com.android.cmpe277project.base.BaseActivity;
import com.android.cmpe277project.model.Book;
import com.android.cmpe277project.model.User;
import com.android.cmpe277project.module.util.Bakery;
import com.android.cmpe277project.service.librarian.LibrarianPresenter;
import com.android.cmpe277project.service.librarian.LibrarianPresenterImpl;
import com.android.cmpe277project.service.librarian.LibrarianViewInteractor;
import com.android.cmpe277project.util.UserPreference;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateBookActivity extends BaseActivity implements LibrarianViewInteractor {

    @BindView(R.id.edt_title)
    EditText edtTitle;
    @BindView(R.id.edt_author)
    EditText edtAuthor;
    @BindView(R.id.edt_callNumber)
    EditText edtCallNumber;
    @BindView(R.id.edt_publisher)
    EditText edtPublisher;
    @BindView(R.id.edt_publish_year)
    EditText edtPublishYear;
    @BindView(R.id.edt_location)
    EditText edtLocation;
    @BindView(R.id.edt_copies)
    EditText edtCopies;
    @BindView(R.id.edt_keyword)
    EditText edtKeyword;
    @BindView(R.id.onboard_progressBar)
    ProgressBar onboardProgressBar;
    @BindView(R.id.rootLayout)
    RelativeLayout rootLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private LibrarianPresenter librarianPresenter;
    private User user;
    private UserPreference userPreference;
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_book);
        ButterKnife.bind(this);

        Bundle b = getIntent().getExtras();
        String bookString = b.getString("book");

        book = new Gson().fromJson(bookString, Book.class);
        if (book != null) {
            setBookData(book);
        }

        userPreference = new UserPreference(this);
        user = userPreference.readUser();
        librarianPresenter = new LibrarianPresenterImpl(this);
        librarianPresenter.attachViewInteractor(this);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public void setBookData(Book book) {
        edtAuthor.setText(book.getAuthor());
        edtCallNumber.setText(book.getCallNumber());
        edtTitle.setText(book.getTitle());
        edtCopies.setText(String.valueOf(book.getCopies()));
        edtKeyword.setText(book.getKeywords());
        edtPublisher.setText(book.getPublisher());
        edtPublishYear.setText(String.valueOf(book.getYearOfPub()));
        edtLocation.setText(book.getLocation());
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

    @OnClick(R.id.btn_update)
    public void onViewClicked() {
        book.setAuthor(edtAuthor.getText().toString());
        book.setCopies(Integer.parseInt(edtCopies.getText().toString()));
        book.setCallNumber(edtCallNumber.getText().toString());
        book.setImage("");
        book.setTitle(edtTitle.getText().toString());
        book.setPublisher(edtPublisher.getText().toString());
        book.setYearOfPub(Integer.parseInt(edtPublishYear.getText().toString()));
        book.setLocation(edtLocation.getText().toString());
        book.setStatus(Book.Status.AVAILABLE);
        book.setKeywords(edtKeyword.getText().toString());
        book.setOwnerId(user.getEmail());

        librarianPresenter.updateBook(book);
    }

    @Override
    public void showProgress() {
        onboardProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        onboardProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onSuccess() {
        Bakery bakery = new Bakery(this);
        bakery.snackShort(rootLayout, "Book Updated Successfully");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = getIntent();
                finish();
            }
        },3000);
    }

    @Override
    public void onResult(List<Book> books) {

    }
}
