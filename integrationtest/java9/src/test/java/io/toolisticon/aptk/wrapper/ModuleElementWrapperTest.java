package io.toolisticon.aptk.wrapper;

import io.toolisticon.aptk.common.ToolingProvider;
import io.toolisticon.aptk.tools.wrapper.ElementWrapper;
import io.toolisticon.aptk.tools.wrapper.ModuleElementWrapper;
import io.toolisticon.aptk.tools.wrapper.TypeElementWrapper;
import io.toolisticon.cute.Cute;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mockito;

import javax.annotation.processing.Processor;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ModuleElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import java.util.Optional;

/**
 * Unit test for {@link io.toolisticon.aptk.tools.wrapper.ModuleElementWrapper}.
 */
public class ModuleElementWrapperTest {


    @Test
    public void testCreationOfWrapperAndUnwrap() {
        ModuleElement moduleElement = Mockito.mock(ModuleElement.class);
        Mockito.when(moduleElement.getKind()).thenReturn(ElementKind.MODULE);

        ModuleElementWrapper unit = ModuleElementWrapper.wrap((Element) moduleElement);
        MatcherAssert.assertThat(unit, Matchers.notNullValue());
        MatcherAssert.assertThat(unit.unwrap(), Matchers.is(moduleElement));
    }

    @Test
    public void test_getQualifiedName() {
        ModuleElement moduleElement = Mockito.mock(ModuleElement.class);
        Mockito.when(moduleElement.getKind()).thenReturn(ElementKind.MODULE);

        Name name = Mockito.mock(Name.class);
        Mockito.when(name.toString()).thenReturn("JUPP");
        Mockito.when(moduleElement.getQualifiedName()).thenReturn(name);
        ModuleElementWrapper unit = ModuleElementWrapper.wrap((Element) moduleElement);
        MatcherAssert.assertThat(unit.getQualifiedName(), Matchers.is("JUPP"));
    }

    @Test
    public void test_hasQualifiedName() {
        ModuleElement moduleElement = Mockito.mock(ModuleElement.class);
        Mockito.when(moduleElement.getKind()).thenReturn(ElementKind.MODULE);

        Name name = Mockito.mock(Name.class);
        Mockito.when(name.toString()).thenReturn("YES");
        Mockito.when(moduleElement.getQualifiedName()).thenReturn(name);
        ModuleElementWrapper unit = ModuleElementWrapper.wrap((Element) moduleElement);
        MatcherAssert.assertThat(unit.hasQualifiedName("YES"), Matchers.is(true));
        MatcherAssert.assertThat(unit.hasQualifiedName("NO"), Matchers.is(false));
        MatcherAssert.assertThat(unit.hasQualifiedName(null), Matchers.is(false));
    }

    @Test
    public void proxyTests_isOpen() {
        ModuleElement moduleElement = Mockito.spy(ModuleElement.class);
        Mockito.when(moduleElement.getKind()).thenReturn(ElementKind.MODULE);

        ModuleElementWrapper unit = ModuleElementWrapper.wrap((Element) moduleElement);

        unit.isOpen();
        Mockito.verify(moduleElement, Mockito.times(1)).isOpen();

    }

    @Test
    public void proxyTests_isUnnamed() {
        ModuleElement moduleElement = Mockito.spy(ModuleElement.class);
        Mockito.when(moduleElement.getKind()).thenReturn(ElementKind.MODULE);

        ModuleElementWrapper unit = ModuleElementWrapper.wrap((Element) moduleElement);

        unit.isUnnamed();
        Mockito.verify(moduleElement, Mockito.times(1)).isUnnamed();

    }

    /*-
    // TODO: MUST IMPLEMENT DIRECTIVES VIA REFLECTION
    @Test
    public void proxyTests_getDirectives() {
        ModuleElement moduleElement = Mockito.spy(ModuleElement.class);
        ModuleElementWrapper unit = ModuleElementWrapper.wrap((Element)moduleElement);

        unit.getDirectives();
        Mockito.verify(moduleElement, Mockito.times(1)).getDirectives();

    }
    */

    @Test
    public void test_isModuleElement() {
        ModuleElement moduleElement = Mockito.mock(ModuleElement.class);
        Mockito.when(moduleElement.getKind()).thenReturn(ElementKind.MODULE);

        MatcherAssert.assertThat(ElementWrapper.wrap(moduleElement).isModule(), Matchers.is(true));


        TypeElement typeElement = Mockito.mock(TypeElement.class);
        Mockito.when(typeElement.getKind()).thenReturn(ElementKind.CLASS);
        MatcherAssert.assertThat(ElementWrapper.wrap(typeElement).isModuleElement(), Matchers.is(false));

    }


