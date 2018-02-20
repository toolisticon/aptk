package io.toolisticon.annotationprocessortoolkit.tools.corematcher;

import io.toolisticon.annotationprocessortoolkit.tools.filter.ExclusiveCharacteristicsElementFilter;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.CharacteristicsMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.validator.ExclusiveCharacteristicsElementValidator;

import javax.lang.model.element.Element;

/**
 * Convenience class to use just one class which can be used in Fluent validators and filters.
 */
public class ExclusiveCharacteristicElementBasedCoreMatcher<
        CHARACTERISTIC
        > extends AbstractBaseCoreMatcher{


    private final CharacteristicsMatcher<Element, CHARACTERISTIC> matcher;

    public ExclusiveCharacteristicElementBasedCoreMatcher(CharacteristicsMatcher<Element, CHARACTERISTIC> matcher, CoreMatcherValidationMessages defaultValidatorMessage) {
        super(defaultValidatorMessage);
        this.matcher = matcher;
    }

    public CharacteristicsMatcher<Element, CHARACTERISTIC> getMatcher() {
        return matcher;
    }

    public ExclusiveCharacteristicsElementValidator<Element, CHARACTERISTIC, CharacteristicsMatcher<Element, CHARACTERISTIC>> getValidator() {
        return new ExclusiveCharacteristicsElementValidator<Element, CHARACTERISTIC, CharacteristicsMatcher<Element, CHARACTERISTIC>>(matcher, this.getDefaultValidatorMessage());
    }

    public ExclusiveCharacteristicsElementFilter<Element, CHARACTERISTIC, ExclusiveCharacteristicsElementValidator<Element, CHARACTERISTIC, CharacteristicsMatcher<Element, CHARACTERISTIC>>> getFilter() {
        return new ExclusiveCharacteristicsElementFilter<Element, CHARACTERISTIC, ExclusiveCharacteristicsElementValidator<Element, CHARACTERISTIC, CharacteristicsMatcher<Element, CHARACTERISTIC>>>(getValidator());
    }


}