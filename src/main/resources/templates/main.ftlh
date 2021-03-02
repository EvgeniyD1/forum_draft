<#import "parts/common.ftlh" as c>
<#include "parts/security.ftlh">
<@c.page>
    <a class="btn btn-outline-info mx-3 mb-2" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false"
       aria-controls="collapseExample">
        Add new message
    </a>
    <div class="collapse mx-3" id="collapseExample">
        <div class="form-group my-3">
            <form method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <input type="text" class="form-control" name="topicName" placeholder="Topic"/>
                </div>
                <div class="form-group">
                    <input type="text" class="form-control" name="text" placeholder="Message"/>
                </div>
                <div class="form-group">
                    <input type="text" class="form-control" name="tag" placeholder="Tag"/>
                </div>
                <div class="form-group">
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


<#--<#list messages as message>
    <div class="card mb-2">
        <div class="row no-gutters">
            <div class="col-md-4 py-1 px-1">
                <#if message.filename??>
                    <img src="/img/${message.filename}" class="card-img" alt="" width="120" height="220"
                         style="object-fit: cover">
                <#else >
                    <img src="/static/logo-2.png" class="card-img" alt="" width="120" height="220"
                         style="object-fit: cover">
                </#if>
            </div>
            <div class="col-md-8">
                <div class="card-body">
                    <a class="card-title" href="/topic/${message.id}">${message.topicName}</a>
                    <p class="card-text">${message.text}</p>
                    <p class="card-text">${message.tag}</p>
                    <p class="card-footer text-muted">${message.authorName}</p>
                </div>
            </div>
        </div>
        &lt;#&ndash;<p class="card-footer text-muted">${message.authorName}</p>&ndash;&gt;
    </div>
<#else>No message
</#list>-->

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
                        <#if name == message.authorName>
                            <br><a href="/main/update/${message.id}" class="badge badge-info">update</a>
                        </#if>
                    </div>
                </div>
            </div>

        <#else>No message
        </#list>
    </div>
</@c.page>