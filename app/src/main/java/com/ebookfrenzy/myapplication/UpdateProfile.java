package com.ebookfrenzy.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class UpdateProfile extends AppCompatActivity {

    INodeJS myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    Retrofit retrofit;

    Button button;
    EditText name,pass,cnfm_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        retrofit= RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);

        name = findViewById(R.id.name);
        pass = findViewById(R.id.new_pass);
        cnfm_pass = findViewById(R.id.confirm_pass);

        name.setText(SharedData.getName(getApplicationContext()));

        button = findViewById(R.id.update);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!name.getText().toString().trim().equals("") &&
                        !pass.getText().toString().trim().equals("") &&
                        pass.getText().toString().trim().equals(cnfm_pass.getText().toString().trim())){
                    updateprofile(name.getText().toString().trim(), pass.getText().toString().trim());
                }
            }
        });
    }

    /**
     * This Method Is Used For Updating User Name And Password Using Node API And Retrofit
     * @param name
     * @param pass
     */
    private void updateprofile(final String name, String pass) {
        compositeDisposable.add(myAPI.updateUser(SharedData.getEmail(getApplicationContext()),name,pass)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        Toast.makeText(UpdateProfile.this,s,Toast.LENGTH_SHORT).show();
                        if (s.equals("\"Update Profile Successful!\"")) {
                            Intent i = new Intent(UpdateProfile.this,HomeActivity.class);
                            SharedData.setName(getApplicationContext(),name);
                            startActivity(i);
                            finish();
                        }
                    }
                }));
    }
}