package io.toolisticon.aptk.constraints;

import io.toolisticon.aptk.common.ToolingProvider;
import io.toolisticon.aptk.tools.MessagerUtils;
import io.toolisticon.cute.Cute;
import io.toolisticon.cute.PassIn;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;


@TargetMustBeAnnotatedWithConstraintsTest.AnotherTopLevelAccompanyingAnnotation
public class TargetMustBeAnnotatedWithConstraintsTest implements Serializable{

    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }

    @interface AccompanyingAnnotation{

    }


    @TargetMustBeAnnotatedWith(value = AccompanyingAnnotation.class)
    @interface AnnotatedElementMustBeAnnotatedWith {

    }

    interface MyInterface{
        String doSomething();
    }


    @PassIn
    @AnnotatedElementMustBeAnnotatedWith()
    @AccompanyingAnnotation
    static class AnnotatedElementMustBeAnnotatedWith_Match{
    }

    @PassIn
    @AnnotatedElementMustBeAnnotatedWith()
    static class AnnotatedElementMustBeAnnotatedWith_NoMatch {

    }

    @Test
    public void annotatedElementMustBeAssignable_happyPath() {
        Cute.unitTest().when().passInElement().fromClass(AnnotatedElementMustBeAnnotatedWith_Match.class).intoUnitTest((procEnv, e) -> {

            ToolingProvider.setTooling(procEnv);
            try {
                MatcherAssert.assertThat("Expect true", BasicConstraints.checkConstraints(e));
            } finally {
                ToolingProvider.clearTooling();
            }

        }).thenExpectThat().compilationSucceeds().executeTest();
    }

    @Test
    public void annotatedElementMustBeAssignable_failure() {
        Cute.unitTest().when().passInElement().fromClass(AnnotatedElementMustBeAnnotatedWith_NoMatch.class).intoUnitTest((procEnv, e) -> {

                    ToolingProvider.setTooling(procEnv);
                    try {
                        MatcherAssert.assertThat("Expect false", !BasicConstraints.checkConstraints(e));
                    } finally {
                        ToolingProvider.clearTooling();
                    }

                }).thenExpectThat().compilationFails()
                .andThat().compilerMessage().ofKindError().contains(BasicConstraintsCompilerMessages.TARGETMUSTBEANNOTATEDWITH_ERROR_ELEMENT_IS_NOT_ANNOTATED_WITH.getCode()).executeTest();
    }

    @interface AnotherTopLevelAccompanyingAnnotation{

    }


    @TargetMustBeAnnotatedWith(value = AccompanyingAnnotation.class)
    @TargetMustBeAnnotatedWith(value = AnotherTopLevelAccompanyingAnnotation.class, target = TargetMustBeAnnotatedWith.TargetElement.TOP_LEVEL_TYPE_ELEMENT)
    @interface RepeatableMustBeAnnotatedWith {

    }


    @PassIn
    @RepeatableMustBeAnnotatedWith()
    @AccompanyingAnnotation
    static class RepeatableElementMustBeAnnotatedWith_Match{
    }


    @Test
    public void repeatableElementMustBeAssignable_happyPath() {
        Cute.unitTest().when().passInElement().fromClass(RepeatableElementMustBeAnnotatedWith_Match.class).intoUnitTest((procEnv, e) -> {

            ToolingProvider.setTooling(procEnv);
            try {
                MatcherAssert.assertThat("Expect true", BasicConstraints.checkConstraints(e));
            } finally {
                ToolingProvider.clearTooling();
            }

        }).thenExpectThat().compilationSucceeds().executeTest();
    }

}
