package org.devefx.generator.build;

import java.util.Properties;

import org.devefx.generator.NamedMapping;
import org.devefx.generator.core.Generator;
import org.devefx.generator.core.JdbcConfig;
import org.devefx.generator.core.StandardSqlExecutor;
import org.devefx.generator.named.RegexpCaseNamedMapping;
import org.devefx.generator.named.UnderscoreToCamelCaseNamedMapping;
import org.devefx.generator.template.JavaEntityTemplate;
import org.devefx.generator.util.ResKit;

public class JavaEntityGenerator {

	public static void main(String[] args) throws Exception {
		// jdbc连接配置
		JdbcConfig config = new JdbcConfig();
		config.setUrl("jdbc:mysql://192.168.3.6:3306/hospital");
		config.setUsername("baichou");
		config.setPassword("xmts@sla_kxb_337");
		// 创建一个生成器
		Generator gen = new Generator();
		gen.setJdbcConfig(config);
		// 设置sql执行器
		gen.setSqlExecutor(new StandardSqlExecutor());
		// 添加一个java实体类生成模板
		JavaEntityTemplate javaEntityTemplate = new JavaEntityTemplate();
		javaEntityTemplate.setOutDir("F:/Users/yueyouqian/Documents/Projects/java");
		gen.addTemplate(javaEntityTemplate);
		// 设置自定义表名称映射
		gen.setTableNamedMapping(new NamedMapping() {
			NamedMapping mapping = new RegexpCaseNamedMapping("t_([a-z_]+)", "_$1");
			@Override
			public String mapping(String inputName) {
				return mapping.mapping(inputName);
			}
		});
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
		
		System.out.println("生成完成");
	}
	
}
