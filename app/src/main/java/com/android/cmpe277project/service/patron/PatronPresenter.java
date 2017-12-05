package com.android.cmpe277project.service.patron;

import com.android.cmpe277project.base.Presenter;
import com.android.cmpe277project.base.ViewInteractor;

import java.util.List;

/**
 * Created by aaditya on 12/4/17.
 */

public interface PatronPresenter extends Presenter<ViewInteractor> {

    void search(String query);

    void issueBook(List<String> bookIds);

    void renewBook(List<String> bookIds);

    void returnBook(List<String> bookIds);

}
