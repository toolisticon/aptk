package io.toolisticon.aptk.annotationwrapper.annotationonpackage;

import io.toolisticon.aptk.compilermessage.test.OnClassAndNestedClass;
import io.toolisticon.aptk.compilermessage.test.OnClassAndNestedClassCompilerMessages;
import io.toolisticon.aptk.tools.MessagerUtils;
import io.toolisticon.cute.CompileTestBuilder;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

/**
 * Integration Test to test correctness of generated code
 */
public class IntegrationTest {


    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }

    @Test
    public void testCodes() {
        MatcherAssert.assertThat(OnClassAndNestedClassCompilerMessages.BY_ANNOTATION.getCode(), Matchers.is("WTF_001"));
        MatcherAssert.assertThat(OnClassAndNestedClassCompilerMessages.ON_CLASS.getCode(), Matchers.is("WTF_002"));
        MatcherAssert.assertThat(OnClassAndNestedClassCompilerMessages.ON_NESTED_CLASS.getCode(), Matchers.is("WTF_004"));
        MatcherAssert.assertThat(OnClassAndNestedClassCompilerMessages.ON_CLASS_METHOD.getCode(), Matchers.is("WTF_003"));
    }

    @Test
    public void testMessages() {
        MatcherAssert.assertThat(OnClassAndNestedClassCompilerMessages.BY_ANNOTATION.getMessage(), Matchers.containsString("Test"));
        MatcherAssert.assertThat(OnClassAndNestedClassCompilerMessages.ON_CLASS.getMessage(), Matchers.containsString("ON CLASS"));
        MatcherAssert.assertThat(OnClassAndNestedClassCompilerMessages.ON_NESTED_CLASS.getMessage(), Matchers.containsString("ON NESTED CLASS"));
        MatcherAssert.assertThat(OnClassAndNestedClassCompilerMessages.ON_CLASS_METHOD.getMessage(), Matchers.containsString("ON CLASS METHOD"));
    }

}
