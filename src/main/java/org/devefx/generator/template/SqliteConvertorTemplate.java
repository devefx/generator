package org.devefx.generator.template;

import java.io.File;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import org.devefx.generator.Column;
import org.devefx.generator.Table;
import org.devefx.generator.convertor.executor.InsertSqlExecutor;
import org.devefx.generator.util.FreeMarkerKit;

public class SqliteConvertorTemplate extends AbstractTemplate<String> {

	private static final String FTL_PATH = "template/sqlite3/create_sql.ftl";
	private static final String TABLES = "tables";
	
	private InsertSqlExecutor querySqlExecutor;
	
	public void setQuerySqlExecutor(InsertSqlExecutor querySqlExecutor) {
		this.querySqlExecutor = querySqlExecutor;
	}
	
	@Override
	public void run(List<Table> tables, Map<Object, Object> modelMap) {
		for (Table table : tables) {
			for (Column column : table.getColumns()) {
				String type = MessageFormat.format(typeMap(column), column.getPrecision(),
						column.getScale());
				column.setJavaType(type);
			}
			try {
				querySqlExecutor.insertSQL(table);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		modelMap.put(TABLES, tables);
		
		FreeMarkerKit.generate(dir, FTL_PATH, modelMap);
		
		StringWriter writer = new StringWriter();
		FreeMarkerKit.generate(writer, FTL_PATH, modelMap);
		createDb(writer.toString());
	}
	
	protected void createDb(String sql) {
		Connection conn = null;
		Statement stmt = null;
		try {
			File f = new File(dir + ".db");
			if (f.exists()) {
				f.delete();
			}
			f.createNewFile();
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:" + dir + ".db");
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Sqlie3 数据库生成成功");
	}
	
	public SqliteConvertorTemplate() {
		jdbcTypeMapper.put(Types.BIT, "INT2");
		jdbcTypeMapper.put(Types.TINYINT, "TINYINT");
		jdbcTypeMapper.put(Types.SMALLINT, "SMALLINT");
		jdbcTypeMapper.put(Types.INTEGER, "INTEGER");
		jdbcTypeMapper.put(Types.BIGINT, "BIGINT");
		
		jdbcTypeMapper.put(Types.FLOAT, "FLOAT");
		jdbcTypeMapper.put(Types.REAL, "REAL");
		jdbcTypeMapper.put(Types.DOUBLE, "DOUBLE");
		jdbcTypeMapper.put(Types.NUMERIC, "NUMERIC");
		jdbcTypeMapper.put(Types.DECIMAL, "DECIMAL({0,number,#},{1,number,#})");
		
		jdbcTypeMapper.put(Types.CHAR, "VARCHAR({0,number,#})");
		jdbcTypeMapper.put(Types.VARCHAR, "VARCHAR({0,number,#})");
		jdbcTypeMapper.put(Types.LONGVARCHAR, "VARCHAR({0,number,#})");
		
		jdbcTypeMapper.put(Types.DATE, "DATE");
		jdbcTypeMapper.put(Types.TIME, "DATETIME");
		jdbcTypeMapper.put(Types.TIMESTAMP, "DATETIME");
		jdbcTypeMapper.put(Types.BINARY, "INT8");
		jdbcTypeMapper.put(Types.VARBINARY, "INT8");
		jdbcTypeMapper.put(Types.LONGVARBINARY, "INT8");
	}
	
}
