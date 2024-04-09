package io.toolisticon.aptk.tools.wrapper;

import javax.lang.model.element.Element;
import javax.lang.model.element.RecordComponentElement;
import javax.lang.model.element.TypeElement;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Wrapper class for RecordComponentElementWrapper.
 */
public class RecordComponentElementWrapper extends ElementWrapper<RecordComponentElement> {
    private RecordComponentElementWrapper(RecordComponentElement recordComponentElement) {
        super(recordComponentElement);
    }

    /**
     * Gets the enclosing records TypeElement
     * @return the wrapped enclosing records TypeElement
     */
    public TypeElementWrapper getEnclosingRecordTypeElement() {
        return TypeElementWrapper.wrap((TypeElement) element.getEnclosingElement());
    }

    /**
     * Wraps the getAccessor method, but returns a ExecutableElementWrapper
     * @return the accessors wrapped ExecutableElement
     */
    public ExecutableElementWrapper getAccessor() {
        return ExecutableElementWrapper.wrap(element.getAccessor());
    }

    /**
     * Gets the record components for a TypeElementWrapper.
     * @param typeElement the type element wrapper to get the record components for
     * @return A list containing the wrapped RecordComponentElement if they exist, otherwise an empty list.
     */
    public static List<RecordComponentElementWrapper> getRecordComponents(TypeElementWrapper typeElement) {
        if (typeElement == null) {
            return Collections.EMPTY_LIST;
        }
        return getRecordComponents(typeElement.unwrap());
    }

    /**
     * Gets the record components for a TypeElement.
     * @param typeElement the type element wrapper to get the record components for
     * @return A list containing the wrapped RecordComponentElement if they exist, otherwise an empty list.
     */
    public static List<RecordComponentElementWrapper> getRecordComponents(TypeElement typeElement) {
        if (typeElement == null) {
            return Collections.EMPTY_LIST;
        }

        return typeElement.getRecordComponents().stream().map(RecordComponentElementWrapper::wrap).toList();

    }

    public static RecordComponentElementWrapper toRecordComponentElement(Element element) {
        return RecordComponentElementWrapper.wrap((RecordComponentElement) element);
    }

    public static RecordComponentElementWrapper wrap(RecordComponentElement moduleElement) {
        return new RecordComponentElementWrapper(moduleElement);
    }

    public static List<RecordComponentElementWrapper> wrapList(List<RecordComponentElement> moduleElements) {
        return moduleElements.stream().map(RecordComponentElementWrapper::new).collect(Collectors.toList());
    }

}
