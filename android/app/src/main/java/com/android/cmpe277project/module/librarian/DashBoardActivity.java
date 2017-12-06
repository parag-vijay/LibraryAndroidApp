package com.android.cmpe277project.module.librarian;

import android.os.Bundle;
import android.view.View;

import com.android.cmpe277project.R;
import com.android.cmpe277project.base.BaseActivity;
import com.android.cmpe277project.module.cart.CartActivity;

import butterknife.OnClick;
import butterknife.Optional;

public class DashBoardActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        String type = bundle.getString("type");
        if (type.equals("librarian")) {
            setContentView(R.layout.activity_dash_board);
            return;
        }

        setContentView(R.layout.patron_main);
    }

    @Optional
    @OnClick({R.id.search, R.id.profile, R.id.manage, R.id.add})
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
        }
    }

    @Optional
    @OnClick({R.id.patronSearch, R.id.patronProfile, R.id.checkoutList, R.id.cart})
    public void onPatronViewClicked(View view) {
        switch (view.getId()) {
            case R.id.patronSearch:
                startActivity(SearchActivity.class, null);
                break;
            case R.id.patronProfile:
                break;
            case R.id.checkoutList:
                break;
            case R.id.cart:
                startActivity(CartActivity.class, null);
        }
    }
}
