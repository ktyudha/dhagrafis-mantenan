package com.example.dhagrafis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import com.example.dhagrafis.design.HistoryOrderAdapter;
import com.example.dhagrafis.design.RupiahConvert;
import com.example.dhagrafis.models.Order;
import com.example.dhagrafis.models.PaketList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    BottomNavigationView bottomNavigationView;
    TextView tePaket, teCat, teDesc, tePrice, teName, tePhone, teLoc, teDatetime, teNot;
    Button btnClose;
    private ArrayList<PaketList> paketLists;
    private ArrayList<Order> orders;
    ImageButton btnBack, btnDel;
    FirebaseUser user;
    String key = "";
    DatabaseReference dbRef;
    HistoryOrderAdapter historyOrderAdapter;
    private BottomSheetDialog bottomSheetDialog;

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

    private void goBack() {
        btnBack = findViewById(R.id.back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HistoryActivity.this, HomeActivity.class));
            }
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

                    orders.clear();
                    for (DataSnapshot postSnapshot : task.getResult().getChildren()) {
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
                case R.id.person:
                    startActivity(new Intent(getApplicationContext(), ProfilActivity.class));
                    finish();
                    return true;
            }
            return false;
        });
    }

    private void openBottomSheet(String name, String category, String description, int price, String customer, String phone, String datetime, String location, String note, int posisi) {
        View bottomSheetView = getLayoutInflater().inflate(R.layout.bottomsheet_history, null);
        bottomSheetDialog = new BottomSheetDialog(HistoryActivity.this);
        bottomSheetDialog.setContentView(bottomSheetView);

        RupiahConvert rupiahConvert = new RupiahConvert();

        tePaket = bottomSheetView.findViewById(R.id.paket_hisdet);
        teCat = bottomSheetView.findViewById(R.id.cat_hisdet);
        teDesc = bottomSheetView.findViewById(R.id.desc_hisdet);
        tePrice = bottomSheetView.findViewById(R.id.price_hisdet);
        teName = bottomSheetView.findViewById(R.id.name_hisdet);
        tePhone = bottomSheetView.findViewById(R.id.phone_hisdet);
        teLoc = bottomSheetView.findViewById(R.id.loc_hisdet);
        teDatetime = bottomSheetView.findViewById(R.id.time_hisdet);
        teNot = bottomSheetView.findViewById(R.id.note_hisdet);

        btnClose = bottomSheetView.findViewById(R.id.btnFinish);
        btnDel = bottomSheetView.findViewById(R.id.btnDelete);

        tePaket.setText(name);
        teCat.setText(category + " -");
        teDesc.setText(description);
        tePrice.setText(String.valueOf(rupiahConvert.convertToRupiah(price)));
        teName.setText("a.n. " + customer);
        tePhone.setText(phone);
        teLoc.setText(location + ", ");
        teDatetime.setText(datetime + " WIB");
        teNot.setText("*" + note);

        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) bottomSheetView.getParent());
        bottomSheetBehavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);

        bottomSheetDialog.show();

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int count = 0;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            if (count == posisi) {
                                snapshot.getRef().removeValue()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                // Data berhasil dihapus
                                                bottomSheetDialog.dismiss();
                                                recreate();

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // Terjadi kesalahan saat menghapus data
                                            }
                                        });
                                break;
                            }
                            count++;
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Terjadi kesalahan saat membaca data
                    }
                };

                dbRef.addListenerForSingleValueEvent(valueEventListener);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Order order = orders.get(position);
        openBottomSheet(order.paketOrder, order.categoryOrder, order.descriptionOrder, order.priceOrder, order.nameOrder, order.phoneOrder, order.datetimeOrder, order.locationOrder, order.noteOrder, position);
//        Toast.makeText(HistoryActivity.this, order.nameOrder, Toast.LENGTH_SHORT).show();
    }
}

