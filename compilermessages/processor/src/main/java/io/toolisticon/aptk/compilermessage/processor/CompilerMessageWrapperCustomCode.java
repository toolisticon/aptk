package io.toolisticon.aptk.compilermessage.processor;

import io.toolisticon.aptk.annotationwrapper.api.CustomCodeMethod;
import io.toolisticon.aptk.compilermessage.api.DeclareCompilerMessage;
import io.toolisticon.aptk.tools.wrapper.ElementWrapper;
import io.toolisticon.aptk.tools.wrapper.TypeElementWrapper;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import java.util.Optional;
import java.util.UUID;

public class CompilerMessageWrapperCustomCode {

    @CustomCodeMethod(DeclareCompilerMessage.class)
    public static CompilerMessageProcessor.TargetCompilerMessageEnum getTarget(DeclareCompilerMessageWrapper compilerMessageWrapper){

        TypeElementWrapper typeElementWrapper;

        if (!compilerMessageWrapper.processorClassIsDefaultValue()) {

            Optional<TypeElementWrapper> optionalTypeMirrorWrapper = compilerMessageWrapper.processorClassAsTypeMirrorWrapper().getTypeElement();
            if (!optionalTypeMirrorWrapper.isPresent()) {
                compilerMessageWrapper.processorClassAsAttributeWrapper().compilerMessage().asError();
                return null;
            }
            typeElementWrapper = optionalTypeMirrorWrapper.get();

        } else {

            ElementWrapper<Element> elementWrapper = ElementWrapper.wrap(compilerMessageWrapper._annotatedElement());

            if (elementWrapper.isTypeElement()) {

                // is placed on class - must check for top level class later...
                TypeElementWrapper tmpTypeElementWrapper = TypeElementWrapper.toTypeElement(elementWrapper);
                typeElementWrapper = tmpTypeElementWrapper.isNested() ? tmpTypeElementWrapper.getOuterTopLevelType().get() : tmpTypeElementWrapper;

            } else {

                // is placed on method
                TypeElementWrapper tmpTypeElementWrapper = TypeElementWrapper.toTypeElement(elementWrapper.getFirstEnclosingElementWithKind(ElementKind.CLASS).get());
                typeElementWrapper = tmpTypeElementWrapper.isNested() ? tmpTypeElementWrapper.getOuterTopLevelType().get() : tmpTypeElementWrapper;

            }

        }





        return new CompilerMessageProcessor.TargetCompilerMessageEnum(
                typeElementWrapper.getPackageName(),
                typeElementWrapper.getSimpleName() + "CompilerMessages",
                DeclareCompilerMessageCodePrefixWrapper.isAnnotated(typeElementWrapper.unwrap()) ? DeclareCompilerMessageCodePrefixWrapper.wrap(typeElementWrapper.unwrap()).value(): typeElementWrapper.getSimpleName());

    }

    @CustomCodeMethod(DeclareCompilerMessage.class)
    public static String getCalculatedCode(DeclareCompilerMessageWrapper compilerMessageWrapper){
        return compilerMessageWrapper.codeIsDefaultValue() ? compilerMessageWrapper.enumValueName() : compilerMessageWrapper.code();
    }

}
