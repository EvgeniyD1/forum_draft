<#import "parts/common.ftlh" as c>
<#include "parts/security.ftlh">
<@c.page>
    <div class="card border-light mb-3" style="max-width: 100%">
        <div class="row no-gutters">
            <div class="col-md-5">
                <img src="/img/${user.pictureName}" class="card-img" alt="" height="250"
                     style="object-fit: cover">
            </div>
            <div class="col-md-7">
                <div class="card-body">
                    <div class="row">
                        <div class="p-2" style="width: 100px">Name</div>
                        <div class="p-2">${user.username}</div>
                    </div>
                    <div class="row">
                        <div class="p-2" style="width: 100px">EMail</div>
                        <div class="p-2">${user.email}</div>
                    </div>
                    <div class="row">
                        <div class="p-2" style="width: 100px">Phone Number</div>
                        <div class="p-2">${user.phoneNumber}</div>
                    </div>
                    <div class="row">
                        <#if cerrentUser==user.getId()>
                            <div class="p-2">
                                <form>
                                    <input class="btn btn-outline-info" type="button" value="Edit"
                                           onClick='location.href="/user/userPageEdit/${user.id}"'>
                                </form>
                            </div>
                        </#if>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col">
            <div class="card" style="height: 120px">
                <div class="card-body">
                    <div class="card-title">Subscribers</div>
                    <h3 class="card-text">
                        <a href="/user/subscribers/${user.id}">${subscribersCount}</a>
                        <#if cerrentUser!=user.getId()>
                            <#if isSubscriber>
                                <a class="btn btn btn-outline-info" href="/user/unsubscribe/${user.id}">Unsubscribe</a>
                            <#else>
                                <a class="btn btn btn-outline-info" href="/user/subscribe/${user.id}">Subscribe</a>
                            </#if>
                        </#if>
                    </h3>
                </div>
            </div>
        </div>
        <div class="col">
            <div class="card" style="height: 120px">
                <div class="card-body">
                    <div class="card-title">Subscriptions</div>
                    <h3 class="card-text">
                        <a href="/user/subscriptions/${user.id}">${subscriptionsCount}</a>
                    </h3>
                </div>
            </div>
        </div>
    </div>

    <#list messages?sort_by("time")?reverse as message>
        <div class="card my-3">
            <#if message.filename?? >
                <img src="/img/${message.filename}" class="card-img" alt="" height="250"
                     style="object-fit: cover">
            <#else >
                <img src="/static/logo-2.png" class="card-img" alt="" height="250"
                     style="object-fit: cover">
            </#if>
            <div class="card-body py-1 px-1">
                <h5 class="card-title my-0">
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