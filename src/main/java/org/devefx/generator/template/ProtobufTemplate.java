package org.devefx.generator.template;

import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.devefx.generator.Column;
import org.devefx.generator.Table;
import org.devefx.generator.util.FreeMarkerKit;

public class ProtobufTemplate extends AbstractTemplate<String> {

	private static final String FTL_PATH = "template/protobuf/proto.ftl";
	private static final String TABLES = "tables";
	
	@Override
	public void run(List<Table> tables, Map<Object, Object> modelMap) {
		for (Table table : tables) {
			for (Column column : table.getColumns()) {
				column.setJavaType(typeMap(column));
			}
		}
		modelMap.put(TABLES, tables);
		FreeMarkerKit.generate(dir, FTL_PATH, modelMap);
	}
	
	public ProtobufTemplate() {
		jdbcTypeMapper.put(Types.BIT, "int32");
		jdbcTypeMapper.put(Types.TINYINT, "int32");
		jdbcTypeMapper.put(Types.SMALLINT, "int32");
		jdbcTypeMapper.put(Types.INTEGER, "int32");
		jdbcTypeMapper.put(Types.BIGINT, "int64");
		jdbcTypeMapper.put(Types.FLOAT, "float");
		jdbcTypeMapper.put(Types.REAL, "float");
		jdbcTypeMapper.put(Types.DOUBLE, "double");
		jdbcTypeMapper.put(Types.NUMERIC, "double");
		jdbcTypeMapper.put(Types.DECIMAL, "double");
		
		jdbcTypeMapper.put(Types.CHAR, "string");
		jdbcTypeMapper.put(Types.VARCHAR, "string");
		jdbcTypeMapper.put(Types.LONGVARCHAR, "string");
		
		jdbcTypeMapper.put(Types.DATE, "string");
		jdbcTypeMapper.put(Types.TIME, "string");
		jdbcTypeMapper.put(Types.TIMESTAMP, "string");
		jdbcTypeMapper.put(Types.BINARY, "bytes");
		jdbcTypeMapper.put(Types.VARBINARY, "bytes");
		jdbcTypeMapper.put(Types.LONGVARBINARY, "bytes");
	}

}
