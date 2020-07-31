/*
 * Copyright(C) 2011 Matrix
 * All right reserved.
 */
package net.matrix.lang;

public class ReflectionsTestData {
    public static class ParentBean<T, ID> {
        private T privateParentField;

        public ID publicParentField;

        public ParentBean(T privateParentField, ID publicParentField) {
            this.privateParentField = privateParentField;
            this.publicParentField = publicParentField;
        }

        public T inspectPrivateParentField() {
            return privateParentField;
        }

        public ID inspectPublicParentField() {
            return publicParentField;
        }
    }

    public static class TestBean
        extends ParentBean<String, Long> {
        private int privateField = 1;

        private int publicField = 1;

        public TestBean() {
            super("1", 1L);
        }

        public int getPublicField() {
            return publicField + 1;
        }

        public void setPublicField(int publicField) {
            this.publicField = publicField + 1;
        }

        public int inspectPrivateField() {
            return privateField;
        }

        public int inspectPublicField() {
            return publicField;
        }

        private String privateMethod(String text) {
            return "hello " + text;
        }

        public String publicMethod(String text) {
            return privateMethod(text);
        }
    }

    public static class TestBean2
        extends ParentBean {
        public TestBean2() {
            super(null, null);
        }
    }

    public static class TestBean3 {
        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public static class TestBean4
        extends TestBean {
    }
}
