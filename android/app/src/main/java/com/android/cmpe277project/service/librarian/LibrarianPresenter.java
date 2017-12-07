package com.android.cmpe277project.service.librarian;

import com.android.cmpe277project.base.Presenter;
import com.android.cmpe277project.base.ViewInteractor;
import com.android.cmpe277project.model.Book;

/**
 * Created by parag on 12/4/17.
 */

public interface LibrarianPresenter extends Presenter<LibrarianViewInteractor> {

    void search(String query);

    void addBook(Book book);

    void updateBook(Book book);

    void deleteBook(String bookId);

}
