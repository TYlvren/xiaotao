<#include "header.ftl">
<div id="main">
    <div class="container">
        头像: <img alt="头像" src="${contextPath}/${user.headUrl!}" width="100px" height="100px"/><br>
        用户名: ${user.username!} <br>

        <!--<ul>
        <li class="js-sendMsg"><a href="javascript:void(0);">发站内信</a></li>
        </ul>-->

    </div>
</div>
<#include "footer.ftl">
