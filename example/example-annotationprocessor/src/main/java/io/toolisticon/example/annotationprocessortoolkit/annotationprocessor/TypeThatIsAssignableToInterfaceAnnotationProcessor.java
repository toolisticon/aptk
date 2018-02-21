package io.toolisticon.example.annotationprocessortoolkit.annotationprocessor;

import io.toolisticon.annotationprocessortoolkit.AbstractAnnotationProcessor;
import io.toolisticon.annotationprocessortoolkit.tools.ElementUtils;
import io.toolisticon.annotationprocessortoolkit.tools.TypeUtils;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.CoreMatchers;
import io.toolisticon.annotationprocessortoolkit.tools.fluentvalidator.FluentElementValidator;
import io.toolisticon.example.annotationprocessortoolkit.annotations.SomeInterface;
import io.toolisticon.example.annotationprocessortoolkit.annotations.TypeThatIsAssignableToInterfaceAnnotation;
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
@SupportedAnnotationTypes("io.toolisticon.example.annotationprocessortoolkit.annotations.TypeThatIsAssignableToInterfaceAnnotation")
@Service(Processor.class)
public class TypeThatIsAssignableToInterfaceAnnotationProcessor extends AbstractAnnotationProcessor {

    // Overriding the getSupportedAnnotationTypes instead of using the SupportedAnnotationTypes annotation
    // might be an option this is especially useful if you have inheritance
    // private final static Set<String> SUPPORTED_ANNOTATION_TYPES =
    //     createSupportedAnnotationSet(TypeThatIsAssignableToInterfaceAnnotation.class);
    // @Override
    // public Set<String> getSupportedAnnotationTypes() {
    //    return SUPPORTED_ANNOTATION_TYPES;
    // }

    @Override
    public boolean processAnnotations(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        for (Element element : roundEnv.getElementsAnnotatedWith(TypeThatIsAssignableToInterfaceAnnotation.class)) {


            // validator already will print output so additional actions are not necessary
            FluentElementValidator.createFluentElementValidator(ElementUtils.CastElement.castToTypeElement(element))
                    .applyValidator(CoreMatchers.IS_ASSIGNABLE_TO).hasOneOf(SomeInterface.class)
                    .validateAndIssueMessages();

        }


        return false;
    }

    protected boolean isAssignableTo(Element element, String fqn) {
        return TypeUtils
                .getTypes()
                .isAssignable(
                        element.asType(),
                        TypeUtils.TypeRetrieval.getTypeMirror(fqn)
                );
    }


}
