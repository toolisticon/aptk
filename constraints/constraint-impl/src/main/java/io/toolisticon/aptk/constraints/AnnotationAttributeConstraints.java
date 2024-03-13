package io.toolisticon.aptk.constraints;

import io.toolisticon.aptk.tools.wrapper.AnnotationMirrorWrapper;

import javax.lang.model.element.AnnotationMirror;
import java.util.ArrayList;
import java.util.List;

class AnnotationAttributeConstraints {

    private final String attributeName;
    private final List<AnnotationMirrorWrapper> constraints;

    AnnotationAttributeConstraints(String attributeNumber,List<AnnotationMirrorWrapper> constraints) {
        this.attributeName = attributeNumber;
        this.constraints = constraints;
    }

    String getAttributeName() {
        return attributeName;
    }

    List<AnnotationMirrorWrapper> getConstraints() {
        return constraints;
    }
}
