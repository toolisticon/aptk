package io.toolisticon.annotationprocessortoolkit.tools.matcher.impl;

import io.toolisticon.annotationprocessortoolkit.tools.corematcher.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mockito;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Unit Test for {@link HasNoParametersMatcher}.
 */
public class HasNoThrownTypesMatcherTest {

    @Test
    public void test_check_withNullValue() {

        MatcherAssert.assertThat(CoreMatchers.HAS_NO_THROWN_TYPES.getMatcher().check(null), Matchers.is(false));

    }

    @Test
    public void test_check_withoutThrownTypes() {

        ExecutableElement element = Mockito.mock(ExecutableElement.class);
        TypeMirror typeMirror = Mockito.mock(TypeMirror.class);
        Mockito.when(element.getThrownTypes()).thenReturn(Collections.EMPTY_LIST);

        MatcherAssert.assertThat(CoreMatchers.HAS_NO_THROWN_TYPES.getMatcher().check(element), Matchers.is(true));

    }

    @Test
    public void test_check_withNonVoidReturnType() {

        ExecutableElement element = Mockito.mock(ExecutableElement.class);
        TypeMirror typeMirror = Mockito.mock(TypeMirror.class);
        List list = new ArrayList<TypeMirror>();
        list.add(Mockito.mock(TypeMirror.class));
        Mockito.when(element.getThrownTypes()).thenReturn(list);

        MatcherAssert.assertThat(CoreMatchers.HAS_NO_THROWN_TYPES.getMatcher().check(element), Matchers.is(false));

    }


}