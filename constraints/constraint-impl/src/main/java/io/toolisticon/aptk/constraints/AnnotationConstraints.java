package io.toolisticon.aptk.constraints;

import io.toolisticon.aptk.tools.wrapper.AnnotationMirrorWrapper;
import io.toolisticon.aptk.tools.wrapper.TypeElementWrapper;

import java.util.ArrayList;
import java.util.List;

class AnnotationConstraints {

    private final TypeElementWrapper annotationTypeElement;
    private final List<AnnotationMirrorWrapper> constraintsOnAnnotationType = new ArrayList<>();
    private final List<AnnotationAttributeConstraints> constraintsOnAnnotationAttributes = new ArrayList<>();

    AnnotationConstraints(TypeElementWrapper annotationTypeElement) {

        this.annotationTypeElement = annotationTypeElement;

    }

    void addConstraintOnAttribute(AnnotationMirrorWrapper annotationMirror) {
        if (annotationMirror != null) {
            constraintsOnAnnotationType.add(annotationMirror);
        }
    }

    void addConstraintOnAttribute(AnnotationAttributeConstraints annotationAttributeConstraints) {
        if (annotationAttributeConstraints != null) {
            constraintsOnAnnotationAttributes.add(annotationAttributeConstraints);
        }
    }

    public TypeElementWrapper getAnnotationTypeElement() {
        return annotationTypeElement;
    }

    public List<AnnotationMirrorWrapper> getConstraintsOnAnnotationType() {
        return constraintsOnAnnotationType;
    }

    public List<AnnotationAttributeConstraints> getConstraintsOnAnnotationAttributes() {
        return constraintsOnAnnotationAttributes;
    }

    public boolean hasConstraints () {
        return constraintsOnAnnotationType.size() > 0 || constraintsOnAnnotationAttributes.size() > 0;
    }
}
