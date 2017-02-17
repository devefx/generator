package org.devefx.generator.template;

import java.sql.Types;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.devefx.generator.Column;
import org.devefx.generator.Table;
import org.devefx.generator.util.FreeMarkerKit;

public class MarkdownTemplate extends AbstractTemplate<String> {

	private static final String FTL_PATH = "template/markdown/md.ftl";
	private static final String TABLES = "tables";
	
	public void run(List<Table> tables, Map<Object, Object> modelMap) {
		modelMap.put(DATETIME, dateFormat.format(new Date()));
		
		for (Table table : tables) {
			for (Column column : table.getColumns()) {
				column.setJavaType(typeMap(column));
			}
		}
		modelMap.put(TABLES, tables);
		FreeMarkerKit.generate(dir, FTL_PATH, modelMap);
	}
	
	public MarkdownTemplate() {
		jdbcTypeMapper.put(Types.BIT, "bit");
		jdbcTypeMapper.put(Types.TINYINT, "tinyint");
		jdbcTypeMapper.put(Types.SMALLINT, "smallint");
		jdbcTypeMapper.put(Types.INTEGER, "integer");
		jdbcTypeMapper.put(Types.BIGINT, "bigint");
		jdbcTypeMapper.put(Types.FLOAT, "float");
		jdbcTypeMapper.put(Types.REAL, "real");
		jdbcTypeMapper.put(Types.DOUBLE, "double");
		jdbcTypeMapper.put(Types.NUMERIC, "numeric");
		jdbcTypeMapper.put(Types.DECIMAL, "decimal");
		
		jdbcTypeMapper.put(Types.CHAR, "char");
		jdbcTypeMapper.put(Types.VARCHAR, "varchar");
		jdbcTypeMapper.put(Types.LONGVARCHAR, "longvarchar");
		
		jdbcTypeMapper.put(Types.DATE, "date");
		jdbcTypeMapper.put(Types.TIME, "time");
		jdbcTypeMapper.put(Types.TIMESTAMP, "timestamp");
		jdbcTypeMapper.put(Types.BINARY, "binary");
		jdbcTypeMapper.put(Types.VARBINARY, "varbinary");
		jdbcTypeMapper.put(Types.LONGVARBINARY, "longvarbinary");
	}
}
