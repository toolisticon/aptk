package de.holisticon.annotationprocessortoolkit.testhelper;

import de.holisticon.annotationprocessortoolkit.AbstractAnnotationProcessor;
import de.holisticon.annotationprocessortoolkit.testhelper.testcases.TestAnnotation;
import de.holisticon.annotationprocessortoolkit.testhelper.testcases.TestAnnotationProcessor;
import de.holisticon.annotationprocessortoolkit.testhelper.testcases.TestAnnotationProcessorWithMissingNoArgConstructor;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mockito;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.HashSet;
import java.util.Set;

/**
 * Test for Wrapper class {@link AnnotationProcessorWrapper} to be used with {@link AbstractAnnotationProcessorTest}.
 */
public class AnnotationProcessorWrapperTest {

    @Test
    public void createWrapperWithInstance() {

        Processor unit = AnnotationProcessorWrapper.wrapProcessor(TestAnnotationProcessor.class);

        MatcherAssert.assertThat("Must return non null valued Processor", unit != null);

    }

    @Test(expected = IllegalArgumentException.class)
    public void createWrapperWithNullValuedInstance() {

        Processor unit = AnnotationProcessorWrapper.wrapProcessor((AbstractAnnotationProcessor) null);


    }

    @Test
    public void createWrapperWithType() {

        Processor unit = AnnotationProcessorWrapper.wrapProcessor(TestAnnotationProcessor.class);

        MatcherAssert.assertThat("Must return non null valued Processor", unit != null);

    }


    @Test(expected = IllegalArgumentException.class)
    public void createWrapperWithTypeOnProcessorWithNoArgConstructor() {

        Processor unit = AnnotationProcessorWrapper.wrapProcessor(TestAnnotationProcessorWithMissingNoArgConstructor.class);


    }

    @Test(expected = IllegalArgumentException.class)
    public void createWrapperWithNullValuedType() {

        Processor unit = AnnotationProcessorWrapper.wrapProcessor((Class) null);

    }

    @Test
    public void testWrappedSupportedOptionsCall() {

        AbstractAnnotationProcessor processorSpy = Mockito.spy(AbstractAnnotationProcessor.class);

        Processor unit = AnnotationProcessorWrapper.wrapProcessor(processorSpy);
        unit.getSupportedOptions();

        Mockito.verify(processorSpy).getSupportedOptions();


    }

    @Test
    public void testWrappedSupportedAnnotationTypesCall() {

        AbstractAnnotationProcessor processorSpy = Mockito.spy(AbstractAnnotationProcessor.class);

        Processor unit = AnnotationProcessorWrapper.wrapProcessor(processorSpy);
        unit.getSupportedAnnotationTypes();

        Mockito.verify(processorSpy).getSupportedAnnotationTypes();


    }

    @Test
    public void testWrappedSupportedSourceVersionCall() {

        AbstractAnnotationProcessor processorSpy = Mockito.spy(AbstractAnnotationProcessor.class);

        Processor unit = AnnotationProcessorWrapper.wrapProcessor(processorSpy);
        unit.getSupportedSourceVersion();

        Mockito.verify(processorSpy).getSupportedSourceVersion();


    }


    @Test
    public void testWrappedInitCall() {

        AbstractAnnotationProcessor processorSpy = Mockito.spy(AbstractAnnotationProcessor.class);

        Messager messager = Mockito.spy(Messager.class);
        ProcessingEnvironment processingEnvironment = Mockito.spy(ProcessingEnvironment.class);
        Mockito.when(processingEnvironment.getMessager()).thenReturn(messager);

        Processor unit = AnnotationProcessorWrapper.wrapProcessor(processorSpy);
        unit.init(processingEnvironment);

        Mockito.verify(processorSpy).init(processingEnvironment);


    }


    @Test
    public void testWrappedProcessCall() {

        AbstractAnnotationProcessor processorSpy = Mockito.spy(AbstractAnnotationProcessor.class);

        Messager messager = Mockito.spy(Messager.class);
        ProcessingEnvironment processingEnvironment = Mockito.spy(ProcessingEnvironment.class);
        Mockito.when(processingEnvironment.getMessager()).thenReturn(messager);

        Processor unit = AnnotationProcessorWrapper.wrapProcessor(processorSpy);
        unit.init(processingEnvironment);


        Set<? extends TypeElement> set = new HashSet<TypeElement>();
        RoundEnvironment roundEnvironment = Mockito.mock(RoundEnvironment.class);


        unit.process(set, roundEnvironment);

        Mockito.verify(messager).printMessage(Diagnostic.Kind.NOTE, AbstractAnnotationProcessorTest.TEST_EXECUTION_MESSAGE);
        Mockito.verify(processorSpy).process(set, roundEnvironment);

    }


    @Test
    public void testWrappedCompletionsCall() {

        AbstractAnnotationProcessor processorSpy = Mockito.spy(AbstractAnnotationProcessor.class);

        Element element = Mockito.mock(Element.class);
        AnnotationMirror annotationMirror = Mockito.mock(AnnotationMirror.class);
        ExecutableElement executableElement = Mockito.mock(ExecutableElement.class);
        String str = "XX";


        Processor unit = AnnotationProcessorWrapper.wrapProcessor(processorSpy);
        unit.getCompletions(element, annotationMirror, executableElement, str);

        Mockito.verify(processorSpy).getCompletions(element, annotationMirror, executableElement, str);


    }

    @Test
    public void getSupportedAnnotationsDefinedPerAnnotationCorrectly() {

        Processor unit = AnnotationProcessorWrapper.wrapProcessor(new TestAnnotationProcessor());

        MatcherAssert.assertThat(unit.getSupportedAnnotationTypes(), Matchers.contains(TestAnnotation.class
                .getCanonicalName()));


    }


}
