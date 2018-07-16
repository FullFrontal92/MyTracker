package com.example.adambenyahia.mytracker;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GlobalInfo {
    public static String PhoneNumber="";

    public static void UpdatesInfo(String UserPhone){
        DateFormat df= new SimpleDateFormat("yyyy/MM/dd HH:MM:ss");
        Date date= new Date();
        DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Users").child(UserPhone).child("Updates").setValue(df.format(date).toString());
    }
}
