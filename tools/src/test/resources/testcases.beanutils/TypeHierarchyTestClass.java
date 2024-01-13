package io.toolisticon.aptk.test;

import io.toolisticon.aptk.tools.TestAnnotation;

@TestAnnotation
public class TypeHierarchyTestClass {



    public static class SuperType {

        private boolean superBooleanField;
        private String superStringField;

        public boolean isSuperBooleanField() {
            return superBooleanField;
        }

        public void setSuperBooleanField(boolean superBooleanField) {
            this.superBooleanField = superBooleanField;
        }

        public String getSuperStringField() {
            return superStringField;
        }

        public void setSuperStringField(String superStringField) {
            this.superStringField = superStringField;
        }
    }


    public static class InheritingType extends SuperType{

        private boolean booleanField;

        public boolean isBooleanField() {
            return booleanField;
        }

        public void setBooleanField(boolean booleanField) {
            this.booleanField = booleanField;
        }

        private String stringField;

        public String getStringField() {
            return stringField;
        }

        public void setStringField(String stringField) {
            this.stringField = stringField;
        }


        private String nonAttributeField;

        public String getNonAttributeField() {
            return nonAttributeField;
        }
    }

}