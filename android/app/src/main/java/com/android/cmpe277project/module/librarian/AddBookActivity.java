package com.android.cmpe277project.module.librarian;

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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddBookActivity extends BaseActivity implements LibrarianViewInteractor {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        ButterKnife.bind(this);

        userPreference = new UserPreference(this);
        user = userPreference.readUser();
        librarianPresenter = new LibrarianPresenterImpl(this);
        librarianPresenter.attachViewInteractor(this);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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

    @OnClick(R.id.btn_add)
    public void onViewClicked() {
        Book book = new Book();
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

        librarianPresenter.addBook(book);
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
        bakery.snackShort(rootLayout, "Book Added Successfully");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        },3000);
    }

    @Override
    public void onResult(List<Book> books) {

    }
}
