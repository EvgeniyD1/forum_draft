<#import "parts/common.ftlh" as c>
<@c.page>
    <div class="ml-2 mb-2">List of Users</div>
    <table class="table">
        <thead class="thead-light">
        <tr>
            <th scope="col">#</th>
            <th scope="col">Username</th>
            <th scope="col">Email</th>
            <th scope="col">Phone Number</th>
            <th scope="col">Roles</th>
            <th scope="col">Action</th>
        </tr>
        </thead>
        <tbody>
        <#list users as user>
            <tr>
                <th scope="row">${user.id}</th>
                <td>${user.username}</td>
                <td>${user.email}</td>
                <td>${user.phoneNumber}</td>
                <td>
                    <#list user.roles as role>
                        ${role}
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