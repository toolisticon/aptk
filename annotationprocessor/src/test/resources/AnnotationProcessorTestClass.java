package io.toolisticon.annotationprocessor;

import io.toolisticon.annotationprocessortoolkit.FilterTestAnnotation1;
import io.toolisticon.annotationprocessortoolkit.FilterTestAnnotation2;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.TestAnnotation;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

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

    public Comparator<Long> comparatorWithAnonymousClass = new Comparator<Long>() {
        @Override
        public int compare(Long o1, Long o2) {
            return 0;
        }
    };


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


    public int testGenericsOnParameter(Map<String, Comparator<Long>> o1, Map<? extends StringBuilder, Comparator<? super List<?>>> o2) {
        return 0;
    }

}
