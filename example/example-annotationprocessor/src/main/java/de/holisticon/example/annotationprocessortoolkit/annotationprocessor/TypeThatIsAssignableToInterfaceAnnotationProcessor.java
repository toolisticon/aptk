package de.holisticon.example.annotationprocessortoolkit.annotationprocessor;

import de.holisticon.annotationprocessortoolkit.AbstractAnnotationProcessor;
import de.holisticon.annotationprocessortoolkit.tools.ElementUtils;
import de.holisticon.example.annotationprocessortoolkit.annotations.SomeInterface;
import de.holisticon.example.annotationprocessortoolkit.annotations.TypeThatIsAssignableToInterfaceAnnotation;

import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Set;

/**
 * Test annotation processor which demonstrates the usage of the annotation processor toolkit.
 */
@SupportedAnnotationTypes({"de.holisticon.example.annotationprocessortoolkit.annotations.TypeThatIsAssignableToInterfaceAnnotation"})
public class TypeThatIsAssignableToInterfaceAnnotationProcessor extends AbstractAnnotationProcessor {

    // Overriding the getSupportedAnnotationTypes instead of using the SupportedAnnotationTypes annotation might be an option this is especially useful if you have inheritance
    // private final static Set<String> SUPPORTED_ANNOTATION_TYPES = createSupportedAnnotationSet(TypeThatIsAssignableToInterfaceAnnotation.class);
    // @Override
    // public Set<String> getSupportedAnnotationTypes() {
    //    return SUPPORTED_ANNOTATION_TYPES;
    // }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        for (Element element : roundEnv.getElementsAnnotatedWith(TypeThatIsAssignableToInterfaceAnnotation.class)) {


            // validator already will print output so additional actions are not necessary
            getFluentTypeValidator(ElementUtils.getElementUtils().castToTypeElement(element)).isAssignableTo(SomeInterface.class).getValidationResult();

        }


        return false;
    }


}
