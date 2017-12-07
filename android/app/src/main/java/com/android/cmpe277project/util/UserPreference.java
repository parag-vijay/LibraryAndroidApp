package com.android.cmpe277project.util;

import android.content.Context;

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
    private static final String CART = "_cart";

    public UserPreference(Context context) {
        preferenceUtil = new PreferenceUtil(context);
    }

    public void saveUser(User user) {
        preferenceUtil.save(USER, user);
    }

    public User readUser() {
        return (User) preferenceUtil.read(USER, User.class);
    }

    public void saveToCart(List<Cart>  books) {
        preferenceUtil.save(CART, gson.toJson(books));
    }

    public List<Cart> readCart(){
        String json = preferenceUtil.readString(CART,"[]");

        Type listType = new TypeToken<List<Cart>>() {
        }.getType();

        return gson.fromJson(json, listType);
    }

    public void clearUser() {
        preferenceUtil.clear();
    }
}
