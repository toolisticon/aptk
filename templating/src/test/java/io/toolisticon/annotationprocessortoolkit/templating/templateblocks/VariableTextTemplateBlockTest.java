package io.toolisticon.annotationprocessortoolkit.templating.templateblocks;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Unit Test for {@link VariableTextTemplateBlock}.
 */
public class VariableTextTemplateBlockTest {

    @Test
    public void test_getTemplateBlockType() {
        MatcherAssert.assertThat(new VariableTextTemplateBlock("").getTemplateBlockType(), Matchers.is(TemplateBlockType.DYNAMIC_TEXT));
    }

}
