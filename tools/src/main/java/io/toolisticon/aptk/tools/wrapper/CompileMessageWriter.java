package io.toolisticon.aptk.tools.wrapper;

import io.toolisticon.aptk.tools.MessagerUtils;
import io.toolisticon.aptk.tools.corematcher.ValidationMessage;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;

public class CompileMessageWriter {

    public interface CompileMessageWriterStart {
        CompileMessageWriterEnd asError();

        CompileMessageWriterEnd asNote();

        CompileMessageWriterEnd asWarning();

        CompileMessageWriterEnd asMandatoryWarning();
    }


    public interface CompileMessageWriterEnd {
        void write(String message, Object... variables);

        void write(ValidationMessage message, Object... variables);
    }

    private Diagnostic.Kind kind;
    private Element targetElement;
    private AnnotationMirror annotationMirror;
    private AnnotationValue annotationValue;

    class CompileMessageWriterStartImpl implements CompileMessageWriterStart {
        public CompileMessageWriterEnd asError() {
            kind = Diagnostic.Kind.ERROR;
            return new CompileMessageWriterEndImpl();
        }

        public CompileMessageWriterEnd asNote() {
            kind = Diagnostic.Kind.NOTE;
            return new CompileMessageWriterEndImpl();
        }

        public CompileMessageWriterEnd asWarning() {
            kind = Diagnostic.Kind.WARNING;
            return new CompileMessageWriterEndImpl();
        }

        public CompileMessageWriterEnd asMandatoryWarning() {
            kind = Diagnostic.Kind.MANDATORY_WARNING;
            return new CompileMessageWriterEndImpl();
        }
    }

    public class CompileMessageWriterEndImpl implements CompileMessageWriterEnd {
        public void write(String message, Object... variables) {
            if (targetElement != null && annotationMirror != null && annotationValue != null) {
                MessagerUtils.printMessage(targetElement, annotationMirror, annotationValue, kind, message, variables);
            } else if (targetElement != null && annotationMirror != null) {
                MessagerUtils.printMessage(targetElement, annotationMirror, kind, message, variables);
            } else MessagerUtils.printMessage(targetElement, kind, message, variables);

        }

        public void write(ValidationMessage message, Object... variables) {
            if (targetElement != null && annotationMirror != null && annotationValue != null) {
                MessagerUtils.printMessage(targetElement, annotationMirror, annotationValue, kind, message, variables);
            } else if (targetElement != null && annotationMirror != null) {
                MessagerUtils.printMessage(targetElement, annotationMirror, kind, message, variables);
            } else MessagerUtils.printMessage(targetElement, kind, message, variables);
        }
    }

    public static CompileMessageWriterStart at( Element targetElement,
         AnnotationMirror annotationMirror,
         AnnotationValue annotationValue) {
        CompileMessageWriter instance = new CompileMessageWriter();
        instance.targetElement = targetElement;
        instance.annotationMirror = annotationMirror;
        instance.annotationValue = annotationValue;
        return instance.new CompileMessageWriterStartImpl();
    }

    public static CompileMessageWriterStart at( Element targetElement,
                                                AnnotationMirror annotationMirror) {
        CompileMessageWriter instance = new CompileMessageWriter();
        instance.targetElement = targetElement;
        instance.annotationMirror = annotationMirror;
        return instance.new CompileMessageWriterStartImpl();
    }

    public static CompileMessageWriterStart at( Element targetElement) {
        CompileMessageWriter instance = new CompileMessageWriter();
        instance.targetElement = targetElement;
        return instance.new CompileMessageWriterStartImpl();
    }

}
