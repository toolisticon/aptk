package io.toolisticon.annotationprocessortoolkit.templating.templateblocks;

import java.util.Map;

/**
 * Block for a plain text template block.
 */
public class PlainTextTemplateBlock implements TemplateBlock {

    private final String content;

    public PlainTextTemplateBlock(String content) {
        this.content = content;
    }

    @Override
    public TemplateBlockType getTemplateBlockType() {
        return TemplateBlockType.PLAIN_TEXT;
    }

    public String getContent(Map<String, Object> variables) {
        return content;
    }
}
