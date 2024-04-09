package io.toolisticon.aptk.tools.wrapper;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

/**
 * Wrapper class for RecordComponentElementWrapper.
 */
public class RecordComponentElementWrapper extends ElementWrapper<Element> {
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
        // doesn't use default value
        return ExecutableElementWrapper.wrap(this.<ExecutableElement>invokeParameterlessMethodOfElement("getAccessor", null));
    }

    public static RecordComponentElementWrapper toRecordComponentElement(ElementWrapper<?> element) {
        if (element == null) {
            return null;
        }
        return RecordComponentElementWrapper.wrap(element.unwrap());
    }

    public static RecordComponentElementWrapper wrap(Element element) {
        if (element == null || !"RECORD_COMPONENT".equals(element.getKind().name())) {
            return null;
        }
        return new RecordComponentElementWrapper(element);
    }


}
