<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>
<@c.page>
<div>Login page</div>
<@l.login "/login" />
<div>
    <a href="/registration">Create an account</a>
</div>
</@c.page>