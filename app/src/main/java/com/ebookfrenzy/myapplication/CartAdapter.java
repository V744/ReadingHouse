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

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private ArrayList<Cart> CartList;
    private Context context;
    @NonNull
    private OnClick onClick;

    interface OnClick{
        void onItemClick(Cart cart);
    }

    public CartAdapter(ArrayList<Cart> CartList, Context context, @NonNull OnClick onClick) {
        this.CartList = CartList;
        this.context = context;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list_view, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.bookname.setText(CartList.get(position).getBookname());
        holder.bookauthor.setText(CartList.get(position).getAuthor());
        holder.price.setText(CartList.get(position).getTotal_price()+"$");
        holder.quantity.setText("Qty: "+CartList.get(position).getQuantity());
        Glide.with(context).load(CartList.get(position).getImage()).centerCrop().into(holder.bookimage);
        holder.remove_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cart cart = CartList.get(position);
                onClick.onItemClick(cart);
            }
        });
    }

    @Override
    public int getItemCount() {
        return CartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView bookname, bookauthor, price, quantity;
        ImageView bookimage;
        Button remove_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            bookimage = itemView.findViewById(R.id.bookimage);
            bookname = itemView.findViewById(R.id.bookname);
            bookauthor = itemView.findViewById(R.id.bookauthor);
            remove_btn = itemView.findViewById(R.id.remove_btn);
            price = itemView.findViewById(R.id.price);
            quantity = itemView.findViewById(R.id.quantity);

        }
    }


}