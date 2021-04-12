<#import "parts/common.ftlh" as c>
<#include "parts/security.ftlh">
<@c.page>
    <div class="card" id="message">
        <#if message.filename?? >
            <img src="/img/${message.filename}" class="card-img" alt="" height="250"
                 style="object-fit: cover"/>
        <#else >
            <img src="/static/logo-2.png" class="card-img" alt="" height="250"
                 style="object-fit: cover"/>
        </#if>
        <div class="card-body py-1 px-1">
            <h5 class="card-title my-0 text-muted">${message.topicName}</h5>
            <p class="card-text text-muted">${message.text}</p>
        </div>
        <div class="card-footer border-light">
            <small class="text-muted ml-1">${message.time}</small>
            <small class="text-muted ml-1">
                <a href="/user/${message.author.id}" class="text-secondary">${message.authorName}</a>
            </small>
            <small class="text-muted ml-1">
                <a href="/main?search=${message.tag}" class="text-secondary">#${message.tag}</a>
            </small>
        </div>
    </div>
    <#if name??>
        <a class="btn btn-outline-info my-2" data-toggle="collapse" href="#collapseExample" role="button"
           aria-expanded="false" aria-controls="collapseExample">Add new Comment</a>
    </#if>
    <div class="collapse" id="collapseExample">
        <div class="form-group my-3">
            <form action="/topic/addComment" method="post">
                <div class="form-group">
                    <input type="text" class="form-control" name="text" placeholder="Message"/>
                </div>
                <input type="hidden" name="messageId" value="${message.id}"/>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <div class="form-group">
                    <button type="submit" class="btn btn-outline-info">Add</button>
                </div>
            </form>
        </div>
    </div>
    <div id="comment-list">
        <#if comments??>
            <#list comments as comment>
                <div class="card my-1" data-id="${comment.id}">
                    <div class="card-body">
                        <p class="card-text">${comment.text}</p>
                    </div>
                    <div class="card-footer">
                        <small class="text-muted">id${comment.id}</small>
                        <small class="text-muted">${comment.time}</small>
                        <small class="text-muted">
                            <a href="/user/${comment.author.id}" class="text-secondary">${comment.authorName}</a>
                        </small>
                        <#if comment.parentId??>
                            <small class="text-muted">answer to id${comment.parentId}</small>
                        </#if>
                        <#if name??>
                            <br/><a href="#comment${comment.id}" class="badge badge-info"
                                   data-toggle="collapse">comment</a>
                            <#if name==comment.authorName>
                                <a href="#commentUpdate${comment.id}" class="badge badge-info"
                                   data-toggle="collapse">update</a>
                            </#if>
                        </#if>
                        <div class="collapse" id="comment${comment.id}">
                            <div class="form-group my-3">
                                <form action="/topic/commentingComment" method="post">
                                    <div class="form-group">
                                        <input type="text" class="form-control" name="text" placeholder="Message"/>
                                    </div>
                                    <input type="hidden" name="messageId" value="${message.id}"/>
                                    <input type="hidden" name="parentId" value="${comment.id}"/>
                                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                    <div class="form-group">
                                        <button type="submit" class="btn btn-outline-info">Add</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                        <div class="collapse" id="commentUpdate${comment.id}">
                            <div class="form-group my-3">
                                <form action="/topic/updateComment" method="post">
                                    <div class="form-group">
                                        <input type="text" class="form-control" name="text" value="${comment.text}"/>
                                    </div>
                                    <input type="hidden" name="messageId" value="${message.id}"/>
                                    <input type="hidden" name="commentId" value="${comment.id}"/>
                                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                    <div class="form-group">
                                        <button type="submit" class="btn btn-outline-info">Update</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            <#else>
                <br/><p>No comments</p>
            </#list>
        </#if>
    </div>

</@c.page>