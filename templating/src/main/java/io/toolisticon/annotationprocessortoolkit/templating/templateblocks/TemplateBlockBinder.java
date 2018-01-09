package io.toolisticon.annotationprocessortoolkit.templating.templateblocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class TemplateBlockBinder implements TemplateBlock {

    private final List<TemplateBlock> templateBlocks = new ArrayList<TemplateBlock>();


    public TemplateBlockBinder( String templateString) {
    }

    @Override
    public TemplateBlockType getTemplateBlockType() {
        return TemplateBlockType.BINDER;
    }

    @Override
    public String getContent(Map<String, Object> variables) {

        StringBuilder stringBuilder = new StringBuilder();

        for (TemplateBlock templateBlock : templateBlocks) {
            stringBuilder.append(templateBlock.getContent(variables));
        }

        return stringBuilder.toString();
    }

    public void addTemplateBlock(TemplateBlock templateBlock) {
        templateBlocks.add(templateBlock);
    }


}
