package org.devefx.generator;

import java.util.List;
import java.util.Map;

public interface Template {

	void setOutDir(String dir);

	void run(List<Table> tables, Map<Object, Object> modelMap);

}
