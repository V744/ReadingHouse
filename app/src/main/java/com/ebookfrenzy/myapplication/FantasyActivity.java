package com.ebookfrenzy.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class FantasyActivity extends AppCompatActivity {
    private ListView listView;
    ArrayList<User> users;

    public ArrayList<User> getUsers(){
        ArrayList<User> users=new ArrayList<>();

        users.add(new User("MICHAEL J.SULLIVAN ","AGE MYTH"));
        return users;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fantasy);
        listView=findViewById(R.id.list_view);
        users = getUsers();
        FantasyAdapter adapter=new FantasyAdapter(users);
        listView.setAdapter(adapter);
    }
}