<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>
<@c.page>
    <div>This is main page!!</div>
    <div>
        <@l.logout />
        <span>
            <a href="/user">User List</a>
        </span>
    </div>
    <div>Add new message</div>
    <div>
        <form method="post" enctype="multipart/form-data">
            <label>
                <input type="text" name="text" placeholder="Message"/>
            </label>
            <label>
                <input type="text" name="tag" placeholder="Tag"/>
            </label>
            <div>
                <input type="file" name="file">
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <button type="submit">Add new message</button>
        </form>
    </div>
    <div>Search messages</div>
    <div>
        <form method="get" action="/main">
            <label>
                <input type="text" name="search" placeholder="Search" value="${search!}"/>
            </label>
            <button type="submit">Find</button>
        </form>
    </div>
    <div>List of messages</div>
    <#list messages as message>
        <div>
            <b>${message.id}</b>
            <span>${message.text}</span>
            <i>${message.tag}</i>
            <strong>${message.authorName}</strong>
            <div>
                <#if message.filename??>
                    <img src="/img/${message.filename}">
                </#if>
            </div>
        </div>
    <#else>No message
    </#list>
</@c.page>