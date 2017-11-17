---
layout: imprintBottom
used_in_navigation: true
menu_name: Code and resource generation with templating
order: 4
---

# Code and resource generation with templating

The annotation processor toolkit provides support for the template based generation of java code and resouce files.
Generated source code will be compiled and all generated / compiled files will be added to your artifact.

The templating engine has the following features:

- Dynamic text replacement
- Static text blocks
- if control blocks
- loop blocks

# Writing templates

Just create a resource file in /src/main/resources.

## Expressions

Control and text replacement blocks are internally using expressions.


### Operands

It's possible to use static and dynamics operands.

The following static operand types are supported.

- null values represented by *null*
- boolean values represented by *true* or *false*
- floating point numbers: for example 5.0
- integer numbers: for example 5
- Strings wrapped in single quotes: for example 'Example'


Dynamic operands are read from the model.
It's possible to define a getter and a field name chain to access a value.
Fieldnames and getter method names must be separated by a '.' to define an access path to the dynamic value.
The following getter method prefixes are supported: *get*, *is*, *has*

    // example
    modelAttributeName.fieldName.getFieldName


### Operations

The expression language used is very flexible and supports most basic operations.
Expressions are evaluated according to the following operation precedence from left to right:

- ! : unary operands and sub expression(encapsulated in braces)
- *, / : multiplication and division
- +, - : addition and subtraction
- &gt;, &gt;=, &lt;, &lt;= : decimal comparison operands
- ==, != : Equality check operands
- &amp;&amp; : Boolean and operation
- || : Boolean or operation


### Examples for expressions

    // equal types : results to 25 and 25.0
    5 + 5
    5.0 + 5.0

    // this will result in a floating point number: 10.0
    5 + 5.0

    // String based addition : result = '55'
    5 + '5'
    '5' + 5

    // braces : results = true
    (5 + ( 2 * 5 )) * 20 == 300 && 3 * 2 == 6

    // using dynamic values
    value.intField + 3

    // null checks
    value.intField == null


## Dynamic text

The template engine provides support for dynamic text replacement.

    // get value from model
    ${value}

    // get value from model using a getter chain - automatically searches getter methods
    ${value.subvalue1.subsubvalue}

    // get value from model using a getter chain - use getters explicitey
    ${value.getSubValue1.getSubSubValue}

    // use expression
    ${'This is working as well :' + (value * 10 + 200) + !(otherBooleanValue)}

## Control blocks

### Static text
By default most of the template text defined outside of text replacement (${...}) and control (!{...} !{/...}) blocks is implicitly handled as static text.
Additionally we offer you a possibility to explicitly define a static text block:

    !{static}
    SOME STATIC TEXT
    !{if asds} <== this will be ignored
    ...
    !{/static}

### if control block

if blocks can be used to dynamically enable or disable the encapsulate template block.

    !{if <expression>}

        // encapsulted block

    !{/if}


Example:

    !{ if model.getShowHelloWorldBlock == true }

        HELLO WORLD

    !{/if}

### for loop control block

for blocks to processs and print the encapsulate template block for some elements.
The expression defined in the for start tag must resolve to a list or array type

    !{for name: <expression>}
        // encapsulted block
    !{/if}


Example:

    !{for name: model.loopValues}
        value : ${name.value}
    !{/for}

# Generate files by using templates

    // create and fill model
    Map<String, Object> model = new HashMap<String, Object>();
    model.put("value1", "test");
    model.put("value2", true);

    String filePath = "de.holisticon.somePackage.TargetClass";

    try {
        SimpleJavaWriter javaWriter = getFileObjectUtils().createSourceFile(filePath, element);
        javaWriter.writeTemplate( "/yourTemplateFile.tpl", model);
        javaWriter.close();
    } catch (IOException e) {
        getMessager().error(element, "An error occurred during code generation", filePath);
    }

    Please take a lookat our example projects in github.