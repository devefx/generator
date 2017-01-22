package org.devefx.generator;

import java.util.ArrayList;
import java.util.List;

public class Table {

	private String name;
	
	private String comment;
	
	private String formatName;

	private List<Column> columns;
	
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
	
	public void setFormatName(String formatName) {
		this.formatName = formatName;
	}
	
	public String getFormatName() {
		return formatName;
	}
	
	public void setObject(Object object) {
		this.object = object;
	}
	
	public Object getObject() {
		return object;
	}
	
}
