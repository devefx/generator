#include "db_${table.fileName}.h"

<#if (imports?size > 0)>
<#list imports as tab>
#include "db_${tab.fileName}.h"
</#list>

</#if>
namespace db_<#if table.name?starts_with("t01")>data<#elseif table.name?starts_with("t02")>game</#if>
{

<#list table.columns as column>
${column.javaType} ${table.formatName}::${column.name}() const
{
    return ${column.name}_;
}
void ${table.formatName}::set_${column.name}(${column.javaType} value)
{
    ${column.name}_ = value;
}

</#list>
<#if (table.foreignTables?? && table.foreignTables?size > 0)>
<#list table.foreignTables as tab>
int ${table.formatName}::${firstToLower(tab.formatName)}_size() const
{
	return ${firstToLower(tab.formatName)}Array_.size();
}
void ${table.formatName}::clear_${firstToLower(tab.formatName)}()
{
	for(auto element : ${firstToLower(tab.formatName)}Array_)
		delete element;
	${firstToLower(tab.formatName)}Array_.clear();
}
const ${tab.formatName}& ${table.formatName}::get_${firstToLower(tab.formatName)}(int index) const
{
	return *(${firstToLower(tab.formatName)}Array_[index]);
}
${tab.formatName}* ${table.formatName}::add_${firstToLower(tab.formatName)}()
{
	${tab.formatName}* result = new ${tab.formatName}();
	${firstToLower(tab.formatName)}Array_.push_back(result);
	return result;
}

</#list>
</#if>
<#if (table.referenceTables?? && table.referenceTables?size > 0)>
<#list table.referenceTables as tab>
${tab.formatName}* ${table.formatName}::get_${firstToLower(tab.formatName)}() const
{
	return ${firstToLower(tab.formatName)}_;
}
void ${table.formatName}::set_${firstToLower(tab.formatName)}(${tab.formatName}* value)
{
	${firstToLower(tab.formatName)}_ = value;
}
</#list>
</#if>
}