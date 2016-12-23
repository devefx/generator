package org.devefx.generator.core;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.devefx.generator.Column;
import org.devefx.generator.ColumnFilter;
import org.devefx.generator.NamedMapping;
import org.devefx.generator.Table;
import org.devefx.generator.TableFilter;

public class StandardSqlExecutor implements SqlExecutor {
	
	protected Connection connection;
	
	@Override
	public boolean connect(JdbcConfig config) throws SQLException {
		Properties props = new Properties();
		props.setProperty("user", config.getUsername());
		props.setProperty("password", config.getPassword());
		props.setProperty("remarks", "true");
		props.setProperty("useInformationSchema", "true");
		this.connection = DriverManager.getConnection(config.getUrl(), props);
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
			resultSet = databaseMetaData.getTables(connection.getCatalog(), "ROOT", null, new String[] { "TABLE" });
			statement = connection.createStatement();
			while (resultSet.next()) {
				String name = resultSet.getString("TABLE_NAME");
				String comment = resultSet.getString("REMARKS");
				if (tableFilter == null || tableFilter.filter(name)) {
					Table table = new Table(name);
					if (namedMapping != null) {
						name = namedMapping.mapping(name);
					}
					table.setFormatName(name);
					table.setComment(comment);
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
	
	@Override
	public void findColumns(Table table, ColumnFilter columnFilter,
			NamedMapping namedMapping) throws SQLException {
		
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		ResultSet resultSet = null;
		try {
			String keyName = null;
			resultSet = databaseMetaData.getPrimaryKeys(connection.getCatalog(), null, table.getName());
			if (resultSet.next()) {
				keyName = resultSet.getString("COLUMN_NAME");
			}
			if (resultSet != null) {
				resultSet.close();
			}
			resultSet = databaseMetaData.getColumns(connection.getCatalog(), "ROOT", table.getName(), null);
			while (resultSet.next()) {
				String name = resultSet.getString("COLUMN_NAME");
				if (columnFilter == null || columnFilter.filter(name)) {
					Column column = new Column();
					column.setName(name);
					if (name.equals(keyName)) {
						column.setKey(true);
					}
					if (namedMapping != null) {
						name = namedMapping.mapping(name);
					}
					column.setFormatName(name);
					column.setComment(resultSet.getString("REMARKS"));
					column.setJdbcType(resultSet.getInt("DATA_TYPE"));
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
