package org.devefx.generator.core;

import java.sql.SQLException;
import java.util.List;

import org.devefx.generator.ColumnFilter;
import org.devefx.generator.NamedMapping;
import org.devefx.generator.Table;
import org.devefx.generator.TableFilter;

public interface SqlExecutor {
	
	boolean connect(JdbcConfig config) throws SQLException;

	void close() throws SQLException;
	
	List<Table> findTables(TableFilter tableFilter, NamedMapping namedMapping) throws SQLException;

	void findColumns(Table table, ColumnFilter columnFilter, NamedMapping namedMapping) throws SQLException;
	
}
