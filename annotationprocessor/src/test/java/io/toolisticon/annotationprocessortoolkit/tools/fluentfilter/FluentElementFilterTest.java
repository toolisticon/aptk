package io.toolisticon.annotationprocessortoolkit.tools.fluentfilter;

import io.toolisticon.annotationprocessortoolkit.tools.TestCoreMatcherFactory;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mockito;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Unit Test for {@link FluentElementFilter}.
 */
public class FluentElementFilterTest {

    public <T extends Element> List<T> createElementList(Class<T> listType, int nrOfElements) {

        List<T> list = new ArrayList<T>();

        for (int i = 0; i < nrOfElements; i++) {
            list.add(Mockito.mock(listType));
        }

        return list;

    }

    // -----------------------------------------------------
    // -- Implicit Filters
    // -----------------------------------------------------

    @Test
    public void testElementBasedImplicitFilter() {


        List<Element> list = createElementList(Element.class, 5);


        List<Element> resultList = FluentElementFilter.createFluentElementFilter(list)
                .applyFilter(TestCoreMatcherFactory.createElementBasedImplicitCoreMatcher("XXX", true, false, true, false, false))
                .getResult();

        MatcherAssert.assertThat(resultList, Matchers.contains(list.get(0), list.get(2)));

    }

    @Test
    public void testImplicitFilter() {


        List<TypeElement> list = createElementList(TypeElement.class, 5);


        List<TypeElement> resultList = FluentElementFilter.createFluentElementFilter(list)
                .applyFilter(TestCoreMatcherFactory.createImplicitCoreMatcher(TypeElement.class, "XXX", true, false, true, false, false))
                .getResult();

        MatcherAssert.assertThat(resultList, Matchers.contains(list.get(0), list.get(2)));

    }

    @Test
    public void testElementBasedImplicitFilter_inverted() {


        List<Element> list = createElementList(Element.class, 5);


        List<Element> resultList = FluentElementFilter.createFluentElementFilter(list)
                .applyInvertedFilter(TestCoreMatcherFactory.createElementBasedImplicitCoreMatcher("XXX", true, false, true, false, false))
                .getResult();

        MatcherAssert.assertThat(resultList, Matchers.contains(list.get(1), list.get(3), list.get(4)));

    }

    @Test
    public void testImplicitFilter_inverted() {


        List<TypeElement> list = createElementList(TypeElement.class, 5);


        List<TypeElement> resultList = FluentElementFilter.createFluentElementFilter(list)
                .applyInvertedFilter(TestCoreMatcherFactory.createImplicitCoreMatcher(TypeElement.class, "XXX", true, false, true, false, false))
                .getResult();

        MatcherAssert.assertThat(resultList, Matchers.contains(list.get(1), list.get(3), list.get(4)));

    }

    // -----------------------------------------------------
    // -- Is Filters
    // -----------------------------------------------------

    @Test
    public void testElementBasedIsFilter() {


        List<Element> list = createElementList(Element.class, 5);


        List<TypeElement> resultList = FluentElementFilter.createFluentElementFilter(list)
                .applyFilter(TestCoreMatcherFactory.createElementBasedIsCoreMatcher(TypeElement.class, "XXX", true, false, true, false, false))
                .getResult();

        MatcherAssert.assertThat(resultList, Matchers.contains(list.get(0), list.get(2)));

    }

    @Test
    public void testIsFilter() {


        List<TypeElement> list = createElementList(TypeElement.class, 5);


        List<TypeElement> resultList = FluentElementFilter.createFluentElementFilter(list)
                .applyFilter(TestCoreMatcherFactory.createIsCoreMatcher(TypeElement.class, TypeElement.class, "XXX", true, false, true, false, false))
                .getResult();

        MatcherAssert.assertThat(resultList, Matchers.contains(list.get(0), list.get(2)));

    }

    @Test
    public void testElementBasedIsFilter_inverted() {


        List<Element> list = createElementList(Element.class, 5);


        List<Element> resultList = FluentElementFilter.createFluentElementFilter(list)
                .applyInvertedFilter(TestCoreMatcherFactory.createElementBasedIsCoreMatcher(TypeElement.class, "XXX", true, false, true, false, false))
                .getResult();

        MatcherAssert.assertThat(resultList, Matchers.contains(list.get(1), list.get(3), list.get(4)));

    }

