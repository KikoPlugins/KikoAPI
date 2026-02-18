package fr.kikoplugins.kikoapi.utils;

import net.kyori.adventure.text.format.TextColor;
import org.jspecify.annotations.NullMarked;

/**
 * Utility class for managing text colors.
 */
// Hex values are hardcoded
@SuppressWarnings("DataFlowIssue")
@NullMarked
public class ColorUtils {
    private ColorUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Gets the primary color used in the KikoAPI.
     *
     * @return The primary TextColor.
     */
    public static TextColor getPrimaryColor() {
        return TextColor.fromHexString("#FC67FA");
    }

    /**
     * Gets the secondary color used in the KikoAPI.
     *
     * @return The secondary TextColor.
     */
    public static TextColor getSecondaryColor() {
        return TextColor.fromHexString("#F4C4F3");
    }
}
