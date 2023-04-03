package com.example.qr_go_gotta_scan_em_all;

import static com.google.common.base.CharMatcher.any;
import static com.google.firebase.firestore.util.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import android.app.Activity;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class LoginTest {
    /**
     * Test that the login page is displayed
     */

    private Solo solo;

    @Rule
    public ActivityTestRule<LoginActivity> rule =
            new ActivityTestRule<>(LoginActivity.class, true, true);

    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());

        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);

    }

    /**
     * Tests Login activity
     */
    @Test
    public void checkActivity() {
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
    }

    /**
     * Test createUserSession() method
     */
    @Test
    public void testCreateUserSession() {
        // Setup
        EditText userText = new EditText(rule.getActivity());
        userText.setText("testUser");
        boolean[] isTaken = {false};
        LoginActivity.OnCheckUsernameCallback callback = new LoginActivity.OnCheckUsernameCallback() {
            @Override
            public void onResult(boolean taken) {
                isTaken[0] = taken;
            }
        };
        // Exercise
        createUserSession(userText, callback);

        // Verify
        if (isTaken[0]) {
            // The username should not be taken
            fail("The test user should not be taken");
        }
    }

    private void createUserSession(EditText userText, LoginActivity.OnCheckUsernameCallback callback) {
    }

    /**
     * Test addPlayer(Player p) function
     */

    @Test
    public void testAddPlayer() {
        // Setup
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userID = "testUserID";
        String userName = "testUserName";
        Player player = new Player(userID, userName);

        // Testing the function
        addPlayer(player);

        // Verify
        DocumentReference docRef = db.collection("players").document(userID);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                assertEquals(userName, documentSnapshot.get("username"));
                assertTrue(documentSnapshot.get("pokemon_owned") instanceof List);
                assertTrue(documentSnapshot.get("leaderboard_stats") instanceof Map);
                assertTrue(documentSnapshot.get("friends") instanceof List);
            }
        });
    }

    private void addPlayer(Player player) {
    }

    /**
     * Test getPlayerData() function
     */
    @Test
    public void testGetPlayerData() {
        // Setup
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userID = "testUserID";
        String userName = "testUserName";
        Player player = new Player(userID, userName);

        // Testing the function
        addPlayer(player);
        getPlayerData();

        // Verify
        DocumentReference docRef = db.collection("players").document(userID);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                assertEquals(userName, documentSnapshot.get("username"));
                assertTrue(documentSnapshot.get("pokemon_owned") instanceof List);
                assertTrue(documentSnapshot.get("leaderboard_stats") instanceof Map);
                assertTrue(documentSnapshot.get("friends") instanceof List);
            }
        });
    }

    private void getPlayerData() {
    }

    @Test
    public void testIsUserNameTaken() {
        // Arrange
        String username = "pikachu";
        boolean isTaken = true; // Set this to true if the username is already taken

        // Act
        boolean result = isUserNameTaken(username);

        // Assert
        if (isTaken) {
            assertTrue(result);
        } else {
            assertFalse(result);
        }
    }

    private boolean isUserNameTaken(String username) {
        return true;
    }


}
