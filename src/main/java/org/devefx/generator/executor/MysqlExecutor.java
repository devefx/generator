package org.devefx.generator.executor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.devefx.generator.Column;
import org.devefx.generator.ColumnFilter;
import org.devefx.generator.NamedMapping;
import org.devefx.generator.Table;
import org.devefx.generator.core.AbstractSqlExecutor;
import org.devefx.generator.core.JdbcConfig;

public class MysqlExecutor extends AbstractSqlExecutor {

	private Statement statement;
	
	@Override
	public boolean connect(JdbcConfig config) throws SQLException {
		boolean ret = super.connect(config);
		if (ret) {
			statement = connection.createStatement();
		}
		return ret;
	}
	
	@Override
	public void close() throws SQLException {
		if (statement != null) {
			statement.close();
		}
		super.close();
	}
	
	@Override
	public void findColumns(Table table, ColumnFilter columnFilter,
			NamedMapping namedMapping) throws SQLException {
		ResultSet resultSet = null;
		try {
			String sql = String.format("select column_name, column_key, column_comment, data_type from information_schema.columns a " +
					"where table_schema = '%s' and table_name = '%s'", connection.getCatalog(), table.getName());
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				String name = resultSet.getString("column_name");
				if (columnFilter == null || columnFilter.filter(name)) {
					Column column = new Column();
					column.setName(name);
					if (namedMapping != null) {
						name = namedMapping.mapping(name);
					}
					column.setFormatName(name);
					column.setKey("PRI".equals(resultSet.getString("column_key")));
					column.setComment(resultSet.getString("column_comment"));
					column.setJdbcType(resultSet.getString("data_type"));
					table.addColumn(column);
				}
			}
		} finally {
			if (resultSet != null) {
				resultSet.close();
			}
		}
	}
	
}
