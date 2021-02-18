<#import "parts/common.ftlh" as c>
<@c.page>
    <div class="card">
        <#if message.filename??>
            <img src="/img/${message.filename}" class="card-img" alt="" height="250"
                 style="object-fit: cover">
        <#else >
            <img src="/static/logo-2.png" class="card-img" alt="" height="250"
                    style="object-fit: cover">
        </#if>
        <div class="card-body py-1 px-1">
            <h5 class="card-title my-0 text-muted">
                ${message.topicName}
            </h5>
            <p class="card-text text-muted">${message.text}</p>
        </div>
        <div class="card-footer border-light">
            <small class="text-muted">${message.time}</small>
            <small class="text-muted">${message.authorName}</small>
            <small class="text-muted"> #${message.tag}</small>
        </div>
    </div>
</@c.page>