<#ftl auto_esc=false>

/* eslint-disable */

<#list domains as domain>
export class ${domain.signatureClassName?no_esc}<#if domain.parent??> extends ${domain.parent?no_esc}</#if> {
    <#list domain.fields as field>
        ${field.systemName}<#if field.nullable>?</#if>: ${field.signature?no_esc} ${field.definition};
    </#list>
}

</#list>
