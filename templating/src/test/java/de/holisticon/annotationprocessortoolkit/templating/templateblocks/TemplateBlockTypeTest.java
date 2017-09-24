package de.holisticon.annotationprocessortoolkit.templating.templateblocks;

import de.holisticon.annotationprocessortoolkit.templating.NextDetectedBlockResult;
import de.holisticon.annotationprocessortoolkit.templating.ParseUtilities;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test for {@link TemplateBlockType}.
 */
public class TemplateBlockTypeTest {


    @Test
    public void getNextBlockType_getStaticBlock() {

        NextDetectedBlockResult result = TemplateBlockType.getNextBlock("abc !<static>asdsa!</static>!<if sad> !<for dasdsa> !</for> !</if>");

        MatcherAssert.assertThat(result.getTemplateBlockType(), Matchers.is(TemplateBlockType.STATIC));
        MatcherAssert.assertThat(result.getAttributes(), Matchers.nullValue());
        MatcherAssert.assertThat(result.getBeginIndex(), Matchers.is(4));
        MatcherAssert.assertThat(result.getContent(), Matchers.is("asdsa"));
        MatcherAssert.assertThat(result.getRemainingStringToBeProcessed(), Matchers.is("!<if sad> !<for dasdsa> !</for> !</if>"));


    }

    @Test
    public void getNextBlockType_getStaticBlock_startTagInStaticBlock() {

        NextDetectedBlockResult result = TemplateBlockType.getNextBlock("abc !<static>!<static>asdsa!</static>!<if sad> !<for dasdsa> !</for> !</if>");

        MatcherAssert.assertThat(result.getTemplateBlockType(), Matchers.is(TemplateBlockType.STATIC));
        MatcherAssert.assertThat(result.getAttributes(), Matchers.nullValue());
        MatcherAssert.assertThat(result.getBeginIndex(), Matchers.is(4));
        MatcherAssert.assertThat(result.getContent(), Matchers.is("!<static>asdsa"));
        MatcherAssert.assertThat(result.getRemainingStringToBeProcessed(), Matchers.is("!<if sad> !<for dasdsa> !</for> !</if>"));


    }

    @Test
    public void getNextBlockType_getIfBlock() {

        NextDetectedBlockResult result = TemplateBlockType.getNextBlock("abc !<if ddsdf>sdasd!</if>dsfd!<static>asdsa!</static>!<if sad> !<for dasdsa> !</for> !</if>");

        MatcherAssert.assertThat(result.getTemplateBlockType(), Matchers.is(TemplateBlockType.IF));
        MatcherAssert.assertThat(result.getAttributes(), Matchers.is("ddsdf"));
        MatcherAssert.assertThat(result.getBeginIndex(), Matchers.is(4));
        MatcherAssert.assertThat(result.getContent(), Matchers.is("sdasd"));
        MatcherAssert.assertThat(result.getRemainingStringToBeProcessed(), Matchers.is("dsfd!<static>asdsa!</static>!<if sad> !<for dasdsa> !</for> !</if>"));


    }

    @Test
    public void getNextBlockType_getIfBlock_endTagInStaticBlock() {

        NextDetectedBlockResult result = TemplateBlockType.getNextBlock("abc !<if ddsdf>sdasd!<static>hvhvhhv!</if>fxf!</static>ds!</if>fd!<static>asdsa!</static>!<if sad> !<for dasdsa> !</for> !</if>");

        MatcherAssert.assertThat(result.getTemplateBlockType(), Matchers.is(TemplateBlockType.IF));
        MatcherAssert.assertThat(result.getAttributes(), Matchers.is("ddsdf"));
        MatcherAssert.assertThat(result.getBeginIndex(), Matchers.is(4));
        MatcherAssert.assertThat(result.getContent(), Matchers.is("sdasd!<static>hvhvhhv!</if>fxf!</static>ds"));
        MatcherAssert.assertThat(result.getRemainingStringToBeProcessed(), Matchers.is("fd!<static>asdsa!</static>!<if sad> !<for dasdsa> !</for> !</if>"));


    }

    @Test
    public void getNextBlockType_getIfBlock_withEmbeddedIfs() {

        NextDetectedBlockResult result = TemplateBlockType.getNextBlock("abc !<if ddsdf>sdasd!<if fd>hvh!<if gh> dg !</if>vhhv!</if>fxf!<if hghg>dg!</if>vs!</if>fd!<static>asdsa!</static>!<if sad> !<for dasdsa> !</for> !</if>");

        MatcherAssert.assertThat(result.getTemplateBlockType(), Matchers.is(TemplateBlockType.IF));
        MatcherAssert.assertThat(result.getAttributes(), Matchers.is("ddsdf"));
        MatcherAssert.assertThat(result.getBeginIndex(), Matchers.is(4));
        MatcherAssert.assertThat(result.getContent(), Matchers.is("sdasd!<if fd>hvh!<if gh> dg !</if>vhhv!</if>fxf!<if hghg>dg!</if>vs"));
        MatcherAssert.assertThat(result.getRemainingStringToBeProcessed(), Matchers.is("fd!<static>asdsa!</static>!<if sad> !<for dasdsa> !</for> !</if>"));


    }


    @Test
    public void controlBlockEmbeddedInStaticBlock_testNotEmbeddedInStaticBlock() {


        String text1 = "!<static>gadsad!</static>sadsd";
        MatcherAssert.assertThat("Should detect no static block", !TemplateBlockType.controlBlockEmbeddedInStaticBlock(text1, text1.length()));

        String text2 = "!<static>gadsad!<static>!</static>sadsd";
        MatcherAssert.assertThat("Should detect no static block", !TemplateBlockType.controlBlockEmbeddedInStaticBlock(text2, text2.length()));

        String text3 = "!<static>gadsad!</static>sad!<static>sasdasds!</static>sd";
        MatcherAssert.assertThat("Should detect no static block", !TemplateBlockType.controlBlockEmbeddedInStaticBlock(text3, text3.length()));

    }

    @Test
    public void controlBlockEmbeddedInStaticBlock_testEmbeddedInStaticBlock() {


        String text1 = "sada!<static>gadsadsadsd";
        MatcherAssert.assertThat("Should detect a static block", TemplateBlockType.controlBlockEmbeddedInStaticBlock(text1, text1.length()));

        String text2 = "adsdas!<static>gadsad!<static>!</static>sad!<static>sd";
        MatcherAssert.assertThat("Should detect a static block", TemplateBlockType.controlBlockEmbeddedInStaticBlock(text2, text2.length()));

        String text3 = "adssd!<static>gadsad!</static>sad!<static>sasdasdssd";
        MatcherAssert.assertThat("Should detect a static block", TemplateBlockType.controlBlockEmbeddedInStaticBlock(text3, text3.length()));

    }


}