    @Test
    public void testIsFilter_inverted() {


        List<TypeElement> list = createElementList(TypeElement.class, 5);


        List<TypeElement> resultList = FluentElementFilter.createFluentElementFilter(list)
                .applyInvertedFilter(TestCoreMatcherFactory.createIsCoreMatcher(TypeElement.class, VariableElement.class, "XXX", true, false, true, false, false))
                .getResult();

        MatcherAssert.assertThat(resultList, Matchers.contains(list.get(1), list.get(3), list.get(4)));

    }


    // -----------------------------------------------------
    // -- Exclusive Criteria Filters
    // -----------------------------------------------------

    @Test
    public void testElementBasedExclusiveCriteriaFilter_none() {


        List<Element> list = createElementList(Element.class, 5);


        List<Element> resultList = FluentElementFilter.createFluentElementFilter(list)
                .applyFilter(TestCoreMatcherFactory.createElementBasedExclusiveCriteriaCoreMatcher(String.class, "XXX",
                        false, false, false,
                        false, true, false,
                        false, false, false,
                        false, true, true,
                        true, false, true))
                .filterByNoneOf("A", "B", "C")
                .getResult();

        MatcherAssert.assertThat(resultList, Matchers.contains(list.get(0), list.get(2)));

    }

    @Test
    public void testExclusiveCriteriaFilter_none() {


        List<TypeElement> list = createElementList(TypeElement.class, 5);


        List<TypeElement> resultList = FluentElementFilter.createFluentElementFilter(list)
                .applyFilter(TestCoreMatcherFactory.createElementBasedExclusiveCriteriaCoreMatcher(String.class, "XXX",
                        false, false, false,
                        false, true, false,
                        false, false, false,
                        false, true, true,
                        true, false, true))
                .filterByNoneOf("A", "B", "C")
                .getResult();

        MatcherAssert.assertThat(resultList, Matchers.contains(list.get(0), list.get(2)));

    }

    @Test
    public void testElementBasedExclusiveCriteriaFilter_none_inverted() {


        List<Element> list = createElementList(Element.class, 5);


        List<Element> resultList = FluentElementFilter.createFluentElementFilter(list)
                .applyInvertedFilter(TestCoreMatcherFactory.createElementBasedExclusiveCriteriaCoreMatcher(String.class, "XXX",
                        false, false, false,
                        false, true, false,
                        false, false, false,
                        false, true, true,
                        true, false, true))
                .filterByNoneOf("A", "B", "C")
                .getResult();

        MatcherAssert.assertThat(resultList, Matchers.contains(list.get(1), list.get(3), list.get(4)));

    }

    @Test
    public void testExclusiveCriteriaFilter_none_inverted() {


        List<TypeElement> list = createElementList(TypeElement.class, 5);


        List<TypeElement> resultList = FluentElementFilter.createFluentElementFilter(list)
                .applyInvertedFilter(TestCoreMatcherFactory.createExclusiveCriteriaCoreMatcher(TypeElement.class, String.class, "XXX",
                        false, false, false,
                        false, true, false,
                        false, false, false,
                        false, true, true,
                        true, false, true))
                .filterByNoneOf("A", "B", "C")
                .getResult();

        MatcherAssert.assertThat(resultList, Matchers.contains(list.get(1), list.get(3), list.get(4)));

    }

    @Test
    public void testElementBasedExclusiveCriteriaFilter_one() {


        List<Element> list = createElementList(Element.class, 5);


        List<Element> resultList = FluentElementFilter.createFluentElementFilter(list)
                .applyFilter(TestCoreMatcherFactory.createElementBasedExclusiveCriteriaCoreMatcher(String.class, "XXX",
                        false, true, false,
                        false, true, true,
                        true, false, false,
                        false, false, false,
                        true, true, true))
                .filterByOneOf("A", "B", "C")
                .getResult();

        MatcherAssert.assertThat(resultList, Matchers.contains(list.get(0), list.get(2)));

    }

