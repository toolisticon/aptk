package io.toolisticon.annotationprocessortoolkit.tools;

import io.toolisticon.annotationprocessortoolkit.internal.FrameworkToolWrapper;
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
        unit = MessagerUtils.getMessagerUtils(new FrameworkToolWrapper(processingEnvironment));

    }

    // ----------------------------------------------------
    // Test common stuff
    // ----------------------------------------------------

    @Test
    public void testGetMessaggerUtils() {

        MatcherAssert.assertThat(unit.getMessager(), Matchers.is(messager));

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
