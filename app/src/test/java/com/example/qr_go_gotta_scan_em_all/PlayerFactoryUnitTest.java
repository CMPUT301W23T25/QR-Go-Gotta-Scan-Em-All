package com.example.qr_go_gotta_scan_em_all;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import android.content.Context;
import android.provider.Settings;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

// Based on answer by fitu on StackOverflow:
// https://stackoverflow.com/a/40023848
@RunWith(PowerMockRunner.class)
@PrepareForTest({Settings.Secure.class})
@PowerMockIgnore({"javax.net.ssl.*", "javax.security.auth.x500.*"})
public class PlayerFactoryUnitTest {
    @Test
    public void testGeneratePlayer() {
        String androidId = "test_android_id";

        // Mock the context and the Android ID
        Context context = mock(Context.class);
        mockStatic(Settings.Secure.class);
        when(Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID))
                .thenReturn(androidId);

        // Create a PlayerFactory
        PlayerFactory playerFactory = new PlayerFactory(context);

        // Generate a player
        Player player = playerFactory.generatePlayer();

        assertEquals(androidId, player.getUserId());
    }

    @Test
    public void testGetUserId() {
        String androidId = "test_android_id";

        // Mock the context and the Android ID
        Context context = mock(Context.class);
        mockStatic(Settings.Secure.class);
        when(Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID))
                .thenReturn(androidId);

        // Create a PlayerFactory
        PlayerFactory playerFactory = new PlayerFactory(context);

        // Get the user ID
        String userId = playerFactory.getUserId();

        assertEquals(androidId, userId);
    }
}
