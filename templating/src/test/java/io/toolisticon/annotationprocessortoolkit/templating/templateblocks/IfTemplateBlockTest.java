package io.toolisticon.annotationprocessortoolkit.templating.templateblocks;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Unit Test for {@link IfTemplateBlock}.
 */
public class IfTemplateBlockTest {

    @Test
    public void test_getTemplateBlockType() {
        MatcherAssert.assertThat(new IfTemplateBlock("abc","").getTemplateBlockType(), Matchers.is(TemplateBlockType.IF));
    }

}
