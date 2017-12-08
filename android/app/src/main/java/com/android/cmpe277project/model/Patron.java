package com.android.cmpe277project.model;

import java.util.List;

/**
 * Created by aaditya on 12/4/17.
 */

public class Patron extends User {

    private long due;


    public long getDue() {
        return due;
    }

    public void setDue(long due) {
        this.due = due;
    }
}
