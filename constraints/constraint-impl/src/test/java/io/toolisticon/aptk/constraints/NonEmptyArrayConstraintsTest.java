package io.toolisticon.aptk.constraints;

import io.toolisticon.aptk.common.ToolingProvider;
import io.toolisticon.aptk.tools.MessagerUtils;
import io.toolisticon.cute.Cute;
import io.toolisticon.cute.PassIn;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;

public class NonEmptyArrayConstraintsTest {

    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }


    @interface ArrayMustBeNonEmpty {

        @NonEmptyArray String[] value();

    }

    @PassIn
    @ArrayMustBeNonEmpty({"ABC"})
    static class ArrayMustBeNonEmpty_Match {

    }

    @PassIn
    @ArrayMustBeNonEmpty({})
    static class ArrayMustBeNonEmpty_NoMatch {

    }

    @Test
    public void nonEmptyArray_happyPath() {
        Cute.unitTest().when().passInElement().fromClass(ArrayMustBeNonEmpty_Match.class).intoUnitTest((procEnv, e) -> {

            ToolingProvider.setTooling(procEnv);
            try {
                MatcherAssert.assertThat("Expect true", BasicConstraints.checkConstraints(e));
            } finally {
                ToolingProvider.clearTooling();
            }

        }).thenExpectThat().compilationSucceeds().executeTest();
    }

    @Test
    public void stringMustMatch_failure() {
        Cute.unitTest().when().passInElement().fromClass(ArrayMustBeNonEmpty_NoMatch.class).intoUnitTest((procEnv, e) -> {

                    ToolingProvider.setTooling(procEnv);
                    try {
                        MatcherAssert.assertThat("Expect false", !BasicConstraints.checkConstraints(e));
                    } finally {
                        ToolingProvider.clearTooling();
                    }

                }).thenExpectThat().compilationFails()
                .andThat().compilerMessage().ofKindError().contains(BasicConstraintsCompilerMessages.NONEMPTYARRAY_DETECTED_EMPTY_ARRAY.getCode()).executeTest();
    }

}
