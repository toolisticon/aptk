package io.toolisticon.annotationprocessortoolkit;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * Unit test for {@link ToolingProvider}.
 */
public class ToolingProviderTest {


    private ProcessingEnvironment processingEnvironment;
    private Elements elements;
    private Filer filer;
    private Messager messager;
    private Types types;

    @Before
    public void init() {
        processingEnvironment = Mockito.mock(ProcessingEnvironment.class);
        elements = Mockito.mock(Elements.class);
        filer = Mockito.mock(Filer.class);
        messager = Mockito.mock(Messager.class);
        types = Mockito.mock(Types.class);

        Mockito.when(processingEnvironment.getElementUtils()).thenReturn(elements);
        Mockito.when(processingEnvironment.getFiler()).thenReturn(filer);
        Mockito.when(processingEnvironment.getMessager()).thenReturn(messager);
        Mockito.when(processingEnvironment.getTypeUtils()).thenReturn(types);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testToolingProviderSetToolingWithNullValue() {

        ToolingProvider.setTooling(null);

    }

    @Test
    public void testToolingProviderSetToolingWithValidProcessingEnvironment() {

        ToolingProvider.setTooling(processingEnvironment);

        MatcherAssert.assertThat(ToolingProvider.getTooling().getElements(), Matchers.is(elements));
        MatcherAssert.assertThat(ToolingProvider.getTooling().getFiler(), Matchers.is(filer));
        MatcherAssert.assertThat(ToolingProvider.getTooling().getMessager(), Matchers.is(messager));
        MatcherAssert.assertThat(ToolingProvider.getTooling().getTypes(), Matchers.is(types));


    }

    @Test
    public void testToolingProviderSetToolingWithValidProcessingEnvironmentWithClear() {
        testToolingProviderSetToolingWithValidProcessingEnvironment();
        ToolingProvider.clearTooling();
        testToolingProviderSetToolingWithValidProcessingEnvironment();
    }

    @Test(expected = IllegalStateException.class)
    public void testToolingProviderClearTooling_Elements() {

        ToolingProvider.setTooling(processingEnvironment);
        MatcherAssert.assertThat(ToolingProvider.getTooling().getElements(), Matchers.is(elements));
        ToolingProvider.clearTooling();

        ToolingProvider.setTooling(processingEnvironment);
        MatcherAssert.assertThat(ToolingProvider.getTooling().getElements(), Matchers.is(elements));
        ToolingProvider.clearTooling();

        ToolingProvider.getTooling().getElements();


    }

    @Test(expected = IllegalStateException.class)
    public void testToolingProviderClearTooling_Filer() {

        ToolingProvider.setTooling(processingEnvironment);
        MatcherAssert.assertThat(ToolingProvider.getTooling().getFiler(), Matchers.is(filer));
        ToolingProvider.clearTooling();

        ToolingProvider.setTooling(processingEnvironment);
        MatcherAssert.assertThat(ToolingProvider.getTooling().getFiler(), Matchers.is(filer));
        ToolingProvider.clearTooling();

        ToolingProvider.getTooling().getFiler();


    }

    @Test(expected = IllegalStateException.class)
    public void testToolingProviderClearTooling_Messager() {

        ToolingProvider.setTooling(processingEnvironment);
        MatcherAssert.assertThat(ToolingProvider.getTooling().getMessager(), Matchers.is(messager));
        ToolingProvider.clearTooling();

        ToolingProvider.setTooling(processingEnvironment);
        MatcherAssert.assertThat(ToolingProvider.getTooling().getMessager(), Matchers.is(messager));
        ToolingProvider.clearTooling();

        ToolingProvider.getTooling().getMessager();


    }

    @Test(expected = IllegalStateException.class)
    public void testToolingProviderClearTooling_Types() {

        ToolingProvider.setTooling(processingEnvironment);
        MatcherAssert.assertThat(ToolingProvider.getTooling().getTypes(), Matchers.is(types));
        ToolingProvider.clearTooling();

        ToolingProvider.setTooling(processingEnvironment);
        MatcherAssert.assertThat(ToolingProvider.getTooling().getTypes(), Matchers.is(types));
        ToolingProvider.clearTooling();

        ToolingProvider.getTooling().getTypes();


    }




}
