package com.android.cmpe277project.module.cart;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.cmpe277project.R;
import com.android.cmpe277project.model.Cart;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by aaditya on 12/6/17.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {


    private Context context;
    private List<Cart> books;

    public CartAdapter(Context context, List<Cart> books) {
        this.context = context;
        this.books = books;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartViewHolder viewHolder, int position) {
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
            ((CartActivity)context).deleteFromCart(books.get(getAdapterPosition()));
        }
    }
}
