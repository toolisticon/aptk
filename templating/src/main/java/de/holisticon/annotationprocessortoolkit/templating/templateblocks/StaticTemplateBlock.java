package de.holisticon.annotationprocessortoolkit.templating.templateblocks;

import java.util.Map;

/**
 * Used to define static template blocks.
 */
public class StaticTemplateBlock implements TemplateBlock {

    private final String content;

    public StaticTemplateBlock(String content) {
        this.content = content;
    }


    @Override
    public TemplateBlockType getTemplateBlockType() {
        return TemplateBlockType.STATIC;
    }

    @Override
    public String getContent(Map<String, Object> variables) {
        return content;
    }
}
