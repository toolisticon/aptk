package io.toolisticon.annotationprocessortoolkit.tools.filter;

import io.toolisticon.annotationprocessortoolkit.tools.filter.impl.CharacteristicsElementFilterImpl;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.CharacteristicsMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.validator.InclusiveCharacteristicsElementValidator;

import javax.lang.model.element.Element;
import java.util.List;

/**
 * Inclusive characteristic filter based on inclusive characteristic validator.
 */
public class InclusiveCharacteristicsElementFilter<ELEMENT extends Element, CHARACTERISTIC, VALIDATOR extends InclusiveCharacteristicsElementValidator<ELEMENT, CHARACTERISTIC, CharacteristicsMatcher<ELEMENT, CHARACTERISTIC>>> {

    private final VALIDATOR validator;

    public InclusiveCharacteristicsElementFilter(VALIDATOR validator) {
        this.validator = validator;
    }

    /**
     * Filters passed element list. Checks if passed element is annotated with EXACTLY one of the passed characteristics.
     *
     * @param elements               the element to check
     * @param characteristicsToCheck the annotation types to check for
     * @return true if all passed annotationTypes are present
     */
    public <PARAM_ELEMENT extends ELEMENT> List<PARAM_ELEMENT> filterByOneOf(List<PARAM_ELEMENT> elements, CHARACTERISTIC... characteristicsToCheck) {

        return filterByOneOf(elements,false,characteristicsToCheck);

    }


    /**
     * Filters passed element list. Checks if passed element is annotated with EXACTLY one of the passed characteristics.
     *
     * @param elements               the element to check
     * @param invertFilter
     * @param characteristicsToCheck the annotation types to check for
     * @return true if all passed annotationTypes are present
     */
    public <PARAM_ELEMENT extends ELEMENT> List<PARAM_ELEMENT> filterByOneOf(List<PARAM_ELEMENT> elements, boolean invertFilter, CHARACTERISTIC... characteristicsToCheck) {

        return CharacteristicsElementFilterImpl.filterByOneOf((CharacteristicsMatcher<PARAM_ELEMENT, CHARACTERISTIC>) validator.getMatcher(), invertFilter, elements, characteristicsToCheck);

    }



    /**
     * Filters passed element list. Checks if passed element is annotated with EXACTLY one of the passed characteristics.
     *
     * @param elements               the element to check
     * @param characteristicsToCheck the annotation types to check for
     * @return true if all passed annotationTypes are present
     */
    public <PARAM_ELEMENT extends ELEMENT> List<PARAM_ELEMENT> filterByNoneOf(List<PARAM_ELEMENT> elements, CHARACTERISTIC... characteristicsToCheck) {

        return filterByNoneOf(elements,false,characteristicsToCheck);

    }


    /**
     * Filters passed element list. Checks if passed element is annotated with EXACTLY one of the passed characteristics.
     *
     * @param elements               the element to check
     * @param invertFilter
     * @param characteristicsToCheck the annotation types to check for
     * @return true if all passed annotationTypes are present
     */
    public <PARAM_ELEMENT extends ELEMENT> List<PARAM_ELEMENT> filterByNoneOf(List<PARAM_ELEMENT> elements, boolean invertFilter, CHARACTERISTIC... characteristicsToCheck) {

        return CharacteristicsElementFilterImpl.filterByNoneOf((CharacteristicsMatcher<PARAM_ELEMENT, CHARACTERISTIC>) validator.getMatcher(), invertFilter, elements, characteristicsToCheck);

    }

    /**
     * Filters passed element list. Checks if passed element is annotated with EXACTLY one of the passed characteristics.
     *
     * @param elements               the element to check
     * @param characteristicsToCheck the annotation types to check for
     * @return true if all passed annotationTypes are present
     */
    public <PARAM_ELEMENT extends ELEMENT> List<PARAM_ELEMENT> filterByAtLeastOneOf(List<PARAM_ELEMENT> elements, CHARACTERISTIC... characteristicsToCheck) {

        return filterByAtLeastOneOf(elements, false, characteristicsToCheck);

    }


    /**
     * Filters passed element list. Checks if passed element is annotated with EXACTLY one of the passed characteristics.
     *
     * @param elements               the element to check
     * @param invertFilter
     * @param characteristicsToCheck the annotation types to check for
     * @return true if all passed annotationTypes are present
     */
    public <PARAM_ELEMENT extends ELEMENT> List<PARAM_ELEMENT> filterByAtLeastOneOf(List<PARAM_ELEMENT> elements, boolean invertFilter, CHARACTERISTIC... characteristicsToCheck) {

        return CharacteristicsElementFilterImpl.filterByAtLeastOneOf((CharacteristicsMatcher<PARAM_ELEMENT, CHARACTERISTIC>) validator.getMatcher(), invertFilter, elements, characteristicsToCheck);

    }


    /**
     * Filters passed element list. Checks if passed element is annotated with EXACTLY one of the passed characteristics.
     *
     * @param elements               the element to check
     * @param characteristicsToCheck the annotation types to check for
     * @return true if all passed annotationTypes are present
     */
    public <PARAM_ELEMENT extends ELEMENT> List<PARAM_ELEMENT> filterByAllOf(List<PARAM_ELEMENT> elements, CHARACTERISTIC... characteristicsToCheck) {

        return filterByAllOf(elements, false, characteristicsToCheck);

    }


    /**
     * Filters passed element list. Checks if passed element is annotated with EXACTLY one of the passed characteristics.
     *
     * @param elements               the element to check
     * @param invertFilter
     * @param characteristicsToCheck the annotation types to check for
     * @return true if all passed annotationTypes are present
     */
    public <PARAM_ELEMENT extends ELEMENT> List<PARAM_ELEMENT> filterByAllOf(List<PARAM_ELEMENT> elements, boolean invertFilter, CHARACTERISTIC... characteristicsToCheck) {

        return CharacteristicsElementFilterImpl.filterByAllOf((CharacteristicsMatcher<PARAM_ELEMENT, CHARACTERISTIC>) validator.getMatcher(), invertFilter, elements, characteristicsToCheck);

    }


}