    @Test
    public void testExclusiveCriteriaFilter_one() {


        List<TypeElement> list = createElementList(TypeElement.class, 5);


        List<TypeElement> resultList = FluentElementFilter.createFluentElementFilter(list)
                .applyFilter(TestCoreMatcherFactory.createExclusiveCriteriaCoreMatcher(TypeElement.class, String.class, "XXX",
                        false, true, false,
                        false, true, true,
                        true, false, false,
                        false, false, false,
                        true, true, true))
                .filterByOneOf("A", "B", "C")
                .getResult();

        MatcherAssert.assertThat(resultList, Matchers.contains(list.get(0), list.get(2)));

    }

    @Test
    public void testElementBasedExclusiveCriteriaFilter_one_inverted() {


        List<Element> list = createElementList(Element.class, 5);


        List<Element> resultList = FluentElementFilter.createFluentElementFilter(list)
                .applyInvertedFilter(TestCoreMatcherFactory.createElementBasedExclusiveCriteriaCoreMatcher(String.class,
                        "XXX",
                        false, true, false,
                        false, true, true,
                        true, false, false,
                        false, false, false,
                        true, true, true))
                .filterByOneOf("A", "B", "C")
                .getResult();

        MatcherAssert.assertThat(resultList, Matchers.contains(list.get(1), list.get(3), list.get(4)));

    }

    @Test
    public void testExclusiveCriteriaFilter_one_inverted() {


        List<TypeElement> list = createElementList(TypeElement.class, 5);


        List<TypeElement> resultList = FluentElementFilter.createFluentElementFilter(list)
                .applyInvertedFilter(TestCoreMatcherFactory.createExclusiveCriteriaCoreMatcher(TypeElement.class, String.class, "XXX",
                        false, true, false,
                        false, true, true,
                        true, false, false,
                        false, false, false,
                        true, true, true))
                .filterByOneOf("A", "B", "C")
                .getResult();

        MatcherAssert.assertThat(resultList, Matchers.contains(list.get(1), list.get(3), list.get(4)));

    }

    // -----------------------------------------------------
    // -- Inclusive Criteria Filters
    // -----------------------------------------------------


    @Test
    public void testElementBasedInclusiveCriteriaFilter_none() {


        List<Element> list = createElementList(Element.class, 5);


        List<Element> resultList = FluentElementFilter.createFluentElementFilter(list)
                .applyFilter(TestCoreMatcherFactory.createElementBasedInclusiveCriteriaCoreMatcher(String.class, "XXX",
                        false, false, false,
                        false, true, false,
                        false, false, false,
                        false, true, true,
                        true, false, true))
                .filterByNoneOf("A", "B", "C")
                .getResult();

        MatcherAssert.assertThat(resultList, Matchers.contains(list.get(0), list.get(2)));

    }

    @Test
    public void testInclusiveCriteriaFilter_none() {


        List<TypeElement> list = createElementList(TypeElement.class, 5);


        List<TypeElement> resultList = FluentElementFilter.createFluentElementFilter(list)
                .applyFilter(TestCoreMatcherFactory.createElementBasedInclusiveCriteriaCoreMatcher(String.class, "XXX",
                        false, false, false,
                        false, true, false,
                        false, false, false,
                        false, true, true,
                        true, false, true))
                .filterByNoneOf("A", "B", "C")
                .getResult();

        MatcherAssert.assertThat(resultList, Matchers.contains(list.get(0), list.get(2)));

    }

    @Test
    public void testElementBasedInclusiveCriteriaFilter_none_inverted() {


        List<Element> list = createElementList(Element.class, 5);


        List<Element> resultList = FluentElementFilter.createFluentElementFilter(list)
                .applyInvertedFilter(TestCoreMatcherFactory.createElementBasedInclusiveCriteriaCoreMatcher(String.class, "XXX",
                        false, false, false,
                        false, true, false,
                        false, false, false,
                        false, true, true,
                        true, false, true))
                .filterByNoneOf("A", "B", "C")
                .getResult();

        MatcherAssert.assertThat(resultList, Matchers.contains(list.get(1), list.get(3), list.get(4)));

    }

