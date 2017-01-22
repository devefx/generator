PRAGMA foreign_keys = OFF;

<#list tables as table>
-- ----------------------------
-- Table structure for "main"."${table.name}"
-- ----------------------------
/** DROP TABLE "main"."${table.name}"; **/
CREATE TABLE "main"."${table.name}"(
	<#list table.columns as column>
		<#if (column_index > 0)>,</#if>[${column.name}] ${column.javaType}<#if (column.isKey())> PRIMARY KEY AUTOINCREMENT</#if>
	</#list>
);

<#if (table.object?size > 0)>
-- ----------------------------
-- Records of ${table.name}
-- ----------------------------
BEGIN TRANSACTION;
<#list table.object as sql>
${sql}
</#list>
COMMIT;

</#if>
</#list>