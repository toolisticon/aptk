package io.toolisticon.annotationprocessortoolkit.templating.templateblocks;

import io.toolisticon.annotationprocessortoolkit.templating.ModelPathResolver;
import io.toolisticon.annotationprocessortoolkit.templating.ParseUtilities;
import io.toolisticon.annotationprocessortoolkit.templating.TemplateProcessor;
import io.toolisticon.annotationprocessortoolkit.templating.expressions.Expression;
import io.toolisticon.annotationprocessortoolkit.templating.expressions.ExpressionParser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IncludeTemplateBlock implements TemplateBlock {

    final static String ATTRIBUTE_NAME_RESOURCE = "resource";
    final static String ATTRIBUTE_NAME_MODEL = "model";

    private final String modelAccessPath;

    private final String templateResource;
    private final String templateString;


    public IncludeTemplateBlock(String attributeString) {

        if (attributeString == null || attributeString.trim().isEmpty()) {
            throw new IllegalArgumentException("include command has no attribute string.");
        }

        Map<String,String> attributeMap = ParseUtilities.parseNamedAttributes(attributeString);

        templateResource = attributeMap.get(ATTRIBUTE_NAME_RESOURCE);
        if (templateResource == null) {
            throw new IllegalArgumentException("you must set resource at include control block");
        }

        modelAccessPath = attributeMap.get(ATTRIBUTE_NAME_MODEL);

        try {
            templateString = ParseUtilities.readResourceToString(templateResource);
        } catch (IOException e) {
            throw new IllegalArgumentException("Didn't found included template resource file : " + templateResource, e);
        }

    }


    @Override
    public TemplateBlockType getTemplateBlockType() {
        return TemplateBlockType.INCLUDE;
    }

    @Override
    public String getContent(Map<String, Object> variables) {

        Map<String, Object> model;
        if (this.modelAccessPath != null) {
            Object values = ModelPathResolver.resolveModelPath(variables, this.modelAccessPath).getValue();

            model = new HashMap<>();
            model.put("model", values);
        } else {
            model = variables;
        }

        return TemplateProcessor.processTemplate(templateString, model);
    }

    public String getTemplateResource() {
        return templateResource;
    }

    public String getTemplateString() {
        return templateString;
    }

    public String getModelAccessPath() {
        return modelAccessPath;
    }
}
