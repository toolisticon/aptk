---
layout: imprintBottom
used_in_navigation: true
menu_name: Messager utilities
order: 3
---
# Messager utilities

The examples on this page describe the usage of the MessagerUtils from inside your annotation processor that extends the *de.holisticon.annotationprocessortoolkit.AbstractAnnotationProcessor* class.

Nevertheless you are able to get a MessagerUtils instance via a static getter:

```java
MessagerUtils messager = MessagerUtils.getMessagerUtils(processingEnv);
```

# Writing messages

You can trigger compile messages using the *MessagerUtils* class

```java
// f.e. to create an error - see javadoc for further details
getMessager().error(element, "Message with placeholders ${1} before ${2}", "p1", "p2");
```

The api supports a placeholder mechanism. Placeholder can be used in the message string and are refering the varargs with a zero based index (f.e. ${0} references first vararg).

