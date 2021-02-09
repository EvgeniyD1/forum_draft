<#import "parts/common.ftlh" as c>
<@c.page>
    <div class="d-flex p-2">
        <div class="d-flex flex-column">
            <div class="p-2 bd-highlight">
                ${message.topicName}
            </div>
            <div class="p-2 bd-highlight">
                ${message.tag}
            </div>
            <div class="p-2 bd-highlight">
                ${message.text}
            </div>
        </div>
    </div>
</@c.page>