package io.toolisticon.aptk.tools.wrapper;

import io.toolisticon.aptk.tools.MessagerUtils;
import io.toolisticon.aptk.tools.wrapper.ExecutableElementWrapper;
import io.toolisticon.cute.CompileTestBuilder;
import io.toolisticon.cute.PassIn;
import io.toolisticon.cute.UnitTest;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ExecutableElement;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class ExecutableElementWrapperTest {

    private CompileTestBuilder.UnitTestBuilder unitTestBuilder = CompileTestBuilder
            .unitTest();

    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }


    @Test
    public void getMethodSignature_simple() {

        String clazz = "package io.toolisticon.aptk.tools;\n" +
                "import io.toolisticon.cute.PassIn;\n" +
                "interface GetMethodSignatureTest_Simple {\n" +
                "    @PassIn\n" +
                "    public String test(int abc, String def);\n" +
                "}";

        unitTestBuilder.
                useSource("io.toolisticon.aptk.tools.GetMethodSignatureTest_Simple", clazz)
                .<ExecutableElement>defineTest(PassIn.class, new UnitTest<ExecutableElement>() {
                    @Override
                    public void unitTest(ProcessingEnvironment processingEnvironment, ExecutableElement executableElement) {

                        MatcherAssert.assertThat(ExecutableElementWrapper.wrap(executableElement).getMethodSignature(), Matchers.is("String test(int abc, String def)"));

                    }
                }).compilationShouldSucceed()
                .executeTest();

    }

    @Test
    public void getMethodSignature_withVoidReturnType() {

        String clazz = "package io.toolisticon.aptk.tools;\n" +
                "import io.toolisticon.cute.PassIn;\n" +
                "interface GetMethodSignatureTest_Simple {\n" +
                "    @PassIn\n" +
                "    void test(int abc, String def);\n" +
                "}";

        unitTestBuilder.
                useSource("io.toolisticon.aptk.tools.GetMethodSignatureTest_Simple", clazz)
                .<ExecutableElement>defineTest(PassIn.class, new UnitTest<ExecutableElement>() {
                    @Override
                    public void unitTest(ProcessingEnvironment processingEnvironment, ExecutableElement executableElement) {

                        MatcherAssert.assertThat(ExecutableElementWrapper.wrap(executableElement).getMethodSignature(), Matchers.is("void test(int abc, String def)"));

                    }
                }).compilationShouldSucceed()
                .executeTest();

    }

    @Test
    public void getMethodSignature_withThrows() {

        String clazz = "package io.toolisticon.aptk.tools;\n" +
                "import io.toolisticon.cute.PassIn;\n" +
                "import java.io.IOException;\n" +
                "interface GetMethodSignatureTest_WithThrows {\n" +
                "    @PassIn\n" +
                "    public String test(int abc, String def) throws IOException;\n" +
                "}";

        unitTestBuilder.
                useSource("io.toolisticon.aptk.tools.GetMethodSignatureTest_WithoutGenerics", clazz)
                .<ExecutableElement>defineTest(PassIn.class, new UnitTest<ExecutableElement>() {
                    @Override
                    public void unitTest(ProcessingEnvironment processingEnvironment, ExecutableElement executableElement) {

                        MatcherAssert.assertThat(ExecutableElementWrapper.wrap(executableElement).getMethodSignature(), Matchers.is("String test(int abc, String def) throws IOException"));

                    }
                }).compilationShouldSucceed()
                .executeTest();

    }


    @Test
    public void getMethodSignature_withGenerics() {

        String clazz = "package io.toolisticon.aptk.tools;\n" +
                "import io.toolisticon.cute.PassIn;\n" +
                "import java.io.IOException;\n" +
                "import java.io.Serializable;\n" +
                "import java.util.List;\n" +
                "interface GetMethodSignatureTest_WithGenerics {\n" +
                "    @PassIn\n" +
                "    public <T extends List<String> & Serializable> String test(List<T> abc, List<String> def) throws IOException;\n" +
                "}";

        unitTestBuilder.
                useSource("io.toolisticon.aptk.tools.GetMethodSignatureTest_WithoutGenerics", clazz)
                .<ExecutableElement>defineTest(PassIn.class, new UnitTest<ExecutableElement>() {
                    @Override
                    public void unitTest(ProcessingEnvironment processingEnvironment, ExecutableElement executableElement) {

                        MatcherAssert.assertThat(ExecutableElementWrapper.wrap(executableElement).getMethodSignature(), Matchers.is("<T extends List<String> & Serializable> String test(List<T> abc, List<String> def) throws IOException"));

                    }
                }).compilationShouldSucceed()
                .executeTest();

    }

    @Test
    public void getMethodSignature_withVarargs() {

        String clazz = "package io.toolisticon.aptk.tools;\n" +
                "import io.toolisticon.cute.PassIn;\n" +
                "import java.io.IOException;\n" +
                "import java.io.Serializable;\n" +
                "import java.util.List;\n" +
                "interface GetMethodSignatureTest_WithGenerics {\n" +
                "    @PassIn\n" +
                "    public String test(String abc, Integer def, String... hij) throws IOException;\n" +
                "}";

        unitTestBuilder.
                useSource("io.toolisticon.aptk.tools.GetMethodSignatureTest_WithoutGenerics", clazz)
                .<ExecutableElement>defineTest(PassIn.class, new UnitTest<ExecutableElement>() {
                    @Override
                    public void unitTest(ProcessingEnvironment processingEnvironment, ExecutableElement executableElement) {

                        MatcherAssert.assertThat(ExecutableElementWrapper.wrap(executableElement).getMethodSignature(), Matchers.is("String test(String abc, Integer def, String... hij) throws IOException"));

                    }
                }).compilationShouldSucceed()
                .executeTest();

    }

    interface GetImportsTest {

        @PassIn
        <A extends NoSuchElementException>  IOException test(List<Map<String, Collection<String>>> abc) throws FileNotFoundException;

    }

    @Test
    public void getImports_test() {

        unitTestBuilder
                .<ExecutableElement>defineTestWithPassedInElement(GetImportsTest.class, new UnitTest<ExecutableElement>() {
                    @Override
                    public void unitTest(ProcessingEnvironment processingEnvironment, ExecutableElement executableElement) {

                        MatcherAssert.assertThat(ExecutableElementWrapper.wrap(executableElement).getImports(), Matchers.containsInAnyOrder(NoSuchElementException.class.getCanonicalName(), List.class.getCanonicalName(), Map.class.getCanonicalName(), Collection.class.getCanonicalName(), IOException.class.getCanonicalName(), FileNotFoundException.class.getCanonicalName()));

                    }
                }).compilationShouldSucceed()
                .executeTest();

    }

}
