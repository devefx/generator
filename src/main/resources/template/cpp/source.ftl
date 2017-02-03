#include "db_${table.formatName}.h"

namespace db
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
}