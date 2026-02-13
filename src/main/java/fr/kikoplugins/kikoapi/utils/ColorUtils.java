package fr.kikoplugins.kikoapi.utils;

import net.kyori.adventure.text.format.TextColor;
import org.jspecify.annotations.NullMarked;

/**
 * Utility class for managing text colors.
 */
@NullMarked
public class ColorUtils {
    private ColorUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Gets the primary color used in the Niveria API.
     *
     * @return The primary TextColor.
     */
    public static TextColor primaryColor() {
        return TextColor.fromHexString("#FC67FA");
    }

    /**
     * Gets the secondary color used in the Niveria API.
     *
     * @return The secondary TextColor.
     */
    public static TextColor secondaryColor() {
        return TextColor.fromHexString("#F4C4F3");
    }
}
