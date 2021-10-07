package io.toolisticon.annotationprocessortoolkit.wrapper.processor;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Unit test for {@link AnnotationWrapperProcessorMessages}.
 * <p>
 */
public class WrapperAnnotationProcessorMessagesTest {

    @Test
    public void test_enum() {

        MatcherAssert.assertThat(AnnotationWrapperProcessorMessages.ERROR_CANT_CREATE_WRAPPER.getCode(), Matchers.is("ANNOTATION_WRAPPER_ERROR_001"));
        MatcherAssert.assertThat(AnnotationWrapperProcessorMessages.ERROR_CANT_CREATE_WRAPPER.getMessage(), Matchers.notNullValue());
    }


}
