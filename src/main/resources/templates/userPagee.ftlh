<#import "parts/common.ftlh" as c>
<@c.page>
    <div>User Page</div>
    <div class="form-inline">

    </div>
    <table>
        <thead>
        <rt>Name</rt>
        </thead>
        <tbody>
        <td>${user.username}</td>
        </tbody>
    </table>
    <button type="submit">Update</button>
</@c.page>}