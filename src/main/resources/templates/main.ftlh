<#import "parts/common.ftlh" as c>
<@c.page>
    <a class="btn btn-outline-info" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false"
       aria-controls="collapseExample">
        Add new message
    </a>
    <div class="collapse" id="collapseExample">
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
    <div>List of messages</div>
<#--<div class="card&lt;#&ndash;-columns&ndash;&gt;">
    <#list messages as message>
        <div class="card my-3">
            <div class="card-img-top alt=">
                <#if message.filename??>
                    <img src="/img/${message.filename}" width="300" height="300" class="img-fluid">
                </#if>
            </div>
            <div class="m-2">
                <div>
                    <a href="/topic/${message.id}">${message.topicName}</a>
                </div>
                <div>
                    <span>${message.text}</span>
                </div>
                <div>
                    <i>${message.tag}</i>
                </div>
            </div>
            <div class="card-footer text-muted">
                ${message.authorName}
            </div>
        </div>
    <#else>No message
    </#list>
</div>-->
    <#list messages as message>
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
            <#--<p class="card-footer text-muted">${message.authorName}</p>-->
        </div>
    <#else>No message
    </#list>
</@c.page>