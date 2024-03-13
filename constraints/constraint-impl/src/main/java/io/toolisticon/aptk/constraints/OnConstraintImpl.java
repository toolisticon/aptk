package io.toolisticon.aptk.constraints;


import io.toolisticon.aptk.compilermessage.api.DeclareCompilerMessage;
import io.toolisticon.aptk.tools.ElementUtils;
import io.toolisticon.aptk.tools.MessagerUtils;
import io.toolisticon.aptk.tools.wrapper.ElementWrapper;
import io.toolisticon.spiap.api.SpiService;
import io.toolisticon.spiap.api.SpiServices;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@SpiServices({
        @SpiService(value = AnnotationConstraintSpi.class),
        @SpiService(value = ManualConstraintSpi.class)
})
@DeclareCompilerMessage(code = "ON_001", enumValueName = "ON_ERROR_MATCHING_TARGET_NOT_FOUND", message = "Couldn't find matching target value for On Location ${0}", processorClass = BasicConstraints.class)
@DeclareCompilerMessage(code = "ON_002", enumValueName = "ON_ERROR_TARGET_ANNOTATION_NOT_FOUND", message = "No target annotation has been found. Please either remove On Constraint annotation or add matching Target annotation.", processorClass = BasicConstraints.class)
@DeclareCompilerMessage(code = "ON_003", enumValueName = "ON_ERROR_WRONG_USAGE", message = "'${0}' Constraint violated: Annotation ${1} must be placed on either ${2}", processorClass = BasicConstraints.class)

public class OnConstraintImpl implements AnnotationConstraintSpi, ManualConstraintSpi {

    @Override
    public Class<? extends Annotation> getSupportedAnnotation() {
        return On.class;
    }

    @Override
    public void checkManualConstraints(Element annotatedElement, AnnotationMirror constraintAnnotationMirror) {

        ElementWrapper annotatedElementWrapper = ElementWrapper.wrap(annotatedElement);

        if (!annotatedElementWrapper.isAnnotation()) {
            MessagerUtils.error(annotatedElement, constraintAnnotationMirror, BasicConstraintsCompilerMessages.BASE_ERROR_MUST_BE_PLACE_ON_ANNOTATION_TYPE);
        }

        // Now check if annotation value matches
        Target target = annotatedElement.getAnnotation(Target.class);
        OnWrapper onAnnotation = OnWrapper.wrap(constraintAnnotationMirror);


        if (target != null) {

            for (On.Location location : onAnnotation.value()) {

                boolean foundLocation = false;

                for (ElementType elementType : target.value()) {
                    switch (location) {
                        case CLASS:
                        case INTERFACE:
                        case ENUM: {
                            if (ElementType.TYPE.equals(elementType)) {
                                foundLocation = true;
                            }
                            break;
                        }
                        case ANNOTATION: {
                            if (ElementType.TYPE.equals(elementType) || ElementType.ANNOTATION_TYPE.equals(elementType)) {
                                foundLocation = true;
                            }
                            break;
                        }
                        case METHOD:
                        case ANNOTATION_ATTRIBUTE: {
                            if (ElementType.METHOD.equals(elementType)) {
                                foundLocation = true;
                            }
                            break;
                        }
                        case CONSTRUCTOR: {
                            if (ElementType.CONSTRUCTOR.equals(elementType)) {
                                foundLocation = true;
                            }
                            break;
                        }
                        case FIELD: {
                            if (ElementType.FIELD.equals(elementType)) {
                                foundLocation = true;
                            }
                            break;
                        }
                        case PACKAGE: {
                            if (ElementType.PACKAGE.equals(elementType)) {
                                foundLocation = true;
                            }
                            break;
                        }
                        case PARAMETER:
                        case CONSTRUCTOR_PARAMETER:
                        case METHOD_PARAMETER: {
                            if (ElementType.PARAMETER.equals(elementType)) {
                                foundLocation = true;
                            }
                            break;
                        }

                    }

                    // skip searching if match has been found
                    if (foundLocation) {
                        break;
                    }
                }

                if (!foundLocation) {
                    MessagerUtils.error(annotatedElement, constraintAnnotationMirror, BasicConstraintsCompilerMessages.ON_ERROR_MATCHING_TARGET_NOT_FOUND, location.toString());
                }

            }

        } else {
            MessagerUtils.error(annotatedElement, BasicConstraintsCompilerMessages.ON_ERROR_TARGET_ANNOTATION_NOT_FOUND);
        }


    }


