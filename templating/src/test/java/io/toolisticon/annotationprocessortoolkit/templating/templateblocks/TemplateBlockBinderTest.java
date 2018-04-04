package io.toolisticon.annotationprocessortoolkit.templating.templateblocks;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Unit Test for {@link TemplateBlockBinder}.
 */
public class TemplateBlockBinderTest {

    @Test
    public void test_getTemplateBlockType() {
        MatcherAssert.assertThat(new TemplateBlockBinder("").getTemplateBlockType(), Matchers.is(TemplateBlockType.BINDER));
    }

}
