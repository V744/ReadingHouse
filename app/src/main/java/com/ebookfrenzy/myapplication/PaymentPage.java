package com.ebookfrenzy.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PaymentPage extends AppCompatActivity {

    EditText edt_addr, edt_code, edt_phone;
    Button place_btn;
    ArrayList<Cart> cartArrayList;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_page);

        db = FirebaseFirestore.getInstance();

        edt_addr = findViewById(R.id.address);
        edt_phone = findViewById(R.id.phone);
        edt_code = findViewById(R.id.postal_code);

        place_btn = findViewById(R.id.place_order_btn);

        place_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edt_addr.getText().toString().trim().equals("") &&
                        !edt_code.getText().toString().trim().equals("") &&
                        !edt_code.getText().toString().trim().equals("")) {
                    PlaceOrder(edt_addr.getText().toString().trim(), edt_code.getText().toString().trim(), edt_phone.getText().toString().trim());
                }
            }
        });

        cartArrayList = getIntent().getParcelableArrayListExtra("list");
    }

    /**
     * This Method Is Used For Storing Order Of User In Firestore
     * @param address
     * @param code
     * @param phone
     */
    private void PlaceOrder(String address, String code, String phone) {
        for (int i=0;i<cartArrayList.size();i++){

            Map<String, Object> orderData = new HashMap<>();
            orderData.put("bookid", cartArrayList.get(i).getBookid());
            orderData.put("userid", cartArrayList.get(i).getUserid());
            orderData.put("quantity", cartArrayList.get(i).getQuantity());
            orderData.put("total_price",cartArrayList.get(i).getTotal_price());
            orderData.put("author", cartArrayList.get(i).getAuthor());
            orderData.put("category", cartArrayList.get(i).getCategory());
            orderData.put("image",cartArrayList.get(i).getImage());
            orderData.put("address",address);
            orderData.put("postal_code",code);
            orderData.put("phone",phone);

            // Add a new document with a generated ID
            final int finalI = i;
            db.collection("order")
                    .add(orderData)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            db.collection("cart").document(cartArrayList.get(finalI).getCartid()).delete();
                        }
                    });
        }
        Toast.makeText(PaymentPage.this,"Order Placed!",Toast.LENGTH_LONG).show();
        Intent i = new Intent(PaymentPage.this,HomeActivity.class);
        startActivity(i);
        finish();
    }
}