package ${mapper_package};

import ${java_package}.${table.formatName};

/**
 * ${table.formatName}Mapper
 * @date ${datetime}
 */
public interface ${table.formatName}Mapper {

	${table.formatName} select${table.formatName}(${table.primaryKey.javaType} id);
	
	int insert${table.formatName}(${table.formatName} ${firstToLower(table.formatName)});

	int update${table.formatName}(${table.formatName} ${firstToLower(table.formatName)});
	
	int delete${table.formatName}(${table.formatName} ${firstToLower(table.formatName)});

}