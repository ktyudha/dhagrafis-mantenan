package com.example.dhagrafis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

    private ArrayList<PaketList> paketLists;

    OrderController orderController = new OrderController();

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getUserProfile();
        promoSlider();

        ListView listView = findViewById(R.id.customlistcard);
        paketLists = setIconAndName();
        CustomAdapter customAdapter = new CustomAdapter(HomeActivity.this, paketLists);
        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener(this);

        userprof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logout();
            }
        });

    }

    private void Logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(HomeActivity.this, Login.class));
    }

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

        slideModels.add(new SlideModel(R.drawable.promo4, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.promo4, ScaleTypes.FIT));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);
    }

//    public void loadOrder() {
//
//        ArrayList<Order> orders = orderController.loadOrder();
//      for (int i = 0; i<orders.size(); i++) {
//            Order order = orders.get(i);
//          .add(order.nameOrder);
//        }
//    }
    private ArrayList<PaketList> setIconAndName() {
        paketLists = new ArrayList<>();

        paketLists.add(new PaketList(R.drawable.promo1, "BRONZE", "2 Fotografer", "Rp 10.000", "Wedding"));
        paketLists.add(new PaketList(R.drawable.promo2, "SILVER", "2 Fotografer", "Rp 10.000", "Wedding"));
        return paketLists;
    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        PaketList list = paketLists.get(position);
        Toast.makeText(HomeActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
    }
}