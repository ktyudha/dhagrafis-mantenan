package com.example.dhagrafis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dhagrafis.design.CustomAdapter;
import com.example.dhagrafis.design.HistoryOrderAdapter;
import com.example.dhagrafis.models.Order;
import com.example.dhagrafis.models.PaketList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    BottomNavigationView bottomNavigationView;
    private ArrayList<PaketList> paketLists;

    private ArrayList<Order> orders;

    FirebaseUser user;
    DatabaseReference dbRef;
    HistoryOrderAdapter historyOrderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        BottomNavigation();

        user = FirebaseAuth.getInstance().getCurrentUser();
        dbRef = FirebaseDatabase.getInstance().getReference().child("orders").child(user.getUid());
        orders = new ArrayList<>();

        ListView listView = findViewById(R.id.customlistcard);
        historyOrderAdapter = new HistoryOrderAdapter(HistoryActivity.this, orders);
        listView.setAdapter(historyOrderAdapter);
        listView.setOnItemClickListener(this);

        getOrders();
    }

    private void getOrders() {
        dbRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));

                    orders.clear();
                    for (DataSnapshot postSnapshot: task.getResult().getChildren()) {
                        Order order = postSnapshot.getValue(Order.class);
                        orders.add(order);
                    }
                    Log.d("firebase", String.valueOf(orders.size()));
                    historyOrderAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void BottomNavigation() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.history);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    finish();
                    return true;
                case R.id.paket:
                    startActivity(new Intent(getApplicationContext(), PaketActivity.class));
                    finish();
                    return true;
                case R.id.history:
                    return true;
            }
            return false;
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        PaketList list = paketLists.get(position);
        Toast.makeText(HistoryActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
    }
}

