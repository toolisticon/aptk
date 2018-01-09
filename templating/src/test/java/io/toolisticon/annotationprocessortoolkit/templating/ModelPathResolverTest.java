package io.toolisticon.annotationprocessortoolkit.templating;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Unit test for {@link ModelPathResolver}.
 */
public class ModelPathResolverTest {

    @Test
    public void getGetter_checkNullSafety() {

        MatcherAssert.assertThat(ModelPathResolver.getGetter(null, "abc"), Matchers.nullValue());
        MatcherAssert.assertThat(ModelPathResolver.getGetter(this, null), Matchers.nullValue());
        MatcherAssert.assertThat(ModelPathResolver.getGetter(null, null), Matchers.nullValue());

    }

    public static class GetGetterTestClass {

        public void isIsGetter() {

        }

        public void hasHasGetter() {

        }

        public void getGetGetter() {

        }

        public void methodWithParameter(String test) {

        }

        public void getMethodWithParameter() {

        }

        public void someMethod() {

        }

        private void nonAccessibleMethod() {

        }

    }

    @Test
    public void getGetter_nonexistingFieldNameShouldReturnNull() {

        MatcherAssert.assertThat(ModelPathResolver.getGetter(new GetGetterTestClass(), "xxx"), Matchers.nullValue());

    }

    @Test
    public void getGetter_testExistingMethodName() {

        MatcherAssert.assertThat(ModelPathResolver.getGetter(new GetGetterTestClass(), "someMethod"), Matchers.is("someMethod"));

    }

    @Test
    public void getGetter_testNonAccessibleExistingMethodName() {

        MatcherAssert.assertThat(ModelPathResolver.getGetter(new GetGetterTestClass(), "nonAccessibleMethod"), Matchers.nullValue());

    }

    @Test
    public void getGetter_testDifferentKindOfGetterPrefixes() {

        MatcherAssert.assertThat(ModelPathResolver.getGetter(new GetGetterTestClass(), "isGetter"), Matchers.is("isIsGetter"));
        MatcherAssert.assertThat(ModelPathResolver.getGetter(new GetGetterTestClass(), "getGetter"), Matchers.is("getGetGetter"));
        MatcherAssert.assertThat(ModelPathResolver.getGetter(new GetGetterTestClass(), "hasGetter"), Matchers.is("hasHasGetter"));

    }


    @Test
    public void getGetter_testMethodWithParameter() {

        MatcherAssert.assertThat(ModelPathResolver.getGetter(new GetGetterTestClass(), "methodWithParameter"), Matchers.is("getMethodWithParameter"));

    }

}
