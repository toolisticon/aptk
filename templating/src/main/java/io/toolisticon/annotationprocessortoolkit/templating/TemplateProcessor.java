package io.toolisticon.annotationprocessortoolkit.templating;

import io.toolisticon.annotationprocessortoolkit.templating.templateblocks.TemplateBlockBinder;

import java.util.Map;

/**
 * Base class for parsing templates.
 */
public class TemplateProcessor {


    public static String processTemplate(String templateString, Map<String, Object> values) {

        TemplateBlockBinder binder = ParseUtilities.parseString(templateString);
        return binder.getContent(values);

    }

    public static String processTemplateResourceFile(String templateFileName, Map<String, Object> values) {

        String templateString = null;
        try {
            templateString = ParseUtilities.readResourceToString(templateFileName);
        } catch (Exception e) {
            throw new IllegalArgumentException("Cannot open template file '" + templateFileName + "'", e);
        }

        return processTemplate(templateString, values);
    }


}
