package com.dungps19554.gatifymusic;

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

import java.util.regex.Pattern;

public class DangKiActivity extends AppCompatActivity {
    TextView tvBack;
    EditText edtRgUsername,edtRgPassword,edtRgEmail;
    Button btnRegistration;
    ProgressBar pbProgress;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ki);

        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_dang_ki);
        tvBack = findViewById(R.id.tvBack);
        edtRgUsername=findViewById(R.id.edtRgUsername);
        edtRgPassword=findViewById(R.id.edtRgPassword);
        edtRgEmail=findViewById(R.id.edtRgEmail);
        btnRegistration=findViewById(R.id.btnRegistration);
        pbProgress=findViewById(R.id.pbProgress);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DangKiActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        View.OnKeyListener keyListener = new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP) {
                    registration();
                }
                return false;
            }
        };
        edtRgUsername.setOnKeyListener(keyListener);
        edtRgPassword.setOnKeyListener(keyListener);
        edtRgEmail.setOnKeyListener(keyListener);
        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pbProgress.setVisibility(View.VISIBLE);
                String email=String.valueOf(edtRgEmail.getText());
                String password=String.valueOf(edtRgPassword.getText());
                String username=String.valueOf(edtRgUsername.getText());
                try {
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                       /* User user=new User(username,password,email);
                                        FirebaseDatabase.getInstance("https://gatifymusic-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                        */            Toast.makeText(DangKiActivity.this,"Đăng ký thành công",Toast.LENGTH_SHORT).show();
                                                    pbProgress.setVisibility(View.GONE);
                                                    edtRgEmail.setText("");
                                                    edtRgUsername.setText("");
                                                    edtRgPassword.setText("");
                                       /*         }
                                                else {
                                                    Toast.makeText(DangKiActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                                                    pbProgress.setVisibility(View.GONE);
                                                }
                                            }
                                        });*/
                                    } else {
                                        Toast.makeText(DangKiActivity.this, "Tài khoản hoặc Email đã tồn tại hoặc vui lòng kiểm tra lại kết nối mạng.", Toast.LENGTH_SHORT).show();
                                        pbProgress.setVisibility(View.GONE);
                                    }
                                }
                            });
                }catch (Exception ex){
                    registration();
                    pbProgress.setVisibility(View.GONE);
                }
            }
        });
    }
    private void registration(){
        String email=edtRgEmail.getText().toString().trim();
        String username=edtRgUsername.getText().toString().trim();
        String password=edtRgPassword.getText().toString().trim();
        if(username.isEmpty()){
            edtRgUsername.setError("Username không được bỏ trống");

        }
        if(password.isEmpty()){
            edtRgPassword.setError("Mật khẩu không được bỏ trống");


        }
        if(email.isEmpty()){
            edtRgEmail.setError("Email không được bỏ trống.");


        }
        if(password.length()<6){
            edtRgPassword.setError("Mật khẩu phải lớn hơn hoặc bằng 6 ký tự.");


        }
        if(!Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$").matcher(email).matches()){
            edtRgEmail.setError("Vui lòng nhập email chính xác.");


        }
    }
}