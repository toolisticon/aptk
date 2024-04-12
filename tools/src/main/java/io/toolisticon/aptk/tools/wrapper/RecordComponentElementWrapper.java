package io.toolisticon.aptk.tools.wrapper;

import io.toolisticon.aptk.tools.corematcher.AptkCoreMatchers;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.util.List;

/**
 * Wrapper class for RecordComponentElementWrapper.
 */
public class RecordComponentElementWrapper extends ElementWrapper<Element> {

    private final static String RECORD_COMPONENT_ELEMENT_CLASS_NAME = "javax.lang.model.element.RecordComponentElement";


    private RecordComponentElementWrapper(Element recordComponentElement) {
        super(recordComponentElement);
    }

    /**
     * Gets the enclosing records TypeElement
     *
     * @return the wrapped enclosing records TypeElement
     */
    public TypeElementWrapper getEnclosingRecordTypeElement() {
        return TypeElementWrapper.wrap((TypeElement) element.getEnclosingElement());
    }

    /**
     * Wraps the getAccessor method, but returns a ExecutableElementWrapper
     * !!! WARNING THERE SEEMS TO BE DIFFERENT BEHAVIOR BETWEEN DIFFERENT JDK DISTRIBUTIONS !!!
     * So it will look up the accessor manually if necessary
     * @return the accessors wrapped ExecutableElement, might return null if even the workaround doesn't work
     */
    public ExecutableElementWrapper getAccessor() {
        // safe to call since it's guaranteed that the wrapped element is a RecordComponentElement
        ExecutableElement executableElement = this.<ExecutableElement>invokeParameterlessMethodOfElement(RECORD_COMPONENT_ELEMENT_CLASS_NAME, "getAccessor");

        return executableElement != null ? ExecutableElementWrapper.wrap(executableElement) : determineAccessorWorkaround();
    }

    /**
     * Gets the related field for the record compoent.
     * @return the wrapped of the VariableElement of the related field
     */
    public VariableElementWrapper getField() {
        List<VariableElement> results = this.getEnclosingRecordTypeElement().filterEnclosedElements()
                .applyFilter(AptkCoreMatchers.IS_FIELD)
                .applyFilter(AptkCoreMatchers.BY_NAME).filterByOneOf(getSimpleName())
                .getResult();

        return results.isEmpty() ? null : VariableElementWrapper.wrap(results.get(0));
    }

    private ExecutableElementWrapper determineAccessorWorkaround(){
        List<ExecutableElement> results = this.getEnclosingRecordTypeElement().filterEnclosedElements()
                .applyFilter(AptkCoreMatchers.IS_METHOD)
                .applyFilter(AptkCoreMatchers.BY_NAME).filterByOneOf(getSimpleName())
                .applyFilter(AptkCoreMatchers.HAS_NO_PARAMETERS)
                .getResult();

        return results.isEmpty() ? null : ExecutableElementWrapper.wrap(results.get(0));
    }

    /**
     * Re-wraps an ElementWrapper to a RecordComponentElementWrapper.
     *
     * @param element the wrapper to re-wrap
     * @return The RecordComponentElementWrapper or null if the passed ElementWrapper doesn't wrap a RecordComponentElement
     */
    public static RecordComponentElementWrapper toRecordComponentElement(ElementWrapper<?> element) {
        if (element == null) {
            return null;
        }
        return RecordComponentElementWrapper.wrap(element.unwrap());
    }

    /**
     * Wraps an element with the RecordComponentElementWrapper.
     *
     * @param element the element to wrap
     * @return the wrapped element, or null if passed element isn't an RecordComponentElement
     */
    public static RecordComponentElementWrapper wrap(Element element) {
        if (element == null || !"RECORD_COMPONENT".equals(element.getKind().name())) {
            return null;
        }
        return new RecordComponentElementWrapper(element);
    }


}
