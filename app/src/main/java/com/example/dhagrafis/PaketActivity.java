package com.example.dhagrafis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dhagrafis.design.CustomAdapter;
import com.example.dhagrafis.models.PaketList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PaketActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    BottomNavigationView bottomNavigationView, categoryNavigation;
    ImageButton btnBack;

    private ArrayList<PaketList> paketLists;

    DatabaseReference dbRef;

    CustomAdapter customAdapter;
    String nameChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paket);

        listViewCategory();
        regComponent();
        orderByCategory();
        BottomNavigation();
        goBack();
    }

    private void regComponent() {
        btnBack = findViewById(R.id.back);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        categoryNavigation = findViewById(R.id.linecategory);
    }

    private void listViewCategory() {
        paketLists = new ArrayList<>();

        ListView listView = findViewById(R.id.customlistcard);
        customAdapter = new CustomAdapter(PaketActivity.this, paketLists);
        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener(this);
    }


    private void goBack() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PaketActivity.this, HomeActivity.class));
            }
        });
    }


    private void orderByCategory() {
        categoryNavigation.setSelectedItemId(R.id.wedding);
        getOrders("wedding");

        categoryNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.wedding:
                    paketLists.clear();
                    getOrders("wedding");
                    return true;
                case R.id.prewedding:
                    paketLists.clear();
                    getOrders("prewedding");
                    return true;
                case R.id.akad:
                    paketLists.clear();
                    getOrders("akad");
                    return true;
                case R.id.engagement:
                    paketLists.clear();
                    getOrders("engagement");
                    return true;
            }
            return false;
        });
    }

    private void BottomNavigation() {
        bottomNavigationView.setSelectedItemId(R.id.paket);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    finish();
                    return true;
                case R.id.paket:
                    return true;
                case R.id.history:
                    startActivity(new Intent(getApplicationContext(), HistoryActivity.class));
                    finish();
                    return true;
                case R.id.person:
                    startActivity(new Intent(getApplicationContext(), ProfilActivity.class));
                    finish();
                    return true;
            }
            return false;
        });
    }

    private void getOrders(String id) {

        dbRef = FirebaseDatabase.getInstance().getReference("pakets").child(id);

        dbRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));


                    for (DataSnapshot postSnapshot : task.getResult().getChildren()) {
                        PaketList paketList = postSnapshot.getValue(PaketList.class);
                        paketLists.add(paketList);
                    }
                    Log.d("firebase", String.valueOf(paketLists.size()));
                    customAdapter.notifyDataSetChanged();
                }
                Log.d("firebase", String.valueOf(paketLists.size()));
                customAdapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        PaketList list = paketLists.get(position);

        Intent intent = new Intent(PaketActivity.this, CreateOrder.class);
        intent.putExtra("name", list.name);
        intent.putExtra("category", list.category);
        intent.putExtra("description", list.description);
        intent.putExtra("price", list.price);
        startActivity(intent);
    }
}