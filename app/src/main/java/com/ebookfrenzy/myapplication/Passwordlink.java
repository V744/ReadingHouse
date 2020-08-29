package com.ebookfrenzy.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class Passwordlink extends AppCompatActivity {

    INodeJS myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    Retrofit retrofit;

    EditText userEmail;
    Button email_check_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passwordlink);

        retrofit= RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);

        userEmail = findViewById(R.id.email_address);
        email_check_btn = findViewById(R.id.check_email);
        email_check_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!userEmail.getText().toString().trim().equals("")) {
                    checkUser(userEmail.getText().toString().trim());
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
     * This Method Will Be Used For Checking That Email Exists Or Not In Database
     * @param email
     */
    private void checkUser(final String email) {
        compositeDisposable.add(myAPI.checkUser(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        if (s.equals("\"User Found!\"")) {
                            Intent i = new Intent(Passwordlink.this, ForgotPass.class);
                            i.putExtra("email",email);
                            startActivity(i);
                        }
                        else {
                            Toast.makeText(Passwordlink.this,s,Toast.LENGTH_SHORT).show();
                        }
                    }
                }));
    }
}