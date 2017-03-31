package de.holisticon.annotationprocessor.filter;

import de.holisticon.annotationprocessor.tools.ElementUtils;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import java.lang.annotation.Annotation;
import java.util.List;


/**
 * Fluent filter utility class to filter lists of Elements.
 * Each filter operation produces a FluentElementFilter instance.
 *
 * @param <T>
 */
public class FluentElementFilter<T extends Element> {

    private final List<T> result;

    public FluentElementFilter(List<T> elementList) {
        result = elementList;
    }

    public FluentElementFilter<T> filterByNames(String... names) {
        return new FluentElementFilter<T>(ElementUtils.getElementUtils().filterElementListByName(result, names));
    }

    public FluentElementFilter<T> filterByNameWithRegularExpressions(String... regularExpressions) {
        return new FluentElementFilter<T>(ElementUtils.getElementUtils().filterElementsByNameWithRegularExpression(result, regularExpressions));
    }

    public FluentElementFilter<T> filterByKinds(ElementKind... kinds) {
        return new FluentElementFilter<T>(ElementUtils.getElementUtils().filterElementListByKind(result, kinds));
    }

    public FluentElementFilter<T> filterByModifiers(Modifier... modifiers) {
        return new FluentElementFilter<T>(ElementUtils.getElementUtils().filterElementListByModifier(result, modifiers));
    }

    public FluentElementFilter<T> filterByAnnotation(Class<? extends Annotation>... annotations) {
        return new FluentElementFilter<T>(ElementUtils.getElementUtils().filterElementListByAnnotation(result, annotations));
    }

    public FluentElementFilter<T> inverseFilterByNames(String... names) {
        return new FluentElementFilter<T>(ElementUtils.getElementUtils().inverseFilterElementListByName(result, names));
    }

    public FluentElementFilter<T> inverseFilterByKinds(ElementKind... kinds) {
        return new FluentElementFilter<T>(ElementUtils.getElementUtils().inverseFilterElementListByKind(result, kinds));
    }

    public FluentElementFilter<T> inverseFilterByAnnotation(Class<? extends Annotation>... annotations) {
        return new FluentElementFilter<T>(ElementUtils.getElementUtils().inverseFilterElementListByAnnotation(result, annotations));
    }

    public List<T> getResult() {
        return result;
    }

    public boolean isEmpty() {
        return result.isEmpty();
    }

    public boolean hasSingleResult() {
        return result.size() == 1;
    }

    public boolean hasMultipleRecords() {
        return result.size() > 1;
    }

    public boolean hasSize(int size) {
        return result.size() == size;
    }

    public int getResultSize() {
        return result.size();
    }

}