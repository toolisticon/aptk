package io.toolisticon.aptk.tools.filter;

import io.toolisticon.aptk.tools.matcher.ImplicitMatcher;
import io.toolisticon.aptk.tools.validator.ImplicitValidator;
import io.toolisticon.aptk.tools.TestCoreMatcherFactory;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.lang.model.element.Element;
import java.util.ArrayList;
import java.util.List;

/**
 * Unit test for {@link ImplicitFilter}.
 */
public class ImplicitFilterTest {

    List<Element> list;

    Element element1;
    Element element2;
    Element element3;

    @Before
    public void init() {
        list = new ArrayList<Element>();

        element1 = Mockito.mock(Element.class);
        element2 = Mockito.mock(Element.class);
        element3 = Mockito.mock(Element.class);

        list.add(element1);
        list.add(element2);
        list.add(element3);
    }


    @Test
    public void testImplicitFilter_allMatching() {

        ImplicitFilter<Element, ImplicitValidator<Element, ImplicitMatcher<Element>>> unit =
                new ImplicitFilter<Element, ImplicitValidator<Element, ImplicitMatcher<Element>>>(TestCoreMatcherFactory.createElementBasedImplicitCoreMatcher("XXX", true).getValidator());


        List<Element> result = unit.filter(list);

        MatcherAssert.assertThat(result, Matchers.contains(element1, element2, element3));


    }


    @Test
    public void testImplicitFilter_oneNotMatching() {

        ImplicitFilter<Element, ImplicitValidator<Element, ImplicitMatcher<Element>>> unit =
                new ImplicitFilter<Element, ImplicitValidator<Element, ImplicitMatcher<Element>>>(TestCoreMatcherFactory.createElementBasedImplicitCoreMatcher("XXX", true, false, true).getValidator());


        List<Element> result = unit.filter(list);

        MatcherAssert.assertThat(result, Matchers.contains(element1, element3));


    }

    @Test
    public void testImplicitFilter_allNotMatching() {

        ImplicitFilter<Element, ImplicitValidator<Element, ImplicitMatcher<Element>>> unit =
                new ImplicitFilter<Element, ImplicitValidator<Element, ImplicitMatcher<Element>>>(TestCoreMatcherFactory.createElementBasedImplicitCoreMatcher("XXX", false).getValidator());


        List<Element> result = unit.filter(list);

        MatcherAssert.assertThat(result, Matchers.<Element>empty());


    }


    @Test
    public void testImplicitFilter_inverted_allMatching() {

        ImplicitFilter<Element, ImplicitValidator<Element, ImplicitMatcher<Element>>> unit =
                new ImplicitFilter<Element, ImplicitValidator<Element, ImplicitMatcher<Element>>>(TestCoreMatcherFactory.createElementBasedImplicitCoreMatcher("XXX", false).getValidator());


        List<Element> result = unit.filter(list, true);

        MatcherAssert.assertThat(result, Matchers.contains(element1, element2, element3));


    }


    @Test
    public void testImplicitFilter_inverted_oneNotMatching() {

        ImplicitFilter<Element, ImplicitValidator<Element, ImplicitMatcher<Element>>> unit =
                new ImplicitFilter<Element, ImplicitValidator<Element, ImplicitMatcher<Element>>>(TestCoreMatcherFactory.createElementBasedImplicitCoreMatcher("XXX", true, false, true).getValidator());


        List<Element> result = unit.filter(list, true);

        MatcherAssert.assertThat(result, Matchers.contains(element2));


    }

    @Test
    public void testImplicitFilter_inverted_allNotMatching() {

        ImplicitFilter<Element, ImplicitValidator<Element, ImplicitMatcher<Element>>> unit =
                new ImplicitFilter<Element, ImplicitValidator<Element, ImplicitMatcher<Element>>>(TestCoreMatcherFactory.createElementBasedImplicitCoreMatcher("XXX", false).getValidator());


        List<Element> result = unit.filter(list, true);

        MatcherAssert.assertThat(result, Matchers.contains(element1, element2, element3));


    }

    @Test
    public void testImplicitFilter_nullSafety_nullValuedValidator() {

        ImplicitFilter<Element, ImplicitValidator<Element, ImplicitMatcher<Element>>> unit =
                new ImplicitFilter<Element, ImplicitValidator<Element, ImplicitMatcher<Element>>>(null);


        List<Element> result = unit.filter(list);

        MatcherAssert.assertThat(result, Matchers.empty());


    }

    @Test
    public void testImplicitFilter_nullSafety_nullListToFilter() {

        ImplicitFilter<Element, ImplicitValidator<Element, ImplicitMatcher<Element>>> unit =
                new ImplicitFilter<Element, ImplicitValidator<Element, ImplicitMatcher<Element>>>(TestCoreMatcherFactory.createElementBasedImplicitCoreMatcher("XXX", false).getValidator());


        List<Element> result = unit.filter(null);

        MatcherAssert.assertThat(result, Matchers.empty());


    }

}
