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
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.luannt.R;
import com.luannt.model.User;
import com.luannt.service.ServiceAPI;

import java.util.regex.Pattern;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistryActivity extends AppCompatActivity {
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
                Intent intent = new Intent(RegistryActivity.this, MainActivity.class);
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
                                        */
                                        User user=new User(username,email,"no","01-01-0001","https://i.ibb.co/JByPCsZ/avt.png");
                                        createUser(user);
                                        Toast.makeText(RegistryActivity.this,"????ng k?? th??nh c??ng",Toast.LENGTH_SHORT).show();
                                        pbProgress.setVisibility(View.GONE);
                                        edtRgEmail.setText("");
                                        edtRgUsername.setText("");
                                        edtRgPassword.setText("");
                                       /*         }
                                                else {
                                                    Toast.makeText(DangKiActivity.this, "????ng k?? th???t b???i", Toast.LENGTH_SHORT).show();
                                                    pbProgress.setVisibility(View.GONE);
                                                }
                                            }
                                        });*/
                                    } else {
                                        Toast.makeText(RegistryActivity.this, "T??i kho???n ho???c Email ???? t???n t???i ho???c vui l??ng ki???m tra l???i k???t n???i m???ng.", Toast.LENGTH_SHORT).show();
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


    public void createUser(User user){
        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(ServiceAPI.BASE_SERVICE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);
        new CompositeDisposable()
                .add(requestInterface.CreateUser(user)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe() );
    }


    private void registration(){
        String email=edtRgEmail.getText().toString().trim();
        String username=edtRgUsername.getText().toString().trim();
        String password=edtRgPassword.getText().toString().trim();
        if(username.isEmpty()){
            edtRgUsername.setError("Username kh??ng ???????c b??? tr???ng");

        }
        if(password.isEmpty()){
            edtRgPassword.setError("M???t kh???u kh??ng ???????c b??? tr???ng");


        }
        if(email.isEmpty()){
            edtRgEmail.setError("Email kh??ng ???????c b??? tr???ng.");


        }
        if(password.length()<6){
            edtRgPassword.setError("M???t kh???u ph???i l???n h??n ho???c b???ng 6 k?? t???.");


        }
        if(!Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$").matcher(email).matches()){
            edtRgEmail.setError("Vui l??ng nh???p email ch??nh x??c.");


        }
    }
}