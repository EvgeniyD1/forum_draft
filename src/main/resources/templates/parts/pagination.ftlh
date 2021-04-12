<#macro pagination url page>
    <div>
        <#if page.getTotalPages() gt 7>
            <#assign
            totalPages = page.getTotalPages()
            pageNumber = page.getNumber() + 1

            first = (pageNumber > 4)?then([1,-1], [1,2,3])
            last = (pageNumber < totalPages - 3)?then([-1,totalPages],[totalPages-2, totalPages-1, totalPages])

            beforeCurrent = (pageNumber > 4 && pageNumber < totalPages-1)?then([pageNumber-2,pageNumber-1],[])
            afterCurrent = (pageNumber > 2 && pageNumber < totalPages-3)?then([pageNumber+1,pageNumber+2],[])

            current = first + beforeCurrent + (pageNumber > 3 && pageNumber < totalPages-2)?then([pageNumber],[]) + afterCurrent + last
            />
        <#else>
            <#assign
            current = 1..page.getTotalPages()
            />
        </#if>
        <nav aria-label="Page navigation example">
            <ul class="pagination justify-content-center">
                <li class="page-item disabled">
                    <a class="page-link" href="#" tabindex="-1">Page</a>
                </li>
                <#list current as p>
                    <#if (p-1)==page.getNumber()>
                        <li class="page-item active">
                            <a class="page-link" href="#">${p}</a>
                        </li>
                    <#elseif p==-1>
                        <li class="page-item disabled">
                            <a class="page-link" href="#">...</a>
                        </li>
                    <#else >
                        <li class="page-item">
                            <a class="page-link" href="${url}?page=${p-1}&amp;size=${page.getSize()}">${p}</a>
                        </li>
                    </#if>
                </#list>
            </ul>
            <ul class="pagination justify-content-center">
                <li class="page-item disabled">
                    <a class="page-link" href="#" tabindex="-1">Elements</a>
                </li>
                <#list [6, 9, 12, 15] as e>
                    <#if e==page.getSize()>
                        <li class="page-item active">
                            <a class="page-link" href="#">${e}</a>
                        </li>
                    <#else >
                        <li class="page-item">
                            <a class="page-link" href="${url}?page=${page.getNumber()}&amp;size=${e}">${e}</a>
                        </li>
                    </#if>
                </#list>
            </ul>
        </nav>
    </div>
</#macro>