package com.android.cmpe277project.module.auth;

import android.os.Bundle;
import android.support.v7.widget.CardView;

import com.android.cmpe277project.R;
import com.android.cmpe277project.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by parag on 12/5/17.
 */

public class LibrarianMainActivity extends BaseActivity {

    @BindView(R.id.search_card)
    CardView searchCard;

    @BindView(R.id.profile_card)
    CardView profileCard;

    @BindView(R.id.managed_books_card)
    CardView managedBooksCard;

    @BindView(R.id.add_book_card)
    CardView addBookCard;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.librarian_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.search_card)
    public void onSearchClick(){

    }

    @OnClick(R.id.profile_card)
    public void onProfileClick(){

    }

    @OnClick(R.id.managed_books_card)
    public void onManagedClick(){

    }

    @OnClick(R.id.add_book_card)
    public void onAddClick(){

    }
}
