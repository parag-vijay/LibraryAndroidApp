package com.android.cmpe277project.module.patron;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.cmpe277project.R;
import com.android.cmpe277project.model.Book;
import com.android.cmpe277project.model.Cart;
import com.android.cmpe277project.module.util.Bakery;
import com.android.cmpe277project.util.UserPreference;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by parag on 12/7/17.
 */

public class IssuedBooksAdapter extends RecyclerView.Adapter<IssuedBooksAdapter.IssuedBookViewHolder> {

    private Context context;
    private List<Book> books;
    private UserPreference userPreference;
    private Bakery bakery;

    public IssuedBooksAdapter(Context context, List<Book> books) {
        this.context = context;
        this.books = books;
        userPreference = new UserPreference(context);
    }

    @Override
    public IssuedBookViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patron_book_item_view, parent, false);
        return new IssuedBookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IssuedBookViewHolder viewHolder, int position){
        viewHolder.bindViews(position);
    }

    @Override
    public int getItemCount(){
        return books.size();
    }

    public class IssuedBookViewHolder  extends RecyclerView.ViewHolder{

        @BindView(R.id.issued_book_name)
        TextView bookTitle;

        @BindView(R.id.issue_date)
        TextView issueDate;

        @BindView(R.id.due_date)
        TextView dueDate;

        public IssuedBookViewHolder(View itemView){
                super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindViews(int position){
            Book book = books.get(position);

            bookTitle.setText(book.getTitle());

            issueDate.setText(book.getIssueDate());

            dueDate.setText(book.getDueDate());
        }

        @OnClick(R.id.btn_renew)
        public void onRenewClick() {
            List<Cart> cartList = userPreference.readCartRenew();
            if (cartList.size() <= 3){
                Cart cart = new Cart();
                cart.setBookID(books.get(getAdapterPosition()).getId());
                cart.setTitle(books.get(getAdapterPosition()).getTitle());
                cartList.add(cart);

                Toast.makeText(context, "Book Added to cart", Toast.LENGTH_SHORT).show();
                userPreference.saveToCartRenew(cartList);

            }else {
                Toast.makeText(context, "Cart Limit Reached", Toast.LENGTH_SHORT).show();
            }

        }

        @OnClick(R.id.btn_return)
        public void onReturnClick() {
            List<Cart> cartList = userPreference.readCartReturn();
            if (cartList.size() <= 9){
                Cart cart = new Cart();
                cart.setBookID(books.get(getAdapterPosition()).getId());
                cart.setTitle(books.get(getAdapterPosition()).getTitle());
                cartList.add(cart);

                Toast.makeText(context, "Book Added to cart", Toast.LENGTH_SHORT).show();
                userPreference.saveToCartReturn(cartList);

            }else {
                Toast.makeText(context, "Cart Limit Reached", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
