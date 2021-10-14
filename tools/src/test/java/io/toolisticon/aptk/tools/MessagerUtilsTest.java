package io.toolisticon.aptk.tools;

import io.toolisticon.aptk.common.ToolingProvider;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;
import java.util.Arrays;

/**
 * Unit test for {@link MessagerUtils}.
 */
public class MessagerUtilsTest {

    final static String MESSSAGE = "message ${0} : ${1}";
    final static String MESSAGE_ARG_1 = "A";
    final static String MESSAGE_ARG_2 = "B";
    final static String MESSSAGE_STR_WITH_REPLACED_ARGUMENTS = "message " + MESSAGE_ARG_1 + " : " + MESSAGE_ARG_2 + "";
    final static String MESSSAGE_STR_WITH_REPLACED_FIRST_ARGUMENTS = "message " + MESSAGE_ARG_1 + " : ${1}";


    private MessagerUtils unit;

    private Messager messager;


    private Element element;
    private AnnotationMirror annotationMirror;
    private AnnotationValue annotationValue;

    @Before
    public void init() {
        element = Mockito.mock(Element.class);
        messager = Mockito.spy(Messager.class);
        annotationMirror = Mockito.spy(AnnotationMirror.class);
        annotationValue = Mockito.spy(AnnotationValue.class);


        ProcessingEnvironment processingEnvironment = Mockito.mock(ProcessingEnvironment.class);
        Mockito.when(processingEnvironment.getMessager()).thenReturn(messager);

        ToolingProvider.setTooling(processingEnvironment);

        unit = MessagerUtils.getMessagerUtils();

    }

    // ----------------------------------------------------
    // Test common stuff
    // ----------------------------------------------------

    @Test
    public void testGetMessaggerUtils() {

        MatcherAssert.assertThat(unit.getMessager(), Matchers.is(messager));

    }

    @Test
    public void createMessage_exactNumberOfParameters() {

        MatcherAssert.assertThat(MessagerUtils.createMessage("TEST ${0}, ${2}, ${1}", 1, "YES", true), Matchers.is("TEST 1, true, YES"));

    }

    @Test
    public void createMessage_smallerNumberOfParameters() {

        MatcherAssert.assertThat(MessagerUtils.createMessage("TEST ${0}, ${2}, ${1}", 1, "YES"), Matchers.is("TEST 1, ${2}, YES"));

    }

    @Test
    public void createMessage_greaterNumberOfParameters() {

        MatcherAssert.assertThat(MessagerUtils.createMessage("TEST ${0}, ${2}, ${1}", 1, "YES", true, "WTF"), Matchers.is("TEST 1, true, YES"));

    }

    @Test
    public void createMessage_noParameters() {

        MatcherAssert.assertThat(MessagerUtils.createMessage("TEST ${0}, ${2}, ${1}"), Matchers.is("TEST ${0}, ${2}, ${1}"));

    }


    @Test
    public void argToString_nullValue() {
        MatcherAssert.assertThat(MessagerUtils.argToString(null), Matchers.is("<NULL>"));
    }

    @Test
    public void argToString_arrayValue() {

        String[] array = {"A", "B", "C"};
        MatcherAssert.assertThat(MessagerUtils.argToString(array), Matchers.is("[A, B, C]"));

    }

    @Test
    public void argToString_collectionValue() {

        String[] array = {"A", "B", "C"};

        MatcherAssert.assertThat(MessagerUtils.argToString(Arrays.asList(array)), Matchers.is("[A, B, C]"));

    }

    @Test
    public void argToString_otherValue() {

        MatcherAssert.assertThat(MessagerUtils.argToString(1), Matchers.is("1"));

    }


    // ----------------------------------------------------
    // Test different number of args and it's stability
    // ----------------------------------------------------

    @Test
    public void testErrorElementMessage1xArgumentEvenMessageHas2Placeholders() {

        unit.error(element, MESSSAGE, MESSAGE_ARG_1);

        Mockito.verify(messager).printMessage(Diagnostic.Kind.ERROR, MESSSAGE_STR_WITH_REPLACED_FIRST_ARGUMENTS, element);

    }

    @Test
    public void testErrorElementMessageWithoutArgumentEvenMessageHas2Placeholders() {

        unit.error(element, MESSSAGE);

        Mockito.verify(messager).printMessage(Diagnostic.Kind.ERROR, MESSSAGE, element);

    }

