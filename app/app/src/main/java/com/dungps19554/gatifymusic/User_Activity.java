package com.dungps19554.gatifymusic;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.dungps19554.gatifymusic.fragment.home_fragment;

public class User_Activity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        TextView thongtin = findViewById(R.id.txtThongtin);
        TextView doipass = findViewById(R.id.txtDoipass);
        TextView dangxuat = findViewById(R.id.txtDangXuat);
        thongtin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(User_Activity.this,ThongTin_Activity.class);
                startActivity(intent);
            }
        });

        doipass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(User_Activity.this,DoiPass_Activity.class);
                startActivity(intent);
            }
        });
        dangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(User_Activity.this);
                dialog.setContentView(R.layout.dangxuat_layout);
                dialog.show();
            }
        });
    }

}