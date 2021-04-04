<#include "security.ftlh">
<#macro login path isRegisterForm>
    <form method="post" action="${path}">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label" for="username">User Name:</label>
            <div class="col-sm-5 my-1">
                <input type="text" name="username" class="form-control ${(usernameError??)?string('is-invalid','')}"
                       placeholder="username" id="username" value="<#if userFromSession??>${userFromSession.username}</#if>"/>
                <#if usernameError??>
                    <div class="invalid-feedback">
                        ${usernameError}
                    </div>
                </#if>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label" for="password">Password:</label>
            <div class="col-sm-5 my-1">
                <input type="password" name="password" class="form-control ${(passwordError??)?string('is-invalid','')}"
                       placeholder="password" id="password"/>
                <#if passwordError??>
                    <div class="invalid-feedback">
                        ${passwordError}
                    </div>
                </#if>
            </div>
        </div>
        <#if isRegisterForm>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label" for="password2">Password:</label>
                <div class="col-sm-5 my-1">
                    <input type="password" name="password2"
                           class="form-control ${(password2Error??)?string('is-invalid','')}"
                           placeholder="retype password" id="password2"/>
                    <#if password2Error??>
                        <div class="invalid-feedback">
                            ${password2Error}
                        </div>
                    </#if>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label" for="email">Email:</label>
                <div class="col-sm-5 my-1">
                    <input type="email" name="email" class="form-control ${(emailError??)?string('is-invalid','')}"
                           placeholder="email@email.com" id="email" value="<#if userFromSession??>${userFromSession.email}</#if>"/>
                    <#if emailError??>
                        <div class="invalid-feedback">
                            ${emailError}
                        </div>
                    </#if>
                </div>
            </div>
        </#if>
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
        <button type="submit" class="btn btn-outline-info"><#if !userFromSession??>Sing In <#else>Sing Out</#if></button>
    </form>
</#macro>