package de.holisticon.annotationprocessortoolkit.templating.templateblocks;

import de.holisticon.annotationprocessortoolkit.templating.NextDetectedBlockResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Used to distinct between different kind of template blocks.
 */
public enum TemplateBlockType {

    BINDER(false, null),
    PLAIN_TEXT(false, null),
    DYNAMIC_TEXT(true, null),
    FOR(true, "for"),
    IF(true, "if");

    private final boolean usedForBlockSearch;
    private final boolean isControlBlock;
    private final String controlBlockCommand;
    private final Pattern blockDetectionPattern;

    TemplateBlockType(final boolean usedForBlockSearch, final String controlBlockCommand) {

        this.usedForBlockSearch = usedForBlockSearch;
        this.controlBlockCommand = controlBlockCommand;
        isControlBlock = (controlBlockCommand != null);

        blockDetectionPattern = Pattern.compile(isControlBlock ? "[!][<]" + controlBlockCommand + "\\s+(.*?)\\s*>" : "[$]<\\s*((?:\\w|.)*?)\\s*>");

    }

    public static NextDetectedBlockResult getNextBlockType(final String templateString) {

        int bestStartIndex = -1;
        int endIndex = -1;
        TemplateBlockType result = null;
        String attributes = null;

        for (TemplateBlockType templateBlockType : values()) {

            // skip those enum values which represent implicit blocks
            if (!templateBlockType.usedForBlockSearch) {
                continue;
            }

            // check if block can be found
            Matcher matcher = templateBlockType.blockDetectionPattern.matcher(templateString);

            if (matcher.find()) {

                if (result == null) {
                    bestStartIndex = matcher.start();
                    endIndex = matcher.end();
                    result = templateBlockType;
                    attributes = matcher.group(1);
                } else if (bestStartIndex > matcher.start()) {
                    bestStartIndex = matcher.start();
                    endIndex = matcher.end();
                    result = templateBlockType;
                    attributes = matcher.group(1);
                }

            }


        }

        return result != null ? new NextDetectedBlockResult(result, bestStartIndex, endIndex, attributes) : null;

    }

    /**
     * Returns the detection Pattern for a start control block tag.
     *
     * @return the detection pattern for a start control block tag, otherwise null
     */
    public Pattern getStartControlBlockPattern() {
        return isControlBlock ? Pattern.compile("[!][<]" + controlBlockCommand + "\\s+(.*?)\\s*>") : null;
    }

    /**
     * Returns the detection Pattern for a end control block tag.
     *
     * @return the detection pattern for a end control block tag, otherwise null
     */
    public Pattern getEndControlBlockPattern() {
        return isControlBlock ? Pattern.compile("[!][<][/]" + controlBlockCommand + "\\s*>") : null;
    }

    public boolean isControlBlock() {
        return isControlBlock;
    }
}
