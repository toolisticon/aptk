package io.toolisticon.aptk.tools.wrapper;

import io.toolisticon.aptk.tools.MessagerUtils;
import io.toolisticon.aptk.tools.TypeMirrorWrapper;
import io.toolisticon.aptk.tools.wrapper.ExecutableElementWrapper;
import io.toolisticon.cute.CompileTestBuilder;
import io.toolisticon.cute.PassIn;
import io.toolisticon.cute.UnitTest;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
                .<ExecutableElement>defineTest(PassIn.class, (processingEnvironment, executableElement) -> MatcherAssert.assertThat(ExecutableElementWrapper.wrap(executableElement).getMethodSignature(), Matchers.is("String test(int abc, String def)"))).compilationShouldSucceed()
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
                .<ExecutableElement>defineTest(PassIn.class, (processingEnvironment, executableElement) -> MatcherAssert.assertThat(ExecutableElementWrapper.wrap(executableElement).getMethodSignature(), Matchers.is("void test(int abc, String def)"))).compilationShouldSucceed()
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
                .<ExecutableElement>defineTest(PassIn.class, (processingEnvironment, executableElement) -> MatcherAssert.assertThat(ExecutableElementWrapper.wrap(executableElement).getMethodSignature(), Matchers.is("String test(int abc, String def) throws IOException"))).compilationShouldSucceed()
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
                .<ExecutableElement>defineTest(PassIn.class, (processingEnvironment, executableElement) -> MatcherAssert.assertThat(ExecutableElementWrapper.wrap(executableElement).getMethodSignature(), Matchers.is("<T extends List<String> & Serializable> String test(List<T> abc, List<String> def) throws IOException"))).compilationShouldSucceed()
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
                .<ExecutableElement>defineTest(PassIn.class, (processingEnvironment, executableElement) -> MatcherAssert.assertThat(ExecutableElementWrapper.wrap(executableElement).getMethodSignature(), Matchers.is("String test(String abc, Integer def, String... hij) throws IOException"))).compilationShouldSucceed()
                .executeTest();

    }

    interface GetImportsTest {

        @PassIn
        <A extends NoSuchElementException>  IOException test(List<Map<String, Collection<String>>> abc) throws FileNotFoundException;

    }

    @Test
    public void getImports_test() {

        unitTestBuilder
                .<ExecutableElement>defineTestWithPassedInElement(GetImportsTest.class, (processingEnvironment, executableElement) -> MatcherAssert.assertThat(ExecutableElementWrapper.wrap(executableElement).getImports(), Matchers.containsInAnyOrder(NoSuchElementException.class.getCanonicalName(), List.class.getCanonicalName(), Map.class.getCanonicalName(), Collection.class.getCanonicalName(), IOException.class.getCanonicalName(), FileNotFoundException.class.getCanonicalName()))).compilationShouldSucceed()
                .executeTest();

    }

    public static class WithReturnTypeTestClass{

        @PassIn
        public String testMethod() {
            return "";
        }

    }

    @Test
    public void test_getReturnType_nonVoid(){
        unitTestBuilder
                .<ExecutableElement>defineTestWithPassedInElement(WithReturnTypeTestClass.class, PassIn.class, (processingEnvironment, executableElement) -> {
                        MatcherAssert.assertThat(ExecutableElementWrapper.wrap(executableElement).getReturnType().getQualifiedName(), Matchers.is(String.class.getCanonicalName()));
                        MatcherAssert.assertThat("Must not be detected as Void type", !ExecutableElementWrapper.wrap(executableElement).getReturnType().isVoidType());
                }
                ).compilationShouldSucceed()
                .executeTest();
    }

    public static class WithoutReturnTypeTestClass{

        @PassIn
        public void testMethod() {

        }

    }

    @Test
    public void test_getReturnType_void(){
        unitTestBuilder
                .<ExecutableElement>defineTestWithPassedInElement(WithoutReturnTypeTestClass.class, PassIn.class, (processingEnvironment, executableElement) ->
                        MatcherAssert.assertThat("Must be detected as void type",ExecutableElementWrapper.wrap(executableElement).getReturnType().isVoidType())).compilationShouldSucceed()
                .executeTest();
    }

    public static class GetParameterTestClass{

        @PassIn
        public void testMethod(String a, Integer b) {

        }

    }

    @Test
    public void test_getParameters(){
        unitTestBuilder
                .<ExecutableElement>defineTestWithPassedInElement(GetParameterTestClass.class, PassIn.class, (processingEnvironment, executableElement) ->
                        MatcherAssert.assertThat(ExecutableElementWrapper.wrap(executableElement).getParameters().stream().map(e -> e.asType().getQualifiedName()).collect(Collectors.toList()), Matchers.contains(String.class.getCanonicalName(),Integer.class.getCanonicalName()))).compilationShouldSucceed()
                .executeTest();
    }

    public static class GetParameterTestClass_NoParameters{

        @PassIn
        public void testMethod() {

        }

    }

    @Test
    public void test_getParameters_withoutParams(){
        unitTestBuilder
                .<ExecutableElement>defineTestWithPassedInElement(GetParameterTestClass_NoParameters.class, PassIn.class, (processingEnvironment, executableElement) ->
                        MatcherAssert.assertThat(ExecutableElementWrapper.wrap(executableElement).getParameters(), Matchers.hasSize(0))).compilationShouldSucceed()
                .executeTest();
    }

    @interface GetDefaultValueTestAnnotation{
        @PassIn
        String value() default "XOXO";
    }

    @Test
    public void test_getDefaultValue_withDefault(){
        unitTestBuilder
                .<ExecutableElement>defineTestWithPassedInElement(GetDefaultValueTestAnnotation.class, PassIn.class, (processingEnvironment, executableElement) -> {
                    MatcherAssert.assertThat(ExecutableElementWrapper.wrap(executableElement).getDefaultValue().get().getStringValue().get(), Matchers.is("XOXO"));
                }).compilationShouldSucceed()
                .executeTest();
    }

    @interface GetDefaultValueTestAnnotation_WithoutDefault{
        @PassIn
        String value();
    }

    @Test
    public void test_getDefaultValue_withoutDefault(){
        unitTestBuilder
                .<ExecutableElement>defineTestWithPassedInElement(GetDefaultValueTestAnnotation_WithoutDefault.class, PassIn.class, (processingEnvironment, executableElement) -> {
                    MatcherAssert.assertThat("No default value should be detected => empty Optional", !ExecutableElementWrapper.wrap(executableElement).getDefaultValue().isPresent());
                }).compilationShouldSucceed()
                .executeTest();
    }

    interface IsDefaultValueTestInterface{
        @PassIn
        default String value() {
            return "XOXO";
        }
    }

    @Test
    public void test_isDefault_withDefaultImpl(){
        unitTestBuilder
                .<ExecutableElement>defineTestWithPassedInElement(IsDefaultValueTestInterface.class, PassIn.class, (processingEnvironment, executableElement) -> {
                    MatcherAssert.assertThat("Should be detected as default implementation", ExecutableElementWrapper.wrap(executableElement).isDefault());
                }).compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void test_isDefault_withoutDefaultImpl(){
        unitTestBuilder
                .<ExecutableElement>defineTestWithPassedInElement(GetDefaultValueTestAnnotation.class, PassIn.class, (processingEnvironment, executableElement) -> {
                    MatcherAssert.assertThat("Shouldn't be detected as default implementation", !ExecutableElementWrapper.wrap(executableElement).isDefault());
                }).compilationShouldSucceed()
                .executeTest();
    }

    interface IsVarArgsValueTestInterface{
        @PassIn
        String value(String... a);
    }

    @Test
    public void test_isDefault_withVarArgs(){
        unitTestBuilder
                .<ExecutableElement>defineTestWithPassedInElement(IsVarArgsValueTestInterface.class, PassIn.class, (processingEnvironment, executableElement) -> {
                    MatcherAssert.assertThat("Should be detected as varargs", ExecutableElementWrapper.wrap(executableElement).isVarArgs());
                }).compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void test_isDefault_withoutVarArgs(){
        unitTestBuilder
                .<ExecutableElement>defineTestWithPassedInElement(IsDefaultValueTestInterface.class, PassIn.class, (processingEnvironment, executableElement) -> {
                    MatcherAssert.assertThat("Shouldn't be detected as varargs", !ExecutableElementWrapper.wrap(executableElement).isVarArgs());
                }).compilationShouldSucceed()
                .executeTest();
    }

    interface GetThrownTypesTestInterface{
        @PassIn
        String value(String... a) throws IOException, IllegalArgumentException;
    }

    @Test
    public void test_getThrownTypes_withThrowTypes(){
        unitTestBuilder
                .<ExecutableElement>defineTestWithPassedInElement(GetThrownTypesTestInterface.class, PassIn.class, (processingEnvironment, executableElement) -> {
                    MatcherAssert.assertThat( ExecutableElementWrapper.wrap(executableElement).getThrownTypes().stream().map(e -> e.getQualifiedName()).collect(Collectors.toList()), Matchers.containsInAnyOrder(IllegalArgumentException.class.getCanonicalName(), IOException.class.getCanonicalName()));
                }).compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void test_getThrownTypes_withoutThrowTypes(){
        unitTestBuilder
                .<ExecutableElement>defineTestWithPassedInElement(IsDefaultValueTestInterface.class, PassIn.class, (processingEnvironment, executableElement) -> {
                    MatcherAssert.assertThat( ExecutableElementWrapper.wrap(executableElement).getThrownTypes(), Matchers.hasSize(0));
                }).compilationShouldSucceed()
                .executeTest();
    }



    @Test
    public void test_getReceiverType(){
        ExecutableElement executableElement = Mockito.mock(ExecutableElement.class);
        TypeMirror typeMirror = Mockito.mock(TypeMirror.class);
        Mockito.when(executableElement.getReceiverType()).thenReturn(typeMirror);

        MatcherAssert.assertThat(ExecutableElementWrapper.wrap(executableElement).getReceiverType().unwrap(), Matchers.is(typeMirror));

    }


}
