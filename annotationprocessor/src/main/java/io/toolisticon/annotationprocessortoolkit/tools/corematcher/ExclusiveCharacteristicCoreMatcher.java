package io.toolisticon.annotationprocessortoolkit.tools.corematcher;

import io.toolisticon.annotationprocessortoolkit.tools.filter.ExclusiveCharacteristicsElementFilter;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.CharacteristicsMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.validator.ExclusiveCharacteristicsElementValidator;

import javax.lang.model.element.Element;


/**
 * Convenience class to use just one class which can be used in Fluent validators and filters.
 */
public class ExclusiveCharacteristicCoreMatcher<
        ELEMENT extends Element,
        CHARACTERISTIC
        > extends AbstractBaseCoreMatcher{


    private final CharacteristicsMatcher<ELEMENT, CHARACTERISTIC> matcher;


    public ExclusiveCharacteristicCoreMatcher(CharacteristicsMatcher<ELEMENT, CHARACTERISTIC> matcher, CoreMatcherValidationMessages defaultValidatorMessage) {
        super(defaultValidatorMessage);
        this.matcher = matcher;
    }

    public CharacteristicsMatcher<ELEMENT, CHARACTERISTIC> getMatcher() {
        return matcher;
    }

    public ExclusiveCharacteristicsElementValidator<ELEMENT, CHARACTERISTIC, CharacteristicsMatcher<ELEMENT, CHARACTERISTIC>> getValidator() {
        return new ExclusiveCharacteristicsElementValidator<ELEMENT, CHARACTERISTIC, CharacteristicsMatcher<ELEMENT, CHARACTERISTIC>>(matcher, getDefaultValidatorMessage());
    }

    public ExclusiveCharacteristicsElementFilter<ELEMENT, CHARACTERISTIC, ExclusiveCharacteristicsElementValidator<ELEMENT, CHARACTERISTIC, CharacteristicsMatcher<ELEMENT, CHARACTERISTIC>>> getFilter() {
        return new ExclusiveCharacteristicsElementFilter<ELEMENT, CHARACTERISTIC, ExclusiveCharacteristicsElementValidator<ELEMENT, CHARACTERISTIC, CharacteristicsMatcher<ELEMENT, CHARACTERISTIC>>>(getValidator());
    }


}