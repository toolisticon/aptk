package io.toolisticon.aptk.constraints;

import io.toolisticon.aptk.compilermessage.api.DeclareCompilerMessage;
import io.toolisticon.aptk.tools.wrapper.ElementWrapper;
import io.toolisticon.aptk.tools.wrapper.ExecutableElementWrapper;
import io.toolisticon.spiap.api.SpiService;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import java.lang.annotation.Annotation;
import java.util.Optional;

@SpiService(value = AnnotationConstraintSpi.class)
@DeclareCompilerMessage(code = "ONATTRIBUTETYPE_001", enumValueName = "ONATTRIBUTETYPE_ERROR_WRONG_USAGE", message = "'${0}' Constraint violated: Annotation ${1} must be placed on annotation attribute of kind ${2}", processorClass = BasicConstraints.class)
public class WithAnnotationAttributeTargetOfTypeConstraintImpl implements AnnotationConstraintSpi {

    @Override
    public Class<? extends Annotation> getSupportedAnnotation() {
        return WithAnnotationAttributeTargetOfType.class;
    }


    @Override
    public boolean checkConstraints(Element annotatedElement, AnnotationMirror annotationMirrorToCheck, AnnotationMirror constraintAnnotationMirror, String attributeNameToBeCheckedByConstraint) {

        ElementWrapper<Element> wrappedElement = ElementWrapper.wrap(annotatedElement);
        Optional<ElementWrapper<Element>> enclosingElement = wrappedElement.getEnclosingElement();

        if (!enclosingElement.isPresent() || !enclosingElement.get().isAnnotation() || !wrappedElement.isExecutableElement()) {
            // TODO: must send warning that constraint is ignored because its not placed correctly
            wrappedElement.compilerMessage(annotationMirrorToCheck).asWarning().write(BasicConstraintsCompilerMessages.BASE_WARNING_INVALID_USAGE_OF_CONSTRAINT, constraintAnnotationMirror.getAnnotationType().asElement().getSimpleName());
            return true;
        }

        // It's safe to cast now
        ExecutableElementWrapper attributeElementWrapper = ExecutableElementWrapper.wrap((ExecutableElement) annotatedElement);

        // Now check if annotation
        WithAnnotationAttributeTargetOfTypeWrapper onAnnotationOfType = WithAnnotationAttributeTargetOfTypeWrapper.wrap(constraintAnnotationMirror);

        boolean foundMatchingElementType = false;
        loop:
        for (WithAnnotationAttributeTargetOfType.AttributeType targetAttributeType : onAnnotationOfType.value()) {

            switch (targetAttributeType) {
                case ARRAY: {
                    if (
                            attributeElementWrapper.getReturnType().isArray()
                    ) {
                        foundMatchingElementType = true;
                        break loop;
                    }
                    break;
                }

                case SINGLE_VALUE: {
                    if (
                            !attributeElementWrapper.getReturnType().isArray()
                    ) {
                        foundMatchingElementType = true;
                        break loop;
                    }
                    break;
                }

                case CLASS: {
                    if (
                            attributeElementWrapper.getReturnType().isClass()
                    ) {
                        foundMatchingElementType = true;
                        break loop;
                    }
                    break;
                }
                case CLASS_ARRAY: {
                    if (
                            attributeElementWrapper.getReturnType().isArray() && attributeElementWrapper.getReturnType().getWrappedComponentType().isClass()
                    ) {
                        foundMatchingElementType = true;
                        break loop;
                    }
                    break;
                }
                case ENUM: {
                    if (
                            attributeElementWrapper.getReturnType().isEnum()
                    ) {
                        foundMatchingElementType = true;
                        break loop;
                    }
                    break;
                }
                case ENUM_ARRAY: {
                    if (
                            attributeElementWrapper.getReturnType().isArray() && attributeElementWrapper.getReturnType().getWrappedComponentType().isEnum()
                    ) {
                        foundMatchingElementType = true;
                        break loop;
                    }
                    break;
                }
                case ANNOTATION: {
                    if (
                            attributeElementWrapper.getReturnType().isAnnotation()
                    ) {
                        foundMatchingElementType = true;
                        break loop;
                    }
                    break;
                }
                case ANNOTATION_ARRAY: {
                    if (
                            attributeElementWrapper.getReturnType().isArray() && attributeElementWrapper.getReturnType().getWrappedComponentType().isArray()
                    ) {
                        foundMatchingElementType = true;
                        break loop;
                    }
                    break;
                }
                case FLOAT: {
                    if (
                            attributeElementWrapper.getReturnType().isPrimitive()
                                    && attributeElementWrapper.getReturnType().getSimpleName().equals(float.class.getSimpleName())
                    ) {
                        foundMatchingElementType = true;
                        break loop;
                    }
                    break;
                }
                case FLOAT_ARRAY: {
                    if (
                            attributeElementWrapper.getReturnType().isArray()
                                    && attributeElementWrapper.getReturnType().getWrappedComponentType().isPrimitive()
                                    && attributeElementWrapper.getReturnType().getWrappedComponentType().getSimpleName().equals(float.class.getSimpleName())
                    ) {
                        foundMatchingElementType = true;
                        break loop;
                    }
                    break;
                }
                case DOUBLE: {
                    if (
                            attributeElementWrapper.getReturnType().isPrimitive()
                                    && attributeElementWrapper.getReturnType().getSimpleName().equals(double.class.getSimpleName())
                    ) {
                        foundMatchingElementType = true;
                        break loop;
                    }
                    break;
                }
                case DOUBLE_ARRAY: {
                    if (
                            attributeElementWrapper.getReturnType().isArray()
                                    && attributeElementWrapper.getReturnType().getWrappedComponentType().isPrimitive()
                                    && attributeElementWrapper.getReturnType().getWrappedComponentType().getSimpleName().equals(double.class.getSimpleName())
                    ) {
                        foundMatchingElementType = true;
                        break loop;
                    }
                    break;
                }
                case INTEGER: {
                    if (
                            attributeElementWrapper.getReturnType().isPrimitive()
                                    && attributeElementWrapper.getReturnType().getSimpleName().equals(int.class.getSimpleName())
                    ) {
                        foundMatchingElementType = true;
                        break loop;
                    }
                    break;
                }
                case INTEGER_ARRAY: {
                    if (
                            attributeElementWrapper.getReturnType().isArray()
                                    && attributeElementWrapper.getReturnType().getWrappedComponentType().isPrimitive()
                                    && attributeElementWrapper.getReturnType().getWrappedComponentType().getSimpleName().equals(int.class.getSimpleName())
                    ) {
                        foundMatchingElementType = true;
                        break loop;
                    }
                    break;
                }
                case LONG: {
                    if (
                            attributeElementWrapper.getReturnType().isPrimitive()
                                    && attributeElementWrapper.getReturnType().getSimpleName().equals(long.class.getSimpleName())
                    ) {
                        foundMatchingElementType = true;
                        break loop;
                    }
                    break;
                }
                case LONG_ARRAY: {
                    if (
                            attributeElementWrapper.getReturnType().isArray()
                                    && attributeElementWrapper.getReturnType().getWrappedComponentType().isPrimitive()
                                    && attributeElementWrapper.getReturnType().getWrappedComponentType().getSimpleName().equals(long.class.getSimpleName())
                    ) {
                        foundMatchingElementType = true;
                        break loop;
                    }
                    break;
                }
                case STRING: {
                    if (
                            attributeElementWrapper.getReturnType().getQualifiedName().equals(String.class.getCanonicalName())
                    ) {
                        foundMatchingElementType = true;
                        break loop;
                    }
                    break;
                }
                case STRING_ARRAY: {
                    if (
                            attributeElementWrapper.getReturnType().isArray()
                                    && attributeElementWrapper.getReturnType().getWrappedComponentType().isDeclared()
                                    && attributeElementWrapper.getReturnType().getWrappedComponentType().getQualifiedName().equals(String.class.getCanonicalName())
                    ) {
                        foundMatchingElementType = true;
                        break loop;
                    }
                    break;
                }

            }


        }

        // trigger error message if matching type hasn't been found
        if (!foundMatchingElementType) {
            wrappedElement.compilerMessage(annotationMirrorToCheck).asError().write(BasicConstraintsCompilerMessages.ONATTRIBUTETYPE_ERROR_WRONG_USAGE, UtilityFunctions.getSimpleName(constraintAnnotationMirror), UtilityFunctions.getSimpleName(annotationMirrorToCheck), onAnnotationOfType.value());
            return false;
        }

        return true;
    }
}