#include "db_${table.fileName}_dao.h"

namespace db_<#if table.name?starts_with("t01")>data<#elseif table.name?starts_with("t02")>game</#if>
{

${table.formatName}* ${table.formatName}_dao::load(int id)
{
    static const char* sql = "select * from ${table.name} where ${table.primaryKey.name}=?";

    return SqlTemplate::queryForObject<${table.formatName}>(sql, ${table.formatName}::GetClass(), id);
}

int ${table.formatName}_dao::save(const ${table.formatName}& obj)
{
    static const char* sql = "insert into ${table.name}(<#list table.columns as column><#if (!column.isKey())>${column.name}<#if (column_index+1 lt table.columns?size)>, </#if></#if></#list>) values(<#list table.columns as column><#if (!column.isKey())>?<#if (column_index+1 lt table.columns?size)>, </#if></#if></#list>)";

    return SqlTemplate::update(sql, <#list table.columns as column><#if (!column.isKey())>obj.get_${column.name}()<#if (column_index+1 lt table.columns?size)>, </#if></#if></#list>);
}

int ${table.formatName}_dao::update(const ${table.formatName}& obj)
{
    static const char* sql = "update ${table.name} set <#list table.columns as column><#if (!column.isKey())>${column.name}=?<#if (column_index+1 lt table.columns?size)>, </#if></#if></#list> where ${table.primaryKey.name}=?";
	
    return SqlTemplate::update(sql, <#list table.columns as column><#if (!column.isKey())>obj.get_${column.name}(), </#if></#list>obj.get_${table.primaryKey.name}());
}

int ${table.formatName}_dao::saveOrUpdate(const ${table.formatName}& obj)
{
    return obj.get_${table.primaryKey.name}() ? update(obj) : save(obj);
}

int ${table.formatName}_dao::remove(const ${table.formatName}& obj)
{
    static const char* sql = "delete from ${table.name} where ${table.primaryKey.name}=?";

    return SqlTemplate::update(sql, obj.get_${table.primaryKey.name}());
}

}