    @Override
    public boolean checkConstraints(Element annotatedElement, AnnotationMirror annotationMirrorToCheck, AnnotationMirror constraintAnnotationMirror, String attributeNameToBeCheckedByConstraint) {


        // Now check if annotation

        OnWrapper onAnnotation = OnWrapper.wrap(constraintAnnotationMirror);

        boolean foundMatchingElementType = false;
        for (On.Location location : onAnnotation.value()) {

            switch (location) {
                case ANNOTATION_ATTRIBUTE: {
                    // must be placed on method of annotation type
                    if (ElementUtils.CheckKindOfElement.isMethod(annotatedElement) && ElementUtils.CheckKindOfElement.isAnnotation(annotatedElement.getEnclosingElement())) {
                        foundMatchingElementType = true;
                    }
                    break;
                }
                case PACKAGE: {
                    // must be placed on Package
                    foundMatchingElementType = ElementUtils.CheckKindOfElement.isPackage(annotatedElement);
                    break;
                }
                case ANNOTATION: {
                    // must be placed on Annotation Type
                    foundMatchingElementType = ElementUtils.CheckKindOfElement.isAnnotation(annotatedElement);
                    break;
                }
                case CLASS: {
                    // must be placed on Type(Class)
                    foundMatchingElementType = ElementUtils.CheckKindOfElement.isClass(annotatedElement);
                    break;
                }
                case INTERFACE: {
                    // must be placed on Interface
                    foundMatchingElementType = ElementUtils.CheckKindOfElement.isInterface(annotatedElement);
                    break;
                }
                case ENUM: {
                    // must be placed on Enum
                    foundMatchingElementType = ElementUtils.CheckKindOfElement.isEnum(annotatedElement);
                    break;
                }
                case CONSTRUCTOR: {
                    // must be placed on constructor
                    foundMatchingElementType = ElementUtils.CheckKindOfElement.isConstructor(annotatedElement);
                    break;
                }
                case METHOD: {
                    // must be placed on method, but not on annotation attribute
                    if (ElementUtils.CheckKindOfElement.isMethod(annotatedElement) && !ElementUtils.CheckKindOfElement.isAnnotation(annotatedElement.getEnclosingElement())) {
                        foundMatchingElementType = true;
                    }
                    break;
                }
                case FIELD: {
                    // must be placed on field
                    foundMatchingElementType = ElementUtils.CheckKindOfElement.isField(annotatedElement);
                    break;
                }
                case PARAMETER: {
                    // must be placed on Parameter
                    foundMatchingElementType = ElementUtils.CheckKindOfElement.isParameter(annotatedElement);
                    break;
                }
                case METHOD_PARAMETER: {
                    // must be placed on Parameter
                    foundMatchingElementType = ElementUtils.CheckKindOfElement.isParameter(annotatedElement) && ElementUtils.CheckKindOfElement.isMethod(annotatedElement.getEnclosingElement());
                    break;
                }
                case CONSTRUCTOR_PARAMETER: {
                    // must be placed on Parameter
                    foundMatchingElementType = ElementUtils.CheckKindOfElement.isParameter(annotatedElement) && ElementUtils.CheckKindOfElement.isConstructor(annotatedElement.getEnclosingElement());
                    break;
                }
            }
            // skip search if matching ElementType has been detected
            if (foundMatchingElementType) {
                break;
            }

        }

        // trigger error message if matching type hasn't been found
        if (!foundMatchingElementType) {
            MessagerUtils.error(annotatedElement, annotationMirrorToCheck, BasicConstraintsCompilerMessages.ON_ERROR_WRONG_USAGE, UtilityFunctions.getSimpleName(constraintAnnotationMirror), UtilityFunctions.getSimpleName(annotationMirrorToCheck), onAnnotation.value());
            return false;
        }

        return true;
    }
}