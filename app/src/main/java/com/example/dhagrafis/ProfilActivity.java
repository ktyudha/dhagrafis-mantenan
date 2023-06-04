package com.example.dhagrafis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class ProfilActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Button btnLogOut;
    TextView namuser, mailuser;
    ImageView profiluser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

       regComponent();
       setUserInformation();
       BottomNavigation();
    }
    private void regComponent() {
        namuser = findViewById(R.id.nameuser);
        mailuser = findViewById(R.id.emailuser);
        profiluser = findViewById(R.id.iconuser);
        btnLogOut = findViewById(R.id.btnOut);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logout();
            }
        });
    }

    private void setUserInformation() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String name = user.getDisplayName().toUpperCase();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            namuser.setText(name);
            mailuser.setText(email);
            profiluser.setImageURI(photoUrl);
            Picasso.get().load(photoUrl).into(profiluser);

        }
    }
    private void Logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(ProfilActivity.this, Login.class));
    }
    private void BottomNavigation() {

        bottomNavigationView.setSelectedItemId(R.id.person);

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
                    startActivity(new Intent(getApplicationContext(), HistoryActivity.class));
                    finish();
                    return true;
                case R.id.person:
                    return true;
            }
            return false;
        });
    }
}