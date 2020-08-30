package com.ebookfrenzy.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BooksListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private ArrayList<Book> bookArrayList;
    private FirebaseFirestore db;
    private AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_list);

        db = FirebaseFirestore.getInstance();

        recyclerView = findViewById(R.id.recyclerView);
        bookArrayList = new ArrayList<>();
        bookAdapter = new BookAdapter(bookArrayList, getApplicationContext(), new BookAdapter.OnClick() {
            @Override
            public void onItemClick(View view, Book book) {

                AddQuantityDialog(view,book);
                /*Intent i = new Intent(BooksListActivity.this,AddtoCart.class);
                i.putExtra("book_name",book.getName());
                i.putExtra("author",book.getAuthor());
                i.putExtra("price",book.getPrice());
                i.putExtra("category",book.getCategory());
                i.putExtra("bookid",book.getBookid());
                i.putExtra("image",book.getImage());
                startActivity(i);*/
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(bookAdapter);
        getBooks();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cart_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.cart){
            Intent i = new Intent(BooksListActivity.this,AddtoCart.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This Method Is Used For Getting List Of Books From Firestore
     */
    private void getBooks(){
        db.collection("books")
                .whereEqualTo("category",getIntent().getStringExtra("category"))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                bookArrayList.add(new Book(document.getId(),
                                        document.getString("book_name"),
                                        document.getString("author"),
                                        document.getString("price"),
                                        document.getString("image"),
                                        document.getString("category")));
                            }
                            bookAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(BooksListActivity.this,task.getException().getLocalizedMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    /**
     * This Method Shows Alert Dialog For Adding Selected Books Quantity
     * @param view
     * @param book
     */
    private void AddQuantityDialog(View view, final Book book) {
        // create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Quantity");
        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.custom_alert, null);
        builder.setView(customLayout);
        final EditText edt_quantity = customLayout.findViewById(R.id.edt_quantity);
        Button add_btn = customLayout.findViewById(R.id.add_btn);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edt_quantity.getText().toString().trim().equals("")){
                    AddBooKToCart(book,edt_quantity.getText().toString().trim());
                }
            }
        });
        // create and show the alert dialog
        dialog = builder.create();
        dialog.show();
    }

    /**
     * This Method Is Used For Adding Book Into Firestore Cart
     * @param book
     * @param quantity
     */
    private void AddBooKToCart(Book book,String quantity){

        int total_price = Integer.parseInt(book.getPrice())* Integer.parseInt(quantity);
        Map<String, Object> bookData = new HashMap<>();
        bookData.put("bookid", book.getBookid());
        bookData.put("userid", SharedData.getId(getApplicationContext()));
        bookData.put("quantity", quantity);
        bookData.put("total_price",String.valueOf(total_price));
        bookData.put("book_name", book.getName());
        bookData.put("author", book.getAuthor());
        bookData.put("category", book.getCategory());
        bookData.put("image",book.getImage());

        // Add a new document with a generated ID
        db.collection("cart")
                .add(bookData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(BooksListActivity.this,"Book Added To Cart!",Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(BooksListActivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                    }
                });
    }

}