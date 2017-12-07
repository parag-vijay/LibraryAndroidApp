package com.android.cmpe277project.model;

import java.util.List;

/**
 * Created by aaditya on 12/4/17.
 */

public class Patron extends User {

    private List<Book> books;
    private long due;

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public long getDue() {
        return due;
    }

    public void setDue(long due) {
        this.due = due;
    }
}
