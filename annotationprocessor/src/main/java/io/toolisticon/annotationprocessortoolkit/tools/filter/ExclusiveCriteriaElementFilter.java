package io.toolisticon.annotationprocessortoolkit.tools.filter;

import io.toolisticon.annotationprocessortoolkit.tools.filter.impl.CriteriaElementFilterImpl;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.CriteriaMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.validator.ExclusiveCriteriaElementValidator;

import javax.lang.model.element.Element;
import java.util.List;


/**
 * Exclusive criteria filter based on exclusive criteria validator.
 *
 * @param <ELEMENT>   The Element type the filter can be applied to
 * @param <CRITERIA>  The criteria type
 * @param <VALIDATOR> The validator type to use
 */
public class ExclusiveCriteriaElementFilter<ELEMENT extends Element, CRITERIA, VALIDATOR extends ExclusiveCriteriaElementValidator<ELEMENT, CRITERIA, CriteriaMatcher<ELEMENT, CRITERIA>>> {

    private final VALIDATOR validator;

    public ExclusiveCriteriaElementFilter(VALIDATOR validator) {
        this.validator = validator;
    }

    /**
     * Filters passed element list. Checks if passed element is annotated with EXACTLY one of the passed criteria.
     *
     * @param elements        the element to check
     * @param criteriaToCheck the annotation types to check for
     * @return true if all passed annotationTypes are present
     */
    public <PARAM_ELEMENT extends ELEMENT> List<PARAM_ELEMENT> filterByOneOf(List<PARAM_ELEMENT> elements, CRITERIA... criteriaToCheck) {

        return filterByOneOf(elements, false, criteriaToCheck);

    }


    /**
     * Filters passed element list. Checks if passed element is annotated with EXACTLY one of the passed criteria.
     *
     * @param elements        the element to check
     * @param invertFilter    invert filter or not
     * @param criteriaToCheck the annotation types to check for
     * @return true if all passed annotationTypes are present
     */
    public <PARAM_ELEMENT extends ELEMENT> List<PARAM_ELEMENT> filterByOneOf(List<PARAM_ELEMENT> elements, boolean invertFilter, CRITERIA... criteriaToCheck) {

        return CriteriaElementFilterImpl.filterByOneOf((CriteriaMatcher<PARAM_ELEMENT, CRITERIA>) validator.getMatcher(), invertFilter, elements, criteriaToCheck);

    }


    /**
     * Filters passed element list. Checks if passed element is annotated with EXACTLY one of the passed criteria.
     *
     * @param elements        the element to check
     * @param criteriaToCheck the annotation types to check for
     * @return true if all passed annotationTypes are present
     */
    public <PARAM_ELEMENT extends ELEMENT> List<PARAM_ELEMENT> filterByNoneOf(List<PARAM_ELEMENT> elements, CRITERIA... criteriaToCheck) {

        return filterByNoneOf(elements, false, criteriaToCheck);

    }


    /**
     * Filters passed element list. Checks if passed element is annotated with EXACTLY one of the passed criteria.
     *
     * @param elements        the element to check
     * @param invertFilter    invert filter or not
     * @param criteriaToCheck the annotation types to check for
     * @return true if all passed annotationTypes are present
     */
    public <PARAM_ELEMENT extends ELEMENT> List<PARAM_ELEMENT> filterByNoneOf(List<PARAM_ELEMENT> elements, boolean invertFilter, CRITERIA... criteriaToCheck) {

        return CriteriaElementFilterImpl.filterByNoneOf((CriteriaMatcher<PARAM_ELEMENT, CRITERIA>) validator.getMatcher(), invertFilter, elements, criteriaToCheck);

    }

}