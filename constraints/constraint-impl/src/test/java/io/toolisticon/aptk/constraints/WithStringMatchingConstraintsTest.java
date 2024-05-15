package io.toolisticon.aptk.constraints;

import io.toolisticon.aptk.common.ToolingProvider;
import io.toolisticon.aptk.tools.MessagerUtils;
import io.toolisticon.cute.Cute;
import io.toolisticon.cute.PassIn;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;

public class WithStringMatchingConstraintsTest implements Serializable {

    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }


    @interface TestStringMustMatch {

        @WithStringMatching("A.+Z") String value();

    }

    @PassIn
    @TestStringMustMatch("ABadasdZ")
    static class TestStringAttributeByRegex_Match {

    }

    @PassIn
    @TestStringMustMatch("AZ")
    static class TestStringAttributeByRegex_NoMatch {

    }

    @Test
    public void stringMustMatch_happyPath() {
        Cute.unitTest().when().passInElement().fromClass(TestStringAttributeByRegex_Match.class).intoUnitTest((procEnv, e) -> {

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
        Cute.unitTest().when().passInElement().fromClass(TestStringAttributeByRegex_NoMatch.class).intoUnitTest((procEnv, e) -> {

                    ToolingProvider.setTooling(procEnv);
                    try {
                        MatcherAssert.assertThat("Expect false", !BasicConstraints.checkConstraints(e));
                    } finally {
                        ToolingProvider.clearTooling();
                    }

                }).thenExpectThat().compilationFails()
                .andThat().compilerMessage().ofKindError().contains(BasicConstraintsCompilerMessages.STRINGMUSTMATCH_ERROR_WRONG_USAGE.getCode()).executeTest();
    }

}
