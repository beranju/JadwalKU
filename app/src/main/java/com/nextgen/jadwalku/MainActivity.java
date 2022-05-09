package com.nextgen.jadwalku;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.util.UidVerifier;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    //deklarasi komponen
    TextView tv_juduljadwal, tv_deskjadwal, tv_harijadwal;
    Button addjadwal;
    //deklarasi layanan auth firebase
    FirebaseAuth mAuth;
    //Deklarasi variable database
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    RecyclerView rv_jadwal;
    ArrayList<Jadwal> arrayList;
    JadwalAdapter jadwalAdapter;
    String userid;
    BottomNavigationView bottomNavigationView;


    //Membuat menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_utama, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_logout){
            mAuth.signOut();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        //inisialisasi komponen
        tv_juduljadwal = (TextView) findViewById(R.id.tv_juduljadwal);
        tv_deskjadwal = (TextView) findViewById(R.id.tv_deskjadwal);
        tv_harijadwal = (TextView) findViewById(R.id.tv_harijadwal);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navbottom);
        mAuth = FirebaseAuth.getInstance();

        //Memanggil recycleview di mainactivity
        rv_jadwal = (RecyclerView) findViewById(R.id.rv_jadwal);
        rv_jadwal.setLayoutManager(new LinearLayoutManager(this));
        rv_jadwal.setHasFixedSize(true);
        arrayList = new ArrayList<Jadwal>();

        //read data form database dan auth
        userid = mAuth.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance("https://jadwalku-a14dc-default-rtdb.asia-southeast1.firebasedatabase.app/");
        databaseReference = firebaseDatabase.getReference().child(userid).child("Jadwal");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {;

                //kode untuk retrive data
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Jadwal jadwal = dataSnapshot.getValue(Jadwal.class);
                    arrayList.add(jadwal);
                }
                jadwalAdapter = new JadwalAdapter(MainActivity.this, arrayList);
                rv_jadwal.setAdapter(jadwalAdapter);
                jadwalAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
            }
        });


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.menu_add_jadwal:
                        Intent intent1 = new Intent(MainActivity.this, AddJadwalActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.menu_user:
                        Intent intent3 = new Intent(MainActivity.this, UserActivity2.class);
                        startActivity(intent3);
                        break;
                }
                return true;
            }
        });


    }


}