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

    private static final String FTL_HEADER_PATH = "template/cpp/myth/header.ftl";
    private static final String FTL_SOURCE_PATH = "template/cpp/myth/source.ftl";
    private static final String FTL_HEADER_DAO_PATH = "template/cpp/sqlite3_dao/header.ftl";
    private static final String FTL_SOURCE_DAO_PATH = "template/cpp/sqlite3_dao/source.ftl";
    private static final String TABLE = "table";
    private static final String IMPORTS = "imports";
    
    private static final String SQLITE_INTEGER	= "int";
    private static final String SQLITE_FLOAT 	= "double";
    private static final String SQLITE_TEXT 	= "std::string";
    

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
            	for (Map.Entry<String, Table> ref : table.getReferenceTables().entrySet()) {
    				imports.add(ref.getValue());
    			}
			}
            modelMap.put(IMPORTS, imports);
            
            for (Column column : table.getColumns()) {
                CppType type = typeMap(column);
                column.setJavaType(type.getTypeName());
                column.setCommonType(type.getType());
                column.setTag(type.getTypeName());
                if (type.isReference()) {
                    column.setJavaType("const " + type.getTypeName() + "&");
                }
            }

            FreeMarkerKit.generate(MessageFormat.format("{0}/db_{1}.h", dir,
                    table.getFileName()), FTL_HEADER_PATH, modelMap);
            FreeMarkerKit.generate(MessageFormat.format("{0}/db_{1}.cpp", dir,
                    table.getFileName()), FTL_SOURCE_PATH, modelMap);
            
            FreeMarkerKit.generate(MessageFormat.format("{0}/dao/db_{1}_dao.h", dir,
                    table.getFileName()), FTL_HEADER_DAO_PATH, modelMap);
            FreeMarkerKit.generate(MessageFormat.format("{0}/dao/db_{1}_dao.cpp", dir,
                    table.getFileName()), FTL_SOURCE_DAO_PATH, modelMap);
        }
    }

    public CppEntityTemplate() {
        jdbcTypeMapper.put(Types.BIT, new CppType("bool", SQLITE_INTEGER));
        jdbcTypeMapper.put(Types.TINYINT, new CppType("char", SQLITE_INTEGER));
        jdbcTypeMapper.put(Types.SMALLINT, new CppType("short", SQLITE_INTEGER));
        jdbcTypeMapper.put(Types.INTEGER, new CppType("int", SQLITE_INTEGER));
        jdbcTypeMapper.put(Types.BIGINT, new CppType("long", SQLITE_INTEGER));
        
        jdbcTypeMapper.put(Types.FLOAT, new CppType("float", SQLITE_FLOAT));
        jdbcTypeMapper.put(Types.REAL, new CppType("float", SQLITE_FLOAT));
        jdbcTypeMapper.put(Types.DOUBLE, new CppType("double", SQLITE_FLOAT));
        jdbcTypeMapper.put(Types.NUMERIC, new CppType("double", SQLITE_FLOAT));
        jdbcTypeMapper.put(Types.DECIMAL, new CppType("double", SQLITE_FLOAT));

        jdbcTypeMapper.put(Types.CHAR, new CppType("char", SQLITE_INTEGER));
        jdbcTypeMapper.put(Types.VARCHAR, new CppType(true, "std::string", SQLITE_TEXT));
        jdbcTypeMapper.put(Types.LONGVARCHAR, new CppType(true, "std::string", SQLITE_TEXT));

        jdbcTypeMapper.put(Types.DATE, new CppType(true, "std::string", SQLITE_TEXT));
        jdbcTypeMapper.put(Types.TIME, new CppType(true, "std::string", SQLITE_TEXT));
        jdbcTypeMapper.put(Types.TIMESTAMP, new CppType(true, "std::string", SQLITE_TEXT));
        /*
        jdbcTypeMapper.put(Types.BINARY, new CppType("char[]"));
        jdbcTypeMapper.put(Types.VARBINARY, new CppType("char[]"));
        jdbcTypeMapper.put(Types.LONGVARBINARY, new CppType("char[]"));
        */
    }

}
