package io.toolisticon.aptk.tools.matcher.impl;

import io.toolisticon.aptk.tools.corematcher.AptkCoreMatchers;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mockito;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Unit Test for {@link HasNoParametersMatcher}.
 */
public class HasNoParameterMatcherTest {

    @Test
    public void test_check_withNullValue() {

        MatcherAssert.assertThat(AptkCoreMatchers.HAS_NO_PARAMETERS.getMatcher().check(null), Matchers.is(false));

    }

    @Test
    public void test_check_withVoidReturnType() {

        ExecutableElement element = Mockito.mock(ExecutableElement.class);
        TypeMirror typeMirror = Mockito.mock(TypeMirror.class);
        Mockito.when(element.getParameters()).thenReturn(Collections.EMPTY_LIST);

        MatcherAssert.assertThat(AptkCoreMatchers.HAS_NO_PARAMETERS.getMatcher().check(element), Matchers.is(true));

    }

    @Test
    public void test_check_withNonVoidReturnType() {

        ExecutableElement element = Mockito.mock(ExecutableElement.class);
        TypeMirror typeMirror = Mockito.mock(TypeMirror.class);
        List list = new ArrayList<VariableElement>();
        list.add(Mockito.mock(VariableElement.class));
        Mockito.when(element.getParameters()).thenReturn(list);

        MatcherAssert.assertThat(AptkCoreMatchers.HAS_NO_PARAMETERS.getMatcher().check(element), Matchers.is(false));

    }



}