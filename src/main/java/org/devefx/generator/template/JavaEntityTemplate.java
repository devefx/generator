package org.devefx.generator.template;

import java.sql.Types;
import java.text.MessageFormat;
import java.util.Date;
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
	
	public void run(List<Table> tables, Map<Object, Object> modelMap) {
		modelMap.put(DATETIME, dateFormat.format(new Date()));
		
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
					table.getFileName()), FTL_PATH, modelMap);
			
			FreeMarkerKit.generate(MessageFormat.format("{0}/mapper/{1}Mapper.xml", dir,
					table.getFileName()), "template/mybatis/mapper-xml.ftl", modelMap);
			
			FreeMarkerKit.generate(MessageFormat.format("{0}/dao/{1}Mapper.java", dir,
					table.getFileName()), "template/mybatis/mapper-java.ftl", modelMap);
			
			FreeMarkerKit.generate(MessageFormat.format("{0}/service/{1}Service.java", dir,
					table.getFileName()), "template/mybatis/mapper-service.ftl", modelMap);
			
			FreeMarkerKit.generate(MessageFormat.format("{0}/service/impl/{1}ServiceImpl.java", dir,
					table.getFileName()), "template/mybatis/mapper-service-impl.ftl", modelMap);
		}
	}
	
	public JavaEntityTemplate() {
		jdbcTypeMapper.put(Types.BIT, new JavaType("boolean"));
		jdbcTypeMapper.put(Types.TINYINT, new JavaType("byte"));
		jdbcTypeMapper.put(Types.SMALLINT, new JavaType("short"));
		jdbcTypeMapper.put(Types.INTEGER, new JavaType("int"));
		jdbcTypeMapper.put(Types.BIGINT, new JavaType("long"));
		jdbcTypeMapper.put(Types.FLOAT, new JavaType("float"));
		jdbcTypeMapper.put(Types.REAL, new JavaType("float"));
		jdbcTypeMapper.put(Types.DOUBLE, new JavaType("double"));
		jdbcTypeMapper.put(Types.NUMERIC, new JavaType("java.math.BigDecimal", "BigDecimal"));
		jdbcTypeMapper.put(Types.DECIMAL, new JavaType("java.math.BigDecimal", "BigDecimal"));
		
		jdbcTypeMapper.put(Types.CHAR, new JavaType("String"));
		jdbcTypeMapper.put(Types.VARCHAR, new JavaType("String"));
		jdbcTypeMapper.put(Types.LONGVARCHAR, new JavaType("String"));
		
		jdbcTypeMapper.put(Types.DATE, new JavaType("java.sql.Date", "Date"));
		jdbcTypeMapper.put(Types.TIME, new JavaType("java.sql.Time", "Time"));
		jdbcTypeMapper.put(Types.TIMESTAMP, new JavaType("java.sql.Timestamp", "Timestamp"));
		jdbcTypeMapper.put(Types.BINARY, new JavaType("byte[]"));
		jdbcTypeMapper.put(Types.VARBINARY, new JavaType("byte[]"));
		jdbcTypeMapper.put(Types.LONGVARBINARY, new JavaType("byte[]"));
		
		jdbcTypeMapper.put(Types.STRUCT, new JavaType("java.sql.Struct", "Struct"));
		jdbcTypeMapper.put(Types.ARRAY, new JavaType("java.sql.Array", "Array"));
		jdbcTypeMapper.put(Types.BLOB, new JavaType("java.sql.Blob", "Blob"));
		jdbcTypeMapper.put(Types.CLOB, new JavaType("java.sql.Clob", "Clob"));
		jdbcTypeMapper.put(Types.REF, new JavaType("java.sql.Ref", "Ref"));
		jdbcTypeMapper.put(Types.BOOLEAN, new JavaType("Boolean"));
		jdbcTypeMapper.put(Types.ROWID, new JavaType("java.sql.RowId", "RowId"));
	}
	
}
