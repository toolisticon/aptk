package io.toolisticon.aptk.tools.wrapper;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

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
     *
     * @return the accessors wrapped ExecutableElement
     */
    public ExecutableElementWrapper getAccessor() {
        return ExecutableElementWrapper.wrap(this.<ExecutableElement>invokeParameterlessMethodOfElement(RECORD_COMPONENT_ELEMENT_CLASS_NAME, "getAccessor").get());
    }

    /**
     * Re-wraps an ElementWrapper to a RecordComponentElementWrapper.
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
