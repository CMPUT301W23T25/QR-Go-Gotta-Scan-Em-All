package com.example.qr_go_gotta_scan_em_all;

import static androidx.core.app.ActivityCompat.requestPermissions;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class QrScannerActivityTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class, true, true);

    /**
     * Runs before all tests and creates solo instance.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());

        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        // click on poke ball
        solo.clickOnView(solo.getView(R.id.poke_ball));
        solo.assertCurrentActivity("Wrong Activity", QrScannerActivity.class);
    }

    /**
     * Tests activity switched
     */
    @Test
    public void checkActivitySwitched() {
        solo.assertCurrentActivity("Wrong Activity", QrScannerActivity.class);
    }

    /**
     * Check back button functionality
     */
    @Test
    public void checkBackButton() {
        // click back button
        solo.clickOnView(solo.getView(R.id.qr_scanner_back_btn));
        solo.assertCurrentActivity("Wrong Activity", PokemonAddActivity.class);
    }

    /**
     * Closes the activity after each test
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
