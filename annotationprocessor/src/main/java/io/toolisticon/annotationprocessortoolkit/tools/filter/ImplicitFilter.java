package io.toolisticon.annotationprocessortoolkit.tools.filter;

import io.toolisticon.annotationprocessortoolkit.tools.matcher.ImplicitMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.validator.ImplicitValidator;

import javax.lang.model.element.Element;
import java.util.ArrayList;
import java.util.List;

/**
 * Implicit filter based on Implicit validator.
 */
public class ImplicitFilter<ELEMENT extends Element, VALIDATOR extends ImplicitValidator<ELEMENT, ImplicitMatcher<ELEMENT>>> {

    private final VALIDATOR validator;

    public ImplicitFilter(VALIDATOR validator) {
        this.validator = validator;
    }

    /**
     * Filters list of elements by the implicit criteria.
     * @param toFilter the element list to filter
     * @return the filtered list or an empty list if either validator or element list are null
     */
    public List<ELEMENT> filter(List<ELEMENT> toFilter) {
        return filter(toFilter, false);
    }

    /**
     * Filters list of elements by the implicit criteria. Allows to do an inverted filtering.
     * @param toFilter the element list to filter
     * @param invertFilter do invert filtering or not
     * @return the filtered list or an empty list if either validator or element list are null
     */
    public List<ELEMENT> filter(List<ELEMENT> toFilter, boolean invertFilter) {

        List<ELEMENT> result = new ArrayList<ELEMENT>();

        if (toFilter != null && validator != null) {

            for (ELEMENT element : toFilter) {

                if ((!invertFilter && validator.validate(element)) || (invertFilter && !validator.validate(element)) ){
                    result.add(element);
                }

            }

        }

        return result;

    }

}
