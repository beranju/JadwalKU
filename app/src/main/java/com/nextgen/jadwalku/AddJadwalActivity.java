package com.nextgen.jadwalku;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalTime;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;
import java.util.SimpleTimeZone;

public class AddJadwalActivity extends AppCompatActivity {
    EditText et_addjudul, et_adddesk;
    TextView addhari, addjam;
    Button btn_buatjadwal, btn_batal;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    BottomNavigationView bottomNavigationView;
    FirebaseAuth mAuth;
    //inserta to database
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Integer jadwalNum = new Random().nextInt();
    //4video3
    String keyjadwal = Integer.toString(jadwalNum);


    //date format
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    SimpleDateFormat simpleDateFormat;
    private Calendar calender;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_jadwal);
        getSupportActionBar().hide();
        et_addjudul = (EditText) findViewById(R.id.et_addjudul);
        et_adddesk = (EditText) findViewById(R.id.et_adddesk);
        addhari = (TextView) findViewById(R.id.addhari);
        btn_buatjadwal = (Button) findViewById(R.id.btn_buatjadwal);
        btn_batal = (Button) findViewById(R.id.btn_batal);
        addjam = (TextView) findViewById(R.id.addjam);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navbottomadd);

        //date format
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());


        mAuth = FirebaseAuth.getInstance();
        String userid;
        userid = mAuth.getUid();
        btn_buatjadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //insert to datebase
                firebaseDatabase = FirebaseDatabase.getInstance("https://jadwalku-a14dc-default-rtdb.asia-southeast1.firebasedatabase.app/");
                databaseReference = firebaseDatabase.getReference().child(userid).child("Jadwal").child("jadwalke" + jadwalNum);
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //2
                        snapshot.getRef().child("juduljadwal").setValue(et_addjudul.getText().toString());
                        snapshot.getRef().child("deskjadwal").setValue(et_adddesk.getText().toString());
                        snapshot.getRef().child("harijadwal").setValue(addhari.getText().toString());
                        snapshot.getRef().child("jamjadwal").setValue(addjam.getText().toString());
                        snapshot.getRef().child("keyjadwal").setValue(keyjadwal);


                        Intent intent = new Intent(AddJadwalActivity.this, MainActivity.class);
                        startActivity(intent);
                        setAlarm();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });

        btn_batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddJadwalActivity.this, MainActivity.class);
                startActivity(intent);
                cancleAlarm();
            }


        });

        addhari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

        addjam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimeDialog();
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.menu_home:
                        Intent intent = new Intent(AddJadwalActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.menu_user:
                        Intent intent3 = new Intent(AddJadwalActivity.this, UserActivity2.class);
                        startActivity(intent3);
                        break;
                }
                return true;
            }
        });



    }

    private void cancleAlarm() {
        Intent intent = new Intent(this, Notifikasi.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        if (alarmManager == null){
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        }
        alarmManager.cancel(pendingIntent);
    }

    private void setAlarm() {
        long when = System.currentTimeMillis() + 60000L;
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this, Notifikasi.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        alarmManager.set(AlarmManager.RTC_WAKEUP, when , pendingIntent);

    }

    private void showTimeDialog() {
        Calendar calendar = Calendar.getInstance();
        timePickerDialog = new TimePickerDialog(AddJadwalActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                addjam.setText(i+":"+i1);
            }
        },calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

    private void showDateDialog() {
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(AddJadwalActivity.this, new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(i, i1, i2);
                addhari.setText(simpleDateFormat.format(calendar1.getTime()));

            }
        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }





}