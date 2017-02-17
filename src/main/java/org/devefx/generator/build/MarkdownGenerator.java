package org.devefx.generator.build;

import java.util.Properties;

import org.devefx.generator.core.Generator;
import org.devefx.generator.core.JdbcConfig;
import org.devefx.generator.core.StandardSqlExecutor;
import org.devefx.generator.named.UnderscoreToCamelCaseNamedMapping;
import org.devefx.generator.template.MarkdownTemplate;
import org.devefx.generator.util.ResKit;

public class MarkdownGenerator {

	public static void main(String[] args) throws Exception {
		// jdbc连接配置
		JdbcConfig config = new JdbcConfig();
		config.setUrl("jdbc:mysql://localhost:3306/myth");
		config.setUsername("root");
		config.setPassword("sql8092");
		// 创建一个生成器
		Generator gen = new Generator();
		gen.setJdbcConfig(config);
		// 设置sql执行器
		gen.setSqlExecutor(new StandardSqlExecutor());
		// 添加一个protobuf生成模板
		MarkdownTemplate markdownTemplate = new MarkdownTemplate();
		markdownTemplate.setOutDir("F:/Users/yueyouqian/Documents/Projects/database.md");
		gen.addTemplate(markdownTemplate);
		// 设置表过滤器
		//gen.setTableFilter(tableFilter);
		// 设置字段过滤器
		//gen.setColumnFilter(columnFilter);
		// 设置驼峰规则的字段名称
		gen.setColumnNamedMapping(new UnderscoreToCamelCaseNamedMapping());
		// 设置自定义环境变量
		Properties props = new Properties();
		props.load(ResKit.getResourceAsReader("env.properties"));
		gen.setEnv(props);
		// 开始生成
		gen.run();
	}
	
}
