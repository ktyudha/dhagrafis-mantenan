package com.example.dhagrafis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

    BottomNavigationView bottomNavigationView;
    TextView teAll, teWedding, teAkad, tePrewedd, teEng;

    private ArrayList<PaketList> paketLists;

    DatabaseReference dbRef;

    CustomAdapter customAdapter;
    String nameChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paket);

        teAll = findViewById(R.id.all);
        teWedding = findViewById(R.id.wedding);
        teAkad = findViewById(R.id.akad);
        tePrewedd = findViewById(R.id.prewedding);
        teEng = findViewById(R.id.engagement);


        dbRef = FirebaseDatabase.getInstance().getReference().child("pakets").child("wedding");
        paketLists = new ArrayList<>();

        ListView listView = findViewById(R.id.customlistcard);
        customAdapter = new CustomAdapter(PaketActivity.this, paketLists);
        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener(this);

        BottomNavigation();
        getOrders();
    }

    private void BottomNavigation() {
        bottomNavigationView =findViewById(R.id.bottomNavigationView);
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
            }
            return false;
        });
    }

    private void getOrders() {
        dbRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));

                    paketLists.clear();
                    for (DataSnapshot postSnapshot: task.getResult().getChildren()) {
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
        startActivity(new Intent(PaketActivity.this, CreateOrder.class));
    }
}