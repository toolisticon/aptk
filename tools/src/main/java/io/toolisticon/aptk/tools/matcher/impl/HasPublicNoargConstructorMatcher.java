package io.toolisticon.aptk.tools.matcher.impl;

import io.toolisticon.aptk.tools.AnnotationUtils;
import io.toolisticon.aptk.tools.TypeUtils;
import io.toolisticon.aptk.tools.corematcher.CoreMatchers;
import io.toolisticon.aptk.tools.fluentfilter.FluentElementFilter;
import io.toolisticon.aptk.tools.matcher.ImplicitMatcher;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * Implicit matcher that checks if a passed Element's TypeMirror TypeElement has a public noarg constructor.
 */
public class HasPublicNoargConstructorMatcher implements ImplicitMatcher<Element> {

    @Override
    public boolean check(Element element) {

        if (element == null) {
            return false;
        }


        // check if underlying TypeElement can be found for element
        TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(element.asType());
        if (typeElement == null) {
            return false;
        }

        // Must have just the default noarg constructor or an explicit
        FluentElementFilter<ExecutableElement> fluentElementFilter = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                .applyFilter(CoreMatchers.IS_CONSTRUCTOR);


        // Check for NoArgConstructor
        boolean hasLombokNoArgConstructor = AnnotationUtils.getAnnotationMirror(typeElement, "lombok.NoArgsConstructor") != null;
        boolean hasLombokAllArgsConstructor = AnnotationUtils.getAnnotationMirror(typeElement, "lombok.AllArgsConstructor") != null;
        boolean hasLombokRequiredArgsConstructor = AnnotationUtils.getAnnotationMirror(typeElement, "lombok.RequiredArgsConstructor") != null;


        return (fluentElementFilter.isEmpty() && !hasLombokAllArgsConstructor && !hasLombokRequiredArgsConstructor)
                || hasLombokNoArgConstructor
                || fluentElementFilter
                .applyFilter(CoreMatchers.HAS_NO_PARAMETERS)
                .applyFilter(CoreMatchers.BY_MODIFIER).filterByOneOf(Modifier.PUBLIC)
                .hasSingleElement();

    }

}
