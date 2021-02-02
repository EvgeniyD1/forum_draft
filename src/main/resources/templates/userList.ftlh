<#import "parts/common.ftlh" as c>
<@c.page>
    <div>List of Users</div>
    <table>
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
    </table>
</@c.page>