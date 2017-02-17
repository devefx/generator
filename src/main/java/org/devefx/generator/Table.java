package org.devefx.generator;

import java.util.ArrayList;
import java.util.List;

public class Table {

	private String name;
	
	private String comment;
	
	private String formatName;

	private List<Column> columns;
	
	private Column primaryKey;
	
	private List<Table> referenceTables;
	
	private List<Table> foreignTables;
	
	private String fileName;
	
	private Object object;
	
	public Table() {
	}
	
	public Table(String name) {
		this.name = name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}
	
	public List<Column> getColumns() {
		return columns;
	}
	
	public void addColumn(Column column) {
		if (columns == null) {
			columns = new ArrayList<Column>();
		}
		columns.add(column);
	}
	
	public Column getPrimaryKey() {
		return primaryKey;
	}
	
	public void setPrimaryKey(Column primaryKey) {
		this.primaryKey = primaryKey;
	}
	
	public List<Table> getReferenceTables() {
		return referenceTables;
	}
	
	public void setReferenceTables(List<Table> referenceTables) {
		this.referenceTables = referenceTables;
	}
	
	public void addReferenceTable(Table table) {
		if (referenceTables == null) {
			referenceTables = new ArrayList<Table>();
		}
		referenceTables.add(table);
	}
	
	public List<Table> getForeignTables() {
		return foreignTables;
	}

	public void setForeignTables(List<Table> foreignTables) {
		this.foreignTables = foreignTables;
	}
	
	public void addForeignTable(Table table) {
		if (foreignTables == null) {
			foreignTables = new ArrayList<Table>();
		}
		foreignTables.add(table);
	}

	public void setFormatName(String formatName) {
		this.formatName = formatName;
	}
	
	public String getFormatName() {
		return formatName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setObject(Object object) {
		this.object = object;
	}
	
	public Object getObject() {
		return object;
	}
	
}
