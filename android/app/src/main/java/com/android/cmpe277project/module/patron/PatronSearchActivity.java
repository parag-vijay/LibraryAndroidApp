package com.android.cmpe277project.module.patron;

import android.os.Bundle;

import com.android.cmpe277project.base.BaseActivity;
import com.android.cmpe277project.model.Book;
import com.android.cmpe277project.service.patron.PatronPresenter;
import com.android.cmpe277project.service.patron.PatronViewInteractor;

import java.util.List;

/**
 * Created by parag on 12/7/17.
 */

public class PatronSearchActivity extends BaseActivity implements PatronViewInteractor {


    private PatronSearchAdapter patronSearchAdapter;
    private List<Book> books;

    private PatronPresenter patronPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void onSuccess(String msg) {

    }

    @Override
    public void onResult(List<Book> books) {

    }
}
