package org.devefx.generator.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.devefx.generator.ColumnFilter;
import org.devefx.generator.NamedMapping;
import org.devefx.generator.Table;
import org.devefx.generator.TableFilter;
import org.devefx.generator.Template;
import org.devefx.generator.core.JdbcConfig;
import org.devefx.generator.core.SqlExecutor;

public class Generator {
	
	private Properties envProps;
	private JdbcConfig jdbcConfig;
	private Set<Template> templates;
	private SqlExecutor sqlExecutor;
	private TableFilter tableFilter;
	private ColumnFilter columnFilter;
	private NamedMapping tableNamedMapping;
	private NamedMapping columnNamedMapping;

	public void setEnv(Properties properties) {
		this.envProps = properties;
	}
	
	public void setJdbcConfig(JdbcConfig config) {
		this.jdbcConfig = config;
	}

	public void setSqlExecutor(SqlExecutor sqlExecutor) {
		this.sqlExecutor = sqlExecutor;
	}

	public void addTemplate(Template template) {
		if (templates == null) {
			templates = new HashSet<Template>();
		}
		templates.add(template);
	}

	public void setTableFilter(TableFilter tableFilter) {
		this.tableFilter = tableFilter;
	}

	public void setColumnFilter(ColumnFilter columnFilter) {
		this.columnFilter = columnFilter;
	}

	public void setTableNamedMapping(NamedMapping namedMapping) {
		this.tableNamedMapping = namedMapping;
	}

	public void setColumnNamedMapping(NamedMapping namedMapping) {
		this.columnNamedMapping = namedMapping;
	}

	public void run() throws Exception {
		if (templates == null || templates.isEmpty() ||
				sqlExecutor == null || jdbcConfig == null) {
			throw new RuntimeException("Setup is not complete!");
		}
		
		if (sqlExecutor.connect(jdbcConfig)) {
			
			List<Table> tables = sqlExecutor.findTables(tableFilter, tableNamedMapping);
			
			if (tables.size() != 0) {
				
				Map<Object, Object> modelMap = new HashMap<Object, Object>();
				
				if (envProps != null) {
					modelMap.putAll(envProps);
				}
				
				for (Table table : tables) {
					sqlExecutor.findColumns(table, columnFilter, columnNamedMapping);
				}
				
				for (Template template : templates) {
					template.run(tables, modelMap);
				}
			}
			
			sqlExecutor.close();
		}
		
	}
	
}
