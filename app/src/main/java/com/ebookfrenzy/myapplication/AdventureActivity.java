package com.ebookfrenzy.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class AdventureActivity extends AppCompatActivity {
    private ListView listView;
    ArrayList<User> users;

    public ArrayList<User> getUsers(){
        ArrayList<User> users=new ArrayList<>();

        users.add(new User("Jack London","Adventure"));
        return users;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adventure);
        listView = findViewById(R.id.list_view_one);

        users = getUsers();
        AdventureAdapter adapter = new AdventureAdapter(users);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(AdventureActivity.this,AddtoCart.class);
                startActivity(intent);
            }
        });


    }

}