package com.nextgen.jadwalku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserActivity2 extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    TextView tv_username;
    Button btn_keluar;
    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user2);
        getSupportActionBar().hide();
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navbottomuser);
        btn_keluar = (Button) findViewById(R.id.btn_keluar);
        tv_username = (TextView) findViewById(R.id.tv_username);
        mAuth = FirebaseAuth.getInstance();

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            String email = user.getEmail();
            tv_username.setText(email);
        }


        btn_keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(UserActivity2.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.menu_home:
                        Intent intent = new Intent(UserActivity2.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.menu_add_jadwal:
                        Intent intent1 = new Intent(UserActivity2.this, AddJadwalActivity.class);
                        startActivity(intent1);
                        break;
                }
                    return true;

            }
        });
    }
}