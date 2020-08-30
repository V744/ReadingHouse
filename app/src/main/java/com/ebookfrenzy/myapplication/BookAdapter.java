package com.ebookfrenzy.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    private ArrayList<Book> BookList;
    private Context context;
    @NonNull
    private OnClick onClick;

    interface OnClick{
        void onItemClick(View view, Book book);
    }

    public BookAdapter(ArrayList<Book> BookList, Context context,  @NonNull OnClick onClick) {
        this.BookList = BookList;
        this.context = context;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.bookname.setText(BookList.get(position).getName());
        holder.bookauthor.setText(BookList.get(position).getAuthor());
        holder.price.setText(BookList.get(position).getPrice()+"$");
        Glide.with(context).load(BookList.get(position).getImage()).centerCrop().into(holder.bookimage);
        holder.addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Book book = BookList.get(position);
                onClick.onItemClick(view, book);
            }
        });
    }

    @Override
    public int getItemCount() {
        return BookList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView bookname, bookauthor, price;
        ImageView bookimage;
        Button addtocart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            bookimage = itemView.findViewById(R.id.bookimage);
            bookname = itemView.findViewById(R.id.bookname);
            bookauthor = itemView.findViewById(R.id.bookauthor);
            addtocart = itemView.findViewById(R.id.addtocart);
            price = itemView.findViewById(R.id.price);

        }
    }


}