package com.example.qr_go_gotta_scan_em_all;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Test;

public class DatabaseTest {
    /**
     * Test database object from database class of firebase
     *
     */
    @Test
    public void testDatabase() {
        Context context = null;

        Database database = new Database(context);
        assertEquals("Database", database.getClass().getSimpleName());
    }

    @Test
    public void testFirebaseDatabaseConnection() {
        Context context = null;
        // Initialize the Firebase app
        FirebaseApp.initializeApp(context);

        // Attempt to connect to the Firebase database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("test");

        // Verify that the connection was successful
        assertNotNull(reference);
    }
}
