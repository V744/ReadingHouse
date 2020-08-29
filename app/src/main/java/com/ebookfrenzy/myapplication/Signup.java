package com.ebookfrenzy.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ebookfrenzy.myapplication.Retrofit.INodeJS;
import com.ebookfrenzy.myapplication.Retrofit.RetrofitClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class Signup extends AppCompatActivity {

    INodeJS myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    Retrofit retrofit;

    Button signup;
    EditText email,password,name, cnfm_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        retrofit= RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);

        email = findViewById(R.id.edt_email);
        password = findViewById(R.id.edt_password);
        name = findViewById(R.id.edt_name);
        cnfm_password = findViewById(R.id.edt_cnfrm_password);
        signup= findViewById(R.id.btn_signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password.getText().toString().trim().equals(cnfm_password.getText().toString().trim())) {
                    registerUser(email.getText().toString().trim(),password.getText().toString().trim(),name.getText().toString().trim());
                }
            }
        });

    }

    @Override
    protected void onStop(){
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onDestroy(){
        compositeDisposable.clear();
        super.onDestroy();
    }

    /**
     * This Method Will Be Used For Registering New User
     * @param email
     * @param password
     * @param name
     */
    private void registerUser(final String email, final String password, final String name) {
        compositeDisposable.add(myAPI.registerUser(email,name,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        Toast.makeText(Signup.this,s,Toast.LENGTH_SHORT).show();
                        if (s.equals("\"Register Successful!\"")) {
                            finish();
                        }
                    }
                }));
    }
}