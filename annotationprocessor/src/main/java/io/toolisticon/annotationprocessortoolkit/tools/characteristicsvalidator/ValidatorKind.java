package io.toolisticon.annotationprocessortoolkit.tools.characteristicsvalidator;

/**
 * Validator type to handle different validators kinds programmatically more easily.
 */
public enum ValidatorKind {

    /**
     * Must match all criteria.
     */
    ALL_OF,
    /**
     * Must match at least one criteria.
     */
    AT_LEAST_ONE_OF,
    /**
     * Must match exactly one criteria.
     */
    ONE_OF,
    /**
     * Must match no criteria.
     */
    NONE_OF;

}
