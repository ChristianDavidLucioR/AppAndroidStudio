package com.example.gestiondeempleados.App;

import com.google.firebase.database.FirebaseDatabase;

public class MyFirebase  extends android.app.Application {

    public void onCreate () {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
