package de.holisticon.annotationprocessortoolkit.tools;

import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.mockito.Mockito;

import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

/**
 * Unit test for {@link TypeUtils.CheckTypeKind}.
 */
public class TypeUtilsTest_CheckTypeKind {

    private TypeMirror getTypeMirrorMockOfKind(TypeKind typeKind) {
        TypeMirror typeMirror = Mockito.mock(TypeMirror.class);
        Mockito.when(typeMirror.getKind()).thenReturn(typeKind);
        return typeMirror;

    }


    // ---------------------------------------------
    // -- isVoid tests
    // ---------------------------------------------


    @Test
    public void testCheckTypeKind_isVoid() {


        MatcherAssert.assertThat("Should detect void type kind", TypeUtils.CheckTypeKind.INSTANCE.isArray(getTypeMirrorMockOfKind(TypeKind.ARRAY)));

    }

    @Test
    public void testCheckTypeKind_isVoid_noVoidKind() {

        MatcherAssert.assertThat("Should not detect void type kind", !TypeUtils.CheckTypeKind.INSTANCE.isArray(getTypeMirrorMockOfKind(TypeKind.DECLARED)));

    }

    @Test
    public void testCheckTypeKind_isVoid_nullSafety() {

        MatcherAssert.assertThat("Should return false for null valued parameter", !TypeUtils.CheckTypeKind.INSTANCE.isVoid(null));

    }

    // ---------------------------------------------
    // -- isArray tests
    // ---------------------------------------------


    @Test
    public void testCheckTypeKind_isArray() {


        MatcherAssert.assertThat("Should detect void type kind", TypeUtils.CheckTypeKind.INSTANCE.isArray(getTypeMirrorMockOfKind(TypeKind.ARRAY)));

    }

    @Test
    public void testCheckTypeKind_isArray_noVoidKind() {


        MatcherAssert.assertThat("Should not detect void type kind", !TypeUtils.CheckTypeKind.INSTANCE.isArray(getTypeMirrorMockOfKind(TypeKind.DECLARED)));

    }

    @Test
    public void testCheckTypeKind_isArray_nullSafety() {

        MatcherAssert.assertThat("Should return false for null valued parameter", !TypeUtils.CheckTypeKind.INSTANCE.isArray(null));

    }

    // ---------------------------------------------
    // -- isPrimitive tests
    // ---------------------------------------------


    @Test
    public void testCheckTypeKind_isPrimitive() {


        MatcherAssert.assertThat("Should detect void primitive kind for long", TypeUtils.CheckTypeKind.INSTANCE.isPrimitive(getTypeMirrorMockOfKind(TypeKind.LONG)));
        MatcherAssert.assertThat("Should detect void primitive kind for int", TypeUtils.CheckTypeKind.INSTANCE.isPrimitive(getTypeMirrorMockOfKind(TypeKind.INT)));
        MatcherAssert.assertThat("Should detect void primitive kind for float", TypeUtils.CheckTypeKind.INSTANCE.isPrimitive(getTypeMirrorMockOfKind(TypeKind.FLOAT)));
        MatcherAssert.assertThat("Should detect void primitive kind for double", TypeUtils.CheckTypeKind.INSTANCE.isPrimitive(getTypeMirrorMockOfKind(TypeKind.DOUBLE)));
        MatcherAssert.assertThat("Should detect void primitive kind for short", TypeUtils.CheckTypeKind.INSTANCE.isPrimitive(getTypeMirrorMockOfKind(TypeKind.SHORT)));
        MatcherAssert.assertThat("Should detect void primitive kind for boolean", TypeUtils.CheckTypeKind.INSTANCE.isPrimitive(getTypeMirrorMockOfKind(TypeKind.BOOLEAN)));
        MatcherAssert.assertThat("Should detect void primitive kind for byte", TypeUtils.CheckTypeKind.INSTANCE.isPrimitive(getTypeMirrorMockOfKind(TypeKind.BYTE)));
        MatcherAssert.assertThat("Should detect void primitive kind for char", TypeUtils.CheckTypeKind.INSTANCE.isPrimitive(getTypeMirrorMockOfKind(TypeKind.CHAR)));

    }

    @Test
    public void testCheckTypeKind_isPrimitive_noVoidKind() {


        MatcherAssert.assertThat("Should not detect primitive type kind", !TypeUtils.CheckTypeKind.INSTANCE.isPrimitive(getTypeMirrorMockOfKind(TypeKind.DECLARED)));

    }

    @Test
    public void testCheckTypeKind_isPrimitive_nullSafety() {

        MatcherAssert.assertThat("Should return false for null valued parameter", !TypeUtils.CheckTypeKind.INSTANCE.isPrimitive(null));

    }

// ---------------------------------------------
    // -- isOfTypeKind tests
    // ---------------------------------------------


