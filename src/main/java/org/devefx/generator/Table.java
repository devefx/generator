package org.devefx.generator;

import java.util.ArrayList;
import java.util.List;

public class Table {

	private String name;
	
	private String formatName;

	private List<Column> columns;
	
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
	
	public void setFormatName(String formatName) {
		this.formatName = formatName;
	}
	
	public String getFormatName() {
		return formatName;
	}
	
}
