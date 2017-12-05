package com.android.cmpe277project.module.auth;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.cmpe277project.R;
import com.android.cmpe277project.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

/**
 * Created by aaditya on 12/4/17.
 */

public class SignUpFragment extends BaseFragment {

    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.input_layout_email)
    TextInputLayout inputLayoutEmail;
    @BindView(R.id.edt_unv_id)
    EditText edtUnvId;
    @BindView(R.id.edt_email)
    EditText edtEmail;
    @BindView(R.id.edt_password)
    EditText edtPassword;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
