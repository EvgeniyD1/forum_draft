<#import "parts/common.ftlh" as c>
<@c.page>
    <div class="form-group">
        <form action="/main/update/${message.id}" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label for="topicName">topicName</label>
                <input type="text" class="form-control ${(topicNameError??)?string('is-invalid','')}"
                       name="topicName" id="topicName" value="${message.topicName}">
                <#if topicNameError??>
                    <div class="invalid-feedback">
                        ${topicNameError}
                    </div>
                </#if>
            </div>
            <div class="form-group">
                <label for="text">text</label>
                <input type="text" class="form-control ${(textError??)?string('is-invalid','')}"
                       name="text" id="text" value="${message.text}">
                <#if textError??>
                    <div class="invalid-feedback">
                        ${textError}
                    </div>
                </#if>
            </div>
            <div class="form-group">
                <label for="tag">tag</label>
                <input type="text" class="form-control ${(tagError??)?string('is-invalid','')}"
                       name="tag" id="tag" value="${message.tag}">
                <#if tagError??>
                    <div class="invalid-feedback">
                        ${tagError}
                    </div>
                </#if>
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