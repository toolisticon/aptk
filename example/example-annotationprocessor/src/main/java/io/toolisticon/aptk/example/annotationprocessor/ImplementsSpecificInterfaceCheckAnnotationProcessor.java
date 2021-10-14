package io.toolisticon.aptk.example.annotationprocessor;

import io.toolisticon.aptk.example.annotations.ImplementsSpecificInterfaceCheckAnnotation;
import io.toolisticon.aptk.example.annotations.SpecificInterface;
import io.toolisticon.aptk.tools.AbstractAnnotationProcessor;
import io.toolisticon.aptk.tools.ElementUtils;
import io.toolisticon.aptk.tools.TypeUtils;
import io.toolisticon.aptk.tools.corematcher.CoreMatchers;
import io.toolisticon.aptk.tools.fluentvalidator.FluentElementValidator;
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
@SupportedAnnotationTypes("io.toolisticon.aptk.example.annotations.ImplementsSpecificInterfaceCheckAnnotation")
@Service(Processor.class)
public class ImplementsSpecificInterfaceCheckAnnotationProcessor extends AbstractAnnotationProcessor {

    // Overriding the getSupportedAnnotationTypes instead of using the SupportedAnnotationTypes annotation
    // might be an option this is especially useful if you have inheritance
    // private final static Set<String> SUPPORTED_ANNOTATION_TYPES =
    //     createSupportedAnnotationSet(ImplementsSpecificInterfaceCheckAnnotation.class);
    // @Override
    // public Set<String> getSupportedAnnotationTypes() {
    //    return SUPPORTED_ANNOTATION_TYPES;
    // }

    @Override
    public boolean processAnnotations(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        for (Element element : roundEnv.getElementsAnnotatedWith(ImplementsSpecificInterfaceCheckAnnotation.class)) {


            // validator already will print output so additional actions are not necessary
            FluentElementValidator.createFluentElementValidator(ElementUtils.CastElement.castToTypeElement(element))
                    .applyValidator(CoreMatchers.IS_ASSIGNABLE_TO).hasOneOf(SpecificInterface.class)
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
