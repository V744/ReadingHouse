package com.ebookfrenzy.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AddtoCart extends AppCompatActivity {

    public Button b1,b2;
    private RecyclerView recyclerView;
    private TextView order_total;
    private CartAdapter cartAdapter;
    private ArrayList<Cart> cartArrayList;
    private FirebaseFirestore db;
    private int totalAmount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addto_cart);

        db = FirebaseFirestore.getInstance();

        b1=findViewById(R.id.button3);
        b2=findViewById(R.id.button4);
        order_total = findViewById(R.id.order_total);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddtoCart.this, PaymentPage.class);
                i.putParcelableArrayListExtra("list", cartArrayList);
                startActivity(i);
            }

        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddtoCart.this, HomeActivity.class);
                startActivity(i);
            }

        });

        recyclerView = findViewById(R.id.recyclerView);
        cartArrayList = new ArrayList<>();
        cartAdapter = new CartAdapter(cartArrayList, getApplicationContext(), new CartAdapter.OnClick() {
            @Override
            public void onItemClick(Cart cart) {
                removeBook(cart);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(cartAdapter);
        getCart();
    }

    /**
     * This Method Is Used For Getting List Of Books From Firestore
     */
    private void getCart(){
        db.collection("cart")
                .whereEqualTo("userid",SharedData.getId(getApplicationContext()))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                cartArrayList.add(new Cart(document.getId(),
                                        document.getString("bookid"),
                                        document.getString("userid"),
                                        document.getString("book_name"),
                                        document.getString("author"),
                                        document.getString("image"),
                                        document.getString("category"),
                                        document.getString("quantity"),
                                        document.getString("total_price")));

                                totalAmount = totalAmount+Integer.parseInt(document.getString("total_price"));
                            }
                            cartAdapter.notifyDataSetChanged();
                            order_total.setText("Total Amount: "+totalAmount+"$");
                        } else {
                            Toast.makeText(AddtoCart.this,task.getException().getLocalizedMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    /**
     * This Method Is User ForRemoving Any Book From Firestore Cart
     * @param cart
     */
    private void removeBook(final Cart cart){
        db.collection("cart").document(cart.getCartid())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddtoCart.this,"Book Removed!",Toast.LENGTH_LONG).show();
                        totalAmount = totalAmount - Integer.parseInt(cart.getTotal_price());
                        order_total.setText("Total Amount: "+totalAmount+"$");
                        cartArrayList.remove(cart);
                        cartAdapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddtoCart.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                    }
                });
    }
}