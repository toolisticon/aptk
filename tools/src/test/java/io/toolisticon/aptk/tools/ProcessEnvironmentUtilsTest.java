package io.toolisticon.aptk.tools;

import io.toolisticon.aptk.common.ToolingProvider;
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
 * Unit test for {@link ProcessingEnvironmentUtils}.
 */
public class ProcessEnvironmentUtilsTest {


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

        ToolingProvider.setTooling(processingEnvironment);
    }

    @Test
    public void getMessagerTest () {
        MatcherAssert.assertThat(ProcessingEnvironmentUtils.getMessager(), Matchers.equalTo(messager));
    }

    @Test
    public void getFilerTest () {
        MatcherAssert.assertThat(ProcessingEnvironmentUtils.getFiler(), Matchers.equalTo(filer));
    }

    @Test
    public void getElementsTest () {
        MatcherAssert.assertThat(ProcessingEnvironmentUtils.getElements(), Matchers.equalTo(elements));
    }

    @Test
    public void getTypesTest () {
        MatcherAssert.assertThat(ProcessingEnvironmentUtils.getTypes(), Matchers.equalTo(types));
    }


}
