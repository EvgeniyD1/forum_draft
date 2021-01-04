<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Init page</title>
</head>
<body>
    <div>This is main page!!</div>
    <div>
        <form action="/logout" method="post">
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <input type="submit" value="Sing Out"/>
        </form>
    </div>
    <div>Add new message</div>
    <div>
        <form method="post">
            <input type="text" name="text" placeholder="Message"/>
            <input type="text" name="tag" placeholder="Tag"/>
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <button type="submit">Add new message</button>
        </form>
    </div>
    <div>Search messages</div>
    <div>
        <form method="get" action="/main">
            <input type="text" name="search" placeholder="Search"/>
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
        </div>
    </#list>
</body>
</html>