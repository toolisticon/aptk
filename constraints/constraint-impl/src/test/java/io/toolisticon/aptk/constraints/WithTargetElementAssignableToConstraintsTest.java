package io.toolisticon.aptk.constraints;

import io.toolisticon.aptk.common.ToolingProvider;
import io.toolisticon.aptk.tools.MessagerUtils;
import io.toolisticon.cute.Cute;
import io.toolisticon.cute.PassIn;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;

public class WithTargetElementAssignableToConstraintsTest implements Serializable{

    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }


    @interface AnnotatedElementMustBeAssignableTo {

        @WithTargetElementAssignableTo(targetElement = TargetElement.ANNOTATED_ELEMENT) Class<?> value();

    }

    interface MyInterface{
        String doSomething();
    }


    @PassIn
    @AnnotatedElementMustBeAssignableTo(MyInterface.class)
    static class AnnotatedElementMustBeAssignable_Match implements MyInterface {
        @Override
        public String doSomething() {
            return "Yes";
        }
    }

    @PassIn
    @AnnotatedElementMustBeAssignableTo(MyInterface.class)
    static class AnnotatedElementMustBeAssignable_NoMatch {

    }

    @Test
    public void annotatedElementMustBeAssignable_happyPath() {
        Cute.unitTest().when().passInElement().fromClass(AnnotatedElementMustBeAssignable_Match.class).intoUnitTest((procEnv, e) -> {

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
        Cute.unitTest().when().passInElement().fromClass(AnnotatedElementMustBeAssignable_NoMatch.class).intoUnitTest((procEnv, e) -> {

                    ToolingProvider.setTooling(procEnv);
                    try {
                        MatcherAssert.assertThat("Expect false", !BasicConstraints.checkConstraints(e));
                    } finally {
                        ToolingProvider.clearTooling();
                    }

                }).thenExpectThat().compilationFails()
                .andThat().compilerMessage().ofKindError().contains(BasicConstraintsCompilerMessages.TARGETMUSTBEASSIGNABLE_ERROR_ELEMENT_IS_NOT_ASSIGNABLE.getCode()).executeTest();
    }


    @interface TopLevelTypeElementMustBeAssignableTo {

        @WithTargetElementAssignableTo(targetElement = TargetElement.TOP_LEVEL_TYPE_ELEMENT) Class<?> value();

    }



    @PassIn
    @TopLevelTypeElementMustBeAssignableTo(Serializable.class)
    static class TopLevelAnnotatedTypeElementMustBeAssignable_Match  {
    }

    @PassIn
    @TopLevelTypeElementMustBeAssignableTo(MyInterface.class)
    static class TopLevelAnnotatedTypeElementMustBeAssignable_NoMatch {

    }

    @Test
    public void topLevelTypeElementMustBeAssignable_happyPath() {
        Cute.unitTest().when().passInElement().fromClass(TopLevelAnnotatedTypeElementMustBeAssignable_Match.class).intoUnitTest((procEnv, e) -> {

            ToolingProvider.setTooling(procEnv);
            try {
                MatcherAssert.assertThat("Expect true", BasicConstraints.checkConstraints(e));
            } finally {
                ToolingProvider.clearTooling();
            }

        }).thenExpectThat().compilationSucceeds().executeTest();
    }

    @Test
    public void topLevelTypeElementMustBeAssignable_failure() {
        Cute.unitTest().when().passInElement().fromClass(TopLevelAnnotatedTypeElementMustBeAssignable_NoMatch.class).intoUnitTest((procEnv, e) -> {

                    ToolingProvider.setTooling(procEnv);
                    try {
                        MatcherAssert.assertThat("Expect false", !BasicConstraints.checkConstraints(e));
                    } finally {
                        ToolingProvider.clearTooling();
                    }

                }).thenExpectThat().compilationFails()
                .andThat().compilerMessage().ofKindError().contains(BasicConstraintsCompilerMessages.TARGETMUSTBEASSIGNABLE_ERROR_ELEMENT_IS_NOT_ASSIGNABLE.getCode()).executeTest();
    }


    @interface EnclosingTypeElementMustBeAssignableTo {

        @WithTargetElementAssignableTo(targetElement = TargetElement.PARENT_TYPE_ELEMENT) Class<?> value();

    }

    static class EnclosingTypeElementMustBeAssignable_Match implements MyInterface{

        @PassIn
        @EnclosingTypeElementMustBeAssignableTo(MyInterface.class)
        @Override
        public String doSomething(){
            return "yes";
        }

    }

    static class EnclosingTypeElementMustBeAssignable_NoMatch {
        @PassIn
        @EnclosingTypeElementMustBeAssignableTo(MyInterface.class)
        public String doSomething(){
            return "yes";
        }
    }

    @Test
    public void enclosingTypeElementMustBeAssignable_happyPath() {
        Cute.unitTest().when().passInElement().fromClass(EnclosingTypeElementMustBeAssignable_Match.class).intoUnitTest((procEnv, e) -> {

            ToolingProvider.setTooling(procEnv);
            try {
                MatcherAssert.assertThat("Expect true", BasicConstraints.checkConstraints(e));
            } finally {
                ToolingProvider.clearTooling();
            }

        }).thenExpectThat().compilationSucceeds().executeTest();
    }

    @Test
    public void enclosingTypeElementMustBeAssignable_failure() {
        Cute.unitTest().when().passInElement().fromClass(EnclosingTypeElementMustBeAssignable_NoMatch.class).intoUnitTest((procEnv, e) -> {

                    ToolingProvider.setTooling(procEnv);
                    try {
                        MatcherAssert.assertThat("Expect false", !BasicConstraints.checkConstraints(e));
                    } finally {
                        ToolingProvider.clearTooling();
                    }

                }).thenExpectThat().compilationFails()
                .andThat().compilerMessage().ofKindError().contains(BasicConstraintsCompilerMessages.TARGETMUSTBEASSIGNABLE_ERROR_ELEMENT_IS_NOT_ASSIGNABLE.getCode()).executeTest();
    }

}
