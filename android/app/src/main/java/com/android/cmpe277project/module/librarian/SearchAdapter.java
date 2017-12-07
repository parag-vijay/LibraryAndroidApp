package com.android.cmpe277project.module.librarian;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerViewAccessibilityDelegate;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.cmpe277project.R;
import com.android.cmpe277project.model.Book;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by parag on 12/7/17.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private Context context;
    private List<Book> books;

    public SearchAdapter(Context context, List<Book> books){
        this.context = context;
        this.books = books;
    }

    @Override
    public void onBindViewHolder(SearchViewHolder viewHolder, int position){
        viewHolder.bindViews(position);
    }

    @Override
    public int getItemCount(){
        return books.size();
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list_item, parent, false);
        return new SearchViewHolder(view);
    }


    public class SearchViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.img_book)
        ImageView book_image;

        @BindView(R.id.tv_book_name)
        TextView book_title;

        @BindView(R.id.tv_author_name)
        TextView author_name;

        @BindView(R.id.tv_avail_lable)
        TextView availability;


        public SearchViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bindViews(int position){

            Book book = books.get(position);
//            book_image.setImageResource(book.getImage());
            book_title.setText(book.getTitle());
            author_name.setText(book.getAuthor());
//            availability.append();

        }
    }
}
