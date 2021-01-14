<#include "security.ftlh">
<#import "login.ftlh" as l>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm">
    <a class="navbar-brand my-0 mx-3 h1" href="/">
        <img src="/static/logo.png" width="100" height="50" class="d-inline-block align-middle mx-2"
             alt="" loading="lazy">
        <span class="text-white-50 align-middle">Forum_Draft</span>
    </a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="/">Home</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/main">Main</a>
            </li>
            <#if isAdmin>
                <li class="nav-item">
                    <a class="nav-link" href="/user">User List</a>
                </li>
            </#if>
        </ul>
        <span class="navbar-text mx-3 ml-auto">${name}</span>
        <span class="mx-3"><@l.logout /></span>
    </div>
</nav>