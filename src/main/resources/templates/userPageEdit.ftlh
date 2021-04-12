<#import "parts/common.ftlh" as c>
<#include "parts/security.ftlh">
<@c.page>
    <div id="user-editor">
        <#if cerrentUser!=user.id>
            <b>Not authorised</b>
        <#else >
            <div>User Editor</div>
            <div class="form-group">
                <form action="/user" method="post" enctype="multipart/form-data">
                    <div class="form-group">
                        <label for="username">username</label>
                        <input type="text" class="form-control" name="username" id="username" value="${user.username}">
                    </div>
                    <div class="form-group">
                        <label for="email">email</label>
                        <input type="text" class="form-control" name="email" id="email" value="${user.email}">
                    </div>
                    <div class="form-group">
                        <label for="phoneNumber">phone number</label>
                        <input type="text" class="form-control" name="phoneNumber" id="phoneNumber"
                               value="${user.phoneNumber}">
                    </div>
                    <div class="form-group">
                        <label for="customFile">picture</label>
                        <div class="custom-file">
                            <input type="file" name="file" id="customFile">
                            <label class="custom-file-label" for="customFile">Choose file</label>
                        </div>
                    </div>
                    <label>
                        <input type="hidden" name="userId" value="${user.id}">
                    </label>
                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                    <button type="submit" class="btn btn-outline-info">Save</button>
                </form>
            </div>
        </#if>
    </div>
</@c.page>