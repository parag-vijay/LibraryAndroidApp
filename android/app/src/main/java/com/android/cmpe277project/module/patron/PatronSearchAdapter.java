package com.android.cmpe277project.module.patron;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.cmpe277project.R;
import com.android.cmpe277project.model.Book;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by parag on 12/7/17.
 */

public class PatronSearchAdapter  extends RecyclerView.Adapter<PatronSearchAdapter.PatronSearchViewHolder> {

    private Context context;
    private List<Book> books;

    public PatronSearchAdapter(Context context, List<Book> books) {
        this.context = context;
        this.books = books;
    }

    @Override
    public void onBindViewHolder(PatronSearchViewHolder viewHolder, int position) {
        viewHolder.bindViews(position);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    @Override
    public PatronSearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patron_search_item, parent, false);
        return new PatronSearchViewHolder(view);
    }

    public class PatronSearchViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.book_name)
        TextView bookName;

        @BindView(R.id.author_name)
        TextView authorName;


        public PatronSearchViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindViews(int position) {
            Book book = books.get(position);
            bookName.setText(book.getTitle());
            authorName.setText(book.getAuthor());
        }

        @OnClick(R.id.rl_pat_srch_itm)
        public void onItemClicked() {
            ((PatronSearchActivity) context).detailView(books.get(getAdapterPosition()));
        }
    }
}
