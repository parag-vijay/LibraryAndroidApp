package com.android.cmpe277project.module.auth;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.android.cmpe277project.R;
import com.android.cmpe277project.base.BaseActivity;
import com.android.cmpe277project.model.Librarian;
import com.android.cmpe277project.model.User;
import com.android.cmpe277project.module.librarian.DashBoardActivity;
import com.android.cmpe277project.service.auth.AuthPresenter;
import com.android.cmpe277project.service.auth.AuthPresenterImpl;
import com.android.cmpe277project.service.auth.AuthViewInteractor;
import com.android.cmpe277project.util.UserPreference;

import butterknife.BindView;
import butterknife.OnPageChange;

public class OnBoardActivity extends BaseActivity implements AuthViewInteractor{

    @BindView(R.id.home_tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.home_viewpager)
    ViewPager viewpager;
    @BindView(R.id.onboard_progressBar)
    ProgressBar progressBar;
    @BindView(R.id.rootLayout)
    RelativeLayout rootLayout;

    private static int ONBOARD_PAGE_NUM = 0;
    private UserPreference userPreference;
    private AuthPresenter authPresenter;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userPreference = new UserPreference(this);

        user = userPreference.readUser();

        /*Bundle b = new Bundle();
        b.putString("type", "patron");
        startActivity(DashBoardActivity.class, b);
        finish();
        return;*/
        if( user != null) {
            Bundle b = new Bundle();

            if (user.getEmail().contains("sjsu.edu"))
                b.putString("type", "librarian");
            else
                b.putString("type", "patron");

            startActivity(DashBoardActivity.class, b);
            finish();
            return;
        }

        authPresenter = new AuthPresenterImpl();
        authPresenter.attachViewInteractor(this);

        loadFragments();
    }

    @OnPageChange(R.id.home_viewpager)
    public void onPageSelected(int position){
        ONBOARD_PAGE_NUM = position;
    }

    private void loadFragments() {
        viewpager.setAdapter(new LoginFragmentPagerAdpater(getSupportFragmentManager(), this));
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewpager);
            }
        });
        viewpager.setCurrentItem(ONBOARD_PAGE_NUM);
    }

    public void login(String email, String password) {
        authPresenter.login(email, password);
    }

    public void register(User user) {
        this.user = user;
        authPresenter.signUp(user);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLogin(User user) {
        userPreference.saveUser(user);
        Bundle b = new Bundle();

        if (user instanceof Librarian)
            b.putString("type", "librarian");
        else
            b.putString("type", "patron");

        startActivity(DashBoardActivity.class, b);
        finish();
    }

    @Override
    public void onRegister(final String email) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText edittext = new EditText(this);
        alert.setMessage("Enter Your code");
        alert.setTitle("Verify Email");
        alert.setCancelable(false);

        alert.setView(edittext);

        alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //What ever you want to do with the value

                authPresenter.verifyAccount(edittext.getText().toString(),user.getEmail()   );
            }
        });

        alert.show();
    }

    @Override
    public void onVerified(User user) {
        userPreference.saveUser(user);
        if( user != null) {
            Bundle b = new Bundle();

            if (user instanceof Librarian)
                b.putString("type", "librarian");
            else
                b.putString("type", "patron");

            startActivity(DashBoardActivity.class, b);
            finish();
            return;
        }
    }
}
