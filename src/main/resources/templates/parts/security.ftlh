<#assign session = Session.SPRING_SECURITY_CONTEXT??>
<#if session>
    <#assign
        userFromSession = Session.SPRING_SECURITY_CONTEXT.authentication.principal
        name = userFromSession.getUsername()
        isAdmin = userFromSession.isAdmin()
        cerrentUser = userFromSession.getId()
    >
<#else>
    <#assign
        isAdmin = false
        cerrentUser = -1
    >
</#if>
