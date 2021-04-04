<#import "parts/common.ftlh" as c>
<#include "parts/security.ftlh">
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
                <input class="btn btn-outline-info ml-3 mb-2" type="button" value="Edit"
                       onClick='location.href="/user/userPageEdit/${user.id}"'>
            </form>
        </div>
    </#if>
    <#list messages as message>
        <div class="card my-3">
            <#if message.filename?? >
                <img src="/img/${message.filename}" class="card-img" alt="" height="250"
                     style="object-fit: cover">
            <#else >
                <img src="/static/logo-2.png" class="card-img" alt="" height="250"
                     style="object-fit: cover">
            </#if>
            <div class="card-body py-1 px-1">
                <h5 class="card-title my-0<#--text-truncate-->">
                    <a class="text-muted " href="/topic/${message.id}">${message.topicName}</a>
                </h5>
                <p class="card-text style-text-height text-muted">${message.text}</p>
            </div>
            <div class="card-footer border-light">
                <small class="text-muted">${message.time}</small>
                <small class="text-muted">
                    <a href="/user/${message.author.id}" class="text-secondary"> ${message.authorName}</a>
                </small>
                <small class="text-muted">
                    <a href="/main?search=${message.tag}" class="text-secondary">#${message.tag}</a>
                </small>
                <#if name??>
                    <#if name == message.authorName>
                        <a href="/main/update/${message.id}" class="badge badge-info">update</a>
                    </#if>
                </#if>
            </div>
        </div>
    <#else>No message
    </#list>
</@c.page>