package com.example.dhagrafis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.dhagrafis.models.Order;
import com.example.dhagrafis.models.Pakets;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateOrder extends AppCompatActivity {

    EditText edname, edloc, edtime,ednote;
    Button btn;

    ImageButton btnBack;

    public String namepPaket, catePaket;

    private FirebaseUser user;
    private DatabaseReference dbRef;

    private Order order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cactivty);

        user = FirebaseAuth.getInstance().getCurrentUser();
        dbRef = FirebaseDatabase.getInstance().getReference().child("orders").child(user.getUid());

        edname = findViewById(R.id.name);
        edloc = findViewById(R.id.location);
        edtime = findViewById(R.id.time);
        ednote = findViewById(R.id.note);
        btn = findViewById(R.id.sed);
        btnBack = findViewById(R.id.back);

        order = new Order();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                order.setNameOrder(edname.getText().toString().trim());
                order.setPhoneOrder(edloc.getText().toString().trim());
                order.setDatetimeOrder(edtime.getText().toString().trim());
                order.setNoteOrder(ednote.getText().toString().trim());
                order.setPriceOrder(100000);

                dbRef.push().setValue(order);

                Toast.makeText(getApplicationContext(),"Data succes insert", Toast.LENGTH_SHORT).show();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreateOrder.this, HomeActivity.class));
            }
        });

    }
}