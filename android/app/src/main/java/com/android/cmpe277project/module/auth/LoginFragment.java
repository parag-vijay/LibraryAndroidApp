package com.android.cmpe277project.module.auth;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.android.cmpe277project.R;
import com.android.cmpe277project.base.BaseFragment;
import com.android.cmpe277project.module.librarian.DashBoardActivity;
import com.android.cmpe277project.module.util.Bakery;
import com.android.cmpe277project.service.auth.AuthViewInteractor;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by aaditya on 12/4/17.
 */

public class LoginFragment extends BaseFragment{

    @BindView(R.id.edt_email)
    EditText edtEmail;
    @BindView(R.id.input_layout_email)
    TextInputLayout inputLayoutEmail;
    @BindView(R.id.edt_password)
    EditText edtPassword;
    @BindView(R.id.txt_forgot_password)
    TextView txtForgotPassword;

    private Bakery bakery;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signin, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bakery = new Bakery(getContext());
    }

    @OnTextChanged(R.id.edt_email)
    void verifyEmail(CharSequence email) {
        inputLayoutEmail.setErrorEnabled(false);

        if (email == null || email.toString().isEmpty()) {
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            inputLayoutEmail.setErrorEnabled(true);
            inputLayoutEmail.setError("Invalid Email");
            return;
        }
    }


    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        if (edtEmail.getText().toString().isEmpty()) {
            bakery.snackShort(getContentView(), "Email cannot be empty");
            return;
        }

        if (edtPassword.getText().toString().isEmpty() || edtPassword.getText().toString().length() < 6) {
            bakery.snackShort(getContentView(), "Password must be minimum 6 characters");
            return;
        }

        ((OnBoardActivity)getActivity()).login(edtEmail.getText().toString(), edtPassword.getText().toString());
    }
}
