package io.toolisticon.annotationprocessortoolkit.tools.corematcher;

import io.toolisticon.annotationprocessortoolkit.tools.filter.InclusiveCharacteristicsElementFilter;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.CharacteristicsMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.validator.InclusiveCharacteristicsElementValidator;

import javax.lang.model.element.Element;


/**
 * Convenience class to use just one class which can be used in Fluent validators and filters.
 */
public class InclusiveCharacteristicCoreMatcher<
        ELEMENT extends Element,
        CHARACTERISTIC
        > extends AbstractBaseCoreMatcher{


    private final CharacteristicsMatcher<ELEMENT, CHARACTERISTIC> matcher;

    public InclusiveCharacteristicCoreMatcher(CharacteristicsMatcher<ELEMENT, CHARACTERISTIC> matcher, CoreMatcherValidationMessages defaultValidatorMessage) {
        super(defaultValidatorMessage);
        this.matcher = matcher;
    }

    public CharacteristicsMatcher<ELEMENT, CHARACTERISTIC> getMatcher() {
        return matcher;
    }

    public InclusiveCharacteristicsElementValidator<ELEMENT, CHARACTERISTIC, CharacteristicsMatcher<ELEMENT, CHARACTERISTIC>> getValidator() {
        return new InclusiveCharacteristicsElementValidator<ELEMENT, CHARACTERISTIC, CharacteristicsMatcher<ELEMENT, CHARACTERISTIC>>(matcher, this.getDefaultValidatorMessage());
    }

    public InclusiveCharacteristicsElementFilter<ELEMENT, CHARACTERISTIC, InclusiveCharacteristicsElementValidator<ELEMENT, CHARACTERISTIC, CharacteristicsMatcher<ELEMENT, CHARACTERISTIC>>> getFilter() {
        return new InclusiveCharacteristicsElementFilter<ELEMENT, CHARACTERISTIC, InclusiveCharacteristicsElementValidator<ELEMENT, CHARACTERISTIC, CharacteristicsMatcher<ELEMENT, CHARACTERISTIC>>>(getValidator());
    }


}