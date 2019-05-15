<#include "header.ftl">
<div id="main">

    <!--类别展示-->
    <link href="${contextPath}/styles/zzsc.css" type="text/css" rel="stylesheet"/>
    <#--<script src="${contextPath}/scripts/category/jquery.min.js"></script>-->
    <script>
        $(function () {
            $(".left_nav dd").hover(function () {
                $(".nav_right", this).show();
            });
            $(".left_nav dd").mouseleave(function () {
                $(".nav_right", this).hide();
            });
        });
    </script>
    <!-- 代码begin -->
    <div id="templatemo_search">
        <form action="${contextPath! }/fuzzySearch" method="get">
            <input type="text" value="${goods_name! }" name="goodsName" id="keyword" title="keyword"
                   onfocus="clearText(this)" onblur="clearText(this)" class="txt_field"/>
            <input type="submit" name="Search" value=" " alt="Search" id="searchbutton" title="Search"
                   class="sub_btn"/>
        </form>
    </div>
    <div class="left_nav">
        <dl>
            <dt>分类</dt>
            <#--  <dd><a href="javascript:" class="nav_left">玉石</a><div class="nav_right"><a href="javascript:">木雕</a><a href="javascript:">木雕</a><a href="javascript:">木雕</a></div></dd>
              <dd><a href="javascript:" class="nav_left">刺绣</a><div class="nav_right"><a href="javascript:">22222</a></div></dd>
              <dd><a href="javascript:" class="nav_left">玻璃工艺</a><div class="nav_right"><a href="javascript:">3333</a></div></dd>
              <dd><a href="javascript:" class="nav_left">青铜器</a></dd>
              <dd><a href="javascript:" class="nav_left">雕刻</a></dd>
              <dd><a href="javascript:" class="nav_left">金属工艺</a></dd>
              <dd><a href="javascript:" class="nav_left">陶瓷</a></dd>
              <dd><a href="javascript:" class="nav_left">手工绘画</a></dd>
              <dd><a href="javascript:" class="nav_left">特种工艺</a></dd>-->

            <#list categories as category>
                <dd>
                    <#if category_index != 0 && category_index != categories?size - 1>
                        <li>
                            <a href="${contextPath }/categorySearch?cid=${category.id}"
                               class="nav_left">${category.categoryName}</a>
                        </li>
                    <#elseif category_index == 0>
                        <li class="first">
                            <a href="${contextPath }/categorySearch?cid=${category.id}"
                               class="nav_left">${category.categoryName}</a>
                        </li>
                    <#elseif category_index == categories?size - 1>
                        <li class="last">
                            <a href="${contextPath }/categorySearch?cid=${category.id}"
                               class="nav_left">${category.categoryName}</a>
                        </li>
                    </#if>
                </dd>
            </#list>
        </dl>
    </div>
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
                <span>物品资讯 &nbsp; ${good.createdDate?string("yyyy-MM-dd")}</span>
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
                                                                                 src="${good.user.headUrl}"></a>
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

    <!--分页-->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tbody>
        <tr>
            <td width="33%">
                <div align="left">
                <span class="STYLE22">
                    当前第<strong>${page.current }</strong>
                    /<strong>${page.pages}</strong> 页&nbsp;&nbsp;
                    每页显示<strong><#if page.size lte 0>1<#else ><#if page.size lte 0>1<#else >${page.size}</#if></#if></strong>条记录，共<strong>${page.total}</strong>条记录
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
											<a href="${contextPath }/?current=1" onclick="<#if page.current lte 1>return false</#if>">首页</a>
											</span>
                            </div>
                        </td>


                        <td width="49">
                            <div align="center">
                                <span class="STYLE22">
                                    <a href="${contextPath }/?current=${page.current-1}" onclick="<#if page.current lte 1>return false</#if>">上一页</a>
                                </span>
                            </div>
                        </td>


                        <td width="49">
                            <div align="center">
                                <span class="STYLE22">
                                    <a href="${contextPath }/?current=${page.current+1}" onclick="<#if page.current gte page.pages>return false</#if>">下一页</a>
                                </span>
                            </div>
                        </td>


                        <td width="49">
                            <div align="center">
                                <span class="STYLE22">
                                    <a href="${contextPath }/?current=${page.pages }" onclick="<#if page.current gte page.pages>return false</#if>">尾页</a>
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

        window.location.href = "${contextPath}/?current="
            + num;

    }
</script>


<#if pop??>
    <script>
        window.loginpop = ${pop};
    </script>
</#if>
<#include "footer.ftl">