package io.toolisticon.aptk.constraints;

import io.toolisticon.aptk.common.ToolingProvider;
import io.toolisticon.aptk.tools.MessagerUtils;
import io.toolisticon.cute.Cute;
import io.toolisticon.cute.PassIn;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for {@link WithFloatInBoundsConstraintImpl}.
 */
public class WithFloatInBoundsConstraintsTest {

    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }

    @interface WithInclusiveUpperAndLowerBound {

        @WithFloatInBounds(lowerBound = 5.0f, upperBound = 10.0f) float value();

    }

    @PassIn
    @WithInclusiveUpperAndLowerBound(8.0f)
    static class WithInclusiveUpperAndLowerBound_mid_Match {

    }

    @PassIn
    @WithInclusiveUpperAndLowerBound(5.0f)
    static class WithInclusiveUpperAndLowerBound_low_Match {

    }

    @PassIn
    @WithInclusiveUpperAndLowerBound(10.0f)
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

        @WithFloatInBounds(lowerBound = 5.0f, inclusiveLowerBound = false, upperBound = 10.0f, inclusiveUpperBound = false) float value();

    }

    @PassIn
    @WithExclusiveUpperAndLowerBound(8.0f)
    static class WithExclusiveUpperAndLowerBound_mid_Match {

    }

    @PassIn
    @WithExclusiveUpperAndLowerBound(5.0f)
    static class WithExclusiveUpperAndLowerBound_low_Match {

    }

    @PassIn
    @WithExclusiveUpperAndLowerBound(10.0f)
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
                .andThat().compilerMessage().ofKindError().contains(BasicConstraintsCompilerMessages.WITHFLOATBOUNDS_ERROR_WRONG_USAGE.getCode())
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
                .andThat().compilerMessage().ofKindError().contains(BasicConstraintsCompilerMessages.WITHFLOATBOUNDS_ERROR_WRONG_USAGE.getCode())
                .executeTest();
    }

    @interface ArrayWithExclusiveUpperAndLowerBound {

        @WithFloatInBounds(lowerBound = 5.0f, inclusiveLowerBound = false, upperBound = 10.0f, inclusiveUpperBound = false) float[] value();

    }

    @PassIn
    @ArrayWithExclusiveUpperAndLowerBound({8.0f,7.0f,6.0f})
    static class ArrayWithExclusiveUpperAndLowerBound_mid_Match {

    }

    @PassIn
    @ArrayWithExclusiveUpperAndLowerBound({5.0f,9.0f,8.0f})
    static class ArrayWithExclusiveUpperAndLowerBound_low_Match {

    }

    @PassIn
    @ArrayWithExclusiveUpperAndLowerBound({10.0f,7.0f,8.0f})
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
                .andThat().compilerMessage().ofKindError().contains(BasicConstraintsCompilerMessages.WITHFLOATBOUNDS_ERROR_WRONG_USAGE.getCode())
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
                .andThat().compilerMessage().ofKindError().contains(BasicConstraintsCompilerMessages.WITHFLOATBOUNDS_ERROR_WRONG_USAGE.getCode())
                .executeTest();
    }


}
