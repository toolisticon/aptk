package de.holisticon.annotationprocessortoolkit.templating.expressions.operands;

import de.holisticon.annotationprocessortoolkit.templating.ModelPathResolver;
import de.holisticon.annotationprocessortoolkit.templating.exceptions.InvalidPathException;
import de.holisticon.annotationprocessortoolkit.templating.testclasses.TestClass1;
import de.holisticon.annotationprocessortoolkit.templating.testclasses.TestClass2;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Unit test for {@link DynamicOperand}.
 */
public class DynamicOperandTest {

    @Test(expected = IllegalArgumentException.class)
    public void test_passedNullValue() {
        new DynamicOperand(null).value();
    }


    @Test
    public void testResolveModelValue() {

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("longKey", 5L);
        model.put("integerKey", 5);
        model.put("doubleKey", 5.0);
        model.put("floatKey", 5.0f);
        model.put("stringKey", "stringValue");
        model.put("booleanKey", true);
        model.put("objectKey", this);

        // apply model to thread local
        ModelPathResolver.modelMapThreadLocal.set(model);

        MatcherAssert.assertThat((Long) new DynamicOperand("longKey").value(), Matchers.is(5L));
        MatcherAssert.assertThat((Integer) new DynamicOperand("integerKey").value(), Matchers.is(5));
        MatcherAssert.assertThat((Double) new DynamicOperand("doubleKey").value(), Matchers.is(5.0));
        MatcherAssert.assertThat((Float) new DynamicOperand("floatKey").value(), Matchers.is(5.0f));
        MatcherAssert.assertThat((String) new DynamicOperand("stringKey").value(), Matchers.is("stringValue"));
        MatcherAssert.assertThat((Boolean) new DynamicOperand("booleanKey").value(), Matchers.is(true));
        MatcherAssert.assertThat((Object) new DynamicOperand("objectKey").value(), Matchers.is((Object) this));


    }

    @Test(expected = InvalidPathException.class)
    public void test_nonExistingPath() {

        Map<String, Object> model = new HashMap<String, Object>();

        // apply model to thread local
        ModelPathResolver.modelMapThreadLocal.set(model);

        new DynamicOperand("nonExistingKey").value();


    }


    @Test
    public void test_complexPaths() {

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("test", new TestClass2());

        // apply model to thread local
        ModelPathResolver.modelMapThreadLocal.set(model);

        MatcherAssert.assertThat((TestClass2) new DynamicOperand("test").value(), Matchers.is((Object) model.get("test")));

        MatcherAssert.assertThat(((TestClass1) new DynamicOperand("test.testClass1").value()).getValue(), Matchers.is(5));
        MatcherAssert.assertThat(((TestClass1) new DynamicOperand("test.getTestClass1").value()).getValue(), Matchers.is(5));

        MatcherAssert.assertThat((Integer) new DynamicOperand("test.testClass1.value").value(), Matchers.is(5));
        MatcherAssert.assertThat((Integer) new DynamicOperand("test.getTestClass1.getValue").value(), Matchers.is(5));


    }

}
