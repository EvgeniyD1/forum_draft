<#import "parts/common.ftlh" as c>
<@c.page>
    <div>${type}</div>
    <h3>${userChannel.username}</h3>
    <ul class="list-group">
        <#list users as userL>
            <li class="list-group-item">
                <a href="/user/${userL.id}">${userL.username}</a>
            </li>
        </#list>
    </ul>
</@c.page>