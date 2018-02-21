package io.toolisticon.annotationprocessortoolkit.tools;

import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.mockito.Mockito;

import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

/**
 * Unit test for {@link TypeUtils.CheckTypeKind}.
 */
public class TypeUtils_CheckTypeKindTest {

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


        MatcherAssert.assertThat("Should detect void type kind", TypeUtils.CheckTypeKind.isArray(getTypeMirrorMockOfKind(TypeKind.ARRAY)));

    }

    @Test
    public void testCheckTypeKind_isVoid_noVoidKind() {

        MatcherAssert.assertThat("Should not detect void type kind", !TypeUtils.CheckTypeKind.isArray(getTypeMirrorMockOfKind(TypeKind.DECLARED)));

    }

    @Test
    public void testCheckTypeKind_isVoid_nullSafety() {

        MatcherAssert.assertThat("Should return false for null valued parameter", !TypeUtils.CheckTypeKind.isVoid(null));

    }

    // ---------------------------------------------
    // -- isArray tests
    // ---------------------------------------------


    @Test
    public void testCheckTypeKind_isArray() {


        MatcherAssert.assertThat("Should detect void type kind", TypeUtils.CheckTypeKind.isArray(getTypeMirrorMockOfKind(TypeKind.ARRAY)));

    }

    @Test
    public void testCheckTypeKind_isArray_noVoidKind() {


        MatcherAssert.assertThat("Should not detect void type kind", !TypeUtils.CheckTypeKind.isArray(getTypeMirrorMockOfKind(TypeKind.DECLARED)));

    }

    @Test
    public void testCheckTypeKind_isArray_nullSafety() {

        MatcherAssert.assertThat("Should return false for null valued parameter", !TypeUtils.CheckTypeKind.isArray(null));

    }

    // ---------------------------------------------
    // -- isDeclared tests
    // ---------------------------------------------


    @Test
    public void testCheckTypeKind_isDeclared() {


        MatcherAssert.assertThat("Should detect void type kind", TypeUtils.CheckTypeKind.isDeclared(getTypeMirrorMockOfKind(TypeKind.DECLARED)));

    }

    @Test
    public void testCheckTypeKind_isDeclared_noVoidKind() {


        MatcherAssert.assertThat("Should not detect void type kind", !TypeUtils.CheckTypeKind.isDeclared(getTypeMirrorMockOfKind(TypeKind.EXECUTABLE)));

    }

    @Test
    public void testCheckTypeKind_isDeclared_nullSafety() {

        MatcherAssert.assertThat("Should return false for null valued parameter", !TypeUtils.CheckTypeKind.isDeclared(null));

    }

    // ---------------------------------------------
    // -- isExecutable tests
    // ---------------------------------------------


    @Test
    public void testCheckTypeKind_isExecutable() {


        MatcherAssert.assertThat("Should detect void type kind", TypeUtils.CheckTypeKind.isExecutable(getTypeMirrorMockOfKind(TypeKind.EXECUTABLE)));

    }

    @Test
    public void testCheckTypeKind_isExecutable_noVoidKind() {


        MatcherAssert.assertThat("Should not detect void type kind", !TypeUtils.CheckTypeKind.isExecutable(getTypeMirrorMockOfKind(TypeKind.DECLARED)));

    }

    @Test
    public void testCheckTypeKind_isExecutable_nullSafety() {

        MatcherAssert.assertThat("Should return false for null valued parameter", !TypeUtils.CheckTypeKind.isExecutable(null));

    }

    // ---------------------------------------------
    // -- isPrimitive tests
    // ---------------------------------------------


    @Test
    public void testCheckTypeKind_isPrimitive() {


        MatcherAssert.assertThat("Should detect void primitive kind for long", TypeUtils.CheckTypeKind.isPrimitive(getTypeMirrorMockOfKind(TypeKind.LONG)));
        MatcherAssert.assertThat("Should detect void primitive kind for int", TypeUtils.CheckTypeKind.isPrimitive(getTypeMirrorMockOfKind(TypeKind.INT)));
        MatcherAssert.assertThat("Should detect void primitive kind for float", TypeUtils.CheckTypeKind.isPrimitive(getTypeMirrorMockOfKind(TypeKind.FLOAT)));
        MatcherAssert.assertThat("Should detect void primitive kind for double", TypeUtils.CheckTypeKind.isPrimitive(getTypeMirrorMockOfKind(TypeKind.DOUBLE)));
        MatcherAssert.assertThat("Should detect void primitive kind for short", TypeUtils.CheckTypeKind.isPrimitive(getTypeMirrorMockOfKind(TypeKind.SHORT)));
        MatcherAssert.assertThat("Should detect void primitive kind for boolean", TypeUtils.CheckTypeKind.isPrimitive(getTypeMirrorMockOfKind(TypeKind.BOOLEAN)));
        MatcherAssert.assertThat("Should detect void primitive kind for byte", TypeUtils.CheckTypeKind.isPrimitive(getTypeMirrorMockOfKind(TypeKind.BYTE)));
        MatcherAssert.assertThat("Should detect void primitive kind for char", TypeUtils.CheckTypeKind.isPrimitive(getTypeMirrorMockOfKind(TypeKind.CHAR)));

    }

    @Test
    public void testCheckTypeKind_isPrimitive_noVoidKind() {


        MatcherAssert.assertThat("Should not detect primitive type kind", !TypeUtils.CheckTypeKind.isPrimitive(getTypeMirrorMockOfKind(TypeKind.DECLARED)));

    }

    @Test
    public void testCheckTypeKind_isPrimitive_nullSafety() {

        MatcherAssert.assertThat("Should return false for null valued parameter", !TypeUtils.CheckTypeKind.isPrimitive(null));

    }

