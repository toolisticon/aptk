package io.toolisticon.aptk.tools.wrapper;

import io.toolisticon.aptk.tools.TypeMirrorWrapper;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mockito;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Unit tests for {@link TypeParameterElementWrapper}.
 */
public class TypeParameterElementWrapperTest {

    @Test
    public void test_wrapAndUnwrap() {
        TypeParameterElement element = Mockito.mock(TypeParameterElement.class);
        TypeParameterElementWrapper unit = TypeParameterElementWrapper.wrap(element);
        MatcherAssert.assertThat(unit.unwrap(), Matchers.is(element));
    }

    @Test
    public void test_getGenericElement() {
        TypeParameterElement element = Mockito.mock(TypeParameterElement.class);
        TypeParameterElementWrapper unit = TypeParameterElementWrapper.wrap(element);

        Element genericElement = Mockito.mock(Element.class);

        Mockito.when(element.getGenericElement()).thenAnswer(x -> genericElement);

        ElementWrapper<Element> genericElementResult = unit.getGenericElement();

        MatcherAssert.assertThat(genericElementResult.unwrap(), Matchers.is(genericElement));

    }

    @Test
    public void test_getBounds() {
        TypeParameterElement element = Mockito.mock(TypeParameterElement.class);
        TypeParameterElementWrapper unit = TypeParameterElementWrapper.wrap(element);

        TypeMirror typeMirror = Mockito.mock(TypeMirror.class);
        List<TypeMirror> bounds = new ArrayList<>();
        bounds.add(typeMirror);
        Mockito.when(element.getBounds()).thenAnswer(x -> bounds);

        List<TypeMirrorWrapper> boundsResult = unit.getBounds();

        MatcherAssert.assertThat(boundsResult, Matchers.hasSize(1));
        MatcherAssert.assertThat(boundsResult.get(0).unwrap(), Matchers.is(typeMirror));

    }




}
