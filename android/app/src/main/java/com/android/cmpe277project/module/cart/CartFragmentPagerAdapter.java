package com.android.cmpe277project.module.cart;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by aaditya on 12/7/17.
 */

public class CartFragmentPagerAdapter extends FragmentPagerAdapter {

    private Activity activity;

    public CartFragmentPagerAdapter(FragmentManager fm, Activity activity) {
        super(fm);
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new IssueFragment();
            case 1:
                return new ReturnFragment();
            case 2:
                return new RenewFragment();
        }

        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Issue";
            case 1:
                return "Return";
            case 2:
                return "Renew";
        }

        return null;
    }

}
