package com.android.cmpe277project.service.librarian;

import com.android.cmpe277project.base.ViewInteractor;
import com.android.cmpe277project.model.Book;

import java.util.List;

/**
 * Created by aaditya on 12/6/17.
 */

public interface LibrarianViewInteractor extends ViewInteractor {

    void showProgress();

    void hideProgress();

    void onSuccess();

    void onResult(List<Book> books);
}
