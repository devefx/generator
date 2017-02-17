<#list tables as table>
# ${table.name}

${table.comment}

| -- | -- | -- | -- |
| 名称 | 类型 | 主键 | 备注 |
<#list table.columns as column>
| ${column.name} | ${column.javaType} | <#if column.isKey()>true</#if> | ${column.comment} |
</#list>

</#list>