    @Test
    public void testErrorElementMessage3xArgumentEvenMessageHas2Placeholders() {

        unit.error(element, MESSSAGE, MESSAGE_ARG_1, MESSAGE_ARG_2, "XXX");

        Mockito.verify(messager).printMessage(Diagnostic.Kind.ERROR, MESSSAGE_STR_WITH_REPLACED_ARGUMENTS, element);

    }

    @Test
    public void testErrorElementMessageNullValuedArgumentEvenMessageHas2Placeholders() {

        unit.error(element, MESSSAGE, null, null);

        Mockito.verify(messager).printMessage(Diagnostic.Kind.ERROR, "message null : null", element);

    }


    // ----------------------------------------------------
    // Test string based pribtMessage methods
    // ----------------------------------------------------

    @Test
    public void printMessage_testStringBasedMessage() {

        unit.printMessage(element, Diagnostic.Kind.ERROR, MESSSAGE, MESSAGE_ARG_1, MESSAGE_ARG_2);

        Mockito.verify(messager).printMessage(Diagnostic.Kind.ERROR, MESSSAGE_STR_WITH_REPLACED_ARGUMENTS, element);

    }

    @Test
    public void printMessage_testStringBasedMessageWithAnnotationMirror() {

        unit.printMessage(element, annotationMirror, Diagnostic.Kind.ERROR, MESSSAGE, MESSAGE_ARG_1, MESSAGE_ARG_2);

        Mockito.verify(messager).printMessage(Diagnostic.Kind.ERROR, MESSSAGE_STR_WITH_REPLACED_ARGUMENTS, element, annotationMirror);

    }

    @Test
    public void printMessage_testStringBasedMessageWithAnnotationMirrorAndValue() {

        unit.printMessage(element, annotationMirror, annotationValue, Diagnostic.Kind.ERROR, MESSSAGE, MESSAGE_ARG_1, MESSAGE_ARG_2);

        Mockito.verify(messager).printMessage(Diagnostic.Kind.ERROR, MESSSAGE_STR_WITH_REPLACED_ARGUMENTS, element, annotationMirror, annotationValue);

    }

    // ----------------------------------------------------
    // Test different kind of MessagerUtils methods
    // passed element, annotationmirror, message, message arguments
    // ----------------------------------------------------

    @Test
    public void testErrorElementAnnotationMirrorAnnotationValueMessage2xArgument() {

        unit.error(element, annotationMirror, annotationValue, MESSSAGE, MESSAGE_ARG_1, MESSAGE_ARG_2);

        Mockito.verify(messager).printMessage(Diagnostic.Kind.ERROR, MESSSAGE_STR_WITH_REPLACED_ARGUMENTS, element, annotationMirror, annotationValue);

    }


    @Test
    public void testWarningElementAnnotationMirrorAnnotationValueMessage2xArgument() {

        unit.warning(element, annotationMirror, annotationValue, MESSSAGE, MESSAGE_ARG_1, MESSAGE_ARG_2);

        Mockito.verify(messager).printMessage(Diagnostic.Kind.WARNING, MESSSAGE_STR_WITH_REPLACED_ARGUMENTS, element, annotationMirror, annotationValue);

    }

    @Test
    public void testMandatoryWarningElementAnnotationAnnotationValueMirrorMessage2xArgument() {

        unit.mandatoryWarning(element, annotationMirror, annotationValue, MESSSAGE, MESSAGE_ARG_1, MESSAGE_ARG_2);

        Mockito.verify(messager).printMessage(Diagnostic.Kind.MANDATORY_WARNING, MESSSAGE_STR_WITH_REPLACED_ARGUMENTS, element, annotationMirror, annotationValue);

    }

    @Test
    public void testInfoElementAnnotationMirrorAnnotationValueMessage2xArgument() {

        unit.info(element, annotationMirror, annotationValue, MESSSAGE, MESSAGE_ARG_1, MESSAGE_ARG_2);

        Mockito.verify(messager).printMessage(Diagnostic.Kind.NOTE, MESSSAGE_STR_WITH_REPLACED_ARGUMENTS, element, annotationMirror, annotationValue);

    }

    @Test
    public void testOtherElementAnnotationMirrorAnnotationValueMessage2xArgument() {

        unit.other(element, annotationMirror, annotationValue, MESSSAGE, MESSAGE_ARG_1, MESSAGE_ARG_2);

        Mockito.verify(messager).printMessage(Diagnostic.Kind.OTHER, MESSSAGE_STR_WITH_REPLACED_ARGUMENTS, element, annotationMirror, annotationValue);

    }

