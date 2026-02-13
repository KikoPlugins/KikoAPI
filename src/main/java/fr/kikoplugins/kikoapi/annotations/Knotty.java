package fr.kikoplugins.kikoapi.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Indicates that the annotated code/element is really badly designed, poorly implemented,
 * or generally a "knotty" piece of code that may require special attention or refactoring.
 */
@Retention(RetentionPolicy.SOURCE)
public @interface Knotty {
}
