package com.example.enters.book_reader.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.enters.book_reader.Helper.DatabaseHelper;
import com.example.enters.book_reader.Helper.ImageHelper;
import com.example.enters.book_reader.R;
import com.example.enters.book_reader.UI.FavoriteActivity;
import com.example.enters.book_reader.UI.ReaderActivity;
import com.example.enters.book_reader.book.Book;

import java.util.ArrayList;

/**
 * Created by EnTeRs on 3/26/2018.
 */

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private static final String TAG = BookAdapter.class.getName();

    static ArrayList<Book> bookList = new ArrayList<>();
    static Context bookContext;
    DatabaseHelper bookDB;

    public BookAdapter(Context context, ArrayList<Book> arrayList) {
        this.bookContext = context;
        this.bookList = arrayList;
        bookDB = DatabaseHelper.getDbInstance(context);
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
        double percentage = ((double)book.getCurrentPage() / book.getPageCount()) * 100;

        holder.txtBookTitle.setText(book.getTitle());
        holder.txtBookAuthor.setText(book.getAuthor());
        holder.txtBookPages.setText(book.getCurrentPage() + " / " + book.getPageCount() + "   " + (int)percentage + "%");
        holder.imgBookCover.setImageBitmap(ImageHelper.getBitmapFromAsset(bookContext, book.getImgPath()));

        if (book.getType() == 1) {
            holder.favIcon.setColorFilter(bookContext.getResources().getColor(R.color.colorActive), PorterDuff.Mode.SRC_ATOP);
        } else {
            holder.favIcon.setColorFilter(bookContext.getResources().getColor(R.color.colorGrayLight), PorterDuff.Mode.SRC_ATOP);
        }

        holder.currentItem = book;
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        public TextView txtBookTitle;
        public TextView txtBookAuthor;
        public TextView txtBookPages;
        public ImageView imgBookCover;
        public ImageView favIcon;
        public Book currentItem;

        public BookViewHolder(final View bookItemView) {
            super(bookItemView);
            txtBookTitle = bookItemView.findViewById(R.id.textViewTitle);
            txtBookAuthor = bookItemView.findViewById(R.id.textViewAuthor);
            txtBookPages = bookItemView.findViewById(R.id.textViewPages);
            imgBookCover = bookItemView.findViewById(R.id.imageView);
            favIcon = bookItemView.findViewById(R.id.FavIcon);


            bookItemView.setClickable(true);
            bookItemView.setFocusable(true);

            bookItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openBookEvent(currentItem);
                }
            });

            bookItemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    final CharSequence[] items = {"Open book", "Delete book"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(bookContext);

                    builder.setTitle("Select The Action")
                    .setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int item) {
                            dialog.dismiss();
                            switch (item) {
                                case 0:
                                    openBookEvent(currentItem);
                                    break;
                                case 1:
                                    deleteBookEvent(currentItem);
                                    break;
                            }
                        }
                    });
                    builder.show();
                    return true;
                }
            });

            favIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println(currentItem.getId());
                    if (currentItem.getType() == 0) {
                        Log.d(TAG, "Changing book type to 1");
                        addFavoriteBook(currentItem);
                    } else {
                        Log.d(TAG, "Changing book type to 0");
                        removeFavoriteBook(currentItem);
                    }
                }
            });
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

    private static void openBookEvent(Book _currentItem) {
        Intent intent = new Intent(bookContext, ReaderActivity.class);
        intent.putExtra("bookId", _currentItem.getId());
        intent.putExtra("bookPath", _currentItem.getFilePath());
        intent.putExtra("currentPage", _currentItem.getCurrentPage());
        ((Activity) bookContext).startActivityForResult(intent, 1);
//        bookContext.startActivity(intent);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult");
    }

    private void deleteBookEvent(Book _book) {
        int _index = bookList.indexOf(_book);
        bookList.remove(_index);
        bookDB.removeBook(_book.getId());
        notifyItemRemoved(_index);
        notifyItemRangeChanged(_index, bookList.size());
    }

    private void removeFavoriteBook(Book _book) {
        int _index = bookList.indexOf(_book);
        bookList.get(_index).setType(0);
        bookDB.changeBookType(_book.getId(), 0);
        notifyItemRangeChanged(_index, bookList.size());
    }

    private void addFavoriteBook(Book _book) {
        int _index = bookList.indexOf(_book);
        bookList.get(_index).setType(1);
        bookDB.changeBookType(_book.getId(), 1);
        notifyItemRangeChanged(_index, bookList.size());
    }
}
