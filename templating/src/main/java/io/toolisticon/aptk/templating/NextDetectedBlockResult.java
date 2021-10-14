package io.toolisticon.aptk.templating;

import io.toolisticon.aptk.templating.templateblocks.TemplateBlockType;

/**
 * Used by {@link TemplateBlockType} to return next detected block.
 */
public class NextDetectedBlockResult {

    private final TemplateBlockType templateBlockType;
    private final int beginIndex;
    private final int endIndex;
    private final String attributes;
    private final String content;
    private final String remainingStringToBeProcessed;

    public NextDetectedBlockResult(
            TemplateBlockType templateBlockType,
            int beginIndex,
            int endIndex,
            String attributes,
            String content,
            String remainingStringToBeProcessed) {
        this.templateBlockType = templateBlockType;
        this.beginIndex = beginIndex;
        this.endIndex = endIndex;
        this.attributes = attributes;
        this.content = content;
        this.remainingStringToBeProcessed = remainingStringToBeProcessed;
    }

    public TemplateBlockType getTemplateBlockType() {
        return templateBlockType;
    }

    public int getBeginIndex() {
        return beginIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public String getAttributes() {
        return attributes;
    }

    public String getContent() {
        return content;
    }

    public String getRemainingStringToBeProcessed() {
        return this.remainingStringToBeProcessed;
    }


}
