package org.devefx.generator.convertor.executor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.devefx.generator.Table;
import org.devefx.generator.core.JdbcConfig;
import org.devefx.generator.core.StandardSqlExecutor;

public class InsertSqlExecutor extends StandardSqlExecutor {

	public InsertSqlExecutor(JdbcConfig jdbcConfig) {
		try {
			connect(jdbcConfig);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insertSQL(Table table) throws SQLException {
		List<String> result = new ArrayList<>();
		Statement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.createStatement();
			rs = statement.executeQuery("SELECT * FROM " + table.getName());
			int count = rs.getMetaData().getColumnCount();
			while (rs.next()) {
				StringBuffer sql = new StringBuffer();
				sql.append("INSERT INTO ").append(table.getName());
				sql.append(" VALUES(");
				for (int i = 0; i < count; i++) {
					if (i != 0) {
						sql.append(", ");
					}
					String value = rs.getString(i + 1);
					if (value != null) {
						sql.append("'").append(value).append("'");
					} else {
						sql.append("NULL");
					}
				}
				sql.append(");");
				result.add(sql.toString());
			}
			table.setObject(result);
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (statement != null) {
				statement.close();
			}
		}
	}
	
}
