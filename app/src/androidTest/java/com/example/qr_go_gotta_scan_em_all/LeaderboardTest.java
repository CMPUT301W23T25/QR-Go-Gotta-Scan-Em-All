package com.example.qr_go_gotta_scan_em_all;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.app.Fragment;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class LeaderboardTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class, true, false);

    /**
     * Runs before all tests and creates solo instance.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        Player player = new Player("12345");
        player.setUserName("testPlayer");
        Intent intent = new Intent(InstrumentationRegistry.getInstrumentation().getTargetContext(), MainActivity.class);
        intent.putExtra("player", player);
        rule.launchActivity(intent);

        Log.d("TESTING", ((Player) rule.getActivity().getIntent().getSerializableExtra("player")).getUserName());

        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());

        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        // get the BottomNavigationView
        BottomNavigationView bottomNavigationView = (BottomNavigationView) solo.getView(R.id.btmNavView);

        // get the leaderboard menu item
        View menuItemView = bottomNavigationView.findViewById(R.id.leaderboard);

        // click on the leaderboard button using solo
        solo.clickOnView(menuItemView);
    }
    @Test
    public void checkActivitySwitched() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
    }


}
