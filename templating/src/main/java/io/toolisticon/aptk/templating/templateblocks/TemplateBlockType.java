package io.toolisticon.aptk.templating.templateblocks;

import io.toolisticon.aptk.templating.NextDetectedBlockResult;
import io.toolisticon.aptk.templating.exceptions.MissingClosingTagException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Used to distinct between different kind of template blocks.
 */
public enum TemplateBlockType {

    BINDER(false),
    PLAIN_TEXT(false),
    DYNAMIC_TEXT(true),
    STATIC(true, "static", false),
    FOR(true, "for", true),
    IF(true, "if", true),
    INCLUDE(true,"include", true);

    private final boolean usedForBlockSearch;
    private final boolean isControlBlock;
    private final Boolean hasAttributes;
    private final String controlBlockCommand;

    private final Pattern blockDetectionPattern;

    TemplateBlockType(final boolean usedForBlockSearch, final String controlBlockCommand, final Boolean hasAttributes) {

        this.usedForBlockSearch = usedForBlockSearch;
        this.controlBlockCommand = controlBlockCommand;
        isControlBlock = (controlBlockCommand != null);
        this.hasAttributes = hasAttributes != null ? hasAttributes : false;

        if (!isControlBlock) {
            blockDetectionPattern = Pattern.compile("[$][{]\\s*((?:\\w|.)*?)\\s*[}]");
        } else {

            if (hasAttributes) {
                blockDetectionPattern = Pattern.compile("[!][{]" + controlBlockCommand + "\\s+(.*?)\\s*[}]");
            } else {
                blockDetectionPattern = Pattern.compile("[!][{]" + controlBlockCommand + "\\s*[}]");
            }

        }


    }

    TemplateBlockType(final boolean usedForBlockSearch) {

        this(usedForBlockSearch, null, null);

    }


    public static NextDetectedBlockResult getNextBlock(final String templateString) {

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
                    if (templateBlockType.hasAttributes) {
                        attributes = matcher.group(1);
                    }
                } else if (bestStartIndex > matcher.start()) {
                    bestStartIndex = matcher.start();
                    endIndex = matcher.end();
                    result = templateBlockType;
                    if (templateBlockType.hasAttributes) {
                        attributes = matcher.group(1);
                    }
                }

            }


        }

        String content = null;
        String remainingString = null;
        if (result != null && result.usedForBlockSearch) {

            String templateStringAfterStartTag = templateString.substring(endIndex);

            if (STATIC == result) {

                // just get the next closing tag
                Matcher staticEndTagMatcher = STATIC.getEndControlBlockPattern().matcher(templateStringAfterStartTag);
                if (staticEndTagMatcher.find()) {
                    content = templateStringAfterStartTag.substring(0, staticEndTagMatcher.start());
                    remainingString = templateStringAfterStartTag.substring(staticEndTagMatcher.end());
                } else {
                    throw new MissingClosingTagException("Cannot find closing tag for " + result.name() + " control block");
                }


            } else if (result.isControlBlock()) {


                // control blocks can be wrapped and be included in static blocks, so find first end tag not wrapped in a static block
                Matcher endControlBlockMatcher = result.getEndControlBlockPattern().matcher(templateStringAfterStartTag);
                if (endControlBlockMatcher.find()) {

                    while (controlBlockEmbeddedInStaticBlock(templateStringAfterStartTag, endControlBlockMatcher.start())) {

                        // stop if no end block can be found
                        if (!endControlBlockMatcher.find(endControlBlockMatcher.end())) {
                            throw new MissingClosingTagException("Cannot find closing tag for " + result.name() + " control block");
                        }

                    }

                }

                Matcher startControlBlockMatcher = null;
                while (true) {

                    int startMatcherStartIndex = startControlBlockMatcher != null ? startControlBlockMatcher.end() : 0;


                    // check for given start matcher in span beginning to current end tag
                    String tmpContentStr = templateStringAfterStartTag.substring(0, endControlBlockMatcher.start());
                    startControlBlockMatcher = result.getStartControlBlockPattern().matcher(tmpContentStr);

                    boolean foundStartTagInbetween = true;
                    if (startControlBlockMatcher.find(startMatcherStartIndex)) {

                        while (controlBlockEmbeddedInStaticBlock(tmpContentStr, startControlBlockMatcher.start())) {

                            // stop if no end block can be found
                            if (!startControlBlockMatcher.find(startControlBlockMatcher.end())) {
                                // no start tag in between
                                foundStartTagInbetween = false;
                                break;
                            }

                        }

                    } else {
                        foundStartTagInbetween = false;
                    }

                    if (foundStartTagInbetween) {

                        if (endControlBlockMatcher.find(endControlBlockMatcher.end())) {

                            while (controlBlockEmbeddedInStaticBlock(templateStringAfterStartTag, endControlBlockMatcher.start())) {

                                // stop if no end block can be found
                                if (!endControlBlockMatcher.find(endControlBlockMatcher.end())) {
                                    throw new MissingClosingTagException("Cannot find closing tag for " + result.name() + " control block");
                                }

                            }

                        }

                    } else {

                        // Abort while
                        content = templateStringAfterStartTag.substring(0, endControlBlockMatcher.start());
                        remainingString = templateStringAfterStartTag.substring(endControlBlockMatcher.end());
                        break;

                    }


                }

            }


        }


        return result != null ? new NextDetectedBlockResult(result, bestStartIndex, endIndex, attributes, content, remainingString) : null;

    }


    protected static boolean controlBlockEmbeddedInStaticBlock(String templateString, int startIndex) {

        // check occurrence of start / closing static tags before the tag
        String searchTemplateString = templateString.substring(0, startIndex);

        int lastStartStaticTagIndex = getStartIndexOfLastMatch(searchTemplateString, TemplateBlockType.STATIC.getStartControlBlockPattern());
        int lastEndTagIndex = getStartIndexOfLastMatch(searchTemplateString, TemplateBlockType.STATIC.getEndControlBlockPattern());


        return lastStartStaticTagIndex >= 0 && (lastEndTagIndex < 0 || (lastEndTagIndex >= 0 && lastEndTagIndex < lastStartStaticTagIndex));


    }


    private static int getStartIndexOfLastMatch(String text, Pattern pattern) {

        Matcher matcher = pattern.matcher(text);

        int index = -1;
        while (matcher.find(index + 1)) {
            index = matcher.start();
        }
        return index;

    }


    /**
     * Returns the detection Pattern for a start control block tag.
     *
     * @return the detection pattern for a start control block tag, otherwise null
     */
    public Pattern getStartControlBlockPattern() {
        return isControlBlock ? this.blockDetectionPattern : null;
    }

    /**
     * Returns the detection Pattern for a end control block tag.
     *
     * @return the detection pattern for a end control block tag, otherwise null
     */
    public Pattern getEndControlBlockPattern() {
        return isControlBlock ? Pattern.compile("[!][{][/]" + controlBlockCommand + "\\s*[}]") : null;
    }

    public boolean isControlBlock() {
        return isControlBlock;
    }
}
