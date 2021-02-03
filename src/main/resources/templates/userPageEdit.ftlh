<#import "parts/common.ftlh" as c>
<@c.page>
    <div>User Editor</div>
    <form action="/user" method="post">
        <label>
            <input type="text" name="username" value="${user.username}">
        </label>
        <label>
            <input type="hidden" name="userId" value="${user.id}">
        </label>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button type="submit">Save</button>
    </form>
</@c.page>