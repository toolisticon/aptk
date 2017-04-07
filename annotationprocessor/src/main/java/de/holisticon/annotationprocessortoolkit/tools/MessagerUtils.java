package de.holisticon.annotationprocessortoolkit.tools;

import de.holisticon.annotationprocessortoolkit.internal.FrameworkToolWrapper;

import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;

/**
 * Utility class and wrapper for / of {@link Messager}.
 */
public class MessagerUtils {

    private final Messager messager;

    public MessagerUtils(Messager messager) {
        this.messager = messager;
    }

    public void error(Element e, String message, Object... args) {
        this.printMessage(e, Diagnostic.Kind.ERROR, message, args);
    }

    public void warning(Element e, String message, Object... args) {
        this.printMessage(e, Diagnostic.Kind.WARNING, message, args);
    }

    public void mandatoryWarning(Element e, String message, Object... args) {
        this.printMessage(e, Diagnostic.Kind.MANDATORY_WARNING, message, args);
    }

    public void info(Element e, String message, Object... args) {
        this.printMessage(e, Diagnostic.Kind.NOTE, message, args);
    }

    public void other(Element e, String message, Object... args) {
        this.printMessage(e, Diagnostic.Kind.OTHER, message, args);
    }

    public void error(Element e, AnnotationMirror a, String message, Object... args) {
        this.printMessage(e, a, Diagnostic.Kind.ERROR, message, args);
    }

    public void warning(Element e, AnnotationMirror a, String message, Object... args) {
        this.printMessage(e, a, Diagnostic.Kind.WARNING, message, args);
    }

    public void mandatoryWarning(Element e, AnnotationMirror a, String message, Object... args) {
        this.printMessage(e, a, Diagnostic.Kind.MANDATORY_WARNING, message, args);
    }

    public void info(Element e, AnnotationMirror a, String message, Object... args) {
        this.printMessage(e, a, Diagnostic.Kind.NOTE, message, args);
    }

    public void other(Element e, AnnotationMirror a, String message, Object... args) {
        this.printMessage(e, a, Diagnostic.Kind.OTHER, message, args);
    }

    public void error(Element e, AnnotationMirror a, AnnotationValue v, String message, Object... args) {
        this.printMessage(e, a, v, Diagnostic.Kind.ERROR, message, args);
    }

    public void warning(Element e, AnnotationMirror a, AnnotationValue v, String message, Object... args) {
        this.printMessage(e, a, v, Diagnostic.Kind.WARNING, message, args);
    }

    public void mandatoryWarning(Element e, AnnotationMirror a, AnnotationValue v, String message, Object... args) {
        this.printMessage(e, a, v, Diagnostic.Kind.MANDATORY_WARNING, message, args);
    }

    public void info(Element e, AnnotationMirror a, AnnotationValue v, String message, Object... args) {
        this.printMessage(e, a, v, Diagnostic.Kind.NOTE, message, args);
    }

    public void other(Element e, AnnotationMirror a, AnnotationValue v, String message, Object... args) {
        this.printMessage(e, a, v, Diagnostic.Kind.OTHER, message, args);
    }

    public void printMessage(Element e, Diagnostic.Kind kind, String message, Object... args) {
        messager.printMessage(kind, createMessage(message, args), e);
    }

    public void printMessage(Element e, AnnotationMirror a, Diagnostic.Kind kind, String message, Object... args) {
        messager.printMessage(kind, createMessage(message, args), e, a);
    }

    public void printMessage(Element e, AnnotationMirror a, AnnotationValue v, Diagnostic.Kind kind, String message, Object... args) {
        messager.printMessage(kind, createMessage(message, args), e, a, v);
    }

    public Messager getMessager() {
        return messager;
    }


    public static MessagerUtils getMessagerUtils(FrameworkToolWrapper frameworkToolWrapper) {
        return new MessagerUtils(frameworkToolWrapper.getMessager());
    }

    /**
     * Creates a message by using a custom templating mechanism.
     * Argument placeholder with the following format '${index}' (f.E. ${0} for the first argument) can be placed inside the message string to be replaced by the passed arguments.
     * The placeholders will be replaced by the return value of the toString method invoked on passed arguments.
     * <p/>
     * This approach is much more faulty tolerant as using  String.format.
     *
     * @param message           the message str with placeholders for the passed arguments
     * @param messageParameters the arguments which should be used to replace the placeholders
     * @return The message with replaced placeholders
     */
    public static String createMessage(String message, Object... messageParameters) {

        // use custom message
        String result = message;

        if (messageParameters != null) {

            for (int i = 0; i < messageParameters.length; i++) {
                result = result.replaceAll("\\$\\{" + i + "\\}", messageParameters[i] != null ? messageParameters[i].toString() : "null");
            }
        }

        return result;

    }

}
