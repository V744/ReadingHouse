package com.ebookfrenzy.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

public class ForgotPass extends AppCompatActivity {

    INodeJS myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    Retrofit retrofit;

    EditText edt_pass, edt_cnfm_pass;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        retrofit= RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);

        edt_pass = findViewById(R.id.new_pass);
        edt_cnfm_pass = findViewById(R.id.confirm_pass);
        save=  findViewById(R.id.reset_btn);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edt_pass.getText().toString().trim().equals("") &&
                        edt_pass.getText().toString().trim().equals(edt_cnfm_pass.getText().toString().trim())){
                    updatePSWD(getIntent().getStringExtra("email"),edt_pass.getText().toString().trim());
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
     * @param password
     */
    private void updatePSWD(final String email,final String password) {
        compositeDisposable.add(myAPI.updatePSWD(email,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        Toast.makeText(ForgotPass.this,s,Toast.LENGTH_SHORT).show();
                        if (s.equals("\"Reset Password Successful!\"")) {
                            Intent i = new Intent(ForgotPass.this, Login.class);
                            startActivity(i);
                            finish();
                        }
                    }
                }));
    }
}
