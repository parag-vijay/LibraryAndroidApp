package com.android.cmpe277project.service.auth;

import com.android.cmpe277project.base.BasePresenter;
import com.android.cmpe277project.model.Librarian;
import com.android.cmpe277project.model.Patron;
import com.android.cmpe277project.model.User;
import com.android.cmpe277project.service.ApiModule;
import com.android.cmpe277project.service.Service;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by aaditya on 12/5/17.
 */

public class AuthPresenterImpl extends BasePresenter<AuthViewInteractor> implements AuthPresenter {

    private Service service;
    private JsonObject raw_response;

    public AuthPresenterImpl() {
        service = ApiModule.getInstance().getService();
    }

    @Override
    public void signUp(User user) {
        Observable<Response<ResponseBody>> observable = service.signUp(user);
        getViewInteractor().showProgress();

        new CompositeDisposable().add(observable
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeWith(new DisposableObserver<Response<ResponseBody>>() {
                                    @Override
                                    public void onNext(@NonNull Response<ResponseBody> response) {

                                        getViewInteractor().hideProgress();
                                        getViewInteractor().onRegister(null);

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
    public void verifyAccount(String code, String email) {
        Observable<ResponseBody> observable = service.verify(code, email);
        getViewInteractor().showProgress();

        new CompositeDisposable().add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ResponseBody>() {
                    @Override
                    public void onNext(@NonNull ResponseBody response) {
                        User user = null;
                        try {
                            raw_response = (JsonObject) new JsonParser().parse(response.string());

                            user = new Gson().fromJson(raw_response.toString(), User.class);
                            if (user.getEmail().contains("sjsu.edu")) {
                                user = new Gson().fromJson(raw_response.toString(), Librarian.class);
                            } else {
                                user = new Gson().fromJson(raw_response.toString(), Patron.class);
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        getViewInteractor().onVerified(user);
                        getViewInteractor().hideProgress();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        getViewInteractor().hideProgress();
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    @Override
    public void login(String email, String password) {
        Observable<ResponseBody> observable = service.signIn(email,password);
        getViewInteractor().showProgress();

        new CompositeDisposable().add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ResponseBody>() {
                    @Override
                    public void onNext(@NonNull ResponseBody response) {
                        User user = null;
                        try {
                            raw_response = (JsonObject) new JsonParser().parse(response.string());

                            user = new Gson().fromJson(raw_response.toString(), User.class);
                            if (user.getEmail().contains("sjsu.edu")) {
                                user = new Gson().fromJson(raw_response.toString(), Librarian.class);
                            } else {
                                user = new Gson().fromJson(raw_response.toString(), Patron.class);
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        getViewInteractor().onLogin(user);
                        getViewInteractor().hideProgress();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        getViewInteractor().hideProgress();
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

}