// ---------------------------------------------
    // -- isOfTypeKind tests
    // ---------------------------------------------


    @Test
    public void testCheckTypeKind_isOfTypeKind_matchingTypeKinds() {


        MatcherAssert.assertThat("Should detect matching kind for long", TypeUtils.CheckTypeKind.isOfTypeKind(getTypeMirrorMockOfKind(TypeKind.LONG), TypeKind.LONG));
        MatcherAssert.assertThat("Should detect matching kind for int", TypeUtils.CheckTypeKind.isOfTypeKind(getTypeMirrorMockOfKind(TypeKind.INT), TypeKind.INT));
        MatcherAssert.assertThat("Should detect matching kind for float", TypeUtils.CheckTypeKind.isOfTypeKind(getTypeMirrorMockOfKind(TypeKind.FLOAT), TypeKind.FLOAT));
        MatcherAssert.assertThat("Should detect matching kind for double", TypeUtils.CheckTypeKind.isOfTypeKind(getTypeMirrorMockOfKind(TypeKind.DOUBLE), TypeKind.DOUBLE));
        MatcherAssert.assertThat("Should detect matching kind for short", TypeUtils.CheckTypeKind.isOfTypeKind(getTypeMirrorMockOfKind(TypeKind.SHORT), TypeKind.SHORT));
        MatcherAssert.assertThat("Should detect matching kind for boolean", TypeUtils.CheckTypeKind.isOfTypeKind(getTypeMirrorMockOfKind(TypeKind.BOOLEAN), TypeKind.BOOLEAN));
        MatcherAssert.assertThat("Should detect matching kind for byte", TypeUtils.CheckTypeKind.isOfTypeKind(getTypeMirrorMockOfKind(TypeKind.BYTE), TypeKind.BYTE));
        MatcherAssert.assertThat("Should detect matching kind for char", TypeUtils.CheckTypeKind.isOfTypeKind(getTypeMirrorMockOfKind(TypeKind.CHAR), TypeKind.CHAR));
        MatcherAssert.assertThat("Should detect matching kind for char", TypeUtils.CheckTypeKind.isOfTypeKind(getTypeMirrorMockOfKind(TypeKind.DECLARED), TypeKind.DECLARED));
        MatcherAssert.assertThat("Should detect matching kind for char", TypeUtils.CheckTypeKind.isOfTypeKind(getTypeMirrorMockOfKind(TypeKind.ARRAY), TypeKind.ARRAY));
        MatcherAssert.assertThat("Should detect matching kind for char", TypeUtils.CheckTypeKind.isOfTypeKind(getTypeMirrorMockOfKind(TypeKind.VOID), TypeKind.VOID));
    }

    public void testCheckTypeKind_isOfTypeKind_nonMatchingTypeKinds() {


        MatcherAssert.assertThat("Should detect matching kind for long", !TypeUtils.CheckTypeKind.isOfTypeKind(getTypeMirrorMockOfKind(TypeKind.LONG), TypeKind.DECLARED));
        MatcherAssert.assertThat("Should detect matching kind for int", !TypeUtils.CheckTypeKind.isOfTypeKind(getTypeMirrorMockOfKind(TypeKind.INT), TypeKind.DECLARED));
        MatcherAssert.assertThat("Should detect matching kind for float", !TypeUtils.CheckTypeKind.isOfTypeKind(getTypeMirrorMockOfKind(TypeKind.FLOAT), TypeKind.DECLARED));
        MatcherAssert.assertThat("Should detect matching kind for double", !TypeUtils.CheckTypeKind.isOfTypeKind(getTypeMirrorMockOfKind(TypeKind.DOUBLE), TypeKind.DECLARED));
        MatcherAssert.assertThat("Should detect matching kind for short", !TypeUtils.CheckTypeKind.isOfTypeKind(getTypeMirrorMockOfKind(TypeKind.SHORT), TypeKind.DECLARED));
        MatcherAssert.assertThat("Should detect matching kind for boolean", !TypeUtils.CheckTypeKind.isOfTypeKind(getTypeMirrorMockOfKind(TypeKind.BOOLEAN), TypeKind.DECLARED));
        MatcherAssert.assertThat("Should detect matching kind for byte", !TypeUtils.CheckTypeKind.isOfTypeKind(getTypeMirrorMockOfKind(TypeKind.BYTE), TypeKind.DECLARED));
        MatcherAssert.assertThat("Should detect matching kind for char", !TypeUtils.CheckTypeKind.isOfTypeKind(getTypeMirrorMockOfKind(TypeKind.CHAR), TypeKind.DECLARED));
        MatcherAssert.assertThat("Should detect matching kind for char", !TypeUtils.CheckTypeKind.isOfTypeKind(getTypeMirrorMockOfKind(TypeKind.DECLARED), TypeKind.ARRAY));
        MatcherAssert.assertThat("Should detect matching kind for char", !TypeUtils.CheckTypeKind.isOfTypeKind(getTypeMirrorMockOfKind(TypeKind.ARRAY), TypeKind.DECLARED));
        MatcherAssert.assertThat("Should detect matching kind for char", !TypeUtils.CheckTypeKind.isOfTypeKind(getTypeMirrorMockOfKind(TypeKind.VOID), TypeKind.DECLARED));
    }

    @Test
    public void testCheckTypeKind_isOfTypeKind_nullSafetyFirstParameter() {

        MatcherAssert.assertThat("Should return false for null valued parameter", !TypeUtils.CheckTypeKind.isOfTypeKind(null, TypeKind.VOID));

    }

    @Test
    public void testCheckTypeKind_isOfTypeKind_nullSafetySecondParameter() {

        MatcherAssert.assertThat("Should return false for null valued parameter", !TypeUtils.CheckTypeKind.isOfTypeKind(getTypeMirrorMockOfKind(TypeKind.VOID), null));

    }


}