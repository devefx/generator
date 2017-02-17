#include "db_${table.fileName}.h"

<#if (imports?size > 0)>
<#list imports as tab>
#include "db_${tab.fileName}.h"
</#list>

</#if>
namespace db_<#if table.name?starts_with("t01")>data<#elseif table.name?starts_with("t02")>game</#if>
{
REFLECT_IMPLEMENT_CLASS_EXTENDS(db_<#if table.name?starts_with("t01")>data<#elseif table.name?starts_with("t02")>game</#if>::${table.formatName}, reflect::Object)

<#if (table.referenceTables?? && table.referenceTables?size > 0)>
${table.formatName}::~${table.formatName}()
{
<#list table.referenceTables as tab>
	SAFE_DELETE(_${firstToLower(tab.formatName)});
</#list>
}
</#if>
<#if (table.foreignTables?? && table.foreignTables?size > 0)>
<#list table.foreignTables as tab>
int ${table.formatName}::${firstToLower(tab.formatName)}_size() const
{
	return _${firstToLower(tab.formatName)}Array.size();
}
void ${table.formatName}::clear_${firstToLower(tab.formatName)}()
{
	for(auto element : _${firstToLower(tab.formatName)}Array)
		delete element;
	_${firstToLower(tab.formatName)}Array.clear();
}
const ${tab.formatName}& ${table.formatName}::get_${firstToLower(tab.formatName)}(int index) const
{
	return *(_${firstToLower(tab.formatName)}Array[index]);
}
${tab.formatName}* ${table.formatName}::add_${firstToLower(tab.formatName)}()
{
	${tab.formatName}* result = new ${tab.formatName}();
	_${firstToLower(tab.formatName)}Array.push_back(result);
	return result;
}

</#list>
</#if>
<#if (table.referenceTables?? && table.referenceTables?size > 0)>
<#list table.referenceTables as tab>
${tab.formatName}* ${table.formatName}::get_${firstToLower(tab.formatName)}() const
{
	return _${firstToLower(tab.formatName)};
}
void ${table.formatName}::set_${firstToLower(tab.formatName)}(${tab.formatName}* value)
{
	_${firstToLower(tab.formatName)} = value;
}
</#list>
</#if>
}