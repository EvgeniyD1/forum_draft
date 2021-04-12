<#include "security.ftlh">
<#import "login.ftlh" as l>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
    <a class="navbar-brand my-0 h1" href="/">
        <img src="/static/logo-2.png" width="30" height="30" class="d-inline-block align-content-center"
             alt="" loading="lazy"/>
        <span class="text-white-50">Forum_Draft</span>
    </a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
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
            <#if userFromSession??>
                <li class="nav-item">
                    <a class="nav-link" href="/main/sub/${cerrentUser}">My News</a>
                </li>
            </#if>
            <#if isAdmin>
                <li class="nav-item">
                    <a class="nav-link" href="/admin">Admin Page</a>
                </li>
            </#if>
            <li class="nav-item">
                <form class="form-inline ml-auto" method="get" action="/main">
                    <input type="search" name="search" class="form-control ml-sm-2" placeholder="Search"
                           value="${search!}"/>
                    <button type="submit" class="btn btn-outline-info ml-2">Search</button>
                </form>
            </li>
        </ul>
        <ul class="navbar-nav ml-auto">
            <li>
                <#if !name??>
                    <#--<a class="nav-link">unknown user</a>-->
                <#else>
                    <a class="nav-link" href="/user/${cerrentUser}">${name}</a>
                </#if>
            </li>
            <li>
                <#if !name??>
                <#else>
                    <img src="/img/${userFromSession.pictureName}" width="30" height="30" class="rounded-circle mt-1 mr-2 "
                         style="object-fit: cover" alt=""/>
                </#if>
            </li>
            <li>
                <@l.logout />
            </li>
        </ul>
    </div>
</nav>