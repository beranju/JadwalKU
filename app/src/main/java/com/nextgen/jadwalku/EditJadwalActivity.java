package com.nextgen.jadwalku;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.Key;
import java.util.Calendar;
import java.util.Locale;

public class EditJadwalActivity extends AppCompatActivity {
    EditText et_editjudul, et_editdesk;
    TextView edithari, editjam;
    Button btn_simpan, btn_hapus;
    BottomNavigationView bottomNavigationView;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    //6 video 3
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    //time format
    TimePickerDialog timePickerDialog;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat simpleDateFormat;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_jadwal);
        getSupportActionBar().hide();
        et_editjudul = (EditText) findViewById(R.id.et_editjudul);
        et_editdesk = (EditText) findViewById(R.id.et_editdesk);
        edithari = (TextView) findViewById(R.id.edithari);
        editjam = (TextView) findViewById(R.id.editjam);
        btn_simpan = (Button) findViewById(R.id.btn_simpanjadwal);
        btn_hapus = (Button) findViewById(R.id.btn_hapus);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navbottomedit);

        //date format
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ROOT);

        //2 video 3
        //get a value from previuos page saat ngedit
        et_editjudul.setText(getIntent().getStringExtra("juduljadwal"));
        et_editdesk.setText(getIntent().getStringExtra("deskjadwal"));
        edithari.setText(getIntent().getStringExtra("harijadwal"));

        //8
        String KeyJadwal = getIntent().getStringExtra("keyjadwal");
        // membuat inisialisasu databse diluar agar bias diakses btn hpaus viedeo 4

        mAuth = FirebaseAuth.getInstance();
        String userid;
        userid = mAuth.getUid();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://jadwalku-a14dc-default-rtdb.asia-southeast1.firebasedatabase.app/");
        databaseReference = firebaseDatabase.getReference().child(userid).child("Jadwal").child("jadwalke" + KeyJadwal);
        //make event for button save
        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        snapshot.getRef().child("juduljadwal").setValue(et_editjudul.getText().toString());
                        snapshot.getRef().child("deskjadwal").setValue(et_editdesk.getText().toString());
                        snapshot.getRef().child("harijadwal").setValue(edithari.getText().toString());
                        snapshot.getRef().child("jamjadwal").setValue(editjam.getText().toString());
                        snapshot.getRef().child("keyjadwal").setValue(KeyJadwal);
                        Intent intent = new Intent(EditJadwalActivity.this, MainActivity.class);
                        startActivity(intent);
                        setAlarm();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        //1 video 4
        btn_hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Intent intent = new Intent(EditJadwalActivity.this, MainActivity.class);
                            startActivity(intent);
                            cancleAlarm();
                        }else {
                            Toast.makeText(getApplicationContext(), "Gagal", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        //dateppicker
        edithari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });
        editjam.setOnClickListener(new View.OnClickListener() {
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
                        Intent intent = new Intent(EditJadwalActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.menu_add_jadwal:
                        Intent intent1 = new Intent(EditJadwalActivity.this, AddJadwalActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.menu_user:
                        Intent intent3 = new Intent(EditJadwalActivity.this, UserActivity2.class);
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
        timePickerDialog = new TimePickerDialog(EditJadwalActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                editjam.setText(i+":"+i1);
            }
        },calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }


    private void showDateDialog() {
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(EditJadwalActivity.this, new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(i, i1, i2);
                edithari.setText(simpleDateFormat.format(calendar1.getTime()));
            }
        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
}