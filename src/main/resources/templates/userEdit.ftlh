<#import "parts/common.ftlh" as c>
<@c.page>
    <form class="py-2" action="/admin" method="post">
        <div class="form-group row">
            <div class="col-sm-2">
                Username
            </div>
            <div class="col-sm-3">
                ${user.username}
            </div>
        </div>
        <div class="form-group row">
            <div class="col-sm-2">
                User Role
            </div>
            <div class="col-sm-3">
                <#list roles as role>
                    <div class="form-check">
                        <input type="checkbox" class="form-check-input" id="exampleCheck" name="${role}"
                                ${user.roles?seq_contains(role)?string("checked", "")}>
                        <label class="form-check-label" for="exampleCheck">${role}</label>
                    </div>
                </#list>
            </div>
        </div>
        <label>
            <input type="hidden" name="userId" value="${user.id}">
        </label>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button type="submit" class="btn btn-outline-info">Save</button>
    </form>
</@c.page>