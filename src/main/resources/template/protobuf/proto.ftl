syntax = "proto2";

option java_package = "${protobuf_package}";
option java_outer_classname = "${protobuf_classname}";

<#list tables as table>
// ${table.comment}
message PB${table.formatName} {
    <#list table.columns as column>
    optional ${column.javaType} ${column.name}${placeholder(column.javaType+column.name, 25)}= ${column_index + 1}; // ${column.comment}
    </#list>
}

</#list>
