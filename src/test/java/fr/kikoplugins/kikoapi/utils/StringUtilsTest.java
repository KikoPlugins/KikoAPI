package fr.kikoplugins.kikoapi.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StringUtilsTest {

    @Test
    void testCompareSemVer() {
        // Equal versions
        Assertions.assertEquals(0, StringUtils.compareSemVer("1.2.3", "1.2.3"));
        Assertions.assertEquals(0, StringUtils.compareSemVer("1.2", "1.2.0"));
        Assertions.assertEquals(0, StringUtils.compareSemVer("01.002.0003", "1.2.3"));
        // a > b
        Assertions.assertTrue(StringUtils.compareSemVer("2.0.0", "1.0.0") > 0);
        Assertions.assertTrue(StringUtils.compareSemVer("1.1.0", "1.0.0") > 0);
        Assertions.assertTrue(StringUtils.compareSemVer("1.0.1", "1.0.0") > 0);
        // a < b
        Assertions.assertTrue(StringUtils.compareSemVer("1.0.0", "2.0.0") < 0);
        Assertions.assertTrue(StringUtils.compareSemVer("1.0.0", "1.1.0") < 0);
        Assertions.assertTrue(StringUtils.compareSemVer("1.0.0", "1.0.1") < 0);
    }

    @Test
    void testCompareSemVerNullThrows() {
        Assertions.assertThrows(NullPointerException.class, () -> StringUtils.compareSemVer(null, "1.0.0"));
        Assertions.assertThrows(NullPointerException.class, () -> StringUtils.compareSemVer("1.0.0", null));
    }

    @Test
    void testCompareSemVerInvalidFormatThrows() {
        Assertions.assertThrows(NumberFormatException.class, () -> StringUtils.compareSemVer("1.0.a", "1.0.0"));
    }

}
