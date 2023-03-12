package com.example.qr_go_gotta_scan_em_all;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class PokemonAddActivityTest {
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

        // click back button
        solo.clickOnView(solo.getView(R.id.qr_scanner_back_btn));
        solo.assertCurrentActivity("Wrong Activity", PokemonAddActivity.class);
    }

    /**
     * Test the save button
     */
    @Test
    public void testSaveButton() {
        // click on add photo button
        solo.clickOnView(solo.getView(R.id.capture_pokemon_button));

        // TODO: write tests here
    }

    /**
     * Test the release button
     */
    @Test
    public void testReleaseButton() {
        // click on release button
        solo.clickOnView(solo.getView(R.id.release_pokemon_button));

        // TODO: write tests here
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
