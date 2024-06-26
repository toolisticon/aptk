package io.toolisticon.aptk.compilermessage.processor;

import io.toolisticon.aptk.compilermessage.api.DeclareCompilerMessage;
import io.toolisticon.aptk.compilermessage.api.DeclareCompilerMessages;
import io.toolisticon.aptk.tools.AbstractAnnotationProcessor;
import io.toolisticon.aptk.tools.FilerUtils;
import io.toolisticon.aptk.tools.MessagerUtils;
import io.toolisticon.aptk.tools.corematcher.AptkCoreMatchers;
import io.toolisticon.aptk.tools.generators.SimpleJavaWriter;
import io.toolisticon.aptk.tools.wrapper.ElementWrapper;
import io.toolisticon.aptk.tools.wrapper.PackageElementWrapper;
import io.toolisticon.spiap.api.SpiService;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SpiService(Processor.class)
public class CompilerMessageProcessor extends AbstractAnnotationProcessor {

    /**
     * The supported annotation types.
     */
    private final static Set<String> SUPPORTED_ANNOTATIONS = createSupportedAnnotationSet(DeclareCompilerMessage.class);

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return SUPPORTED_ANNOTATIONS;
    }

    private Set<String> detectedPackages = new HashSet<>();

    //private Map<TargetCompilerMessageEnum, List<DeclareCompilerMessageWrapper>> compilerMessagesEnumMap = new HashMap<>();


    public static class TargetCompilerMessageEnum {

        final String packageName;
        final String enumName;
        final String codePrefix;

        public TargetCompilerMessageEnum(String packageName, String enumName, String codePrefix) {
            this.packageName = packageName;
            this.enumName = enumName;
            this.codePrefix = codePrefix;
        }

        public String getPackageName() {
            return packageName;
        }

        public String getEnumName() {
            return enumName;
        }

        public String getCodePrefix() {
            return codePrefix;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TargetCompilerMessageEnum that = (TargetCompilerMessageEnum) o;

            if (!packageName.equals(that.packageName)) return false;
            if (!enumName.equals(that.enumName)) return false;
            return true;
        }

        @Override
        public int hashCode() {
            int result = packageName.hashCode();
            result = 31 * result + enumName.hashCode();
            return result;
        }
    }


    @Override
    public boolean processAnnotations(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {


        if (!roundEnv.processingOver()) {

            // process Services annotation
            for (Element element : getAnnotatedElements(roundEnv, DeclareCompilerMessage.class)) {

                // Just detect the package names - by doing this the processor will have a better compatibility with incremental compilations
                detectedPackages.add(ElementWrapper.wrap(element).getPackageName());

            }

        } else {


            final Map<TargetCompilerMessageEnum, List<DeclareCompilerMessageWrapper>> compilerMessagesEnumMap = new HashMap<>();

            for (String detectedPackage : detectedPackages) {
                List<Element> detectedAnnotatedElements = PackageElementWrapper.getByFqn(detectedPackage).get()
                        .filterFlattenedEnclosedElementTree()
                        // handle Repeatable annotation as well
                        .applyFilter(AptkCoreMatchers.BY_ANNOTATION).filterByOneOf(DeclareCompilerMessage.class, DeclareCompilerMessages.class)
                        .getResult();


                for (Element annotatedElement : detectedAnnotatedElements) {
                    List<DeclareCompilerMessageWrapper> compilerMessageWrappers = DeclareCompilerMessageWrapper.wrap(annotatedElement);
                    for (DeclareCompilerMessageWrapper compilerMessageWrapper : compilerMessageWrappers) {
                        TargetCompilerMessageEnum target = compilerMessageWrapper.getTarget();
                        compilerMessagesEnumMap.computeIfAbsent(target, e -> new ArrayList<DeclareCompilerMessageWrapper>()).add(compilerMessageWrapper);
                    }
                }
            }


            // create those compiler message enums
            for (Map.Entry<TargetCompilerMessageEnum, List<DeclareCompilerMessageWrapper>> entry : compilerMessagesEnumMap.entrySet()) {

                verify(entry.getValue());

                createCompilerMessageEnum(entry);
            }

        }
        return false;

    }


    boolean verify(List<DeclareCompilerMessageWrapper> enumValues) {

        boolean retValue = true;


        Set<String> codeSet = new HashSet<>();
        for (DeclareCompilerMessageWrapper enumValue : enumValues) {

            // check if enum value name is correct
            if (!enumValue.enumValueName().matches("([A-Z_][A-Z0-9_]*)")) {
                enumValue.enumValueNameAsAttributeWrapper().compilerMessage().asError().write(CompilerMessageProcessorMessages.ERROR_CODE_ENUM_VALUE_NAME_MUST_VALID, enumValue.enumValueName());
            }


            // Check if code is unique
            String codeToCheck = CompilerMessageWrapperCustomCode.getCalculatedCode(enumValue);
            if (codeSet.contains(codeToCheck)) {
                enumValue.codeAsAttributeWrapper().compilerMessage().asError().write(CompilerMessageProcessorMessages.ERROR_CODE_MUST_BE_UNIQUE, codeToCheck);
                retValue = false;
            } else {
                codeSet.add(codeToCheck);
            }

        }


        return retValue;
    }


    private void createCompilerMessageEnum(Map.Entry<TargetCompilerMessageEnum, List<DeclareCompilerMessageWrapper>> entry) {

        // Now create class

        // Fill Model
        Map<String, Object> model = new HashMap<>();
        model.put("enum", entry.getKey());
        model.put("compilerMessages", entry.getValue());
        model.put("messageScopes", Arrays.asList("error", "mandatoryWarning", "warning", "info"));

        // create the class
        String filePath = entry.getKey().packageName + "." + entry.getKey().enumName;
        try {
            SimpleJavaWriter javaWriter = FilerUtils.createSourceFile(filePath, null);
            javaWriter.writeTemplate("/CompilerMessageEnum.tpl", model);
            javaWriter.close();
        } catch (IOException e) {
            MessagerUtils.error(null, CompilerMessageProcessorMessages.ERROR_CANT_CREATE_MESSAGE_ENUM, filePath);
        }
    }


}