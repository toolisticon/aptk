package io.toolisticon.aptk.constraints;

import io.toolisticon.aptk.common.ToolingProvider;
import io.toolisticon.aptk.tools.MessagerUtils;
import io.toolisticon.cute.Cute;
import io.toolisticon.cute.PassIn;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for {@link WithIntegerInBoundsConstraintImpl}.
 */
public class WithIntegerInBoundsConstraintsTest {

    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }

    @interface WithInclusiveUpperAndLowerBound {

        @WithIntegerInBounds(lowerBound = 5, upperBound = 10) int value();

    }

    @PassIn
    @WithInclusiveUpperAndLowerBound(8)
    static class WithInclusiveUpperAndLowerBound_mid_Match {

    }

    @PassIn
    @WithInclusiveUpperAndLowerBound(5)
    static class WithInclusiveUpperAndLowerBound_low_Match {

    }

    @PassIn
    @WithInclusiveUpperAndLowerBound(10)
    static class WithInclusiveUpperAndLowerBound_up_Match {

    }

    @Test
    public void inclusive_mid_happyPath() {
        Cute.unitTest().when().passInElement().fromClass(WithInclusiveUpperAndLowerBound_mid_Match.class).intoUnitTest((procEnv, e) -> {

            ToolingProvider.setTooling(procEnv);
            try {
                MatcherAssert.assertThat("Expect true", BasicConstraints.checkConstraints(e));
            } finally {
                ToolingProvider.clearTooling();
            }

        }).thenExpectThat().compilationSucceeds().executeTest();
    }

    @Test
    public void inclusive_low_happyPath() {
        Cute.unitTest().when().passInElement().fromClass(WithInclusiveUpperAndLowerBound_low_Match.class).intoUnitTest((procEnv, e) -> {

            ToolingProvider.setTooling(procEnv);
            try {
                MatcherAssert.assertThat("Expect true", BasicConstraints.checkConstraints(e));
            } finally {
                ToolingProvider.clearTooling();
            }

        }).thenExpectThat().compilationSucceeds().executeTest();
    }

    @Test
    public void inclusive_up_happyPath() {
        Cute.unitTest().when().passInElement().fromClass(WithInclusiveUpperAndLowerBound_up_Match.class).intoUnitTest((procEnv, e) -> {

            ToolingProvider.setTooling(procEnv);
            try {
                MatcherAssert.assertThat("Expect true", BasicConstraints.checkConstraints(e));
            } finally {
                ToolingProvider.clearTooling();
            }

        }).thenExpectThat().compilationSucceeds().executeTest();
    }

    @interface WithExclusiveUpperAndLowerBound {

        @WithIntegerInBounds(lowerBound = 5, inclusiveLowerBound = false, upperBound = 10, inclusiveUpperBound = false) int value();

    }

    @PassIn
    @WithExclusiveUpperAndLowerBound(8)
    static class WithExclusiveUpperAndLowerBound_mid_Match {

    }

    @PassIn
    @WithExclusiveUpperAndLowerBound(5)
    static class WithExclusiveUpperAndLowerBound_low_Match {

    }

    @PassIn
    @WithExclusiveUpperAndLowerBound(10)
    static class WithExclusiveUpperAndLowerBound_up_Match {

    }

    @Test
    public void exclusive_mid_happyPath() {
        Cute.unitTest().when().passInElement().fromClass(WithExclusiveUpperAndLowerBound_mid_Match.class).intoUnitTest((procEnv, e) -> {

            ToolingProvider.setTooling(procEnv);
            try {
                MatcherAssert.assertThat("Expect true", BasicConstraints.checkConstraints(e));
            } finally {
                ToolingProvider.clearTooling();
            }

        }).thenExpectThat().compilationSucceeds().executeTest();
    }

    @Test
    public void exclusive_low_happyPath() {
        Cute.unitTest().when().passInElement().fromClass(WithExclusiveUpperAndLowerBound_low_Match.class).intoUnitTest((procEnv, e) -> {

            ToolingProvider.setTooling(procEnv);
            try {
                MatcherAssert.assertThat("Expect false", !BasicConstraints.checkConstraints(e));
            } finally {
                ToolingProvider.clearTooling();
            }

        }).thenExpectThat().compilationFails()
                .andThat().compilerMessage().ofKindError().contains(BasicConstraintsCompilerMessages.WITHINTEGERBOUNDS_ERROR_WRONG_USAGE.getCode())
                .executeTest();
    }

    @Test
    public void exclusive_up_happyPath() {
        Cute.unitTest().when().passInElement().fromClass(WithExclusiveUpperAndLowerBound_up_Match.class).intoUnitTest((procEnv, e) -> {

            ToolingProvider.setTooling(procEnv);
            try {
                MatcherAssert.assertThat("Expect false", !BasicConstraints.checkConstraints(e));
            } finally {
                ToolingProvider.clearTooling();
            }

        }).thenExpectThat().compilationFails()
                .andThat().compilerMessage().ofKindError().contains(BasicConstraintsCompilerMessages.WITHINTEGERBOUNDS_ERROR_WRONG_USAGE.getCode())
                .executeTest();
    }

    @interface ArrayWithExclusiveUpperAndLowerBound {

        @WithIntegerInBounds(lowerBound = 5, inclusiveLowerBound = false, upperBound = 10, inclusiveUpperBound = false) int[] value();

    }

    @PassIn
    @ArrayWithExclusiveUpperAndLowerBound({8,7,6})
    static class ArrayWithExclusiveUpperAndLowerBound_mid_Match {

    }

    @PassIn
    @ArrayWithExclusiveUpperAndLowerBound({5,9,8})
    static class ArrayWithExclusiveUpperAndLowerBound_low_Match {

    }

    @PassIn
    @ArrayWithExclusiveUpperAndLowerBound({10,7,8})
    static class ArrayWithExclusiveUpperAndLowerBound_up_Match {

    }

    @Test
    public void array_exclusive_mid_happyPath() {
        Cute.unitTest().when().passInElement().fromClass(ArrayWithExclusiveUpperAndLowerBound_mid_Match.class).intoUnitTest((procEnv, e) -> {

            ToolingProvider.setTooling(procEnv);
            try {
                MatcherAssert.assertThat("Expect true", BasicConstraints.checkConstraints(e));
            } finally {
                ToolingProvider.clearTooling();
            }

        }).thenExpectThat().compilationSucceeds().executeTest();
    }

    @Test
    public void array_exclusive_low_happyPath() {
        Cute.unitTest().when().passInElement().fromClass(ArrayWithExclusiveUpperAndLowerBound_low_Match.class).intoUnitTest((procEnv, e) -> {

                    ToolingProvider.setTooling(procEnv);
                    try {
                        MatcherAssert.assertThat("Expect false", !BasicConstraints.checkConstraints(e));
                    } finally {
                        ToolingProvider.clearTooling();
                    }

                }).thenExpectThat().compilationFails()
                .andThat().compilerMessage().ofKindError().contains(BasicConstraintsCompilerMessages.WITHINTEGERBOUNDS_ERROR_WRONG_USAGE.getCode())
                .executeTest();
    }

    @Test
    public void array_exclusive_up_happyPath() {
        Cute.unitTest().when().passInElement().fromClass(ArrayWithExclusiveUpperAndLowerBound_up_Match.class).intoUnitTest((procEnv, e) -> {

                    ToolingProvider.setTooling(procEnv);
                    try {
                        MatcherAssert.assertThat("Expect false", !BasicConstraints.checkConstraints(e));
                    } finally {
                        ToolingProvider.clearTooling();
                    }

                }).thenExpectThat().compilationFails()
                .andThat().compilerMessage().ofKindError().contains(BasicConstraintsCompilerMessages.WITHINTEGERBOUNDS_ERROR_WRONG_USAGE.getCode())
                .executeTest();
    }


}
