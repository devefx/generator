package ${service_impl_package};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ${mapper_package}.${table.formatName}Mapper;
import ${java_package}.${table.formatName};
import ${service_package}.${table.formatName}Service;

/**
 * ${table.formatName}ServiceImpl
 * @date ${datetime}
 */
@Service
public class ${table.formatName}ServiceImpl implements ${table.formatName}Service {

    @Autowired
    private ${table.formatName}Mapper ${firstToLower(table.formatName)}Mapper;
    
    @Override
    public ${table.formatName} select${table.formatName}(${table.primaryKey.javaType} id) {
        return ${firstToLower(table.formatName)}Mapper.select${table.formatName}(id);
    }

    @Override
    public int insert${table.formatName}(${table.formatName} ${firstToLower(table.formatName)}) {
        return ${firstToLower(table.formatName)}Mapper.insert${table.formatName}(${firstToLower(table.formatName)});
    }

    @Override
    public int update${table.formatName}(${table.formatName} ${firstToLower(table.formatName)}) {
        return ${firstToLower(table.formatName)}Mapper.update${table.formatName}(${firstToLower(table.formatName)});
    }

    @Override
    public int delete${table.formatName}(${table.formatName} ${firstToLower(table.formatName)}) {
        return ${firstToLower(table.formatName)}Mapper.delete${table.formatName}(${firstToLower(table.formatName)});
    }

}
