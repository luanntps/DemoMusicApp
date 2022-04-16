package com.luannt.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.luannt.R;

import java.util.regex.Pattern;

public class  DangNhapActivity extends AppCompatActivity {
    TextView tvDangKi;
    EditText edtEmail,edtPassword;
    Button btnLogin;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView tvQuenMatKhau;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        tvQuenMatKhau=findViewById(R.id.tvQuenMatKhau);
        tvDangKi = findViewById(R.id.tvDangKi);
        edtEmail=findViewById(R.id.edtEmail);
        edtPassword=findViewById(R.id.edtPassword);
        btnLogin=findViewById(R.id.btnLogin);
        mAuth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.pbProgressLogin);
        tvDangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(DangNhapActivity.this, DangKiActivity.class);
                startActivity(in);
            }
        });
        tvQuenMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvQuenMatKhau.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(DangNhapActivity.this,QuenMatKhauActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });
        View.OnKeyListener keyListener = new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP) {
                    login();
                }
                return false;
            }
        };
        edtEmail.setOnKeyListener(keyListener);
        edtPassword.setOnKeyListener(keyListener);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=edtEmail.getText()+"";
                String password=edtPassword.getText()+"";
                progressBar.setVisibility(View.VISIBLE);
                try {
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(DangNhapActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(DangNhapActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(DangNhapActivity.this, "Email hoặc mật khẩu không hợp lệ.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }catch (Exception ex){
                    login();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
    private void login() {
        String email = edtEmail.getText() + "";
        String password = edtPassword.getText() + "";
        if (email.isEmpty()) {
            edtEmail.setError("Email không được để trống.");
        }
        if (password.isEmpty()) {
            edtPassword.setError("Mật khẩu không được để trống.");
        }
        if (!Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$").matcher(email).matches()) {
            edtEmail.setError("Vui lòng nhập Email chính xác.");
        }
    }
}