package io.toolisticon.annotationprocessortoolkit.templating.templateblocks;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Unit Test for {@link PlainTextTemplateBlock}.
 */
public class PlainTextTemplateBlockTest {

    @Test
    public void test_getTemplateBlockType() {
        MatcherAssert.assertThat(new PlainTextTemplateBlock("").getTemplateBlockType(), Matchers.is(TemplateBlockType.PLAIN_TEXT));
    }

}
