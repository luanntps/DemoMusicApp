package com.luannt.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.luannt.R;

import java.util.regex.Pattern;

public class QuenMatKhauActivity extends AppCompatActivity {
    EditText edtFgEmail;
    Button btnHuy, btnTimKiem;
    Context context;
    FirebaseAuth auth;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quen_mat_khau);
        auth=FirebaseAuth.getInstance();
        btnHuy = findViewById(R.id.btnHuy);
        btnTimKiem = findViewById(R.id.btnTimKiem);
        edtFgEmail=findViewById(R.id.edtFgEmail);
        progressBar=findViewById(R.id.pbRsProgress);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuenMatKhauActivity.this, DangNhapActivity.class);
                startActivity(intent);
            }
        });

        btnTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });
    }
    private void resetPassword(){
        String email=edtFgEmail.getText()+"";
        if(email.isEmpty()){
            edtFgEmail.setError("Vui lòng nhập email.");
        }
        else if(!Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$").matcher(email).matches()){
            edtFgEmail.setError("Email không hợp lệ");
        }else{
            progressBar.setVisibility(View.VISIBLE);
            auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        progressBar.setVisibility(View.INVISIBLE);
                        AlertDialog.Builder dialog = new AlertDialog.Builder(QuenMatKhauActivity.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View view = inflater.inflate(R.layout.dialog_notification, null);
                        Button btnOkay=view.findViewById(R.id.btnOkay);
                        dialog.setView(view);
                        AlertDialog alertDialog = dialog.create();
                        alertDialog.show();
                        btnOkay.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialog.cancel();
                            }
                        });
                    }else{
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(QuenMatKhauActivity.this,"Email không tồn tại hoặc vui lòng kiểm tra kết nối mạng.",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
