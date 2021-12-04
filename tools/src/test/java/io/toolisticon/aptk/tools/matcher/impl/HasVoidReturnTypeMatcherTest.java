package io.toolisticon.aptk.tools.matcher.impl;

import io.toolisticon.aptk.tools.corematcher.AptkCoreMatchers;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mockito;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

/**
 * Unit Test for {@link HasVoidReturnTypeMatcher}.
 */
public class HasVoidReturnTypeMatcherTest {

    @Test
    public void test_check_withNullValue() {

        MatcherAssert.assertThat(AptkCoreMatchers.HAS_VOID_RETURN_TYPE.getMatcher().check(null), Matchers.is(false));

    }

    @Test
    public void test_check_withVoidReturnType() {

        ExecutableElement element = Mockito.mock(ExecutableElement.class);
        TypeMirror typeMirror = Mockito.mock(TypeMirror.class);
        Mockito.when(element.getReturnType()).thenReturn(typeMirror);
        Mockito.when(typeMirror.getKind()).thenReturn(TypeKind.VOID);

        MatcherAssert.assertThat(AptkCoreMatchers.HAS_VOID_RETURN_TYPE.getMatcher().check(element), Matchers.is(true));

    }

    @Test
    public void test_check_withNonVoidReturnType() {

        ExecutableElement element = Mockito.mock(ExecutableElement.class);
        TypeMirror typeMirror = Mockito.mock(TypeMirror.class);
        Mockito.when(element.getReturnType()).thenReturn(typeMirror);
        Mockito.when(typeMirror.getKind()).thenReturn(TypeKind.ARRAY);

        MatcherAssert.assertThat(AptkCoreMatchers.HAS_VOID_RETURN_TYPE.getMatcher().check(element), Matchers.is(false));

    }

}
