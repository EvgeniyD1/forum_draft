<#import "parts/common.ftlh" as c>
<@c.page>
<div class="form-group">
    <form action="/main/update/${message.id}" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <label for="topicName">topicName</label>
            <input type="text" class="form-control" name="topicName" id="topicName" value="${message.topicName}">
        </div>
        <div class="form-group">
            <label for="text">text</label>
            <input type="text" class="form-control" name="text" id="text" value="${message.text}">
        </div>
        <div class="form-group">
            <label for="tag">tag</label>
            <input type="text" class="form-control" name="tag" id="tag" value="${message.tag}">
        </div>
        <div class="form-group">
            <label for="customFile">picture</label>
            <div class="custom-file">
                <input type="file" name="file" id="customFile">
                <label class="custom-file-label" for="customFile">Choose file</label>
            </div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button type="submit" class="btn btn-outline-info">Update</button>
    </form>
</div>


</@c.page>