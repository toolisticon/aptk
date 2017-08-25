package de.holisticon.annotationprocessortoolkit.templating;

import de.holisticon.annotationprocessortoolkit.templating.templateblocks.TemplateBlockType;

/**
 * Used by {@link TemplateBlockType} to return next detected block.
 */
public class NextDetectedBlockResult {

    private final TemplateBlockType templateBlockType;
    private final int beginIndex;
    private final int endIndex;
    private final String attributes;

    public NextDetectedBlockResult(TemplateBlockType templateBlockType, int beginIndex, int endIndex, String attributes) {
        this.templateBlockType = templateBlockType;
        this.beginIndex = beginIndex;
        this.endIndex = endIndex;
        this.attributes = attributes;
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


}
