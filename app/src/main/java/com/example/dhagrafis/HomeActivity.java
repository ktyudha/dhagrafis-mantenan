package com.example.dhagrafis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.dhagrafis.controllers.OrderController;
import com.example.dhagrafis.design.CustomAdapter;
import com.example.dhagrafis.models.Order;
import com.example.dhagrafis.models.PaketList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    TextView userlogin;
    ImageView userprof;
    ImageButton pic;
    BottomNavigationView bottomNavigationView;

    private ArrayList<PaketList> paketLists;

    CustomAdapter customAdapter;

    private FirebaseAuth mAuth;
    private DatabaseReference dbRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        dbRef = FirebaseDatabase.getInstance().getReference().child("pakets").child("wedding");
        paketLists = new ArrayList<>();

        ListView listView = findViewById(R.id.customlistcard);
        customAdapter = new CustomAdapter(HomeActivity.this, paketLists);
        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener(this);

        BottomNavigation();
        getUserProfile();
        promoSlider();
        getOrders();


        pic = findViewById(R.id.pic1);
        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, PaketActivity.class));
            }
        });
    }

    private void BottomNavigation() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    return true;
                case R.id.paket:
                    startActivity(new Intent(getApplicationContext(), PaketActivity.class));
                    finish();
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

//    private void Logout() {
//        FirebaseAuth.getInstance().signOut();
//        startActivity(new Intent(HomeActivity.this, Login.class));
//    }

    public void getUserProfile() {
        // [START get_user_profile]
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String name = user.getDisplayName().toUpperCase();
            Uri photoUrl = user.getPhotoUrl();

            userlogin = (TextView) findViewById(R.id.userlog);
            userprof = findViewById(R.id.userprofil);
            userlogin.setText(name);
            userprof.setImageURI(photoUrl);
            Picasso.get().load(photoUrl).into(userprof);

        }
    }

    private void promoSlider() {
        ImageSlider imageSlider = findViewById(R.id.slider);
        ArrayList<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.promo1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.promo2, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.promo3, ScaleTypes.FIT));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);
    }


//    private ArrayList<PaketList> setIconAndName() {
//        paketLists = new ArrayList<>();
//
//        paketLists.add(new PaketList(R.drawable.promo1, "BRONZE", "2 Fotografer", "Rp 10.000", "Wedding"));
//        paketLists.add(new PaketList(R.drawable.promo2, "SILVER", "2 Fotografer", "Rp 10.000", "Wedding"));
//        paketLists.add(new PaketList(R.drawable.promo3, "GOLD", "2 Fotografer", "Rp 10.000", "Wedding"));
//        return paketLists;
//    }

    private void getOrders() {
        dbRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));

                    paketLists.clear();
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
        startActivity(new Intent(HomeActivity.this, CreateOrder.class));
//        Toast.makeText(HomeActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
    }


}