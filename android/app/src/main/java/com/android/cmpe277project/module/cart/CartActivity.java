package com.android.cmpe277project.module.cart;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.cmpe277project.R;
import com.android.cmpe277project.base.BaseActivity;
import com.android.cmpe277project.model.Cart;
import com.android.cmpe277project.util.UserPreference;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CartActivity extends BaseActivity {

    @BindView(R.id.recycler_cart)
    RecyclerView recyclerCart;

    private UserPreference userPreference;
    private CartAdapter cartAdapter;
    private List<Cart> carts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);

        userPreference = new UserPreference(this);
        carts = userPreference.readCart();

        carts.add(new Cart("java", "Java Concepts"));
        carts.add(new Cart("python", "Hardway Pythton"));
        cartAdapter = new CartAdapter(this, carts);

        recyclerCart.setLayoutManager(new LinearLayoutManager(this));
        recyclerCart.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerCart.setItemAnimator(new DefaultItemAnimator());
        recyclerCart.setAdapter(cartAdapter);
    }

    @OnClick(R.id.btn_issue)
    public void onViewClicked() {

    }

    public void deleteFromCart(Cart cart) {
        carts.remove(cart);
        cartAdapter.notifyDataSetChanged();
    }
}
