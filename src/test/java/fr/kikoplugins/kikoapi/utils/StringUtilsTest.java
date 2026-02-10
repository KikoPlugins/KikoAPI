package fr.kikoplugins.kikoapi.utils;

import fr.kikoplugins.kikoapi.KikoAPI;
import fr.kikoplugins.kikoapi.mock.MockBukkitHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;

public class StringUtilsTest {

    @BeforeEach
    public void setUp() {
        MockBukkitHelper.safeMock();
        MockBukkit.load(KikoAPI.class);
    }

    @AfterEach
    public void tearDown() {
        MockBukkitHelper.safeUnmock();
    }

    @Test
    public void testCompareSemVer() {
        Assertions.assertEquals(0, StringUtils.compareSemVer("1.2.3", "1.2.3"));
        Assertions.assertEquals(0, StringUtils.compareSemVer("1.2", "1.2.0"));
        Assertions.assertEquals(0, StringUtils.compareSemVer("01.002.0003", "1.2.3"));
    }

}