    @Test
    public void testInclusiveCriteriaFilter_none_inverted() {


        List<TypeElement> list = createElementList(TypeElement.class, 5);


        List<TypeElement> resultList = FluentElementFilter.createFluentElementFilter(list)
                .applyInvertedFilter(TestCoreMatcherFactory.createInclusiveCriteriaCoreMatcher(TypeElement.class, String.class, "XXX",
                        false, false, false,
                        false, true, false,
                        false, false, false,
                        false, true, true,
                        true, false, true))
                .filterByNoneOf("A", "B", "C")
                .getResult();

        MatcherAssert.assertThat(resultList, Matchers.contains(list.get(1), list.get(3), list.get(4)));

    }

    @Test
    public void testElementBasedInclusiveCriteriaFilter_one() {


        List<Element> list = createElementList(Element.class, 5);


        List<Element> resultList = FluentElementFilter.createFluentElementFilter(list)
                .applyFilter(TestCoreMatcherFactory.createElementBasedInclusiveCriteriaCoreMatcher(String.class, "XXX",
                        false, true, false,
                        false, true, true,
                        true, false, false,
                        false, false, false,
                        true, true, true))
                .filterByOneOf("A", "B", "C")
                .getResult();

        MatcherAssert.assertThat(resultList, Matchers.contains(list.get(0), list.get(2)));

    }

    @Test
    public void testInclusiveCriteriaFilter_one() {


        List<TypeElement> list = createElementList(TypeElement.class, 5);


        List<TypeElement> resultList = FluentElementFilter.createFluentElementFilter(list)
                .applyFilter(TestCoreMatcherFactory.createInclusiveCriteriaCoreMatcher(TypeElement.class, String.class, "XXX",
                        false, true, false,
                        false, true, true,
                        true, false, false,
                        false, false, false,
                        true, true, true))
                .filterByOneOf("A", "B", "C")
                .getResult();

        MatcherAssert.assertThat(resultList, Matchers.contains(list.get(0), list.get(2)));

    }

    @Test
    public void testElementBasedInclusiveCriteriaFilter_one_inverted() {


        List<Element> list = createElementList(Element.class, 5);


        List<Element> resultList = FluentElementFilter.createFluentElementFilter(list)
                .applyInvertedFilter(TestCoreMatcherFactory.createElementBasedInclusiveCriteriaCoreMatcher(String.class,
                        "XXX",
                        false, true, false,
                        false, true, true,
                        true, false, false,
                        false, false, false,
                        true, true, true))
                .filterByOneOf("A", "B", "C")
                .getResult();

        MatcherAssert.assertThat(resultList, Matchers.contains(list.get(1), list.get(3), list.get(4)));

    }

    @Test
    public void testInclusiveCriteriaFilter_one_inverted() {


        List<TypeElement> list = createElementList(TypeElement.class, 5);


        List<TypeElement> resultList = FluentElementFilter.createFluentElementFilter(list)
                .applyInvertedFilter(TestCoreMatcherFactory.createInclusiveCriteriaCoreMatcher(TypeElement.class, String.class, "XXX",
                        false, true, false,
                        false, true, true,
                        true, false, false,
                        false, false, false,
                        true, true, true))
                .filterByOneOf("A", "B", "C")
                .getResult();

        MatcherAssert.assertThat(resultList, Matchers.contains(list.get(1), list.get(3), list.get(4)));

    }


    @Test
    public void testElementBasedInclusiveCriteriaFilter_atLeastOne() {


        List<Element> list = createElementList(Element.class, 5);


        List<Element> resultList = FluentElementFilter.createFluentElementFilter(list)
                .applyFilter(TestCoreMatcherFactory.createElementBasedInclusiveCriteriaCoreMatcher(String.class, "XXX",
                        false, true,
                        false, false, false,
                        true,
                        false, false, false,
                        false, false, false))
                .filterByAtLeastOneOf("A", "B", "C")
                .getResult();

        MatcherAssert.assertThat(resultList, Matchers.contains(list.get(0), list.get(2)));

    }

    @Test
    public void testInclusiveCriteriaFilter_atLeastOne() {


        List<TypeElement> list = createElementList(TypeElement.class, 5);


        List<TypeElement> resultList = FluentElementFilter.createFluentElementFilter(list)
                .applyFilter(TestCoreMatcherFactory.createElementBasedInclusiveCriteriaCoreMatcher(String.class, "XXX",
                        false, true,
                        false, false, false,
                        true,
                        false, false, false,
                        false, false, false))
                .filterByAtLeastOneOf("A", "B", "C")
                .getResult();

        MatcherAssert.assertThat(resultList, Matchers.contains(list.get(0), list.get(2)));

    }