    @Test
    public void testCheckTypeKind_isOfTypeKind_matchingTypeKinds() {


        MatcherAssert.assertThat("Should detect matching kind for long", TypeUtils.CheckTypeKind.INSTANCE.isOfTypeKind(getTypeMirrorMockOfKind(TypeKind.LONG), TypeKind.LONG));
        MatcherAssert.assertThat("Should detect matching kind for int", TypeUtils.CheckTypeKind.INSTANCE.isOfTypeKind(getTypeMirrorMockOfKind(TypeKind.INT), TypeKind.INT));
        MatcherAssert.assertThat("Should detect matching kind for float", TypeUtils.CheckTypeKind.INSTANCE.isOfTypeKind(getTypeMirrorMockOfKind(TypeKind.FLOAT), TypeKind.FLOAT));
        MatcherAssert.assertThat("Should detect matching kind for double", TypeUtils.CheckTypeKind.INSTANCE.isOfTypeKind(getTypeMirrorMockOfKind(TypeKind.DOUBLE), TypeKind.DOUBLE));
        MatcherAssert.assertThat("Should detect matching kind for short", TypeUtils.CheckTypeKind.INSTANCE.isOfTypeKind(getTypeMirrorMockOfKind(TypeKind.SHORT), TypeKind.SHORT));
        MatcherAssert.assertThat("Should detect matching kind for boolean", TypeUtils.CheckTypeKind.INSTANCE.isOfTypeKind(getTypeMirrorMockOfKind(TypeKind.BOOLEAN), TypeKind.BOOLEAN));
        MatcherAssert.assertThat("Should detect matching kind for byte", TypeUtils.CheckTypeKind.INSTANCE.isOfTypeKind(getTypeMirrorMockOfKind(TypeKind.BYTE), TypeKind.BYTE));
        MatcherAssert.assertThat("Should detect matching kind for char", TypeUtils.CheckTypeKind.INSTANCE.isOfTypeKind(getTypeMirrorMockOfKind(TypeKind.CHAR), TypeKind.CHAR));
        MatcherAssert.assertThat("Should detect matching kind for char", TypeUtils.CheckTypeKind.INSTANCE.isOfTypeKind(getTypeMirrorMockOfKind(TypeKind.DECLARED), TypeKind.DECLARED));
        MatcherAssert.assertThat("Should detect matching kind for char", TypeUtils.CheckTypeKind.INSTANCE.isOfTypeKind(getTypeMirrorMockOfKind(TypeKind.ARRAY), TypeKind.ARRAY));
        MatcherAssert.assertThat("Should detect matching kind for char", TypeUtils.CheckTypeKind.INSTANCE.isOfTypeKind(getTypeMirrorMockOfKind(TypeKind.VOID), TypeKind.VOID));
    }

    public void testCheckTypeKind_isOfTypeKind_nonMatchingTypeKinds() {


        MatcherAssert.assertThat("Should detect matching kind for long", !TypeUtils.CheckTypeKind.INSTANCE.isOfTypeKind(getTypeMirrorMockOfKind(TypeKind.LONG), TypeKind.DECLARED));
        MatcherAssert.assertThat("Should detect matching kind for int", !TypeUtils.CheckTypeKind.INSTANCE.isOfTypeKind(getTypeMirrorMockOfKind(TypeKind.INT), TypeKind.DECLARED));
        MatcherAssert.assertThat("Should detect matching kind for float", !TypeUtils.CheckTypeKind.INSTANCE.isOfTypeKind(getTypeMirrorMockOfKind(TypeKind.FLOAT), TypeKind.DECLARED));
        MatcherAssert.assertThat("Should detect matching kind for double", !TypeUtils.CheckTypeKind.INSTANCE.isOfTypeKind(getTypeMirrorMockOfKind(TypeKind.DOUBLE), TypeKind.DECLARED));
        MatcherAssert.assertThat("Should detect matching kind for short", !TypeUtils.CheckTypeKind.INSTANCE.isOfTypeKind(getTypeMirrorMockOfKind(TypeKind.SHORT), TypeKind.DECLARED));
        MatcherAssert.assertThat("Should detect matching kind for boolean", !TypeUtils.CheckTypeKind.INSTANCE.isOfTypeKind(getTypeMirrorMockOfKind(TypeKind.BOOLEAN), TypeKind.DECLARED));
        MatcherAssert.assertThat("Should detect matching kind for byte", !TypeUtils.CheckTypeKind.INSTANCE.isOfTypeKind(getTypeMirrorMockOfKind(TypeKind.BYTE), TypeKind.DECLARED));
        MatcherAssert.assertThat("Should detect matching kind for char", !TypeUtils.CheckTypeKind.INSTANCE.isOfTypeKind(getTypeMirrorMockOfKind(TypeKind.CHAR), TypeKind.DECLARED));
        MatcherAssert.assertThat("Should detect matching kind for char", !TypeUtils.CheckTypeKind.INSTANCE.isOfTypeKind(getTypeMirrorMockOfKind(TypeKind.DECLARED), TypeKind.ARRAY));
        MatcherAssert.assertThat("Should detect matching kind for char", !TypeUtils.CheckTypeKind.INSTANCE.isOfTypeKind(getTypeMirrorMockOfKind(TypeKind.ARRAY), TypeKind.DECLARED));
        MatcherAssert.assertThat("Should detect matching kind for char", !TypeUtils.CheckTypeKind.INSTANCE.isOfTypeKind(getTypeMirrorMockOfKind(TypeKind.VOID), TypeKind.DECLARED));
    }

    @Test
    public void testCheckTypeKind_isOfTypeKind_nullSafetyFirstParameter() {

        MatcherAssert.assertThat("Should return false for null valued parameter", !TypeUtils.CheckTypeKind.INSTANCE.isOfTypeKind(null, TypeKind.VOID));

    }

    @Test
    public void testCheckTypeKind_isOfTypeKind_nullSafetySecondParameter() {

        MatcherAssert.assertThat("Should return false for null valued parameter", !TypeUtils.CheckTypeKind.INSTANCE.isOfTypeKind(getTypeMirrorMockOfKind(TypeKind.VOID), null));

    }


}