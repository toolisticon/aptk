package de.holisticon.annotationprocessortoolkit.templating.templateblocks;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * If template block.
 */
public class IfTemplateBlock implements TemplateBlock {

    private final static Pattern ATTRIBUTE_PATTERN = Pattern.compile("\\s*(.+?)\\s*");


    private final String accessPath;
    private final String templateString;

    private TemplateBlockBinder binder;


    public IfTemplateBlock(String attributeString, String templateString) {

        if (attributeString == null || attributeString.trim().isEmpty()) {
            throw new IllegalArgumentException("if command has no attribute string.");
        }

        Matcher matcher = ATTRIBUTE_PATTERN.matcher(attributeString);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("if command has an invalid attribute string.");
        }

        this.accessPath = matcher.group(1);
        this.templateString = templateString;


        binder = new TemplateBlockBinder(templateString);

    }


    @Override
    public TemplateBlockType getTemplateBlockType() {
        return TemplateBlockType.IF;
    }

    @Override
    public String getContent(Map<String, Object> outerVariables) {


        return binder.getContent(outerVariables).toString();

    }


    public TemplateBlockBinder getBinder() {
        return binder;
    }

    public void setBinder(TemplateBlockBinder binder) {
        this.binder = binder;
    }


    public String getAccessPath() {
        return accessPath;
    }

    public String getTemplateString() {
        return templateString;
    }


}
