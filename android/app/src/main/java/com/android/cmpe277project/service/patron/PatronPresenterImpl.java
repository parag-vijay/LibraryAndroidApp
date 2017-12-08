package com.android.cmpe277project.service.patron;

import android.content.Context;

import com.android.cmpe277project.base.BasePresenter;
import com.android.cmpe277project.model.Book;
import com.android.cmpe277project.model.Cart;
import com.android.cmpe277project.model.PatronRequestCart;
import com.android.cmpe277project.model.User;
import com.android.cmpe277project.service.ApiModule;
import com.android.cmpe277project.service.Service;
import com.android.cmpe277project.util.UserPreference;

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
 * Created by aaditya on 12/7/17.
 */

public class PatronPresenterImpl extends BasePresenter<PatronViewInteractor> implements PatronPresenter {

    private Service service;
    private UserPreference userPreference;
    private User user;

    public PatronPresenterImpl(Context context) {
        service = ApiModule.getInstance().getService();
        userPreference = new UserPreference(context);

        user = userPreference.readUser();
    }

    @Override
    public void search(String query) {
        Observable<Response<List<Book>>> observable = service.searchBookPatron(query,user.getEmail());

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
    public void issueBook(List<Cart> cartList) {
        PatronRequestCart requestCart = new PatronRequestCart();
        requestCart.setCarts(cartList);
        requestCart.setEmail(user.getEmail());

        Observable<Response<ResponseBody>> observable = service.issueBooks(requestCart);

        getViewInteractor().showProgress();
        new CompositeDisposable().add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Response<ResponseBody>>() {
                    @Override
                    public void onNext(@NonNull Response<ResponseBody> responseBodyResponse) {
                        getViewInteractor().hideProgress();
                        getViewInteractor().onSuccess("Successfully Issued");
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
    public void renewBook(List<Cart> cartList) {
        PatronRequestCart requestCart = new PatronRequestCart();
        requestCart.setCarts(cartList);
        requestCart.setEmail(user.getEmail());

        Observable<Response<ResponseBody>> observable = service.returnBooks(requestCart);

        getViewInteractor().showProgress();
        new CompositeDisposable().add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Response<ResponseBody>>() {
                    @Override
                    public void onNext(@NonNull Response<ResponseBody> responseBodyResponse) {
                        getViewInteractor().hideProgress();
                        getViewInteractor().onSuccess("Successfully Renewed");
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
    public void returnBook(List<Cart> cartList) {
        PatronRequestCart requestCart = new PatronRequestCart();
        requestCart.setCarts(cartList);
        requestCart.setEmail(user.getEmail());

        Observable<Response<ResponseBody>> observable = service.returnBooks(requestCart);

        getViewInteractor().showProgress();
        new CompositeDisposable().add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Response<ResponseBody>>() {
                    @Override
                    public void onNext(@NonNull Response<ResponseBody> responseBodyResponse) {
                        getViewInteractor().hideProgress();
                        getViewInteractor().onSuccess("Successfully Returned");
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
