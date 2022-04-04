package com.dungps19554.gatifymusic;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ThongTin_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin);

        ImageView avt = findViewById(R.id.imgavt);
        avt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(ThongTin_Activity.this);
                dialog.setContentView(R.layout.doiavt_layout);
                dialog.show();
            }
        });
    }
}