<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>
<@c.page>
<div class="my-1">Registration page</div>
<div>
    ${message!""}
</div>
<@l.login "/registration" true/>
</@c.page>