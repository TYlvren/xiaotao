<#include "header.ftl">
<style>
    .push_button {
        position: relative;
        width: auto;
        height: auto;
        text-align: center;
        color: #FFF;
        text-decoration: none;
        line-height: 20px;
        font-family: 'Oswald', Helvetica;
        display: block;
    }

    .push_button:before {
        background: #f0f0f0;
        background-image: -webkit-gradient(linear, 0% 0%, 0% 100%, from(#D0D0D0), to(#f0f0f0));

        -webkit-border-radius: 5px;
        -moz-border-radius: 5px;
        border-radius: 5px;

        -webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, .5) inset, 0 1px 0 #FFF;
        -moz-box-shadow: 0 1px 2px rgba(0, 0, 0, .5) inset, 0 1px 0 #FFF;
        box-shadow: 0 1px 2px rgba(0, 0, 0, .5) inset, 0 1px 0 #FFF;

        position: absolute;
        content: "";
        left: -6px;
        right: -6px;
        top: -6px;
        bottom: -10px;
        z-index: -1;
    }

    .push_button:active {
        -webkit-box-shadow: 0 1px 0 rgba(255, 255, 255, .5) inset, 0 -1px 0 rgba(255, 255, 255, .1) inset;
        top: 5px;
    }

    .push_button:active:before {
        top: -11px;
        bottom: -5px;
        content: "";
    }

    .red {
        text-shadow: -1px -1px 0 #A84155;
        background: #D25068;
        border: 1px solid #D25068;

        background-image: -webkit-linear-gradient(top, #F66C7B, #D25068);
        background-image: -moz-linear-gradient(top, #F66C7B, #D25068);
        background-image: -ms-linear-gradient(top, #F66C7B, #D25068);
        background-image: -o-linear-gradient(top, #F66C7B, #D25068);
        background-image: linear-gradient(to bottom, #F66C7B, #D25068);

        -webkit-border-radius: 5px;
        -moz-border-radius: 5px;
        border-radius: 5px;

        -webkit-box-shadow: 0 1px 0 rgba(255, 255, 255, .5) inset, 0 -1px 0 rgba(255, 255, 255, .1) inset, 0 4px 0 #AD4257, 0 4px 2px rgba(0, 0, 0, .5);
        -moz-box-shadow: 0 1px 0 rgba(255, 255, 255, .5) inset, 0 -1px 0 rgba(255, 255, 255, .1) inset, 0 4px 0 #AD4257, 0 4px 2px rgba(0, 0, 0, .5);
        box-shadow: 0 1px 0 rgba(255, 255, 255, .5) inset, 0 -1px 0 rgba(255, 255, 255, .1) inset, 0 4px 0 #AD4257, 0 4px 2px rgba(0, 0, 0, .5);
    }

    .red:hover {
        background: #F66C7B;
        background-image: -webkit-linear-gradient(top, #D25068, #F66C7B);
        background-image: -moz-linear-gradient(top, #D25068, #F66C7B);
        background-image: -ms-linear-gradient(top, #D25068, #F66C7B);
        background-image: -o-linear-gradient(top, #D25068, #F66C7B);
        background-image: linear-gradient(top, #D25068, #F66C7B);
    }

    .blue {
        text-shadow: -1px -1px 0 #2C7982;
        background: #3EACBA;
        border: 1px solid #379AA4;
        background-image: -webkit-linear-gradient(top, #48C6D4, #3EACBA);
        background-image: -moz-linear-gradient(top, #48C6D4, #3EACBA);
        background-image: -ms-linear-gradient(top, #48C6D4, #3EACBA);
        background-image: -o-linear-gradient(top, #48C6D4, #3EACBA);
        background-image: linear-gradient(top, #48C6D4, #3EACBA);

        -webkit-border-radius: 5px;
        -moz-border-radius: 5px;
        border-radius: 5px;

        -webkit-box-shadow: 0 1px 0 rgba(255, 255, 255, .5) inset, 0 -1px 0 rgba(255, 255, 255, .1) inset, 0 4px 0 #338A94, 0 4px 2px rgba(0, 0, 0, .5);
        -moz-box-shadow: 0 1px 0 rgba(255, 255, 255, .5) inset, 0 -1px 0 rgba(255, 255, 255, .1) inset, 0 4px 0 #338A94, 0 4px 2px rgba(0, 0, 0, .5);
        box-shadow: 0 1px 0 rgba(255, 255, 255, .5) inset, 0 -1px 0 rgba(255, 255, 255, .1) inset, 0 4px 0 #338A94, 0 4px 2px rgba(0, 0, 0, .5);
    }

    .blue:hover {
        background: #48C6D4;
        background-image: -webkit-linear-gradient(top, #3EACBA, #48C6D4);
        background-image: -moz-linear-gradient(top, #3EACBA, #48C6D4);
        background-image: -ms-linear-gradient(top, #3EACBA, #48C6D4);
        background-image: -o-linear-gradient(top, #3EACBA, #48C6D4);
        background-image: linear-gradient(top, #3EACBA, #48C6D4);
    }
</style>

<div id="main">
    <div class="container">
        头像: <img alt="头像" src="${userMessage.headUrl!}"/><br>
        用户名: ${userMessage.username!} <br>

        <#if userMessage.username != user.username>
            <div class="post-comment-form">
                <form method="post" action="${contextPath}/user/tosendmsg">
                    <input name="toName" type="hidden" value="${userMessage.username}">
                    <div class="text-right">
                        <input type="submit" name="commit" value="发送私信" class="btn btn-default btn-info">
                    </div>
                </form>
            </div>
        </#if>
    </div>

    <hr>

    <!--闲置物品展示-->
    <div class="container" id="daily">
        <div class="jscroll-inner">
            <div class="daily">
                <#assign  cur_date =''/>
                <#list page.records as good >
                <#if cur_date != good.createdDate?string("yyyy-MM-dd")>
                <#if good_index gt 0 >
            </div> <#--   上一个要收尾 -->
            </#if>
            <#assign  cur_date =good.createdDate?string("yyyy-MM-dd")/>
            <h3 class="date">
                <i class="fa icon-calendar"></i>
                <#if userMessage.username == user.username>
                    <span>我的分享 &nbsp; ${good.createdDate?string("yyyy-MM-dd")}</span>
                <#else >
                    <span>${userMessage.username!}的分享 &nbsp; ${good.createdDate?string("yyyy-MM-dd")}</span>
                </#if>
            </h3>
            <div class="posts">
                </#if>
                <div class="post">
                    <div class="votebar">
                        <#if  good.like gt 0 >
                            <button class="click-like up pressed" data-id="${good.id!}" title="关注"><i
                                        class="vote-arrow"></i><span class="count">${good.concernCount!}</span></button>
                        <#else>
                            <button class="click-like up" data-id="${good.id!}" title="关注"><i
                                        class="vote-arrow"></i><span class="count">${good.concernCount!}</span></button>
                        </#if>
                        <#if good.like lt 0>
                            <button class="click-dislike down pressed" data-id="${good.id!}" title="取消关注"><i
                                        class="vote-arrow"></i></button>
                        <#else>
                            <button class="click-dislike down" data-id="${good.id!}" title="取消关注"><i
                                        class="vote-arrow"></i></button>
                        </#if>
                    </div>
                    <div class="content">
                        <div>
                            <img class="content-img" src="${good.imageUrl!}" alt="">
                        </div>
                        <div class="content-main">
                            <h3 class="title">
                                <a target="_blank" rel="external nofollow" style="color: #0D6EB8"
                                   href="${contextPath}/goods/${good.id!}">${good.goodsName!}</a>

                                <!--修改物品-->
                                <#if userMessage.username == user.username>
                                    <#if good.sold == 0>
                                        <!--将物品标记为已售表单-->
                                        <form action="${contextPath}/goods/sold" id="sold" method="post" style="margin: 0">
                                            <input hidden name="id" value="${good.id!}"/>
                                            <input hidden name="user.id" value="${good.user.id}">
                                            <button class="push_button blue"
                                                    style="font-size: 15px"
                                                    onclick="if(!confirm('标记为已售他人将不可见，确定吗?')) return false;">
                                                已售
                                            </button>
                                        </form>
                                    <#else>
                                        <font style="color: grey;font-size: 15px" align="center">该物品已标记为已售</font>
                                    </#if>
                                    <!--将物品删除表单-->
                                    <form action="${contextPath}/goods/delete" method="post" style="margin: 1">
                                        <input hidden name="id" value="${good.id!}"/>
                                        <input hidden name="user.id" value="${good.user.id}">
                                        <button class="push_button red"
                                                style="font-size: 15px"
                                                onclick="if(!confirm('删除后将不可恢复，确定吗?')) return false;">
                                            删除
                                        </button>
                                    </form>
                                </#if>
                            </h3>
                            <div class="meta">
                                <p style="color: black">${good.desc!}</p>
                                <span>
                                            <i class="fa icon-comment"></i> ${good.commentCount!}
                                        </span>
                            </div>
                        </div>
                    </div>
                    <div class="user-info">
                        <div class="user-avatar">
                            <a href="${contextPath!}/user/${good.user.id}/"><img width="32" class="img-circle"
                                                                                 src="${good.user.headUrl}">
                            </a>
                        </div>
                    </div>
                    <div class="subject-name">
                        来自
                        <#if user?? && user.id == good.user.id>
                            <a href="${contextPath!}/user/${good.user.id}/">我</a>
                        <#else >
                            <a href="${contextPath!}/user/${good.user.id}/">${good.user.username}</a>
                        </#if>
                        <h6>
                            <p style="color: #aa0000">￥${good.expectedPrice}</p>
                        </h6>
                    </div>
                </div>

                <#if good_index == page.records?size >
            </div> <#--最后有个元素要收尾 -->
            </#if>

            </#list>
        </div>
    </div>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tbody>
        <tr>
            <td width="33%">
                <div align="left">
                <span class="STYLE22">
                    当前第<strong>${page.current }</strong>
                    /<strong>${page.pages}</strong> 页&nbsp;&nbsp;
                    每页显示<strong><#if page.size lte 0>1<#else >${page.size}</#if></strong>条记录，共<strong>${page.total}</strong>条记录
                </span>
                </div>
            </td>
            <td width="67%">
                <table width="312" border="0" align="right" cellpadding="0" cellspacing="0">
                    <tbody>
                    <tr>
                        <td width="49">
                            <div align="center">
											<span class="STYLE22">
											<a href="${contextPath }/user/${userMessage.id}&current=1"
                                               onclick="<#if page.current lte 1>return false</#if>">首页</a>
											</span>
                            </div>
                        </td>


                        <td width="49">
                            <div align="center">
                                <span class="STYLE22">
                                    <a href="${contextPath }/user/${userMessage.id}&current=${page.current-1}"
                                       onclick="<#if page.current lte 1>return false</#if>">上一页</a>
                                </span>
                            </div>
                        </td>


                        <td width="49">
                            <div align="center">
                                <span class="STYLE22">
                                    <a href="${contextPath }/user/${userMessage.id}&current=${page.current+1}"
                                       onclick="<#if page.current gte page.pages>return false</#if>">下一页</a>
                                </span>
                            </div>
                        </td>


                        <td width="49">
                            <div align="center">
                                <span class="STYLE22">
                                    <a href="${contextPath }/user/${userMessage.id}&current=${page.pages }"
                                       onclick="<#if page.current gte page.pages>return false</#if>">尾页</a>
                                </span>
                            </div>
                        </td>

                        <td width="37" class="STYLE22">
                            <div align="center">转到</div>
                        </td>
                        <td width="22">
                            <div align="center">
                                <input type="text" name="num" id="num" value="${page.current }"
                                       style="width:20px; height:12px; font-size:12px; border:solid 1px #7aaebd;"/>
                            </div>
                        </td>
                        <td width="22" class="STYLE22">
                            <div align="center">页</div>
                        </td>
                        <td width="35">
                            <div align="center">
                            <span class="STYLE22">
                                <a style="cursor:pointer;" onclick="jump()">跳转</a>
                            </span>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </td>
        </tr>
        </tbody>
    </table>
</div>

<script>
    function jump() {
        var num = document.getElementById("num").value;
        if (!/^[1-9][0-9]*$/.test(num)) {
            alert("请输入正确的页码");
            return;
        }

        if (num > ${page.pages}) {
            alert("只有${page.pages}页");
            return;
        }

        window.location.href = "${contextPath}/user/${userMessage.id}&current="
            + num;

    }
</script>


<#if pop??>
    <script>
        window.loginpop = ${(pop==0)?c};
    </script>
</#if>

<#include "footer.ftl">
