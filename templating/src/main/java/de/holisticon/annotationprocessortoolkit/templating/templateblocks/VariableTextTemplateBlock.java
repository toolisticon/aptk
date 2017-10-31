package de.holisticon.annotationprocessortoolkit.templating.templateblocks;

import de.holisticon.annotationprocessortoolkit.templating.ModelPathResolver;
import de.holisticon.annotationprocessortoolkit.templating.ParseUtilities;

import java.util.Map;

/**
 *
 */
public class VariableTextTemplateBlock implements TemplateBlock {


    private final String accessPath;

    public VariableTextTemplateBlock(String accessPath) {
        this.accessPath = accessPath;
    }


    @Override
    public TemplateBlockType getTemplateBlockType() {
        return TemplateBlockType.DYNAMIC_TEXT;
    }

    @Override
    public String getContent(Map<String, Object> variables) {

        ModelPathResolver.ResolvedModelPathResult valueToPrint = ModelPathResolver.resolveModelPath(variables, accessPath);
        return valueToPrint != null && valueToPrint.getValue() != null ? valueToPrint.getValue().toString() : null;

    }
}

