package org.devefx.generator.core;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
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
		try {
			resultSet = databaseMetaData.getTables(connection.getCatalog(), "ROOT", null, new String[] { "TABLE" });
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
		}
		processForeign(tables);
		processReference(tables);
		return tables;
	}
	
	protected void processForeign(List<Table> tables) throws SQLException {
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		for (Table table : tables) {
			ResultSet resultSet = null;
			try {
				resultSet = databaseMetaData.getExportedKeys(connection.getCatalog(),
						null, table.getName());
				while (resultSet.next()) {
					String name = resultSet.getString("FKTABLE_NAME");
					for (Table tab : tables) {
						if (name.equals(tab.getName())) {
							table.addForeignTable(tab);
							break;
						}
					}
				}
			} finally {
				if (resultSet != null) {
					resultSet.close();
				}
			}
		}
	}
	
	protected void processReference(List<Table> tables) throws SQLException {
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		for (Table table : tables) {
			ResultSet resultSet = null;
			try {
				resultSet = databaseMetaData.getImportedKeys(connection.getCatalog(),
						null, table.getName());
				while (resultSet.next()) {
					String name = resultSet.getString("PKTABLE_NAME");
					String column = resultSet.getString("FKCOLUMN_NAME");
					for (Table tab : tables) {
						if (name.equals(tab.getName())) {
							table.addReferenceTable(column.replaceAll("_id", ""), tab);
							break;
						}
					}
				}
			} finally {
				if (resultSet != null) {
					resultSet.close();
				}
			}
		}
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
						table.setPrimaryKey(column);
					}
					if (namedMapping != null) {
						name = namedMapping.mapping(name);
					}
					column.setFormatName(name);
					column.setComment(resultSet.getString("REMARKS"));
					column.setJdbcType(resultSet.getInt("DATA_TYPE"));
					column.setPrecision(resultSet.getInt("COLUMN_SIZE"));
					column.setScale(resultSet.getInt("DECIMAL_DIGITS"));
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
