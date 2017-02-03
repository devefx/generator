#ifndef GENERATOR_${table.formatName}_entity__INCLUDED
#define GENERATOR_${table.formatName}_entity__INCLUDED

#include <string>

namespace db
{

class ${table.formatName}
{
public:
<#list table.columns as column>
    ${column.javaType} ${column.name}() const;
    void set_${column.name}(${column.javaType} value);

</#list>
private:
<#list table.columns as column>
    ${column.tag} ${column.name}_;
</#list>
};

}

#endif