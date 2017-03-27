package de.holisticon.annotationprocessor.example.annotationprocessor;

import de.holisticon.annotationprocessor.AbstractAnnotationProcessor;
import de.holisticon.annotationprocessor.example.annotations.SomeInterface;
import de.holisticon.annotationprocessor.example.annotations.TypeThatIsAssignableToInterfaceAnnotation;
import de.holisticon.annotationprocessor.tools.ElementUtils;

import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.HashSet;
import java.util.Set;

/**
 * Test annotation processor which demonstrates the usage of the annotation processor toolkit.
 */
@SupportedAnnotationTypes({"de.holisticon.annotationprocessor.example.annotations.TypeThatIsAssignableToInterfaceAnnotation"})
public class TypeThatIsAssignableToInterfaceAnnotationProcessor extends AbstractAnnotationProcessor {

    private final static Set<String> SUPPORTED_ANNOTATION_TYPES = new HashSet<String>();

    static {
        SUPPORTED_ANNOTATION_TYPES.add(TypeThatIsAssignableToInterfaceAnnotation.class.getCanonicalName());
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return SUPPORTED_ANNOTATION_TYPES;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        for (Element element : roundEnv.getElementsAnnotatedWith(TypeThatIsAssignableToInterfaceAnnotation.class)) {


            // validator already will print output so additional actions are not necessary
            getFluentTypeValidator(ElementUtils.getElementUtils().castToTypeElement(element)).isAssignableTo(SomeInterface.class).validate();

        }


        return false;
    }


}
