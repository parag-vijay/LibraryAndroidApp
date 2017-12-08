package com.android.cmpe277project.module.cart;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.cmpe277project.R;
import com.android.cmpe277project.base.BaseFragment;
import com.android.cmpe277project.model.Cart;
import com.android.cmpe277project.module.util.Bakery;
import com.android.cmpe277project.util.UserPreference;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by aaditya on 12/7/17.
 */

public class RenewFragment extends BaseFragment {
    @BindView(R.id.recycler_cart)
    RecyclerView recyclerCart;

    private UserPreference userPreference;
    private CartAdapter cartAdapter;
    private List<Cart> carts;

    Button btnIssue;
    Unbinder unbinder;
    private Bakery bakery;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_renew, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bakery = new Bakery(getContext());
        userPreference = new UserPreference(getContext());
        carts = userPreference.readCartRenew();

        carts.add(new Cart("java", "Java Concepts Ren"));
        carts.add(new Cart("python", "Hardway Pythton Ren"));
        cartAdapter = new CartAdapter(getContext(), carts);

        recyclerCart.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerCart.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerCart.setItemAnimator(new DefaultItemAnimator());
        recyclerCart.setAdapter(cartAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_issue)
    public void onViewClicked() {
        ((CartActivity)getActivity()).renewBook(carts);
    }

    public void deleteFromCart(Cart cart) {
        carts.remove(cart);
        cartAdapter.notifyDataSetChanged();
    }

    class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {


        private Context context;
        private List<Cart> books;

        public CartAdapter(Context context, List<Cart> books) {
            this.context = context;
            this.books = books;
        }

        @Override
        public CartAdapter.CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cart_item, parent, false);
            return new CartAdapter.CartViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CartAdapter.CartViewHolder viewHolder, int position) {
            viewHolder.bindViews(position);
        }

        @Override
        public int getItemCount() {
            return books.size();
        }



        public class CartViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.txt_title)
            TextView txtTitle;
            @BindView(R.id.position)
            TextView serial;

            public CartViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            void bindViews(int position) {
                txtTitle.setText(books.get(position).getTitle());
                serial.setText(String.valueOf(position + 1));
            }

            @OnClick(R.id.imageView)
            public void onViewClicked() {
                deleteFromCart(books.get(getAdapterPosition()));
                cartAdapter.notifyDataSetChanged();
                userPreference.saveToCartRenew(books);
            }
        }
    }
}
