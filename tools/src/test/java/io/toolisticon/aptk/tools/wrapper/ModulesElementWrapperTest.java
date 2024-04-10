package io.toolisticon.aptk.tools.wrapper;

import io.toolisticon.aptk.common.ToolingProvider;
import io.toolisticon.cute.Cute;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import javax.lang.model.element.Element;
import javax.tools.Tool;
import java.util.Arrays;
import java.util.Optional;

/**
 * Unit test for {@link ModuleElementWrapper}.
 * Only handles wrapping part since this module is build with Java version <9.
 */
public class ModulesElementWrapperTest {

    @Test
    public void test_invalidWrap () {
        Cute.unitTest().when().unitTestWithoutPassIn(processingEnvironment -> {
            try{
                ToolingProvider.setTooling(processingEnvironment);

                MatcherAssert.assertThat(ModuleElementWrapper.wrap((Element) TypeElementWrapper.getByClass(ModulesElementWrapperTest.class).get().unwrap()),Matchers.nullValue());
            } finally {
                ToolingProvider.clearTooling();
            }
        }).executeTest();
    }

    @Test
    public void test_invalidToModuleElement () {
        Cute.unitTest().when().unitTestWithoutPassIn(processingEnvironment -> {
            try{
                ToolingProvider.setTooling(processingEnvironment);

                MatcherAssert.assertThat(ModuleElementWrapper.toModuleElement((ElementWrapper<?>) TypeElementWrapper.getByClass(ModulesElementWrapperTest.class).get()),Matchers.nullValue());
            } finally {
                ToolingProvider.clearTooling();
            }
        }).executeTest();
    }

    @Test
    public void test_invalidWrapOfList () {
        Cute.unitTest().when().unitTestWithoutPassIn(processingEnvironment -> {
            try{
                ToolingProvider.setTooling(processingEnvironment);

                MatcherAssert.assertThat(ModuleElementWrapper.wrapList(Arrays.asList( TypeElementWrapper.getByClass(ModulesElementWrapperTest.class).get().unwrap())),Matchers.empty());
            } finally {
                ToolingProvider.clearTooling();
            }
        }).executeTest();
    }


}
