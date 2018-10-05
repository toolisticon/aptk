package io.toolisticon.annotationprocessortoolkit.testhelper.compiletest;

import javax.annotation.processing.Processor;

public class CompileTestUtilities {

    private final static String TEMPLATE_ANNOTATION_PROCESSOR_WAS_APPLIED = "!!!--- ANNOTATION PROCESSOR (%s) WAS APPPLIED ---!!!";

    public static String getAnnotationProcessorWasAppliedMessage (Processor processor) {
        return String.format(TEMPLATE_ANNOTATION_PROCESSOR_WAS_APPLIED, processor.getClass().getCanonicalName());
    }

}
