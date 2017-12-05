package com.android.cmpe277project.base;

/**
 * @author Aaditya deowanshi
 */
public interface Presenter<T extends ViewInteractor> {

    void attachViewInteractor(T viewInteractor);

    void detachViewInteractor();

}
