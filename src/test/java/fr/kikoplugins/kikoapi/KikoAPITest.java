package fr.kikoplugins.kikoapi;

import fr.kikoplugins.kikoapi.mock.MockBukkitHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;

public class KikoAPITest {

    private ServerMock server;
    private KikoAPI kiko;

    @BeforeEach
    public void setUp() {
        this.server = MockBukkitHelper.safeMock();
        this.kiko = MockBukkit.load(KikoAPI.class);
    }

    @AfterEach
    public void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    public void testPluginIfEnabled() {
        Assertions.assertTrue(kiko.isEnabled());
    }

    @Test
    public void testIfPluginIsUnitTest() {
        Assertions.assertTrue(KikoAPI.isUnitTest());
    }

}
