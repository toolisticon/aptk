package de.holisticon.annotationprocessor;

import de.holisticon.annotationprocessortoolkit.TestAnnotation;
import de.holisticon.annotationprocessortoolkit.FilterTestAnnotation1;
import de.holisticon.annotationprocessortoolkit.FilterTestAnnotation2;

/**
 * Test class for annotation processor tools.
 */
@TestAnnotation
public class AnnotationProcessorTestClass {

    private String privateField;
    protected String protectedField;
    String packagePrivateField;
    public String publicField;
    public final String publicFinalField = "";
    public static String publicStaticField;
    public transient String publicTransientField;

    enum TestEnum1 {
        TEST11, TEST12;
    }

    public enum TestEnum2 {
        TEST21, TEST22;
    }

    public static class EmbeddedStaticClass {

    }

    public class EmbeddedClass {

    }

    public class EmbeddedClassWithNoNoargConstructor {

        public EmbeddedClassWithNoNoargConstructor(String abs) {

        }

    }

    public abstract class AbstractEmbeddedClass {

        public abstract void abstractMethod();

    }

    {
        int x = 0;
    }

    static {
        int y = 0;
    }

    public AnnotationProcessorTestClass() {

    }

    public AnnotationProcessorTestClass(String withParameter) {

    }

    @FilterTestAnnotation1
    @FilterTestAnnotation2
    public synchronized void synchronizedMethod() {

    }

    public String methodWithReturnTypeAndParameters(Boolean first, String second) {
        return "";
    }

}
