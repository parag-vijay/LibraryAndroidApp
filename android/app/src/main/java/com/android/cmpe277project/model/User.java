package com.android.cmpe277project.model;

import java.util.List;

/**
 * Created by parag on 12/4/17.
 */

public class User {

    private String name;
    private String email;
    private String password;
    private int universityId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUniversityId() {
        return universityId;
    }

    public void setUniversityId(int universityId) {
        this.universityId = universityId;
    }

}