    // ----------------------------------------------------
    // Test different kind of MessagerUtils methods
    // passed element, annotationmirror, message, message arguments
    // ----------------------------------------------------

    @Test
    public void testErrorElementAnnotationMirrorMessage2xArgument() {

        unit.error(element, annotationMirror, MESSSAGE, MESSAGE_ARG_1, MESSAGE_ARG_2);

        Mockito.verify(messager).printMessage(Diagnostic.Kind.ERROR, MESSSAGE_STR_WITH_REPLACED_ARGUMENTS, element, annotationMirror);

    }


    @Test
    public void testWarningElementAnnotationMirrorMessage2xArgument() {

        unit.warning(element, annotationMirror, MESSSAGE, MESSAGE_ARG_1, MESSAGE_ARG_2);

        Mockito.verify(messager).printMessage(Diagnostic.Kind.WARNING, MESSSAGE_STR_WITH_REPLACED_ARGUMENTS, element, annotationMirror);

    }

    @Test
    public void testMandatoryWarningElementAnnotationMirrorMessage2xArgument() {

        unit.mandatoryWarning(element, annotationMirror, MESSSAGE, MESSAGE_ARG_1, MESSAGE_ARG_2);

        Mockito.verify(messager).printMessage(Diagnostic.Kind.MANDATORY_WARNING, MESSSAGE_STR_WITH_REPLACED_ARGUMENTS, element, annotationMirror);

    }

    @Test
    public void testInfoElementAnnotationMirrorMessage2xArgument() {

        unit.info(element, annotationMirror, MESSSAGE, MESSAGE_ARG_1, MESSAGE_ARG_2);

        Mockito.verify(messager).printMessage(Diagnostic.Kind.NOTE, MESSSAGE_STR_WITH_REPLACED_ARGUMENTS, element, annotationMirror);

    }

    @Test
    public void testOtherElementAnnotationMirrorMessage2xArgument() {

        unit.other(element, annotationMirror, MESSSAGE, MESSAGE_ARG_1, MESSAGE_ARG_2);

        Mockito.verify(messager).printMessage(Diagnostic.Kind.OTHER, MESSSAGE_STR_WITH_REPLACED_ARGUMENTS, element, annotationMirror);

    }

    // ----------------------------------------------------
    // Test different kind of MessagerUtils methods
    // passed element, message, message arguments
    // ----------------------------------------------------

    @Test
    public void testErrorElementMessage2xArgument() {

        unit.error(element, MESSSAGE, MESSAGE_ARG_1, MESSAGE_ARG_2);
        Mockito.verify(messager).printMessage(Diagnostic.Kind.ERROR, MESSSAGE_STR_WITH_REPLACED_ARGUMENTS, element);

    }


    @Test
    public void testWarningElementMessage2xArgument() {

        unit.warning(element, MESSSAGE, MESSAGE_ARG_1, MESSAGE_ARG_2);

        Mockito.verify(messager).printMessage(Diagnostic.Kind.WARNING, MESSSAGE_STR_WITH_REPLACED_ARGUMENTS, element);

    }

    @Test
    public void testMandatoryWarningElementMessage2xArgument() {

        unit.mandatoryWarning(element, MESSSAGE, MESSAGE_ARG_1, MESSAGE_ARG_2);

        Mockito.verify(messager).printMessage(Diagnostic.Kind.MANDATORY_WARNING, MESSSAGE_STR_WITH_REPLACED_ARGUMENTS, element);

    }

    @Test
    public void testInfoElementMessage2xArgument() {

        unit.info(element, MESSSAGE, MESSAGE_ARG_1, MESSAGE_ARG_2);

        Mockito.verify(messager).printMessage(Diagnostic.Kind.NOTE, MESSSAGE_STR_WITH_REPLACED_ARGUMENTS, element);

    }

    @Test
    public void testOtherElementMessage2xArgument() {

        unit.other(element, MESSSAGE, MESSAGE_ARG_1, MESSAGE_ARG_2);

        Mockito.verify(messager).printMessage(Diagnostic.Kind.OTHER, MESSSAGE_STR_WITH_REPLACED_ARGUMENTS, element);

    }

}
