package com.android.cmpe277project.module.patron;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.cmpe277project.R;
import com.android.cmpe277project.base.BaseActivity;
import com.android.cmpe277project.model.Book;
import com.android.cmpe277project.model.Cart;
import com.android.cmpe277project.util.UserPreference;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by parag on 12/7/17.
 */

public class BookDetailActivity extends BaseActivity{

    @BindView(R.id.imgbut_issue)
    ImageButton issueButton;

    private UserPreference userPreference;
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_detail_view);
        ButterKnife.bind(this);

        Bundle b = getIntent().getExtras();
        String bookString = b.getString("book");

        book = new Gson().fromJson(bookString, Book.class);


    }

    @OnClick(R.id.imgbut_issue)
    public void onIssueButtonClicked(){
        List<Cart> cartList = userPreference.readCartIssue();
        if (cartList.size() <= 3){
            Cart cart = new Cart();
            cart.setBookID(book.getId());
            cart.setTitle(book.getTitle());
            cartList.add(cart);

            Toast.makeText(this, "Book Added to cart", Toast.LENGTH_SHORT).show();
            userPreference.saveToCartIssue(cartList);

        }else {
            Toast.makeText(this, "Cart Limit Reached", Toast.LENGTH_SHORT).show();
        }

    }
}
