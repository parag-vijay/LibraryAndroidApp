package com.android.cmpe277project.service.auth;

import com.android.cmpe277project.base.ViewInteractor;
import com.android.cmpe277project.model.User;

/**
 * Created by aaditya on 12/5/17.
 */

public interface AuthViewInteractor extends ViewInteractor {

    void showProgress();

    void hideProgress();

    void onLogin(User user);

    void onRegister();

    void onVerified(User user);
}
