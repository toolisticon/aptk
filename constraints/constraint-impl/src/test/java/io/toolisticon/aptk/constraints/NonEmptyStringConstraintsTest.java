package io.toolisticon.aptk.constraints;

import io.toolisticon.aptk.common.ToolingProvider;
import io.toolisticon.aptk.tools.MessagerUtils;
import io.toolisticon.cute.Cute;
import io.toolisticon.cute.PassIn;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;

public class NonEmptyStringConstraintsTest {

    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }


    @interface StringMustBeNonEmpty {

        @NonEmptyString String value();

    }

    @PassIn
    @StringMustBeNonEmpty("ABC")
    static class StringMustBeNonEmpty_Match {

    }

    @PassIn
    @StringMustBeNonEmpty("")
    static class StringMustBeNonEmpty_NoMatch {

    }

    @Test
    public void nonEmptyString_happyPath() {
        Cute.unitTest().when().passInElement().fromClass(StringMustBeNonEmpty_Match.class).intoUnitTest((procEnv, e) -> {

            ToolingProvider.setTooling(procEnv);
            try {
                MatcherAssert.assertThat("Expect true", BasicConstraints.checkConstraints(e));
            } finally {
                ToolingProvider.clearTooling();
            }

        }).thenExpectThat().compilationSucceeds().executeTest();
    }

    @Test
    public void nonEmptyString_failure() {
        Cute.unitTest().when().passInElement().fromClass(StringMustBeNonEmpty_NoMatch.class).intoUnitTest((procEnv, e) -> {

                    ToolingProvider.setTooling(procEnv);
                    try {
                        MatcherAssert.assertThat("Expect false", !BasicConstraints.checkConstraints(e));
                    } finally {
                        ToolingProvider.clearTooling();
                    }

                }).thenExpectThat().compilationFails()
                .andThat().compilerMessage().ofKindError().contains(BasicConstraintsCompilerMessages.NOTEMPTYSTRING_DETECTED_EMPTY_STRING.getCode()).executeTest();
    }


    @interface StringMustBeNonEmpty_InArray {

        @NonEmptyString String[] value();

    }

    @PassIn
    @StringMustBeNonEmpty_InArray({"ABC", "DEF"})
    static class StringMustBeNonEmpty_inArray_Match {

    }

    @PassIn
    @StringMustBeNonEmpty_InArray({"ABC", ""})
    static class StringMustBeNonEmpty_inArray_NoMatch {

    }

    @Test
    public void nonEmptyString_inArray_happyPath() {
        Cute.unitTest().when().passInElement().fromClass(StringMustBeNonEmpty_inArray_Match.class).intoUnitTest((procEnv, e) -> {

            ToolingProvider.setTooling(procEnv);
            try {
                MatcherAssert.assertThat("Expect true", BasicConstraints.checkConstraints(e));
            } finally {
                ToolingProvider.clearTooling();
            }

        }).thenExpectThat().compilationSucceeds().executeTest();
    }

    @Test
    public void nonEmptyString_inArray_failure() {
        Cute.unitTest().when().passInElement().fromClass(StringMustBeNonEmpty_inArray_NoMatch.class).intoUnitTest((procEnv, e) -> {

                    ToolingProvider.setTooling(procEnv);
                    try {
                        MatcherAssert.assertThat("Expect false", !BasicConstraints.checkConstraints(e));
                    } finally {
                        ToolingProvider.clearTooling();
                    }

                }).thenExpectThat().compilationFails()
                .andThat().compilerMessage().ofKindError().contains(BasicConstraintsCompilerMessages.NOTEMPTYSTRING_DETECTED_EMPTY_STRING_IN_ARRAY.getCode()).executeTest();
    }

}
