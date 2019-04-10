<#include "header.ftl">


<div id="main">
    <div class="container" id="daily">
        <div class="jscroll-inner">
            <div class="daily">

           <#assign  cur_date =''/>
           <#list news as new >
            <#if cur_date != new.createdDate?string("yyyy-MM-dd")>
                 <#if new_index gt 0 >
                      </div> <#--   上一个要收尾 -->
                 </#if>
                <#assign  cur_date =new.createdDate?string("yyyy-MM-dd")/>
            <h3 class="date">
                <i class="fa icon-calendar"></i>
                <span>美食资讯 &nbsp; ${new.createdDate?string("yyyy-MM-dd")}</span>
            </h3>
            <div class="posts">
            </#if>
                 <div class="post">
                    <div class="votebar">
                        <#if  new.likeCount gt 0 >
                        <button class="click-like up pressed" data-id="${new.id!}" title="赞同"><i class="vote-arrow"></i><span class="count">${new.likeCount!}</span></button>
                        <#else>
                        <button class="click-like up" data-id="${new.id!}" title="赞同"><i class="vote-arrow"></i><span class="count">${new.likeCount!}</span></button>
                        </#if>
                        <#if new.likeCount < 0>
                        <button class="click-dislike down pressed" data-id="${new.id!}" title="反对"><i class="vote-arrow"></i></button>
                        <#else>
                        <button class="click-dislike down" data-id="${new.id!}" title="反对"><i class="vote-arrow"></i></button>
                        </#if>
                    </div>
                    <div class="content">
                        <div >
                            <img class="content-img" src="${new.image!}" alt="">
                        </div>
                        <div class="content-main">
                            <h3 class="title">
                                <a target="_blank" rel="external nofollow" href="${contextPath}/news/${new.id!}">${new.title!}</a>
                            </h3>
                            <div class="meta">
                                ${new.link!}
                                <span>
                                            <i class="fa icon-comment"></i> ${new.commentCount!}
                                        </span>
                            </div>
                        </div>
                    </div>
                    <div class="user-info">
                        <div class="user-avatar">
                            <a href="${contextPath!}/user/$!{new.user.id}/"><img width="32" class="img-circle" src="${contextPath}/${new.user.headUrl}"></a>
                        </div>


                    </div>

                    <div class="subject-name">来自 <a href="${contextPath!}/user/${new.user.id}/">${new.user.username}</a></div>
                </div>


              <#if new_index == news?size >
                 </div>  <#--最后有个元素要收尾 -->
              </#if>

            </#list>


        </div>
    </div>
</div>

</div>


<#if pop??>
<script>
    window.loginpop = ${(pop==0)?c};
</script>
</#if>

<#include "footer.ftl">