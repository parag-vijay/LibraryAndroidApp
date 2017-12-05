package com.android.cmpe277project.service.auth;

import com.android.cmpe277project.base.Presenter;
import com.android.cmpe277project.base.ViewInteractor;
import com.android.cmpe277project.model.User;

/**
 * Created by parag on 12/4/17.
 */

public interface AuthPresenter extends Presenter<ViewInteractor> {

    void signup(User user);

    void verifyAccout(String code);

    void login(String email, String password);

    void logout(String email);
}
