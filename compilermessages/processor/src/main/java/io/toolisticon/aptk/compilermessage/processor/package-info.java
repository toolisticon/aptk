@AnnotationWrapper(
        value = {DeclareCompilerMessage.class, DeclareCompilerMessageCodePrefix.class},
        bindCustomCode = {CompilerMessageWrapperCustomCode.class},
        automaticallyWrapEmbeddedAnnotations = true,
        usePublicVisibility = true)
package io.toolisticon.aptk.compilermessage.processor;

import io.toolisticon.aptk.annotationwrapper.api.AnnotationWrapper;
import io.toolisticon.aptk.compilermessage.api.DeclareCompilerMessage;
import io.toolisticon.aptk.compilermessage.api.DeclareCompilerMessageCodePrefix;
