package com.nextgen.jadwalku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    EditText email , password;
    Button daftar;
    TextView login;
    //firebase
    private FirebaseAuth mAuth;

    //agar tidak perlu login terusmenerus


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        //deklarasi
        email = (EditText) findViewById(R.id.et_email);
        password = (EditText) findViewById(R.id.et_password);
        daftar = (Button) findViewById(R.id.btn_daftar);
        login = (TextView) findViewById(R.id.tv_login);

        //firebase
        mAuth = FirebaseAuth.getInstance();

        //event untuk button daftar
        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mendapatkan nilai inputan user
                String e = email.getText().toString();
                String p = password.getText().toString();
                if (e.equals("")){
                    Toast.makeText(RegisterActivity.this, "Email tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }else if (p.equals("")){
                    Toast.makeText(RegisterActivity.this, "Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else{
                    //menjalankan button dan mengarah ke fitur auth firebase
                    mAuth.createUserWithEmailAndPassword(e, p)
                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Toast.makeText(RegisterActivity.this, "Registrasi berhasil", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "Registrasi Gagal", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        //event untuk text view login
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}