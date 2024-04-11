import javax.annotation.processing.Processor;

module io.toolisticon.aptk.example {

    exports io.toolisticon.aptk.example.regularmodule to cute;
    requires transitive cute;
    requires static jdk.compiler;
    uses Processor;
    opens io.toolisticon.aptk.example.regularmodule to cute;
    provides Processor with io.toolisticon.aptk.example.regularmodule.MyProcessor;

}