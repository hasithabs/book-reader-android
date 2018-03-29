package com.example.enters.book_reader.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.enters.book_reader.R;
import com.example.enters.book_reader.book.Book;

import java.util.ArrayList;

/**
 * Created by EnTeRs on 3/26/2018.
 */

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    ArrayList<Book> bookList = new ArrayList<>();
    Context bookContext;

    public BookAdapter(Context context, ArrayList<Book> arrayList) {
        this.bookContext = context;
        this.bookList = arrayList;
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View bookView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout, null);
        BookViewHolder bookViewHolder = new BookViewHolder(bookView);
        return bookViewHolder;
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.txtBookTitle.setText(book.getTitle());
        holder.txtBookAuthor.setText(book.getAuthor());
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        public TextView txtBookTitle;
        public TextView txtBookAuthor;

        public BookViewHolder(View bookItemView) {
            super(bookItemView);
            txtBookTitle = bookItemView.findViewById(R.id.textViewTitle);
            txtBookAuthor = bookItemView.findViewById(R.id.textViewShortDesc);
        }
    }

    public void setFilter(ArrayList<Book> searchList){
        bookList = new ArrayList<>();
        bookList.addAll(searchList);
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
}
