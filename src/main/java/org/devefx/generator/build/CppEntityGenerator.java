package org.devefx.generator.build;

import org.devefx.generator.NamedMapping;
import org.devefx.generator.core.Generator;
import org.devefx.generator.core.JdbcConfig;
import org.devefx.generator.core.StandardSqlExecutor;
import org.devefx.generator.named.RegexpCaseNamedMapping;
import org.devefx.generator.named.UnderscoreToCamelCaseNamedMapping;
import org.devefx.generator.template.CppEntityTemplate;
import org.devefx.generator.util.ResKit;

import java.util.Properties;

public class CppEntityGenerator {

    public static void main(String[] args) throws Exception  {
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
        // 添加一个java实体类生成模板
        CppEntityTemplate cppEntityTemplate = new CppEntityTemplate();
        cppEntityTemplate.setOutDir("F:/Users/yueyouqian/Documents/Projects/cpp");
        gen.addTemplate(cppEntityTemplate);
        // 设置自定义表名称映射
        gen.setTableNamedMapping(new NamedMapping() {
            NamedMapping mapping = new RegexpCaseNamedMapping("t[0-9]{2}_([a-z_]+)", "$1", false);
            @Override
            public String mapping(String inputName) {
                return mapping.mapping(inputName);
            }
        });
        gen.setFileNamedMapping(new NamedMapping() {
        	NamedMapping mapping = new RegexpCaseNamedMapping("t[0-9]{2}_([a-z_]+)", "$1", false);
            @Override
            public String mapping(String inputName) {
                if (inputName.startsWith("t01")) {
                    return "tpl_" + mapping.mapping(inputName);
                }
                return "usr_" + mapping.mapping(inputName);
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
