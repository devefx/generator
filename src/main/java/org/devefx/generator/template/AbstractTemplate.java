package org.devefx.generator.template;

import java.util.HashMap;
import java.util.Map;

import org.devefx.generator.Column;
import org.devefx.generator.Template;

public abstract class AbstractTemplate<T> implements Template {

	protected final Map<String, T> jdbcTypeMapper = new HashMap<String, T>();
	
	protected String dir;
	
	@Override
	public void setOutDir(String dir) {
		this.dir = dir;
	}
	
	public T typeMap(Column column) {
		T mapperType = jdbcTypeMapper.get(column.getJdbcType());
		if (mapperType != null) {
			return mapperType;
		} else {
			throw new RuntimeException("No match is found: " + column.getJdbcType());
		}
	}
}
