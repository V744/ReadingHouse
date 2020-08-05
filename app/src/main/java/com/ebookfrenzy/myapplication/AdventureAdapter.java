package com.ebookfrenzy.myapplication;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class AdventureAdapter  extends BaseAdapter {
    ArrayList<User> users;
    LayoutInflater mInflater;
    public AdventureAdapter(ArrayList<User> users){

        this.users=users;
    }


    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public User getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view,null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
        User user=getItem(position);
        viewHolder.author.setText(user.getAuthor());
        viewHolder.bookname.setText(user.getBookname());

        return convertView;
    }
    class ViewHolder {
        TextView author;
        TextView bookname;

        public ViewHolder(View view) {
            author = view.findViewById(R.id.text3);
            bookname= view.findViewById(R.id.text5);




        }
    }
}
