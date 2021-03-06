// Generated by the devefx compiler.  DO NOT EDIT!
#ifndef GENERATOR_${table.formatName?upper_case}_ENTITY__INCLUDED
#define GENERATOR_${table.formatName?upper_case}_ENTITY__INCLUDED

#include "reflect/Object.h"

namespace db_<#if table.name?starts_with("t01")>data<#elseif table.name?starts_with("t02")>game</#if>
{
<#if (imports?size > 0)>

<#list imports as tab>
class ${tab.formatName};
</#list>
</#if>

class ${table.formatName} : public reflect::Object
{
REFLECT_DECLARE_CLASS(${table.formatName})

<#list table.columns as column>
	REFLECT_PROPERTY(${column.javaType}, ${column.name})
</#list>
public:
<#if (table.referenceTables?? && table.referenceTables?size > 0)>
	virtual~${table.formatName}();
</#if>
<#if (table.foreignTables?? && table.foreignTables?size > 0)>
<#list table.foreignTables as tab>
	int ${firstToLower(tab.formatName)}_size() const;
	void clear_${firstToLower(tab.formatName)}();
	const ${tab.formatName}& get_${firstToLower(tab.formatName)}(int index) const;
	${tab.formatName}* add_${firstToLower(tab.formatName)}();
	
</#list>
</#if>
<#if (table.referenceTables?? && table.referenceTables?size > 0)>
<#list table.referenceTables?keys as key>
	${table.referenceTables[key].formatName}* get_${key}() const;
	void set_${key}(${table.referenceTables[key].formatName}* value);
</#list>
</#if>
protected:
<#if (table.foreignTables?? && table.foreignTables?size > 0)>
<#list table.foreignTables as tab>
	std::vector<${tab.formatName}*> _${firstToLower(tab.formatName)}Array;
</#list>
</#if>
<#if (table.referenceTables?? && table.referenceTables?size > 0)>
<#list table.referenceTables?keys as key>
	${table.referenceTables[key].formatName}* _${key};
</#list>
</#if>
};

}

#endif