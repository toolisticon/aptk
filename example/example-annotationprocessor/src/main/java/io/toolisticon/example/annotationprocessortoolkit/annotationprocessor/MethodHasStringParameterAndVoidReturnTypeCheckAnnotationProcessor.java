package io.toolisticon.example.annotationprocessortoolkit.annotationprocessor;

import io.toolisticon.annotationprocessortoolkit.AbstractAnnotationProcessor;
import io.toolisticon.annotationprocessortoolkit.tools.ElementUtils;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.CoreMatchers;
import io.toolisticon.annotationprocessortoolkit.tools.fluentvalidator.FluentElementValidator;
import io.toolisticon.example.annotationprocessortoolkit.annotations.MethodWithOneStringParameterAndVoidReturnTypeAnnotation;
import io.toolisticon.spiap.api.Service;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Set;

/**
 * Test annotation processor which demonstrates the usage of the annotation processor toolkit.
 */
@SupportedAnnotationTypes(
        "io.toolisticon.example.annotationprocessortoolkit.annotations.MethodWithOneStringParameterAndVoidReturnTypeAnnotation")
@Service(Processor.class)
public class MethodHasStringParameterAndVoidReturnTypeCheckAnnotationProcessor extends AbstractAnnotationProcessor {


    // Overriding the getSupportedAnnotationTypes instead of using the SupportedAnnotationTypes annotation
    // might be an option this is especially useful if you have inheritance
    // private final static Set<String> SUPPORTED_ANNOTATION_TYPES =
    //      createSupportedAnnotationSet(MethodWithOneStringParameterAndVoidReturnTypeAnnotation.class);
    // @Override
    // public Set<String> getSupportedAnnotationTypes() {
    //    return SUPPORTED_ANNOTATION_TYPES;
    // }

    @Override
    public boolean processAnnotations(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        getMessager().info(null, "Start processing");

        for (Element element : roundEnv.getElementsAnnotatedWith(
                MethodWithOneStringParameterAndVoidReturnTypeAnnotation.class)) {


            // validator already will print output so additional actions are not necessary
            FluentElementValidator.createFluentElementValidator(ElementUtils.CastElement.castMethod(element))
                    .applyValidator(CoreMatchers.HAS_VOID_RETURN_TYPE)
                    .applyValidator(CoreMatchers.BY_PARAMETER_TYPE).hasOneOf(wrapToArray(String.class))
                    .validateAndIssueMessages();



        }


        return false;
    }


}
