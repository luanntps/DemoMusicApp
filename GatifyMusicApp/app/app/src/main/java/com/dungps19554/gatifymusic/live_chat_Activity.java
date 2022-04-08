    package com.dungps19554.gatifymusic;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import static com.dungps19554.gatifymusic.cords.FirebasCords.MAIN_CHAT_DATABASE;

public class live_chat_Activity extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText edtMessage;
    Button btnSendMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_chat);
        edtMessage=findViewById(R.id.edtMessage);
        btnSendMessage=findViewById(R.id.btnSendMessage);
        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
                Toast.makeText(live_chat_Activity.this,"asdasd",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void sendMessage() {
        String message=edtMessage.getText()+"";
        FirebaseUser user=mAuth.getCurrentUser();
        if(!TextUtils.isEmpty(message)){
            //tạo id của tin nhắn bằng cách sử dụng thời gian hiện tại
            Date today=new Date();
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String messageID=format.format(today);
            //
            HashMap<String,Object> messageObj = new HashMap<>();
            messageObj.put("message",message);
            messageObj.put("user_name",user.getDisplayName());
            messageObj.put("timestamp", FieldValue.serverTimestamp());
            messageObj.put("messageID",messageID);
            MAIN_CHAT_DATABASE.document(messageID).set(messageObj).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(live_chat_Activity.this, "success",Toast.LENGTH_SHORT).show();
                        edtMessage.setText("");
                    }else{
                        Toast.makeText(live_chat_Activity.this, task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        Toast.makeText(live_chat_Activity.this, "failed!",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}