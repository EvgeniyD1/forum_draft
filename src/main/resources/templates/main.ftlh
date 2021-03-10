<#import "parts/common.ftlh" as c>
<#include "parts/security.ftlh">
<@c.page>
    <#if isAdmin==true>
        <a class="btn btn-outline-info mx-3 mb-2" data-toggle="collapse" href="#collapseExample" role="button"
           aria-expanded="false"
           aria-controls="collapseExample">
            Add new message
        </a>
    </#if>
    <div class="collapse <#if message??>show</#if> mx-3" id="collapseExample">
        <div class="form-group my-3">
            <form method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <#--<label for="topicName">topicName</label>-->
                    <input type="text" class="form-control ${(topicNameError??)?string('is-invalid','')}"
                           value="<#if message??>${message.topicName}</#if>" name="topicName" placeholder="Topic"
                           id="topicName"/>
                    <#if topicNameError??>
                        <div class="invalid-feedback">
                            ${topicNameError}
                        </div>
                    </#if>
                </div>
                <div class="form-group">
                    <#--<label for="text">text</label>-->
                    <input type="text" class="form-control ${(textError??)?string('is-invalid','')}"
                           value="<#if message??>${message.text}</#if>" name="text" placeholder="Message"
                           id="text"/>
                    <#if textError??>
                        <div class="invalid-feedback">
                            ${textError}
                        </div>
                    </#if>
                </div>
                <div class="form-group">
                    <#--<label for="tag">tag</label>-->
                    <input type="text" class="form-control ${(tagError??)?string('is-invalid','')}"
                           value="<#if message??>${message.tag}</#if>" name="tag" placeholder="Tag"
                           id="tag"/>
                    <#if tagError??>
                        <div class="invalid-feedback">
                            ${tagError}
                        </div>
                    </#if>
                </div>
                <div class="form-group">
                    <#--<label for="customFile">picture</label>-->
                    <div class="custom-file">
                        <input type="file" name="file" id="customFile">
                        <label class="custom-file-label" for="customFile">Choose file</label>
                    </div>
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}">
                <div class="form-group">
                    <button type="submit" class="btn btn-outline-info">Add</button>
                </div>
            </form>
        </div>
    </div>

    <div class="row row-cols-1 row-cols-md-3 mx-0">
        <#list messages as message>
            <div class="col my-2">
                <div class="card h-100 border-light">
                    <#if message.filename??>
                        <img src="/img/${message.filename}" class="card-img" alt="" height="190"
                             style="object-fit: cover">
                    <#else >
                        <img src="/static/logo-2.png" class="card-img" alt="" height="190"
                                <#--style="object-fit: cover-->>
                    </#if>
                    <div class="card-body py-1 px-1">
                        <h5 class="card-title my-0<#--text-truncate-->">
                            <a class="text-muted " href="/topic/${message.id}">${message.topicName}</a>
                        </h5>
                        <p class="card-text style-text-height text-muted">${message.text}</p>
                    </div>
                    <div class="card-footer border-light">
                        <small class="text-muted">${message.time}</small>
                        <small class="text-muted">${message.authorName}</small>
                        <small class="text-muted"> #${message.tag}</small>
                        <#if name??>
                            <#if name == message.authorName>
                                <br><a href="/main/update/${message.id}" class="badge badge-info">update</a>
                            </#if>
                        </#if>
                    </div>
                </div>
            </div>

        <#else>No message
        </#list>
    </div>
</@c.page>