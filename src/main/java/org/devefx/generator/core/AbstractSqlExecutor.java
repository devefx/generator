package org.devefx.generator.core;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.devefx.generator.NamedMapping;
import org.devefx.generator.Table;
import org.devefx.generator.TableFilter;

public abstract class AbstractSqlExecutor implements SqlExecutor {
	
	protected Connection connection;
	
	@Override
	public boolean connect(JdbcConfig config) throws SQLException {
		this.connection = DriverManager.getConnection(config.getUrl(), 
				config.getUsername(), config.getPassword());
		return true;
	}
	
	@Override
	public void close() throws SQLException {
		connection.close();
	}
	
	@Override
	public List<Table> findTables(TableFilter tableFilter,
			NamedMapping namedMapping) throws SQLException {
		List<Table> tables = new ArrayList<Table>();
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		ResultSet resultSet = null;
		Statement statement = null;
		try {
			resultSet = databaseMetaData.getTables(connection.getCatalog(), "root", null, new String[] { "TABLE" });
			statement = connection.createStatement();
			while (resultSet.next()) {
				String name = resultSet.getString("TABLE_NAME");
				if (tableFilter == null || tableFilter.filter(name)) {
					Table table = new Table(name);
					if (namedMapping != null) {
						name = namedMapping.mapping(name);
					}
					table.setFormatName(name);
					tables.add(table);
				}
			}
		} finally {
			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
		}
		return tables;
	}
	
}
