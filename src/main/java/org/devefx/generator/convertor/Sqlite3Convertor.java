package org.devefx.generator.convertor;

import java.util.Properties;

import org.devefx.generator.convertor.executor.InsertSqlExecutor;
import org.devefx.generator.core.Generator;
import org.devefx.generator.core.JdbcConfig;
import org.devefx.generator.core.StandardSqlExecutor;
import org.devefx.generator.template.SqliteConvertorTemplate;
import org.devefx.generator.util.ResKit;

public class Sqlite3Convertor {

	public static void main(String[] args) throws Exception {
		// jdbc连接配置
		JdbcConfig config = new JdbcConfig();
		config.setUrl("jdbc:mysql://localhost:3306/myth-db");
		config.setUsername("root");
		config.setPassword("sql8092");
		// 创建一个生成器
		Generator gen = new Generator();
		gen.setJdbcConfig(config);
		// 设置sql执行器
		gen.setSqlExecutor(new StandardSqlExecutor());
		// 添加一个protobuf生成模板
		SqliteConvertorTemplate convertorTemplate = new SqliteConvertorTemplate();
		convertorTemplate.setOutDir("F:/Workspaces/20160920/generator/src/test/resources/sqlite3.sql");
		convertorTemplate.setQuerySqlExecutor(new InsertSqlExecutor(config));
		gen.addTemplate(convertorTemplate);
		// 设置自定义环境变量
		Properties props = new Properties();
		props.load(ResKit.getResourceAsReader("env.properties"));
		gen.setEnv(props);
		// 开始生成
		gen.run();
	}
	
}
