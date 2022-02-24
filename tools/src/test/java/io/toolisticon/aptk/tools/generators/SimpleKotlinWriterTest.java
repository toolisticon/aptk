package io.toolisticon.aptk.tools.generators;

import io.toolisticon.aptk.common.ToolingProvider;
import io.toolisticon.aptk.tools.FilerUtils;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class SimpleKotlinWriterTest {

    ProcessingEnvironment processingEnvironment;
    Messager messager;

    Map<String, String> validOptionsMap = new HashMap<>();

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Before
    public void setUp() throws IOException {

        validOptionsMap.put(SimpleKotlinWriter.KAPT_KOTLIN_GENERATED, temporaryFolder.getRoot().getAbsolutePath());

        processingEnvironment = Mockito.mock(ProcessingEnvironment.class);
        messager = Mockito.mock(Messager.class);
        Mockito.when(processingEnvironment.getMessager()).thenReturn(messager);
        ToolingProvider.setTooling(processingEnvironment);

    }

    @After
    public void clean() {
        ToolingProvider.clearTooling();
    }

    @Test
    public void testInitialisation_failing() {
        Element element = Mockito.mock(Element.class);

        new SimpleKotlinWriter("abc.Def", element);

        Mockito.verify(messager, Mockito.times(1)).printMessage(Mockito.eq(Diagnostic.Kind.ERROR), Mockito.anyString(), Mockito.any());
    }

    @Test
    public void append() throws IOException {

        Mockito.when(processingEnvironment.getOptions()).thenReturn(validOptionsMap);

        SimpleKotlinWriter unit = new SimpleKotlinWriter("abc.Def");
        final File targetFile = unit.getTargetFile();

        unit.append("T1");
        MatcherAssert.assertThat(readFile(targetFile), Matchers.is("T1"));

        unit.append("T2");
        MatcherAssert.assertThat(readFile(targetFile), Matchers.is("T1T2"));

        unit.append("T3");
        MatcherAssert.assertThat(readFile(targetFile), Matchers.is("T1T2T3"));

        unit = FilerUtils.createKotlinSourceFile("abc.Def");
        unit.append("T4");
        MatcherAssert.assertThat(readFile(targetFile), Matchers.is("T1T2T3T4"));

    }

    @Test
    public void write() throws IOException {

        Mockito.when(processingEnvironment.getOptions()).thenReturn(validOptionsMap);

        SimpleKotlinWriter unit = new SimpleKotlinWriter("abc.Def");
        final File targetFile = unit.getTargetFile();

        unit.write("T1");
        MatcherAssert.assertThat(readFile(targetFile), Matchers.is("T1"));

        unit.write("T2");
        MatcherAssert.assertThat(readFile(targetFile), Matchers.is("T2"));

        unit.write("T3");
        MatcherAssert.assertThat(readFile(targetFile), Matchers.is("T3"));

        unit = FilerUtils.createKotlinSourceFile("abc.Def");
        unit.write("T4");
        MatcherAssert.assertThat(readFile(targetFile), Matchers.is("T4"));


    }

    @Test
    public void testWrite() throws IOException {

        Mockito.when(processingEnvironment.getOptions()).thenReturn(validOptionsMap);

        SimpleKotlinWriter unit = new SimpleKotlinWriter("abc.Def");
        final File targetFile = unit.getTargetFile();

        unit.write("T1".toCharArray());
        MatcherAssert.assertThat(readFile(targetFile), Matchers.is("T1"));

        unit.write("T2".toCharArray());
        MatcherAssert.assertThat(readFile(targetFile), Matchers.is("T2"));

        unit.write("T3".toCharArray());
        MatcherAssert.assertThat(readFile(targetFile), Matchers.is("T3"));

        unit = FilerUtils.createKotlinSourceFile("abc.Def");
        unit.write("T4".toCharArray());
        MatcherAssert.assertThat(readFile(targetFile), Matchers.is("T4"));
    }


    private String readFile(File fileToRead) throws IOException {
        return new String(Files.readAllBytes(fileToRead.toPath()), StandardCharsets.UTF_8);
    }

    @Test
    public void getPackageFromFqn() {

        MatcherAssert.assertThat(SimpleKotlinWriter.getPackageFromFqn(""), Matchers.is(""));
        MatcherAssert.assertThat(SimpleKotlinWriter.getPackageFromFqn("AbcDef"), Matchers.is(""));
        MatcherAssert.assertThat(SimpleKotlinWriter.getPackageFromFqn("abc.AbcDef"), Matchers.is("abc"));
        MatcherAssert.assertThat(SimpleKotlinWriter.getPackageFromFqn("abc.def.hij.AbcDef"), Matchers.is("abc.def.hij"));

    }

    @Test
    public void getClassNameFromFqn() {
        MatcherAssert.assertThat(SimpleKotlinWriter.getClassNameFromFqn(""), Matchers.is(""));
        MatcherAssert.assertThat(SimpleKotlinWriter.getClassNameFromFqn("AbcDef"), Matchers.is("AbcDef"));
        MatcherAssert.assertThat(SimpleKotlinWriter.getClassNameFromFqn("abc.AbcDef"), Matchers.is("AbcDef"));
        MatcherAssert.assertThat(SimpleKotlinWriter.getClassNameFromFqn("abc.def.hij.AbcDef"), Matchers.is("AbcDef"));
    }

    @Test
    public void getTargetPackageDirectoryFile() {

        Mockito.when(processingEnvironment.getOptions()).thenReturn(validOptionsMap);

        SimpleKotlinWriter unit = new SimpleKotlinWriter("abc.Def");
        MatcherAssert.assertThat(unit.getTargetPackageDirectoryFile().getAbsolutePath(), Matchers.is(temporaryFolder.getRoot().getAbsolutePath() + File.separator + "abc"));

        unit = new SimpleKotlinWriter("Def");
        MatcherAssert.assertThat(unit.getTargetPackageDirectoryFile().getAbsolutePath(), Matchers.is(temporaryFolder.getRoot().getAbsolutePath()));

    }
}