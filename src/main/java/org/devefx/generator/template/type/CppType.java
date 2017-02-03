package org.devefx.generator.template.type;

public class CppType {

    private boolean reference;
    private String typeName;

    public CppType(String typeName) {
        this.typeName = typeName;
    }

    public CppType(boolean reference, String typeName) {
        this.reference = reference;
        this.typeName = typeName;
    }

    public boolean isReference() {
        return reference;
    }

    public String getTypeName() {
        return typeName;
    }
}
