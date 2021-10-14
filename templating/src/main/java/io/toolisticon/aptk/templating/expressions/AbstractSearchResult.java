package io.toolisticon.aptk.templating.expressions;

/**
 * Abstract search result for parsing of expressions.
 */
public abstract class AbstractSearchResult <T>{


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
