package com.android.cmpe277project.module.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.cmpe277project.R;
import com.android.cmpe277project.base.BaseActivity;
import com.android.cmpe277project.model.Librarian;
import com.android.cmpe277project.model.User;
import com.android.cmpe277project.module.auth.OnBoardActivity;
import com.android.cmpe277project.module.cart.CartActivity;
import com.android.cmpe277project.module.librarian.AddBookActivity;
import com.android.cmpe277project.module.librarian.SearchActivity;
import com.android.cmpe277project.module.patron.IssuedBooksActivity;
import com.android.cmpe277project.module.patron.PatronSearchActivity;
import com.android.cmpe277project.util.UserPreference;

import butterknife.OnClick;
import butterknife.Optional;

public class DashBoardActivity extends BaseActivity {

    private UserPreference userPreference;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userPreference = new UserPreference(this);
        user = userPreference.readUser();

        Bundle bundle = getIntent().getExtras();
        String type = bundle.getString("type");
        if (type.equals("librarian")) {
            setContentView(R.layout.activity_dash_board);
            return;
        }

        setContentView(R.layout.patron_main);
    }

    @Optional
    @OnClick({R.id.search, R.id.profile, R.id.manage, R.id.add, R.id.logout})
    public void onLibrarianViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search:
                startActivity(SearchActivity.class, null);
                break;
            case R.id.profile:
                break;
            case R.id.manage:
                break;
            case R.id.add:
                startActivity(AddBookActivity.class, null);
                break;
            case R.id.logout:
                logout();
        }
    }

    @Optional
    @OnClick({R.id.patronSearch, R.id.patronProfile, R.id.checkoutList, R.id.cart_card, R.id.logout})
    public void onPatronViewClicked(View view) {
        switch (view.getId()) {
            case R.id.patronSearch:
                startActivity(PatronSearchActivity.class, null);
                break;
            case R.id.patronProfile:
                break;
            case R.id.checkoutList:
                startActivity(IssuedBooksActivity.class, null);
                break;
            case R.id.cart_card:
                startActivity(CartActivity.class, null);
                break;
            case R.id.logout:
                logout();
        }
    }

    private void logout() {
        userPreference.clearUser();
        Intent intent = new Intent(this, OnBoardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
