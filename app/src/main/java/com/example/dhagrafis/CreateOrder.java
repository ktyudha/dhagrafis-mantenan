package com.example.dhagrafis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
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

import java.util.Calendar;

public class CreateOrder extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView nmPkt, ctPkt, desPkt, priPkt, selectDateTime;
    EditText edname, edphone, ednote;
    Button btn;

    Spinner spinner;

    ImageButton btnBack;

    ImageView lgCouple;

    String selectedLocation, selectedDateTime, namePkt, descPkt, catePkt;
    int pricePkt;

    private FirebaseUser user;
    private DatabaseReference dbRef;

    private Order order;

    private Button dateTimeButton;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cactivty);

        regComponent();
        goBack();
        receiveData();
        pushOrder();
        initialSpinner();
        chooseDateTime();

        user = FirebaseAuth.getInstance().getCurrentUser();
        dbRef = FirebaseDatabase.getInstance().getReference().child("orders").child(user.getUid());

    }

    private void regComponent() {

        nmPkt = findViewById(R.id.orName);
        ctPkt = findViewById(R.id.orCategory);
        desPkt = findViewById(R.id.orDescription);
        priPkt = findViewById(R.id.orPrice);
        selectDateTime = findViewById(R.id.viewSelectDateTime);


        edname = findViewById(R.id.name);
        ednote = findViewById(R.id.note);
        edphone = findViewById(R.id.phone);
        btn = findViewById(R.id.sed);
        btnBack = findViewById(R.id.back);
        spinner = findViewById(R.id.spinnerLocation);

        dateTimeButton = findViewById(R.id.dateTimeButton);
        calendar = Calendar.getInstance();
    }

    private void receiveData() {
        Intent intent = getIntent();
        namePkt = intent.getStringExtra("name");
        descPkt = intent.getStringExtra("description");
        catePkt = intent.getStringExtra("category");
        pricePkt = intent.getIntExtra("price", 0);


        nmPkt.setText(namePkt);
        desPkt.setText(descPkt);
        ctPkt.setText(catePkt);
        priPkt.setText("Rp " + String.valueOf(pricePkt));
    }

    private void goBack() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreateOrder.this, PaketActivity.class));
            }
        });
    }

    private void initialSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.list_location, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    private void chooseDateTime() {
        dateTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimePicker();
            }
        });
    }

    private void showDateTimePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(CreateOrder.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        TimePickerDialog timePickerDialog = new TimePickerDialog(CreateOrder.this,
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                        calendar.set(Calendar.MINUTE, minute);

                                        // Format tanggal dan waktu sesuai kebutuhan
                                        // Anda dapat memodifikasi pola format sesuai keinginan
                                        selectedDateTime = String.format("%02d-%02d-%d %02d:%02d",
                                                dayOfMonth, monthOfYear + 1, year, hourOfDay, minute);

                                        selectDateTime.setText(selectedDateTime);
                                    }
                                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);

                        timePickerDialog.show();
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        selectedLocation = parent.getItemAtPosition(position).toString();

//        Toast.makeText(this, "Selected item: " + String.valueOf(priceLoc), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void pushOrder() {
        order = new Order();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                order.setNameOrder(edname.getText().toString().trim());
                order.setPhoneOrder(edphone.getText().toString().trim());
                order.setLocationOrder(selectedLocation);
                order.setDatetimeOrder(selectedDateTime);
                order.setNoteOrder(ednote.getText().toString().trim());
                order.setPaketOrder(namePkt);
                order.setCategoryOrder(catePkt);
                order.setDescriptionOrder(descPkt);
                order.setPriceOrder(pricePkt);

                dbRef.push().setValue(order);

                startActivity(new Intent(CreateOrder.this, InvoiceActivity.class));
            }
        });
    }
}