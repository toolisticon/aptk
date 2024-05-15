package io.toolisticon.aptk.constraints;

import io.toolisticon.aptk.common.ToolingProvider;
import io.toolisticon.aptk.tools.MessagerUtils;
import io.toolisticon.cute.Cute;
import io.toolisticon.cute.PassIn;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for {@link WithDoubleInBoundsConstraintImpl}.
 */
public class WithDoubleInBoundsConstraintsTest {

    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }

    @interface WithInclusiveUpperAndLowerBound {

        @WithDoubleInBounds(lowerBound = 5.0, upperBound = 10.0) double value();

    }

    @PassIn
    @WithInclusiveUpperAndLowerBound(8.0)
    static class WithInclusiveUpperAndLowerBound_mid_Match {

    }

    @PassIn
    @WithInclusiveUpperAndLowerBound(5.0)
    static class WithInclusiveUpperAndLowerBound_low_Match {

    }

    @PassIn
    @WithInclusiveUpperAndLowerBound(10.0)
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

        @WithDoubleInBounds(lowerBound = 5.0, inclusiveLowerBound = false, upperBound = 10.0, inclusiveUpperBound = false) double value();

    }

    @PassIn
    @WithExclusiveUpperAndLowerBound(8.0)
    static class WithExclusiveUpperAndLowerBound_mid_Match {

    }

    @PassIn
    @WithExclusiveUpperAndLowerBound(5.0)
    static class WithExclusiveUpperAndLowerBound_low_Match {

    }

    @PassIn
    @WithExclusiveUpperAndLowerBound(10.0)
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
    public void exclusive_low_failingValidation() {
        Cute.unitTest().when().passInElement().fromClass(WithExclusiveUpperAndLowerBound_low_Match.class).intoUnitTest((procEnv, e) -> {

            ToolingProvider.setTooling(procEnv);
            try {
                MatcherAssert.assertThat("Expect false", !BasicConstraints.checkConstraints(e));
            } finally {
                ToolingProvider.clearTooling();
            }

        }).thenExpectThat().compilationFails()
                .andThat().compilerMessage().ofKindError().contains(BasicConstraintsCompilerMessages.WITHDOUBLEBOUNDS_ERROR_WRONG_USAGE.getCode())
                .executeTest();
    }

    @Test
    public void exclusive_up_failingValidation() {
        Cute.unitTest().when().passInElement().fromClass(WithExclusiveUpperAndLowerBound_up_Match.class).intoUnitTest((procEnv, e) -> {

            ToolingProvider.setTooling(procEnv);
            try {
                MatcherAssert.assertThat("Expect false", !BasicConstraints.checkConstraints(e));
            } finally {
                ToolingProvider.clearTooling();
            }

        }).thenExpectThat().compilationFails()
                .andThat().compilerMessage().ofKindError().contains(BasicConstraintsCompilerMessages.WITHDOUBLEBOUNDS_ERROR_WRONG_USAGE.getCode())
                .executeTest();
    }

    @interface ArrayWithExclusiveUpperAndLowerBound {

        @WithDoubleInBounds(lowerBound = 5.0, inclusiveLowerBound = false, upperBound = 10.0, inclusiveUpperBound = false) double[] value();

    }

    @PassIn
    @ArrayWithExclusiveUpperAndLowerBound({8.0,7.0,6.0})
    static class ArrayWithExclusiveUpperAndLowerBound_mid_Match {

    }

    @PassIn
    @ArrayWithExclusiveUpperAndLowerBound({5.0,9.0,8.0})
    static class ArrayWithExclusiveUpperAndLowerBound_low_Match {

    }

    @PassIn
    @ArrayWithExclusiveUpperAndLowerBound({10.0,7.0,8.0})
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
    public void array_exclusive_low_failingValidation() {
        Cute.unitTest().when().passInElement().fromClass(ArrayWithExclusiveUpperAndLowerBound_low_Match.class).intoUnitTest((procEnv, e) -> {

                    ToolingProvider.setTooling(procEnv);
                    try {
                        MatcherAssert.assertThat("Expect false", !BasicConstraints.checkConstraints(e));
                    } finally {
                        ToolingProvider.clearTooling();
                    }

                }).thenExpectThat().compilationFails()
                .andThat().compilerMessage().ofKindError().contains(BasicConstraintsCompilerMessages.WITHDOUBLEBOUNDS_ERROR_WRONG_USAGE.getCode())
                .executeTest();
    }

    @Test
    public void array_exclusive_up_failingValidation() {
        Cute.unitTest().when().passInElement().fromClass(ArrayWithExclusiveUpperAndLowerBound_up_Match.class).intoUnitTest((procEnv, e) -> {

                    ToolingProvider.setTooling(procEnv);
                    try {
                        MatcherAssert.assertThat("Expect false", !BasicConstraints.checkConstraints(e));
                    } finally {
                        ToolingProvider.clearTooling();
                    }

                }).thenExpectThat().compilationFails()
                .andThat().compilerMessage().ofKindError().contains(BasicConstraintsCompilerMessages.WITHDOUBLEBOUNDS_ERROR_WRONG_USAGE.getCode())
                .executeTest();
    }


}
