#ifndef GENERATOR_MAP_DAO__INCLUDED
#define GENERATOR_MAP_DAO__INCLUDED

#include "storage/SqlTemplate.h"
#include "model/entity/db_${table.fileName}.h"

namespace db_<#if table.name?starts_with("t01")>data<#elseif table.name?starts_with("t02")>game</#if>
{

class ${table.formatName}_dao : sql::SqlTemplate
{
public:

    ${table.formatName}* load(int id);

    int save(const ${table.formatName}& obj);

    int update(const ${table.formatName}& obj);

    int saveOrUpdate(const ${table.formatName}& obj);

    int remove(const ${table.formatName}& obj);
};

}

#endif