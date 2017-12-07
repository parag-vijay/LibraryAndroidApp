package com.android.cmpe277project.service.librarian;

import android.content.Context;

import com.android.cmpe277project.base.BasePresenter;
import com.android.cmpe277project.model.Book;
import com.android.cmpe277project.model.User;
import com.android.cmpe277project.service.ApiModule;
import com.android.cmpe277project.service.Service;
import com.android.cmpe277project.util.UserPreference;
import com.google.gson.JsonObject;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Created by aaditya on 12/6/17.
 */

public class LibrarianPresenterImpl extends BasePresenter<LibrarianViewInteractor> implements LibrarianPresenter {

    private Service service;
    private JsonObject raw_response;
    private UserPreference userPreference;
    private User user;

    public LibrarianPresenterImpl(Context context) {
        service = ApiModule.getInstance().getService();
        userPreference = new UserPreference(context);

        user = userPreference.readUser();
    }

    @Override
    public void search(String query) {
        Observable<Response<List<Book>>> observable = service.searchBook(query,user.getEmail());

        getViewInteractor().showProgress();
        new CompositeDisposable().add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Response<List<Book>>>() {
                    @Override
                    public void onNext(@NonNull Response<List<Book>> responseBodyResponse) {
                        getViewInteractor().hideProgress();
                        getViewInteractor().onResult(responseBodyResponse.body());
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
    public void addBook(Book book) {
        Observable<Response<ResponseBody>> observable = service.addBook(book,user.getEmail());

        getViewInteractor().showProgress();
        new CompositeDisposable().add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Response<ResponseBody>>() {
                    @Override
                    public void onNext(@NonNull Response<ResponseBody> responseBodyResponse) {
                        getViewInteractor().hideProgress();
                        getViewInteractor().onSuccess();
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
    public void updateBook(Book book) {
        Observable<Response<ResponseBody>> observable = service.updateBook(book,user.getEmail());

        getViewInteractor().showProgress();
        new CompositeDisposable().add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Response<ResponseBody>>() {
                    @Override
                    public void onNext(@NonNull Response<ResponseBody> responseBodyResponse) {
                        getViewInteractor().hideProgress();
                        getViewInteractor().onSuccess();
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
    public void deleteBook(String bookId) {
        Observable<Response<ResponseBody>> observable = service.deleteBook(bookId,user.getEmail());

        getViewInteractor().showProgress();
        new CompositeDisposable().add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Response<ResponseBody>>() {
                    @Override
                    public void onNext(@NonNull Response<ResponseBody> responseBodyResponse) {
                        getViewInteractor().hideProgress();
                        getViewInteractor().onSuccess();
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
