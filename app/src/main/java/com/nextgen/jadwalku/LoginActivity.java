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

public class LoginActivity extends AppCompatActivity {
    EditText email, password;
    Button login;
    TextView daftar;

    //firebase
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        email = (EditText) findViewById(R.id.et_emaillogin);
        password = (EditText) findViewById(R.id.et_passwordlogin);
        login = (Button) findViewById(R.id.btn_login);
        daftar = (TextView) findViewById(R.id.tv_daftar);

        mAuth = FirebaseAuth.getInstance();

        //event pada button login
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mengambil input user
                String e = email.getText().toString();
                String p = password.getText().toString();
                if (e.equals("")){
                    Toast.makeText(LoginActivity.this, "Email tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }else if (p.equals("")){
                    Toast.makeText(LoginActivity.this, "Password tidak boleh kososng", Toast.LENGTH_SHORT).show();
                }else{
                    mAuth.signInWithEmailAndPassword(e, p)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Toast.makeText(LoginActivity.this, "Login berhasil", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }else{
                                        Toast.makeText(LoginActivity.this, "Login Gagal", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        //event untuk text view daftar
        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
}