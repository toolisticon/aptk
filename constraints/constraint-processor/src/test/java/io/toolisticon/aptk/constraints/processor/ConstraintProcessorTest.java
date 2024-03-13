package io.toolisticon.aptk.constraints.processor;

import io.toolisticon.aptk.constraints.BasicConstraintsCompilerMessages;
import io.toolisticon.aptk.tools.MessagerUtils;
import io.toolisticon.cute.Cute;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for {@link ConstraintProcessor}.
 */
public class ConstraintProcessorTest {
    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);

    }


    @Test
    public void test_happyPath() {
        Cute.blackBoxTest().given().processor(ConstraintProcessor.class)
                .andSourceFiles("/testcase/happyPath/HappyPath.java")
                .whenCompiled()
                .thenExpectThat()
                .compilationSucceeds()
                .executeTest();
    }

    @Test
    public void test_invalidUsageOfOn() {
        Cute.blackBoxTest().given().processor(ConstraintProcessor.class)
                .andSourceFiles("/testcase/invalidUsageOnAnnotation/InvalidUsage.java")
                .whenCompiled()
                .thenExpectThat()
                .compilationFails()
                .andThat().compilerMessage().ofKindError().contains(BasicConstraintsCompilerMessages.ON_ERROR_WRONG_USAGE.getCode())
                .executeTest();
    }

    @Test
    public void test_manualConstraints_happyPath() {
        Cute.blackBoxTest().given().processor(ConstraintProcessor.class)
                .andSourceFiles("/testcase/manualConstraintsHappyPath/HappyPath.java")
                .whenCompiled()
                .thenExpectThat()
                .compilationSucceeds()
                .executeTest();
    }
}