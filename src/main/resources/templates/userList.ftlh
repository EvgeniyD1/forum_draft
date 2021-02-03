<#import "parts/common.ftlh" as c>
<@c.page>
    <div class="ml-2">List of Users</div>
    <#--<table>
        <thead>
        <tr>Name</tr>
        <tr>Role</tr>
        <tr></tr>
        </thead>
        <tbody>
        <#list users as user>
            <tr>
                <td>${user.username}</td>
                <td>
                    <#list user.roles as role>
                        ${role}<#sep>,
                    </#list>
                </td>
                <td>
                    <a href="/admin/${user.id}">Edit</a>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>-->
    <div class="d-flex p-2">
        <div class="d-flex flex-column">
            <div class="d-flex flex-row">
                <div class="bd-highlight" style="width: 100px">Name</div>
                <div class="bd-highlight" style="width: 100px">Role</div>
            </div>
            <#list users as user>
                <div class="d-flex flex-row">
                    <div class="bd-highlight" style="width: 100px">${user.username}</div>
                    <div class="bd-highlight" style="width: 100px">
                        <#list user.roles as role>
                            ${role}<#sep>,
                        </#list>
                    </div>
                    <a class="ml-3" href="/admin/${user.id}">Edit</a>
                </div>
            </#list>
        </div>
    </div>
</@c.page>