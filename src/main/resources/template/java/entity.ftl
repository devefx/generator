package ${java_package};
<#if (imports?size > 0)>

<#list imports as import>
import ${import};
</#list>
</#if>

class ${table.formatName} {

	<#list table.columns as column>
	private ${column.javaType} ${column.formatName};${placeholder(column.javaType+column.formatName, 25)} // ${column.comment}
	</#list>
    
    <#list table.columns as column>
    /**
	 * 设置${column.comment}
	 * @param ${column.formatName}
	 */
	public void set${firstToUpper(column.formatName)}(${column.javaType} ${column.formatName}) {
        this.${column.formatName} = ${column.formatName};
	}
	
    /**
	 * 获取${column.comment}
	 * @return
	 */
	public ${column.javaType} get${firstToUpper(column.formatName)}() {
		return ${column.formatName};
	}
	
	</#list>
}