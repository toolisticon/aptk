package io.toolisticon.annotationprocessortoolkit.tools.corematcher;

import io.toolisticon.annotationprocessortoolkit.tools.filter.InclusiveCharacteristicsElementFilter;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.CharacteristicsMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.validator.InclusiveCharacteristicsElementValidator;

import javax.lang.model.element.Element;


/**
 * Convenience class to use just one class which can be used in Fluent validators and filters.
 */
public class InclusiveCharacteristicElementBasedCoreMatcher<
        CHARACTERISTIC
        > extends AbstractBaseCoreMatcher{


    private final CharacteristicsMatcher<Element, CHARACTERISTIC> matcher;

    public InclusiveCharacteristicElementBasedCoreMatcher(CharacteristicsMatcher<Element, CHARACTERISTIC> matcher, CoreMatcherValidationMessages defaultValidatorMessage) {
        super(defaultValidatorMessage);
        this.matcher = matcher;
    }

    public CharacteristicsMatcher<Element, CHARACTERISTIC> getMatcher() {
        return matcher;
    }

    public InclusiveCharacteristicsElementValidator<Element, CHARACTERISTIC, CharacteristicsMatcher<Element, CHARACTERISTIC>> getValidator() {
        return new InclusiveCharacteristicsElementValidator<Element, CHARACTERISTIC, CharacteristicsMatcher<Element, CHARACTERISTIC>>(matcher, this.getDefaultValidatorMessage());
    }

    public InclusiveCharacteristicsElementFilter<Element, CHARACTERISTIC, InclusiveCharacteristicsElementValidator<Element, CHARACTERISTIC, CharacteristicsMatcher<Element, CHARACTERISTIC>>> getFilter() {
        return new InclusiveCharacteristicsElementFilter<Element, CHARACTERISTIC, InclusiveCharacteristicsElementValidator<Element, CHARACTERISTIC, CharacteristicsMatcher<Element, CHARACTERISTIC>>>(getValidator());
    }


}