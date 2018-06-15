package io.toolisticon.annotationprocessortoolkit.tools.fluentvalidator.impl;

import io.toolisticon.annotationprocessortoolkit.tools.MessagerUtils;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.PlainValidationMessage;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.ValidationMessage;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;

/**
 * Class to store a message and it's message type.
 */
public class FluentValidatorMessage {

    private final Element element;
    private final Diagnostic.Kind kind;
    private final ValidationMessage message;
    private final Object[] args;
    private final AnnotationMirror annotationMirror;
    private final AnnotationValue annotationValue;

    public FluentValidatorMessage(final Element element, Diagnostic.Kind kind, final ValidationMessage message, Object... args) {
        this(element, null, kind, message, args);
    }

    public FluentValidatorMessage(final Element element, final AnnotationMirror annotationMirror, Diagnostic.Kind kind, final ValidationMessage message, Object... args) {
        this(element, annotationMirror, null, kind, message, args);
    }

    public FluentValidatorMessage(final Element element, final AnnotationMirror annotationMirror, final AnnotationValue annotationValue, Diagnostic.Kind kind, final ValidationMessage message, Object... args) {

        this.element = element;
        this.annotationMirror = annotationMirror;
        this.annotationValue = annotationValue;
        this.kind = kind;
        this.message = message;
        this.args = args;

    }


    /**
     * Issues stored compiler message.
     */
    public void issueMessage() {

        if (annotationMirror != null && annotationValue != null) {
            MessagerUtils.getMessagerUtils().printMessage(element, annotationMirror, annotationValue, kind, message, args);
        } else if (annotationMirror != null) {
            MessagerUtils.getMessagerUtils().printMessage(element, annotationMirror, kind, message, args);
        } else {
            MessagerUtils.getMessagerUtils().printMessage(element, kind, message, args);
        }

    }

}
