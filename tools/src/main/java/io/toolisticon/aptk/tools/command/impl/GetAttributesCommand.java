package io.toolisticon.aptk.tools.command.impl;

import io.toolisticon.aptk.tools.BeanUtils;
import io.toolisticon.aptk.tools.BeanUtils.AttributeResult;
import io.toolisticon.aptk.tools.TypeMirrorWrapper;
import io.toolisticon.aptk.tools.command.CommandWithReturnType;

import javax.lang.model.element.TypeElement;
import java.util.HashMap;
import java.util.Map;

/**
 * Get all attributes of  passed TypeElement.
 * attribute = field with adequate getter and setter method
 */
public class GetAttributesCommand implements CommandWithReturnType<TypeElement, AttributeResult[]> {

    private final Map<String, TypeMirrorWrapper> resolvedTypeArgumentMap;

    public GetAttributesCommand(Map<String, TypeMirrorWrapper> resolvedTypeArgumentMap) {
        this.resolvedTypeArgumentMap = resolvedTypeArgumentMap;
    }

    @Override
    public AttributeResult[] execute(TypeElement element) {

        return BeanUtils.getAttributes(element);

    }

    public static GetAttributesCommand createCommand(Map<String, TypeMirrorWrapper> resolvedTypeArgumentMap) {
        return new GetAttributesCommand(resolvedTypeArgumentMap);
    }

    public static GetAttributesCommand createCommand() {
        return createCommand(new HashMap<>());
    }
}
