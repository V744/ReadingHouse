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
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class Login extends AppCompatActivity {

    INodeJS myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    Retrofit retrofit;

    EditText edt_email, edt_password;
    Button login,forgot,sign;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        retrofit= RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);

        edt_email = findViewById(R.id.edt_email);
        edt_password = findViewById(R.id.edt_password);
        login=  findViewById(R.id.login);
        forgot=findViewById(R.id.forgot);
        sign= findViewById(R.id.sign);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edt_email.getText().toString().trim().equals("") && !edt_password.getText().toString().trim().equals("")) {
                    loginUser(edt_email.getText().toString(), edt_password.getText().toString());
                }
                //Intent intent =new Intent(Login.this,HomeActivity.class);
                //startActivity(intent);
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(Login.this,Passwordlink.class);
                startActivity(i);
            }
        });
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(Login.this,Signup.class);
                startActivity(i);
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
     * This Method Will Be Used For Logging User Into Application
     * @param email
     * @param password
     */
    private void loginUser(String email, String password) {
        compositeDisposable.add(myAPI.loginUser(email,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        if(s.contains("encrypted_password")) {
                            Log.d("UserData",s);
                            try {
                                JSONArray jsonArray = new JSONArray(s);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                SharedData.setEmail(getApplicationContext(),jsonObject.get("email").toString());
                                SharedData.setId(getApplicationContext(),jsonObject.get("unique_id").toString());
                                SharedData.setName(getApplicationContext(),jsonObject.get("name").toString());

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(Login.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                            Intent intent =new Intent(Login.this,HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                            Toast.makeText(Login.this,s,Toast.LENGTH_SHORT).show();
                    }
                })
        );
    }
}