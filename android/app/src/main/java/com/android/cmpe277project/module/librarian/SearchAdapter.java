package com.android.cmpe277project.module.librarian;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.cmpe277project.R;
import com.android.cmpe277project.model.Book;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by parag on 12/7/17.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {


    private Context context;
    private List<Book> books;

    public SearchAdapter(Context context, List<Book> books) {
        this.context = context;
        this.books = books;
    }

    @Override
    public void onBindViewHolder(SearchViewHolder viewHolder, int position) {
        viewHolder.bindViews(position);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list_item, parent, false);
        return new SearchViewHolder(view);
    }


    public class SearchViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.book_name)
        TextView bookName;
        @BindView(R.id.btn_delete)
        Button btnDelete;
        @BindView(R.id.btn_update)
        Button btnUpdate;


        public SearchViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindViews(int position) {
            Book book = books.get(position);
            bookName.setText(book.getTitle());
        }

        @OnClick(R.id.btn_update)
        public void onBtnUpdateClick() {
            ((SearchActivity)context).updateBook(books.get(getAdapterPosition()));
        }

        @OnClick(R.id.btn_delete)
        public void onBtnDeleteClick() {
            ((SearchActivity)context).deleteBook(books.get(getAdapterPosition()));
        }
    }
}