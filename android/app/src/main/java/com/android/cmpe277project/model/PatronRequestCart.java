package com.android.cmpe277project.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aaditya on 12/7/17.
 */

public class PatronRequestCart {

    private String email;
    @SerializedName("bookIdArray")
    private List<Cart> carts;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Cart> getCarts() {
        return carts;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }
}
