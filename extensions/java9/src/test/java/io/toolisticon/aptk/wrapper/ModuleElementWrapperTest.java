package io.toolisticon.aptk.wrapper;

import io.toolisticon.aptk.tools.wrapper.ElementWrapper;
import io.toolisticon.aptk.tools.wrapper.ModuleElementWrapper;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mockito;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ModuleElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;

/**
 * Unit test for {@link io.toolisticon.aptk.tools.wrapper.ModuleElementWrapper}.
 */
public class ModuleElementWrapperTest {


    @Test
    public void testCreationOfWrapperAndUnwrap() {
        ModuleElement moduleElement = Mockito.mock(ModuleElement.class);
        ModuleElementWrapper unit = ModuleElementWrapper.wrap(moduleElement);
        MatcherAssert.assertThat(unit, Matchers.notNullValue());
        MatcherAssert.assertThat(unit.unwrap(), Matchers.is(moduleElement));
    }

    @Test
    public void test_getQualifiedName() {
        ModuleElement moduleElement = Mockito.mock(ModuleElement.class);
        Name name = Mockito.mock(Name.class);
        Mockito.when(name.toString()).thenReturn("JUPP");
        Mockito.when(moduleElement.getQualifiedName()).thenReturn(name);
        ModuleElementWrapper unit = ModuleElementWrapper.wrap(moduleElement);
        MatcherAssert.assertThat(unit.getQualifiedName(), Matchers.is("JUPP"));
    }

    @Test
    public void test_hasQualifiedName() {
        ModuleElement moduleElement = Mockito.mock(ModuleElement.class);
        Name name = Mockito.mock(Name.class);
        Mockito.when(name.toString()).thenReturn("YES");
        Mockito.when(moduleElement.getQualifiedName()).thenReturn(name);
        ModuleElementWrapper unit = ModuleElementWrapper.wrap(moduleElement);
        MatcherAssert.assertThat(unit.hasQualifiedName("YES"), Matchers.is(true));
        MatcherAssert.assertThat(unit.hasQualifiedName("NO"), Matchers.is(false));
        MatcherAssert.assertThat(unit.hasQualifiedName(null), Matchers.is(false));
    }

    @Test
    public void proxyTests_isOpen() {
        ModuleElement moduleElement = Mockito.spy(ModuleElement.class);
        ModuleElementWrapper unit = ModuleElementWrapper.wrap(moduleElement);

        unit.isOpen();
        Mockito.verify(moduleElement, Mockito.times(1)).isOpen();

    }

    @Test
    public void proxyTests_isUnnamed() {
        ModuleElement moduleElement = Mockito.spy(ModuleElement.class);
        ModuleElementWrapper unit = ModuleElementWrapper.wrap(moduleElement);

        unit.isUnnamed();
        Mockito.verify(moduleElement, Mockito.times(1)).isUnnamed();

    }

    @Test
    public void proxyTests_getDirectives() {
        ModuleElement moduleElement = Mockito.spy(ModuleElement.class);
        ModuleElementWrapper unit = ModuleElementWrapper.wrap(moduleElement);

        unit.getDirectives();
        Mockito.verify(moduleElement, Mockito.times(1)).getDirectives();

    }

    @Test
    public void test_isModuleElement(){
        ModuleElement moduleElement = Mockito.mock(ModuleElement.class);

        Mockito.when(moduleElement.getKind()).thenReturn(ElementKind.MODULE);
        MatcherAssert.assertThat(ModuleElementWrapper.isModuleElement(ElementWrapper.wrap((Element)moduleElement)), Matchers.is(true));


        TypeElement typeElement = Mockito.mock(TypeElement.class);
        Mockito.when(typeElement.getKind()).thenReturn(ElementKind.CLASS);
        MatcherAssert.assertThat(ModuleElementWrapper.isModuleElement(ElementWrapper.wrap((Element) typeElement)), Matchers.is(false));



    }
/*-

    public static boolean isModuleElement(Element element) {
        return element != null & element.getKind().equals(ElementKind.MODULE);
    }

    public static ModuleElementWrapperTest toModuleElement(Element element) {
        return ModuleElementWrapperTest.wrap((ModuleElement) element);
    }

    public static ModuleElementWrapperTest wrap(ModuleElement moduleElement) {
        return new ModuleElementWrapperTest(moduleElement);
    }


 */

}
