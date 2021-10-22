# Templating
The *APTK* comes with it's own templating engine.
A Map<String,Object> can be passed in as model.
Map keys can be used to access the corresponding value from model 

## Expression Language
Most common arithmetic, logical and relational operators are supported:

### Operators
```
// Arithemtic
operand1 + operand2 : ADDITION
operand1 - operand2 : SUBTRACTION
operand1 * operand2 : MULTIPLICATION
operand1 / operand2 : DIVISION

// Logical
!operand : NEGATION
operand1 && operand2 : LOGICAL AND
operand1 || operand2 : LOGICAL OR

// Relational
operand1 == operand2 : EQUAL
operand1 != operand2 : NOT EQUAL
operand1 < operand2 : LESS THAN
operand1 <= operand2 : LESS OR EQUAL THAN
operand1 > operand2 : GREATER THAN
operand1 >= operand2 : GREATER OR EQUAL THAN

```

### Value Expressions
Its possible to access a value by using it's name.
Additional it's possible to read a value by defining a '.' delimited access path
```
varibale.field1.subTypesField1
```


## Template engine commands
### Dynamic text replacement
It's possible to add some dynamic text by using the ${} syntax.
```
${name}
${value.expression}
``` 
The template engine tries to resolve access path token by it's name or it's getter method.

### static text blocks
Static text blocks can be used to avoid escaping issues.
```
// disabled template engine processing between start and end tag
!{static} To disable dynamic replacement for commands like ${whatever}. !{/static}
``` 

### if statements
Simple if statements without else case are currently supported.
```
// currently just supports if statements without else case
!{if expression} 
    your text/code 
!{/if}
```

### for statement
The template engine provides a for loop functionality:
```
// for loop
!{for name : var.access.path}
  Your text/code 
!{/for}
```
It's possible through iterate arrays and collections


### include files
It's possible to split templates in multiple files.

```
// define model as attribute
!{include resource:'/IncludeTemplateBlockTest.tpl', model:'model.bridge'}
!{/include}

// Build new model
!{include resource:'/IncludeTemplateBlockTest.tpl'}
    model:model.bridge
!{/include}
```

## Using The Template Engine
## Template based java source and resource file creation

Template based java source Resource file creation and source file creation is very simple:

### Sample template file
The framework provides a rudimentary templating mechanism which can be used to create resource and java source files.
It supports dynamic text replacement and for and if control blocks.

    !{if textArray != null}
        !{for text:textArray}
            Dynamic text: ${text}<br />
        !{/for}
    !{/if}
    

### Sample code : Resource file creation

```java
    String[] textArray = {"A","B","C"};

    // create Model
    Map<String, Object> model = new HashMap<String, Object>();
    model.put("textArray", textArray);

    final String package = "io.toolisticon.example";
    final String fileName = "generatedExample.txt";

    try {
        // template is loaded resource
        SimpleResourceWriter resourceWriter = FilerUtils.createResource(StandardLocation.CLASS_OUTPUT, package, fileName);
        resourceWriter.writeTemplate("example.tpl", model);
        resourceWriter.close();
    } catch (IOException e) {
        MessagerUtils.error(null, "Example file creation failed for package '${0}' and filename '${1}'", package, fileName);
    }
``` 