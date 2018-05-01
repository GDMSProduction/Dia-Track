package com.example.marshall.Diatrack.Classes;

import com.google.firebase.database.FirebaseDatabase;

public class DatabaseStorage {

    // static variable single_instance of type Singleton
    private static DatabaseStorage instance = null;

    private FirebaseDatabase database;

    // private constructor restricted to this class itself
    private DatabaseStorage()
    {
        database = FirebaseDatabase.getInstance();
    }

    // static method to create instance of Singleton class
    public static DatabaseStorage getInstance()
    {
        if (instance == null)
            instance = new DatabaseStorage();

        return instance;
    }
}
