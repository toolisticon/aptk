package io.toolisticon.aptk.constraints;

import io.toolisticon.aptk.common.ToolingProvider;
import io.toolisticon.aptk.tools.MessagerUtils;
import io.toolisticon.cute.Cute;
import io.toolisticon.cute.PassIn;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;

public class BasicConstraintsTest {

    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }

    @PassIn
    @JustOnInterfacesAnnotation
    public interface JustInterfaceHappyPath {

    }

    @PassIn
    @JustOnInterfacesAnnotation
    public enum JustInterfaceOnEnum {

    }


    @Test
    public void testBasicConstraint_happyPath() {
        Cute.unitTest().when().passInElement().fromClass(JustInterfaceHappyPath.class).intoUnitTest((procEnv, e) -> {

            ToolingProvider.setTooling(procEnv);
            try {
                MatcherAssert.assertThat("Expect true", BasicConstraints.checkConstraints(e));
            } finally {
                ToolingProvider.clearTooling();
            }

        }).thenExpectThat().compilationSucceeds().executeTest();
    }

    @Test
    public void testBasicConstraint_failure_onEnum() {
        Cute.unitTest().when().passInElement().fromClass(JustInterfaceOnEnum.class).intoUnitTest((procEnv, e) -> {

            ToolingProvider.setTooling(procEnv);
            try {
                MatcherAssert.assertThat("Expect false", !BasicConstraints.checkConstraints(e));
            } finally {
                ToolingProvider.clearTooling();
            }

        }).thenExpectThat().compilationFails().andThat().compilerMessage().ofKindError().contains(BasicConstraintsCompilerMessages.ON_ERROR_WRONG_USAGE.getCode()).executeTest();
    }

    @interface JustStringAndIntegersHappyPath {

        @PassIn @JustOnStringAttributeAndIntegers String value();

        @JustOnStringAttributeAndIntegers int secondAttribute();

    }

    @Test
    public void justOnStringAttributeAndIntegers_happyPath() {
        Cute.unitTest().when().passInElement().fromClass(JustStringAndIntegersHappyPath.class).intoUnitTest((procEnv, e) -> {

            ToolingProvider.setTooling(procEnv);
            try {
                MatcherAssert.assertThat("Expect true", BasicConstraints.checkConstraints(e));
            } finally {
                ToolingProvider.clearTooling();
            }

        }).thenExpectThat().compilationSucceeds().executeTest();
    }


    @interface JustStringAndIntegersFailure {

        @PassIn @JustOnStringAttributeAndIntegers Class<?> value();


    }

    @Test
    public void justOnStringAttributeAndIntegers_failure() {
        Cute.unitTest().when().passInElement().fromClass(JustStringAndIntegersFailure.class).intoUnitTest((procEnv, e) -> {

            ToolingProvider.setTooling(procEnv);
            try {
                MatcherAssert.assertThat("Expect false", !BasicConstraints.checkConstraints(e));
            } finally {
                ToolingProvider.clearTooling();
            }

        }).thenExpectThat().compilationFails().andThat().compilerMessage().ofKindError().contains(BasicConstraintsCompilerMessages.ONATTRIBUTETYPE_ERROR_WRONG_USAGE.getCode()).executeTest();
    }




}
