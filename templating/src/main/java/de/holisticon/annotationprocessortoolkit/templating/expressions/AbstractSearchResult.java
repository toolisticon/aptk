package de.holisticon.annotationprocessortoolkit.templating.expressions;

/**
 * Created by tobiasstamann on 30.09.17.
 */
public class AbstractSearchResult <T>{


    private final int startIndex;
    private final int endIndex;
    private final T value;

    public AbstractSearchResult(T value, int startIndex, int endIndex ) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.value = value;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public T getValue() {
        return value;
    }

}
