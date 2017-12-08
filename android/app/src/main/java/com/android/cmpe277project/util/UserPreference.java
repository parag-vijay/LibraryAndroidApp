package com.android.cmpe277project.util;

import android.content.Context;

import com.android.cmpe277project.model.Book;
import com.android.cmpe277project.model.Cart;
import com.android.cmpe277project.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by aaditya on 12/6/17.
 */

public class UserPreference {

    private PreferenceUtil preferenceUtil;
    private Gson gson = new Gson();

    private static final String USER = "_user";
    private static final String CART_ISSUE = "_cart_issue";
    private static final String CART_RENEW = "_cart_renew";
    private static final String CART_RETURN = "_cart_return";
    private static final String ISSUED_BOOK = "_issuedbook";

    public UserPreference(Context context) {
        preferenceUtil = new PreferenceUtil(context);
    }

    public void saveUser(User user) {
        preferenceUtil.save(USER, user);
    }

    public User readUser() {
        return (User) preferenceUtil.read(USER, User.class);
    }

    public void saveToCartIssue(List<Cart>  books) {
        preferenceUtil.save(CART_ISSUE, gson.toJson(books));
    }

    public List<Cart> readCartIssue(){
        String json = preferenceUtil.readString(CART_ISSUE,"[]");

        Type listType = new TypeToken<List<Cart>>() {
        }.getType();

        return gson.fromJson(json, listType);
    }

    public void saveToCartReturn(List<Cart>  books) {
        preferenceUtil.save(CART_RETURN, gson.toJson(books));
    }

    public List<Cart> readCartReturn(){
        String json = preferenceUtil.readString(CART_RETURN,"[]");

        Type listType = new TypeToken<List<Cart>>() {
        }.getType();

        return gson.fromJson(json, listType);
    }

    public void saveToCartRenew(List<Cart>  books) {
        preferenceUtil.save(CART_RENEW, gson.toJson(books));
    }

    public List<Cart> readCartRenew(){
        String json = preferenceUtil.readString(CART_RENEW,"[]");

        Type listType = new TypeToken<List<Cart>>() {
        }.getType();

        return gson.fromJson(json, listType);
    }

    public List<Book> readIssuedBooks(){
        String json = preferenceUtil.readString(ISSUED_BOOK,"[]");

        Type listType = new TypeToken<List<Book>>(){

        }.getType();

        return gson.fromJson(json, listType);
    }


    public void clearUser() {
        preferenceUtil.clear();
    }
}
