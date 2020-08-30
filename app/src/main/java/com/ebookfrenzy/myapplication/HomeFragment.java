package com.ebookfrenzy.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomeFragment extends Fragment {
    private CardView fantasy,adventure,romance,mystery,horror,thriller;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_home, container, false);
        fantasy = v.findViewById(R.id.card_fantasy);
        adventure=v.findViewById(R.id.card_adventure);
        romance=v.findViewById(R.id.card_romance);
        mystery=v.findViewById(R.id.card_mystery);
        horror=v.findViewById(R.id.card_horror);
        thriller=v.findViewById(R.id.card_thriller);
        fantasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BooksListActivity.class);
                intent.putExtra("category","fantasy");
                startActivity(intent);
            }
        });
        adventure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BooksListActivity.class);
                intent.putExtra("category","adventure");
                startActivity(intent);
            }
        });
        romance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BooksListActivity.class);
                intent.putExtra("category","romance");
                startActivity(intent);
            }
        });
        mystery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BooksListActivity.class);
                intent.putExtra("category","mystery");
                startActivity(intent);
            }
        });
        horror.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BooksListActivity.class);
                intent.putExtra("category","horror");
                startActivity(intent);
            }
        });
        thriller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BooksListActivity.class);
                intent.putExtra("category","thriller");
                startActivity(intent);
            }
        });
        return v;
    }
}