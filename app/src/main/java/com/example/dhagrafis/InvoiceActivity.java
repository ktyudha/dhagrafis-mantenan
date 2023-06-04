package com.example.dhagrafis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class InvoiceActivity extends AppCompatActivity {

    Button btnContinue;
    ImageButton btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

        RegComponent();
        GoBack();
        GoContinue();
    }

    private void RegComponent() {
        btnContinue = findViewById(R.id.btnFinish);
        btnBack = findViewById(R.id.back);
    }

    private void GoBack() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InvoiceActivity.this, PaketActivity.class));
            }
        });
    }

    private void GoContinue() {
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InvoiceActivity.this, HistoryActivity.class));
            }
        });
    }
}