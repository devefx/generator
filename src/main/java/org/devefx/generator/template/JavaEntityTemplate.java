package org.devefx.generator.template;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.devefx.generator.Column;
import org.devefx.generator.Table;
import org.devefx.generator.template.type.JavaType;
import org.devefx.generator.util.FreeMarkerKit;

public class JavaEntityTemplate extends AbstractTemplate<JavaType> {

	private static final String FTL_PATH = "template/java/entity.ftl";
	private static final String TABLE = "table";
	private static final String IMPORTS = "imports";
	
	@Override
	public void run(List<Table> tables, Map<Object, Object> modelMap) {
		for (Table table : tables) {
			modelMap.put(TABLE, table);
			
			Set<String> imports = new HashSet<String>();
			
			for (Column column : table.getColumns()) {
				JavaType type = typeMap(column);
				column.setJavaType(type.getTypeName());
				if (type.hasPackageName()) {
					imports.add(type.getPackageName());
				}
			}
			
			modelMap.put(IMPORTS, imports);
			
			FreeMarkerKit.generate(MessageFormat.format("{0}/{1}.java", dir,
					table.getFormatName()), FTL_PATH, modelMap);
		}
	}
	
	public JavaEntityTemplate() {
		jdbcTypeMapper.put("tinyint", new JavaType("byte"));
		jdbcTypeMapper.put("smallint", new JavaType("short"));
		jdbcTypeMapper.put("mediumint", new JavaType("int"));
		jdbcTypeMapper.put("int", new JavaType("int"));
		jdbcTypeMapper.put("bigint", new JavaType("long"));
		jdbcTypeMapper.put("float", new JavaType("float"));
		jdbcTypeMapper.put("double", new JavaType("double"));
		jdbcTypeMapper.put("decimal", new JavaType("java.math.BigDecimal", "BigDecimal"));
		
		jdbcTypeMapper.put("char", new JavaType("String"));
		jdbcTypeMapper.put("varchar", new JavaType("String"));
		jdbcTypeMapper.put("tinyblob", new JavaType("String"));
		jdbcTypeMapper.put("blob", new JavaType("String"));
		jdbcTypeMapper.put("mediumblob", new JavaType("String"));
		jdbcTypeMapper.put("longblob", new JavaType("String"));
		jdbcTypeMapper.put("tinytext", new JavaType("String"));
		jdbcTypeMapper.put("text", new JavaType("String"));
		jdbcTypeMapper.put("mediumtext", new JavaType("String"));
		jdbcTypeMapper.put("longtext", new JavaType("String"));
		
		jdbcTypeMapper.put("date", new JavaType("java.util.Date", "Date"));
		jdbcTypeMapper.put("time", new JavaType("java.util.Date", "Date"));
		jdbcTypeMapper.put("datetime", new JavaType("java.util.Date", "Date"));
		jdbcTypeMapper.put("timestamp", new JavaType("java.util.Date", "Date"));
		jdbcTypeMapper.put("year", new JavaType("java.util.Date", "Date"));
	}
	
}
