package com.android.cmpe277project.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by parag on 12/4/17.
 */

public class Librarian extends User {

    private List<Book> bookList;

    Librarian(){
        bookList = new ArrayList<Book>();
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }
}
