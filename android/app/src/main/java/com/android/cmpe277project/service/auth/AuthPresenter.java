package com.android.cmpe277project.service.auth;

import com.android.cmpe277project.base.Presenter;
import com.android.cmpe277project.base.ViewInteractor;
import com.android.cmpe277project.model.User;

/**
 * Created by parag on 12/4/17.
 */

public interface AuthPresenter extends Presenter<AuthViewInteractor> {

    void signUp(User user);

    void verifyAccount(String code, String email);

    void login(String email, String password);

}
