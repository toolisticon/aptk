package de.holisticon.annotationprocessortoolkit.templating.templateblocks;

import java.util.Map;

/**
 * Interface to handle different kind of template blocks.
 */

public interface TemplateBlock {

    /**
     * Used to determine template block type.
     *
     * @return the type of the template block
     */
    TemplateBlockType getTemplateBlockType();

    /**
     * Gets string representation for this template block.
     *
     * @return the string representation of this block.
     */
    String getContent(Map<String, Object> variables);

}
