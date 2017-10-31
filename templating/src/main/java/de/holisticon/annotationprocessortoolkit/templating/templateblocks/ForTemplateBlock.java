package de.holisticon.annotationprocessortoolkit.templating.templateblocks;

import de.holisticon.annotationprocessortoolkit.templating.ModelPathResolver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Used to handle for loops in templates.
 */
public class ForTemplateBlock implements TemplateBlock {

    private final static Pattern ATTRIBUTE_PATTERN = Pattern.compile("\\s*(\\w+?)\\s*[:]\\s*((?:\\w|[.])+?)\\s*");


    private final String loopVariableName;
    private final String accessPath;
    private final String templateString;

    private TemplateBlockBinder binder;


    public ForTemplateBlock(String attributeString, String templateString) {

        if (attributeString == null || attributeString.trim().isEmpty()) {
            throw new IllegalArgumentException("for command has no attribute string.");
        }

        Matcher matcher = ATTRIBUTE_PATTERN.matcher(attributeString);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("for command has an invalid attribute string.");
        }

        this.loopVariableName = matcher.group(1);
        this.accessPath = matcher.group(2);
        this.templateString = templateString;


        binder = new TemplateBlockBinder(templateString);

    }


    @Override
    public TemplateBlockType getTemplateBlockType() {
        return TemplateBlockType.FOR;
    }

    @Override
    public String getContent(Map<String, Object> outerVariables) {

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.putAll(outerVariables);

        // get array or List
        Object values = ModelPathResolver.resolveModelPath(outerVariables, accessPath).getValue();

        StringBuilder stringBuilder = new StringBuilder();
        if (values != null) {


            if (values.getClass().isArray()) {


                for (Object value : (Object[]) values) {

                    // now update variables
                    variables.put(loopVariableName, value);
                    stringBuilder.append(binder.getContent(variables));

                }

            } else if (values instanceof List) {

                for (Object value : (List) values) {

                    // now update variables
                    variables.put(loopVariableName, value);
                    stringBuilder.append(binder.getContent(variables));

                }

            }


        }

        return stringBuilder.toString();

    }


    public TemplateBlockBinder getBinder() {
        return binder;
    }

    public void setBinder(TemplateBlockBinder binder) {
        this.binder = binder;
    }

    public String getLoopVariableName() {
        return loopVariableName;
    }

    public String getAccessPath() {
        return accessPath;
    }

    public String getTemplateString() {
        return templateString;
    }
}
