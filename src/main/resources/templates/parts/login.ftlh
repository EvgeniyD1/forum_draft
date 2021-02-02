<#include "security.ftlh">
<#macro login path isRegisterForm>
    <form method="post" action="${path}">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">User Name:</label>
            <div class="col-sm-5 my-1">
                <input type="text" name="username" class="form-control" placeholder="username"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Password:</label>
            <div class="col-sm-5 my-1">
                <input type="password" name="password" class="form-control" placeholder="password"/>
            </div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button type="submit" class="btn btn-outline-info col-sm-2 my-1">
            <#if isRegisterForm>
                Create
            <#else>Sign in
            </#if></button>
        <#if !isRegisterForm>
            <div>
                <form>
                    <input class="btn btn-outline-info col-sm-2 my-1" type="button" value="Create an account"
                           onClick='location.href="/registration"'>
                </form>
            </div>
        </#if>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button type="submit" class="btn btn-outline-info"><#if !user??>Sing In <#else>Sing Out</#if></button>
    </form>
</#macro>