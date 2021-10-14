package io.toolisticon.aptk.tools.matcher.impl;

import io.toolisticon.aptk.tools.matcher.CriteriaMatcher;

import javax.lang.model.element.Element;
import java.util.regex.Pattern;

/**
 * Class for checking for find matching elements by name and regular expressions.
 */
public class ByNameRegexMatcher implements CriteriaMatcher<Element, String> {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkForMatchingCharacteristic(Element element, String toCheckFor) {
        if (element != null && toCheckFor != null) {

            Pattern pattern = Pattern.compile(toCheckFor);
            return pattern.matcher(element.getSimpleName().toString()).matches();

        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStringRepresentationOfPassedCharacteristic(String toGetStringRepresentationFor) {
        return toGetStringRepresentationFor;
    }

}
