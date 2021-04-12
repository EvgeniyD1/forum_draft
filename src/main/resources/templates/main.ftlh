<#import "parts/common.ftlh" as c>
<#include "parts/security.ftlh">
<#import "parts/pagination.ftlh" as p>
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
            <form method="post" action="/main" enctype="multipart/form-data">
                <div class="form-group">
                    <label for="topicName">Topic Name</label>
                    <textarea type="text" class="form-control ${(topicNameError??)?string('is-invalid','')}"
                              name="topicName" placeholder="Topic Name" id="topicName" rows="1"
                              style="overflow: hidden"><#if message??>${message.topicName}</#if></textarea>
                    <#if topicNameError??>
                        <div class="invalid-feedback">
                            ${topicNameError}
                        </div>
                    </#if>
                </div>
                <div class="form-group">
                    <label for="text">Text</label>
                    <textarea type="text" class="form-control ${(textError??)?string('is-invalid','')}" name="text"
                              placeholder="Text" id="text" rows="4" style="overflow: hidden"><#if message??>${message.text}</#if></textarea>
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
                        <label for="customFile">Picture</label>
                        <input type="file" name="file" class="form-control-file" id="customFile"/>
                    </div>
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <div class="form-group">
                    <button type="submit" class="btn btn-outline-info">Add</button>
                </div>
            </form>
        </div>
    </div>
    <div class="row row-cols-1 row-cols-md-3 mx-0" id="message-list">
        <#list messages.content as message>
            <div class="col my-2" data-id="${message.id}">
                <div class="card h-100 border-light" <#--data-id="${message.id}"-->>
                    <#if message.filename??>
                        <img src="/img/${message.filename}" class="card-img" alt="" height="190"
                             style="object-fit: cover"/>
                    <#else >
                        <img src="/static/logo-2.png" class="card-img" alt="" height="190"/>
                    </#if>
                    <div class="card-body py-1 px-1" id="card-body">
                        <h5 class="card-title my-0" id="topic-name">
                            <a class="text-muted " href="/topic/${message.id}">${message.topicName}</a>
                        </h5>
                        <p class="card-text style-text-height text-muted" id="card-text">${message.text}</p>
                    </div>
                    <div class="card-footer border-light" id="card-footer">
                        <small class="text-muted" id="time">${message.time}</small>
                        <small class="text-muted" id="author">
                            <a href="/user/${message.author.id}" class="text-secondary">${message.authorName}</a>
                        </small>
                        <small class="text-muted" id="tag">
                            <a href="/main?search=${message.tag}" class="text-secondary">#${message.tag}</a>
                        </small>
                        <#if name??>
                            <#if name == message.authorName>
                                <br/><a href="/main/update/${message.id}" class="badge badge-info">update</a>
                            </#if>
                        </#if>
                    </div>
                </div>
            </div>
        <#else>
            <div class="ml-3">
                <p>No message</p>
            </div>
        </#list>
    </div>
    <#if messages.content?size!=0>
        <@p.pagination url messages/>
    </#if>
</@c.page>