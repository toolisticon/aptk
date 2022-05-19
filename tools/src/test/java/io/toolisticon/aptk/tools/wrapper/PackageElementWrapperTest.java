package io.toolisticon.aptk.tools.wrapper;

import io.toolisticon.cute.CompileTestBuilder;
import io.toolisticon.cute.PassIn;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mockito;

import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;

/**
 * Unit test for {@link PackageElementWrapper}.
 */
@PassIn
public class PackageElementWrapperTest {

    @Test
    public void test_getQualifiedName() {

        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(PackageElementWrapperTest.class, (processingEnvironment, element) -> {
            PackageElementWrapper packageElementWrapper = TypeElementWrapper.wrap(element).getPackage();
            MatcherAssert.assertThat(packageElementWrapper.getQualifiedName(), Matchers.is(PackageElementWrapperTest.class.getPackage().getName().toString()));
        }).executeTest();

    }

    @Test
    public void test_isUnnamed() {
        PackageElement packageElement = Mockito.mock(PackageElement.class);
        PackageElementWrapper unit = PackageElementWrapper.wrap(packageElement);

        Mockito.when(packageElement.isUnnamed()).thenReturn(true);
        MatcherAssert.assertThat("Must be true", unit.isUnnamed());

        Mockito.when(packageElement.isUnnamed()).thenReturn(false);
        MatcherAssert.assertThat("Must be true", !unit.isUnnamed());
    }

    @Test
    public void test_wrap() {

        PackageElement packageElement = Mockito.mock(PackageElement.class);
        MatcherAssert.assertThat(PackageElementWrapper.wrap(packageElement).unwrap(), Matchers.is(packageElement));

    }

}