    @Test
    public void testElementBasedInclusiveCriteriaFilter_atLeastOne_inverted() {


        List<Element> list = createElementList(Element.class, 5);


        List<Element> resultList = FluentElementFilter.createFluentElementFilter(list)
                .applyInvertedFilter(TestCoreMatcherFactory.createElementBasedInclusiveCriteriaCoreMatcher(String.class, "XXX",
                        false, true,
                        false, false, false,
                        true,
                        false, false, false,
                        false, false, false))
                .filterByAtLeastOneOf("A", "B", "C")
                .getResult();

        MatcherAssert.assertThat(resultList, Matchers.contains(list.get(1), list.get(3), list.get(4)));

    }

    @Test
    public void testInclusiveCriteriaFilter_atLeastOne_inverted() {


        List<TypeElement> list = createElementList(TypeElement.class, 5);


        List<TypeElement> resultList = FluentElementFilter.createFluentElementFilter(list)
                .applyInvertedFilter(TestCoreMatcherFactory.createInclusiveCriteriaCoreMatcher(TypeElement.class, String.class, "XXX",
                        false, true,
                        false, false, false,
                        true,
                        false, false, false,
                        false, false, false))
                .filterByAtLeastOneOf("A", "B", "C")
                .getResult();

        MatcherAssert.assertThat(resultList, Matchers.contains(list.get(1), list.get(3), list.get(4)));

    }

    @Test
    public void testElementBasedInclusiveCriteriaFilter_all() {


        List<Element> list = createElementList(Element.class, 5);


        List<Element> resultList = FluentElementFilter.createFluentElementFilter(list)
                .applyFilter(TestCoreMatcherFactory.createElementBasedInclusiveCriteriaCoreMatcher(String.class, "XXX",
                        true, true, true,
                        true, true, false,
                        true, true, true,
                        false,
                        true, false))
                .filterByAllOf("A", "B", "C")
                .getResult();

        MatcherAssert.assertThat(resultList, Matchers.contains(list.get(0), list.get(2)));

    }

    @Test
    public void testInclusiveCriteriaFilter_all() {


        List<TypeElement> list = createElementList(TypeElement.class, 5);


        List<TypeElement> resultList = FluentElementFilter.createFluentElementFilter(list)
                .applyFilter(TestCoreMatcherFactory.createInclusiveCriteriaCoreMatcher(TypeElement.class, String.class, "XXX",
                        true, true, true,
                        false,
                        true, true, true,
                        false,
                        true, true, false))
                .filterByAllOf("A", "B", "C")
                .getResult();

        MatcherAssert.assertThat(resultList, Matchers.contains(list.get(0), list.get(2)));

    }

    @Test
    public void testElementBasedInclusiveCriteriaFilter_all_inverted() {


        List<Element> list = createElementList(Element.class, 5);


        List<Element> resultList = FluentElementFilter.createFluentElementFilter(list)
                .applyInvertedFilter(TestCoreMatcherFactory.createElementBasedInclusiveCriteriaCoreMatcher(String.class, "ABC",
                        true, true, true,
                        false,
                        true, true, true,
                        true, true, false,
                        true, false))
                .filterByAllOf("A", "B", "C")
                .getResult();

        MatcherAssert.assertThat(resultList, Matchers.contains(list.get(1), list.get(3), list.get(4)));

    }

    @Test
    public void testInclusiveCriteriaFilter_all_inverted() {


        List<TypeElement> list = createElementList(TypeElement.class, 5);


        List<TypeElement> resultList = FluentElementFilter.createFluentElementFilter(list)
                .applyInvertedFilter(TestCoreMatcherFactory.createInclusiveCriteriaCoreMatcher(TypeElement.class, String.class, "XXX",
                        true, true, true,
                        false, true, true,
                        true, true, true,
                        false, false, false,
                        true, true, false))
                .filterByAllOf("A", "B", "C")
                .getResult();

        MatcherAssert.assertThat(resultList, Matchers.contains(list.get(1), list.get(3), list.get(4)));

    }

}
