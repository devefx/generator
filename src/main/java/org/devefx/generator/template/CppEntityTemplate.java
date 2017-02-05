package org.devefx.generator.template;

import org.devefx.generator.Column;
import org.devefx.generator.Table;
import org.devefx.generator.template.type.CppType;
import org.devefx.generator.util.FreeMarkerKit;

import java.sql.Types;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CppEntityTemplate extends AbstractTemplate<CppType> {

    private static final String FTL_HEADER_PATH = "template/cpp/header.ftl";
    private static final String FTL_SOURCE_PATH = "template/cpp/source.ftl";
    private static final String TABLE = "table";
    private static final String IMPORTS = "imports";

    public void run(List<Table> tables, Map<Object, Object> modelMap) {
    	modelMap.put(DATETIME, dateFormat.format(new Date()));
    	
        for (Table table : tables) {
            modelMap.put(TABLE, table);

            Set<Table> imports = new HashSet<Table>();
            if (table.getForeignTables() != null) {
            	for (Table tab : table.getForeignTables()) {
    				imports.add(tab);
    			}
			}
            if (table.getReferenceTables() != null) {
            	for (Table tab : table.getReferenceTables()) {
    				imports.add(tab);
    			}
			}
            modelMap.put(IMPORTS, imports);
            
            for (Column column : table.getColumns()) {
                CppType type = typeMap(column);
                column.setJavaType(type.getTypeName());
                column.setTag(type.getTypeName());
                if (type.isReference()) {
                    column.setJavaType("const " + type.getTypeName() + "&");
                }
            }

            FreeMarkerKit.generate(MessageFormat.format("{0}/db_{1}.h", dir,
                    table.getFileName()), FTL_HEADER_PATH, modelMap);
            FreeMarkerKit.generate(MessageFormat.format("{0}/db_{1}.cpp", dir,
                    table.getFileName()), FTL_SOURCE_PATH, modelMap);
        }
    }

    public CppEntityTemplate() {
        jdbcTypeMapper.put(Types.BIT, new CppType("boolean"));
        jdbcTypeMapper.put(Types.TINYINT, new CppType("unsigned int"));
        jdbcTypeMapper.put(Types.SMALLINT, new CppType("short"));
        jdbcTypeMapper.put(Types.INTEGER, new CppType("int"));
        jdbcTypeMapper.put(Types.BIGINT, new CppType("long"));
        jdbcTypeMapper.put(Types.FLOAT, new CppType("float"));
        jdbcTypeMapper.put(Types.REAL, new CppType("float"));
        jdbcTypeMapper.put(Types.DOUBLE, new CppType("double"));
        jdbcTypeMapper.put(Types.NUMERIC, new CppType("double"));
        jdbcTypeMapper.put(Types.DECIMAL, new CppType("double"));

        jdbcTypeMapper.put(Types.CHAR, new CppType("char"));
        jdbcTypeMapper.put(Types.VARCHAR, new CppType(true, "std::string"));
        jdbcTypeMapper.put(Types.LONGVARCHAR, new CppType(true, "std::string"));

        jdbcTypeMapper.put(Types.DATE, new CppType(true, "std::string"));
        jdbcTypeMapper.put(Types.TIME, new CppType(true, "std::string"));
        jdbcTypeMapper.put(Types.TIMESTAMP, new CppType(true, "std::string"));
        jdbcTypeMapper.put(Types.BINARY, new CppType("char[]"));
        jdbcTypeMapper.put(Types.VARBINARY, new CppType("char[]"));
        jdbcTypeMapper.put(Types.LONGVARBINARY, new CppType("char[]"));
    }

}
