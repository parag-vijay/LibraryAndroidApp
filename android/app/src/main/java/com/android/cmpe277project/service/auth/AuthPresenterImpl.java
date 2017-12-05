package com.android.cmpe277project.service.auth;

import com.android.cmpe277project.base.BasePresenter;
import com.android.cmpe277project.model.User;
import com.android.cmpe277project.service.ApiModule;
import com.android.cmpe277project.service.Service;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Created by aaditya on 12/5/17.
 */

public class AuthPresenterImpl extends BasePresenter<AuthViewInteractor> implements AuthPresenter {

    private Service service;

    public AuthPresenterImpl() {
        service = ApiModule.getInstance().getService();
    }

    @Override
    public void signup(User user) {
        Observable<Response> observable = service.signUp(user);
        getViewInteractor().showProgress();

        new CompositeDisposable().add(observable
                                .observeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeWith(new DisposableObserver<Response>() {
                                    @Override
                                    public void onNext(@NonNull Response response) {
                                        if (response.code() == 201) {
                                            getViewInteractor().hideProgress();
                                            getViewInteractor().onRegister();
                                        }
                                    }

                                    @Override
                                    public void onError(@NonNull Throwable e) {
                                        e.printStackTrace();
                                        getViewInteractor().hideProgress();
                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                }));
    }

    @Override
    public void verifyAccount(String code) {

    }

    @Override
    public void login(String email, String password) {
        Observable<ResponseBody> observable = service.signIn(email,password);
        getViewInteractor().showProgress();

        new CompositeDisposable().add(observable
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ResponseBody>() {
                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

}
