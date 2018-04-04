package io.toolisticon.annotationprocessortoolkit.templating.templateblocks;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Unit Test for {@link StaticTemplateBlock}.
 */
public class StaticTemplateBlockTest {

    @Test
    public void test_getTemplateBlockType() {
        MatcherAssert.assertThat(new StaticTemplateBlock("").getTemplateBlockType(), Matchers.is(TemplateBlockType.STATIC));
    }

}
