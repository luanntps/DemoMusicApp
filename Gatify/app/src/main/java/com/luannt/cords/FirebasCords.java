package com.luannt.cords;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebasCords {
    public static final FirebaseFirestore firebase=FirebaseFirestore.getInstance();
    public static final FirebaseAuth mAuth=FirebaseAuth.getInstance();
    public static final CollectionReference MAIN_CHAT_DATABASE= firebase.collection("CHAT");

}
