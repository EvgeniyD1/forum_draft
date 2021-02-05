<#import "parts/common.ftlh" as c>
<@c.page>
    <div>User Editor</div>
    <form class="py-2" action="/admin" method="post">
        <#--<label>
            <input type="text" name="username" value="${user.username}">
        </label>-->
        <div class="form-group">
            <label for="exampleInputUsername">Username</label>
            <input type="text" class="form-control" id="exampleInputUsername" name="username" value="${user.username}">
        </div>
        <div class="form-group">
            <label>User Role</label>
            <#list roles as role>
            <#--<div>
                <label>
                    <input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>
                    ${role}
                </label>
            </div>-->
                <div class="form-check">
                    <input type="checkbox" class="form-check-input" id="exampleCheck" name="${role}"
                            ${user.roles?seq_contains(role)?string("checked", "")}>
                    <label class="form-check-label" for="exampleCheck">${role}</label>
                </div>
            </#list>
        </div>
        <label>
            <input type="hidden" name="userId" value="${user.id}">
        </label>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <#--<button type="submit">Save</button>-->
        <button type="submit" class="btn btn-outline-info">Save</button>
    </form>
</@c.page>