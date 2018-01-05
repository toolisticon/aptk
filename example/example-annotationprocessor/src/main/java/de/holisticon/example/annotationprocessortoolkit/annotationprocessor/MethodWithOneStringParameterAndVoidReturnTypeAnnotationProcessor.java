package de.holisticon.example.annotationprocessortoolkit.annotationprocessor;

import de.holisticon.annotationprocessortoolkit.AbstractAnnotationProcessor;
import de.holisticon.annotationprocessortoolkit.tools.ElementUtils;
import de.holisticon.annotationprocessortoolkit.tools.MessagerUtils;
import de.holisticon.annotationprocessortoolkit.validators.FluentExecutableElementValidator;
import de.holisticon.annotationprocessortoolkit.validators.FluentModifierElementValidator;
import de.holisticon.example.annotationprocessortoolkit.annotations.MethodWithOneStringParameterAndVoidReturnTypeAnnotation;
import io.toolisticon.spiap.api.Service;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.util.Set;

/**
 * Test annotation processor which demonstrates the usage of the annotation processor toolkit.
 */
@SupportedAnnotationTypes(
        "de.holisticon.example.annotationprocessortoolkit.annotations.MethodWithOneStringParameterAndVoidReturnTypeAnnotation")
@Service(Processor.class)
public class MethodWithOneStringParameterAndVoidReturnTypeAnnotationProcessor extends AbstractAnnotationProcessor {


    // Overriding the getSupportedAnnotationTypes instead of using the SupportedAnnotationTypes annotation
    // might be an option this is especially useful if you have inheritance
    // private final static Set<String> SUPPORTED_ANNOTATION_TYPES =
    //      createSupportedAnnotationSet(MethodWithOneStringParameterAndVoidReturnTypeAnnotation.class);
    // @Override
    // public Set<String> getSupportedAnnotationTypes() {
    //    return SUPPORTED_ANNOTATION_TYPES;
    // }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        for (Element element : roundEnv.getElementsAnnotatedWith(
                MethodWithOneStringParameterAndVoidReturnTypeAnnotation.class)) {


            // validator already will print output so additional actions are not necessary
            getFluentMethodValidator(ElementUtils.CastElement.castMethod(element))
                    .hasVoidReturnType()
                    .hasParameters(String.class)
                    .getValidationResult();


        }


        return false;
    }


}