    @Test
    public void test_blackbox_ModuleElementWrapper_variousTests() {
        Cute.unitTest().given().useSourceFiles("/testcases/namedModule/module-info.java", "/testcases/namedModule/TestClass.java", "/testcases/namedModule/MyProcessor.java")
                .when().unitTestWithoutPassIn(processingEnvironment -> {
                    try {
                        ToolingProvider.setTooling(processingEnvironment);
                        TypeElementWrapper typeElementWrapper = TypeElementWrapper.getByFqn("io.toolisticon.aptk.example.regularmodule.TestClass").get();
                        Optional<ModuleElementWrapper> moduleElementWrapper = typeElementWrapper.getModule();
                        MatcherAssert.assertThat("Should find module", moduleElementWrapper.isPresent());
                        MatcherAssert.assertThat("Should be true", moduleElementWrapper.get().isModuleElement());

                        MatcherAssert.assertThat(moduleElementWrapper.get().getQualifiedName(), Matchers.is("io.toolisticon.aptk.example"));
                        MatcherAssert.assertThat(moduleElementWrapper.get().getDirectives(), Matchers.hasSize(7));

                        // default: requires java.base
                        MatcherAssert.assertThat("Should be true", moduleElementWrapper.get().getDirectives().get(0).isRequiresDirective());
                        MatcherAssert.assertThat("Should be false for non static", !moduleElementWrapper.get().getDirectives().get(0).toRequiresDirective().isStatic());
                        MatcherAssert.assertThat("Should be false for non transitive", !moduleElementWrapper.get().getDirectives().get(0).toRequiresDirective().isTransitive());
                        MatcherAssert.assertThat(moduleElementWrapper.get().getDirectives().get(0).toRequiresDirective().getDependency().getQualifiedName(), Matchers.is("java.base"));

                        // exports io.toolisticon.aptk.example.regularmodule;
                        MatcherAssert.assertThat("Should be true", moduleElementWrapper.get().getDirectives().get(1).isExportDirective());
                        MatcherAssert.assertThat(moduleElementWrapper.get().getDirectives().get(1).toExportsDirective().getPackage().getQualifiedName(), Matchers.is("io.toolisticon.aptk.example.regularmodule"));
                        MatcherAssert.assertThat(moduleElementWrapper.get().getDirectives().get(1).toExportsDirective().getTargetModules(), Matchers.hasSize(1));
                        MatcherAssert.assertThat(moduleElementWrapper.get().getDirectives().get(1).toExportsDirective().getTargetModules().get(0).getQualifiedName(), Matchers.is("cute"));

                        // requires cute
                        MatcherAssert.assertThat("Should be true", moduleElementWrapper.get().getDirectives().get(2).isRequiresDirective());
                        MatcherAssert.assertThat("Should be false for non static", !moduleElementWrapper.get().getDirectives().get(2).toRequiresDirective().isStatic());
                        MatcherAssert.assertThat("Should be true for non transitive", moduleElementWrapper.get().getDirectives().get(2).toRequiresDirective().isTransitive());
                        MatcherAssert.assertThat(moduleElementWrapper.get().getDirectives().get(2).toRequiresDirective().getDependency().getQualifiedName(), Matchers.is("cute"));

                        // requires jdk.compiler;
                        MatcherAssert.assertThat("Should be true", moduleElementWrapper.get().getDirectives().get(3).isRequiresDirective());
                        MatcherAssert.assertThat("Should be true for non static", moduleElementWrapper.get().getDirectives().get(3).toRequiresDirective().isStatic());
                        MatcherAssert.assertThat("Should be false for non transitive", !moduleElementWrapper.get().getDirectives().get(3).toRequiresDirective().isTransitive());
                        MatcherAssert.assertThat(moduleElementWrapper.get().getDirectives().get(3).toRequiresDirective().getDependency().getQualifiedName(), Matchers.is("jdk.compiler"));

                        // uses Processor
                        MatcherAssert.assertThat("Should be true", moduleElementWrapper.get().getDirectives().get(4).isUsesDirective());
                        MatcherAssert.assertThat(moduleElementWrapper.get().getDirectives().get(4).toUsesDirective().getService().getQualifiedName(), Matchers.is(Processor.class.getCanonicalName()));

                        // opens io.toolisticon.aptk.example.regularmodule to cute
                        MatcherAssert.assertThat("Should be true", moduleElementWrapper.get().getDirectives().get(5).isOpensDirective());
                        MatcherAssert.assertThat(moduleElementWrapper.get().getDirectives().get(5).toOpensDirective().getPackage().getQualifiedName(), Matchers.is("io.toolisticon.aptk.example.regularmodule"));
                        MatcherAssert.assertThat(moduleElementWrapper.get().getDirectives().get(5).toOpensDirective().getTargetModules().get(0).getQualifiedName(), Matchers.is("cute"));

                        // provides Processor with io.toolisticon.aptk.example.regularmodule.MyProcessor;
                        MatcherAssert.assertThat("Should be true", moduleElementWrapper.get().getDirectives().get(6).isProvidesDirective());
                        MatcherAssert.assertThat(moduleElementWrapper.get().getDirectives().get(6).toProvidesDirective().getService().getQualifiedName(), Matchers.is(Processor.class.getCanonicalName()));
                        MatcherAssert.assertThat(moduleElementWrapper.get().getDirectives().get(6).toProvidesDirective().getImplementations().get(0).getQualifiedName(), Matchers.is("io.toolisticon.aptk.example.regularmodule.MyProcessor"));


                    } finally {
                        ToolingProvider.clearTooling();
                    }
                })
                .thenExpectThat()
                .compilationSucceeds()
                .executeTest();

    }


}
