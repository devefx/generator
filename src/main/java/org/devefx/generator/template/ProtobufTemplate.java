package org.devefx.generator.template;

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
		jdbcTypeMapper.put("tinyint", "int32");
		jdbcTypeMapper.put("smallint", "int32");
		jdbcTypeMapper.put("mediumint", "int32");
		jdbcTypeMapper.put("int", "int32");
		jdbcTypeMapper.put("bigint", "int64");
		jdbcTypeMapper.put("float", "float");
		jdbcTypeMapper.put("double", "double");
		jdbcTypeMapper.put("decimal", "double");
		
		jdbcTypeMapper.put("char", "string");
		jdbcTypeMapper.put("varchar", "string");
		jdbcTypeMapper.put("tinyblob", "string");
		jdbcTypeMapper.put("blob", "string");
		jdbcTypeMapper.put("mediumblob", "string");
		jdbcTypeMapper.put("longblob", "string");
		jdbcTypeMapper.put("tinytext", "string");
		jdbcTypeMapper.put("text", "string");
		jdbcTypeMapper.put("mediumtext", "string");
		jdbcTypeMapper.put("longtext", "string");
		
		jdbcTypeMapper.put("date", "string");
		jdbcTypeMapper.put("time", "string");
		jdbcTypeMapper.put("datetime", "string");
		jdbcTypeMapper.put("timestamp", "string");
		jdbcTypeMapper.put("year", "string");
	}

}
