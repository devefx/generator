package org.devefx.generator;

public class Column {

	private String name;
	
	private String formatName;
	
	private int jdbcType;
	
	private String javaType;

	private String commonType;
	
	private String comment;
	
	private boolean isKey;
	
	private int precision;
	
	private int scale;

	private String tag;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFormatName() {
		return formatName;
	}
	
	public void setFormatName(String formatName) {
		this.formatName = formatName;
	}
	
	public int getJdbcType() {
		return jdbcType;
	}

	public void setJdbcType(int jdbcType) {
		this.jdbcType = jdbcType;
	}
	
	public String getJavaType() {
		return javaType;
	}
	
	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}
	
	public String getCommonType() {
		return commonType;
	}
	
	public void setCommonType(String commonType) {
		this.commonType = commonType;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean isKey() {
		return isKey;
	}

	public void setKey(boolean isKey) {
		this.isKey = isKey;
	}

	public int getPrecision() {
		return precision;
	}

	public void setPrecision(int precision) {
		this.precision = precision;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
}
