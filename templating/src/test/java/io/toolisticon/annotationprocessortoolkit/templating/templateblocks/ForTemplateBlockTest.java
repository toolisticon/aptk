package io.toolisticon.annotationprocessortoolkit.templating.templateblocks;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Unit Test for {@link ForTemplateBlock}.
 */
public class ForTemplateBlockTest {

    @Test
    public void test_getTemplateBlockType() {
        MatcherAssert.assertThat(new ForTemplateBlock("test:abc.def","DEF").getTemplateBlockType(), Matchers.is(TemplateBlockType.FOR));
    }


}
