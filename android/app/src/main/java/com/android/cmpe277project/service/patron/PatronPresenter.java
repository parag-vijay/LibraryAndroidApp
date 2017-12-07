package com.android.cmpe277project.service.patron;

import com.android.cmpe277project.base.Presenter;
import com.android.cmpe277project.base.ViewInteractor;
import com.android.cmpe277project.model.Cart;

import java.util.List;

/**
 * Created by aaditya on 12/4/17.
 */

public interface PatronPresenter extends Presenter<PatronViewInteractor> {

    void search(String query);

    void issueBook(List<Cart> cartList);

    void renewBook(List<Cart> cartList);

    void returnBook(List<Cart> cartList);

}
