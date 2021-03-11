<#import "parts/common.ftlh" as c>
<@c.page>
    <div class="form-group">
        <form action="/main/update/${message.id}" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label for="topicName">Topic Name</label>
                <textarea type="text" class="form-control ${(topicNameError??)?string('is-invalid','')}"
                          name="topicName" placeholder="Topic Name" id="topicName" rows="1"
                          style="overflow: hidden">
                        <#if message??>${message.topicName}</#if>
                    </textarea>
                <#if topicNameError??>
                    <div class="invalid-feedback">
                        ${topicNameError}
                    </div>
                </#if>
            </div>
            <div class="form-group">
                <label for="text">Text</label>
                <textarea type="text" class="form-control ${(textError??)?string('is-invalid','')}"
                          name="text" placeholder="Text" id="text" rows="4" style="overflow: hidden">
                        <#if message??>${message.text}</#if>
                    </textarea>
                <#if textError??>
                    <div class="invalid-feedback">
                        ${textError}
                    </div>
                </#if>
            </div>
            <div class="form-group">
                <label for="tag">Tag</label>
                <input type="text" class="form-control ${(tagError??)?string('is-invalid','')}"
                       value="<#if message??>${message.tag}</#if>" name="tag" id="tag"/>
                <#if tagError??>
                    <div class="invalid-feedback">
                        ${tagError}
                    </div>
                </#if>
            </div>
            <div class="form-group">
                <div class="custom-file">
                    <label <#--class="custom-file-label"--> for="customFile">Picture</label>
                    <input type="file" name="file" class="form-control-file" id="customFile">
                </div>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <button type="submit" class="btn btn-outline-info">Update</button>
        </form>
    </div>
</@c.page>