package com.android.cmpe277project.model;

/**
 * Created by aaditya on 12/6/17.
 */

public class Cart {

    private String bookID;
    private String title;

    public Cart() {

    }

    public Cart(String bookID, String title) {
        this.bookID = bookID;
        this.title = title;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
