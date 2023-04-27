package ${enum.packageName};

import io.toolisticon.aptk.tools.MessagerUtils;
import io.toolisticon.aptk.tools.corematcher.ValidationMessage;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;

public enum ${enum.enumName} implements ValidationMessage{

    !{for compilerMessage : compilerMessages}
    ${compilerMessage.enumValueName}("${enum.codePrefix}_${compilerMessage.calculatedCode}", "${compilerMessage.message}"),
    !{/for};

    /**
     * the message code.
     */
    private final String code;

    /**
     * the message text.
     */
    private final String message;

    /**
     * Constructor.
     *
     * @param code    the message code
     * @param message the message text
     */
    ${enum.enumName}(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Gets the code of the message.
     *
     * @return the message code
     */
    public String getCode() {
        return this.code;
    }

    /**
     * Gets the message text.
     *
     * @return the message text
     */
    public String getMessage() {
        return message;
    }

    // TODO: for loop over info, error, warning, mandatoryWarning
    !{for messageScope : messageScopes}
    /**
     * Writes an ${messageScope} message.
     */
    public void ${messageScope}(Element element, Object... args) {
        MessagerUtils.${messageScope}(element, this, args);
    }

    /**
     * Writes an ${messageScope} message.
     */
    public void ${messageScope}(Element element, AnnotationMirror annotationMirror, Object... args) {
        MessagerUtils.${messageScope}(element, annotationMirror, this, args);
    }

    /**
     * Writes an ${messageScope} message.
     */
    public void ${messageScope}(Element element, AnnotationMirror annotationMirror, AnnotationValue annotationValue, Object... args) {
        MessagerUtils.${messageScope}(element, annotationMirror, annotationValue, this, args);
    }
    !{/for}

}