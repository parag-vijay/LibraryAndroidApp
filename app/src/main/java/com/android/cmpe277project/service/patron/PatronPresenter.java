package com.android.cmpe277project.service.patron;

import com.android.cmpe277project.base.Presenter;
import com.android.cmpe277project.base.ViewInteractor;

/**
 * Created by aaditya on 12/4/17.
 */

public interface PatronPresenter extends Presenter<ViewInteractor> {

    void search(String query);

    void issueBook(String bookId);

    void renewBook(String bookId);

    void returnBook(String bookId);

}
