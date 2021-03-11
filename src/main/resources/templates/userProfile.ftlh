<#import "parts/common.ftlh" as c>
<@c.page>
    <div class="d-flex p-2">
        <div class="d-flex flex-row">
            <div class="p-2">
                <img src="/img/${user.pictureName}" width="300" height="300" class="img-fluid">
            </div>
        </div>
        <div class="d-flex flex-column">
            <div class="d-flex flex-row">
                <div class="p-2" style="width: 100px">Name</div>
                <div class="p-2">${user.username}</div>
            </div>
            <div class="d-flex flex-row">
                <div class="p-2" style="width: 100px">EMail</div>
                <div class="p-2">${user.email}</div>
            </div>
            <div class="d-flex flex-row">
                <div class="p-2" style="width: 100px">Phone Number</div>
                <div class="p-2">${user.phoneNumber}</div>
            </div>
        </div>
    </div>
    <#if Session.SPRING_SECURITY_CONTEXT.authentication.principal.getUsername()==user.username>
        <div>
            <form>
                <input class="btn btn-outline-info ml-3" type="button" value="Edit"
                       onClick='location.href="/user/userPageEdit/${user.id}"'>
            </form>
        </div>
    </#if>
</@c.page>}