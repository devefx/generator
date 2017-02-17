package org.devefx.generator.template.type;

public class CppType {

    private boolean reference;
    private String typeName;
    private String type;
    
    public CppType(String typeName) {
        this.typeName = typeName;
    }

    public CppType(boolean reference, String typeName) {
        this.reference = reference;
        this.typeName = typeName;
    }

    public CppType(String typeName, String type) {
        this.typeName = typeName;
        this.type = type;
    }
    
    public CppType(boolean reference, String typeName, String type) {
        this.reference = reference;
        this.typeName = typeName;
        this.type = type;
    }
    
    public boolean isReference() {
        return reference;
    }

    public String getTypeName() {
        return typeName;
    }
    
    public String getType() {
		return type;
	}
}
