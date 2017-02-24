<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
  PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${mapper_package}.${table.formatName}Mapper">

  <resultMap id="${table.formatName}ResultMap" type="${java_package}.${table.formatName}">
  <#list table.columns as column>
    <#if column.isKey()>
    <id property="${column.formatName}" column="${column.name}" />
    <#else>
    <result property="${column.formatName}" column="${column.name}"/>
	</#if>
  </#list>
  </resultMap>
  
  <sql id="Base_Column_List">
    <#list table.columns as column><#if !column.isKey()><#if flag?? && flag == 0>, </#if>${column.name}<#assign flag=0/></#if></#list>
  </sql>
  
  <select id="select${table.formatName}" parameterType="${table.primaryKey.javaType}" resultMap="${table.formatName}ResultMap">
    select ${table.primaryKey.name}, <include refid="Base_Column_List" />
    from ${table.name} where ${table.primaryKey.name} = <#noparse>#{id}</#noparse>
  </select>
  
  <insert id="insert${table.formatName}" parameterType="${table.formatName}">
    insert into ${table.name}(
    <include refid="Base_Column_List" />
    ) values (
    <#list table.columns as column><#if !column.isKey()><#if flag?? && flag == 1>, </#if>${r"#{" + column.formatName + "}"}<#assign flag=1/></#if></#list>)
  </insert>
  
  <update id="update${table.formatName}" parameterType="${table.formatName}">
    update from ${table.name} set
    <#list table.columns as column><#if !column.isKey()><#if flag?? && flag == 2>, </#if>${column.name + r"=#{" + column.formatName + "}"}<#assign flag=2/></#if></#list>
    where ${table.primaryKey.name} = ${r"#{" + table.primaryKey.formatName + "}"}
  </update>
  
  <delete id="delete${table.formatName}" parameterType="${table.formatName}">
    delete from ${table.name} where ${table.primaryKey.name} = ${r"#{" + table.primaryKey.formatName + "}"}
  </delete>
  
</mapper>