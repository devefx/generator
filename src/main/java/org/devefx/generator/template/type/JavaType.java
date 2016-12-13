package org.devefx.generator.template.type;

public class JavaType {

	private String packageName;
	private String typeName;
	
	public JavaType(String typeName) {
		this.typeName = typeName;
	}
	
	public JavaType(String packageName, String typeName) {
		this.packageName = packageName;
		this.typeName = typeName;
	}
	
	public String getPackageName() {
		return packageName;
	}
	
	public String getTypeName() {
		return typeName;
	}
	
	public boolean hasPackageName() {
		return packageName != null;
	}
	
}
