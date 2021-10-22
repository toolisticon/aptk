package io.toolisticon.aptk.tools.matcher.impl;

import io.toolisticon.aptk.common.ToolingProvider;
import io.toolisticon.aptk.tools.MessagerUtils;
import io.toolisticon.aptk.tools.corematcher.CoreMatchers;
import io.toolisticon.aptk.tools.fluentvalidator.FluentElementValidator;
import io.toolisticon.cute.CompileTestBuilder;
import io.toolisticon.cute.PassIn;
import io.toolisticon.cute.UnitTest;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;


/**
 * Unit test for {@link ByPackageNameMatcher}.
 */
@PassIn
public class ByPackageNameMatcherTest {


    private CompileTestBuilder.UnitTestBuilder unitTestBuilder = CompileTestBuilder
            .unitTest();


    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }

    @Test
    public void getStringRepresentationOfPassedCharacteristic_happyPath() {

        unitTestBuilder.defineTest(new UnitTest<Element>() {
            @Override
            public void unitTest(ProcessingEnvironment processingEnvironment, Element element) {
                MatcherAssert.assertThat("Should return qualified package name", CoreMatchers.BY_PACKAGE_NAME.getMatcher().getStringRepresentationOfPassedCharacteristic("abc").equals("abc"));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void getStringRepresentationOfPassedCharacteristic_passedNullValue_shouldReturnNull() {

        unitTestBuilder.defineTest(new UnitTest<Element>() {
            @Override
            public void unitTest(ProcessingEnvironment processingEnvironment, Element element) {
                MatcherAssert.assertThat("Should return null for null valued parameter", CoreMatchers.BY_PACKAGE_NAME.getMatcher().getStringRepresentationOfPassedCharacteristic(null) == null);

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }


    @Test
    public void checkForMatchingCharacteristic_match() {

        unitTestBuilder.<TypeElement>defineTestWithPassedInElement(this.getClass(), new UnitTest<TypeElement>() {
            @Override
            public void unitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                MatcherAssert.assertThat("Should find match correctly", CoreMatchers.BY_PACKAGE_NAME.getMatcher().checkForMatchingCharacteristic(element, this.getClass().getPackage().getName()));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void checkForMatchingCharacteristic_mismatch() {

        unitTestBuilder.<TypeElement>defineTestWithPassedInElement(this.getClass(), new UnitTest<TypeElement>() {
            @Override
            public void unitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {
                MatcherAssert.assertThat("Should find match correctly", !CoreMatchers.BY_PACKAGE_NAME.getMatcher().checkForMatchingCharacteristic(element, "xyz"));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void checkForMatchingCharacteristic_mismatch_checkErrorMessage() {

        unitTestBuilder.<TypeElement>defineTestWithPassedInElement(this.getClass(), new UnitTest<TypeElement>() {
            @Override
            public void unitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {
                ToolingProvider.setTooling(processingEnvironment);
                CoreMatchers.BY_PACKAGE_NAME.getMatcher().checkForMatchingCharacteristic(element, "xyz");
                FluentElementValidator.createFluentElementValidator(element)
                        .applyValidator(CoreMatchers.BY_PACKAGE_NAME)
                        .hasOneOf("abc")
                        .validateAndIssueMessages();
                ToolingProvider.clearTooling();

            }
        })
                .compilationShouldFail()
                .expectErrorMessageThatContains("(enclosing) PackageElement must  have one of the following qualified names: [abc]")
                .executeTest();
    }

    @Test
    public void checkForMatchingCharacteristic_nullValuedElement() {

        unitTestBuilder.defineTest(new UnitTest<Element>() {
            @Override
            public void unitTest(ProcessingEnvironment processingEnvironment, Element element) {
                MatcherAssert.assertThat("Should return false in case of null valued element", !CoreMatchers.BY_PACKAGE_NAME.getMatcher().checkForMatchingCharacteristic(null, "xyz"));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void checkForMatchingCharacteristic_nullValuedRawType() {

        unitTestBuilder.<TypeElement>defineTestWithPassedInElement(this.getClass(), new UnitTest<TypeElement>() {
            @Override
            public void unitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {
                MatcherAssert.assertThat("Should return false in case of null valued element", !CoreMatchers.BY_PACKAGE_NAME.getMatcher().checkForMatchingCharacteristic(element, null));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void checkForMatchingCharacteristic_nullValuedElementAndRawType() {

        unitTestBuilder.defineTest(new UnitTest<Element>() {
            @Override
            public void unitTest(ProcessingEnvironment processingEnvironment, Element element) {
                MatcherAssert.assertThat("Should return false in case of null valued element", !CoreMatchers.BY_PACKAGE_NAME.getMatcher().checkForMatchingCharacteristic(null, null));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }


}





