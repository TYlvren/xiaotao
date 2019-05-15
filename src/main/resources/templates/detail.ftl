<#include "header.ftl">
<div id="main">
    <div class="container">
        <div class="post detail">

            <div class="votebar">
                <#if goods.like gt 0>
                    <button class="click-like up pressed" data-id="${goods.id}" title="关注"><i
                                class="vote-arrow"></i><span class="count">${goods.concernCount!}</span></button>
                <#else>
                    <button class="click-like up" data-id="${goods.id}" title="关注"><i class="vote-arrow"></i><span
                                class="count">${goods.concernCount!}</span></button>
                </#if>
                <#if goods.like lt 0>
                    <button class="click-dislike down pressed" data-id="${goods.id}" title="取消关注"><i
                                class="vote-arrow"></i></button>
                <#else>
                    <button class="click-dislike down" data-id="${goods.id}" title="取消关注"><i class="vote-arrow"></i>
                    </button>
                </#if>
            </div>

            <div class="content">
                <div>
                    <img class="content-img" src="${goods.imageUrl!}" alt="">
                </div>
                <div class="content-main">
                    <h3 class="title">
                        <a target="_blank" rel="external nofollow" style="color: #0D6EB8"
                           href="${contextPath}/goods/${goods.id!}">${goods.goodsName!}</a>
                    </h3>
                    <div class="meta">
                        <p style="color: black">${goods.desc!}</p>
                        <span>
                                            <i class="fa icon-comment"></i> ${goods.commentCount!}
                                        </span>
                    </div>
                </div>
            </div>
            <div class="user-info">
                <div class="user-avatar">
                    <a href="${contextPath}/user/${goods.user.id!}"><img width="32" class="img-circle"
                                                                         src="${goods.user.headUrl!}"></a>
                </div>
                <!--
                <div class="info">
                    <h5>分享者</h5>

                    <a href="http://nowcoder.com/u/125701"><img width="48" class="img-circle" src="http://images.nowcoder.com/images/20141231/622873_1420036789276_622873_1420036771761_%E8%98%91%E8%8F%87.jpg@0e_200w_200h_0c_1i_1o_90Q_1x" alt="Thumb"></a>

                    <h4 class="m-b-xs">影浅</h4>
                    <a class="btn btn-default btn-xs" href="http://nowcoder.com/signin"><i class="fa icon-eye"></i> 关注TA</a>
                </div>
                -->
            </div>

            <div class="subject-name">
                来自
                <a href="${contextPath!}/user/${goods.user.id}/">${goods.user.username}</a>
                <h6>
                    <p style="color: #aa0000">￥${goods.expectedPrice}</p>
                </h6>
            </div>
        </div>


        <div class="post-comment-form">
            <#if user??>
                <span>评论 (${goods.commentCount!})</span>
                <form method="post" action="${contextPath}/goods/addComment">
                    <input name="goodsId" type="hidden" value="${goods.id}">

                    <div class="form-group text required comment_content">
                        <label class="text required sr-only">
                            <abbr title="required">*</abbr> 评论</label>
                        <textarea rows="5" class="text required comment-content form-control" name="content"
                                  id="content"></textarea>
                    </div>
                    <div class="text-right">
                        <input type="submit" name="commit" value="提 交" class="btn btn-default btn-info">
                    </div>
                </form>
            <#else>
                <div class="login-actions">
                    <li class="js-login"><a class="btn btn-success" href="javascript:void(0);">登录后评论</a></li>
                    <#--<a class="btn btn-success" href="${contextPath}/?pop=1">登录后评论</a>-->
                </div>
            </#if>
        </div>

        <div id="comments" class="comments">
            <#list  page.records as comment >
                <div class="media">
                    <a class="media-left" href="${contextPath}/user/${comment.user.id!}">
                        <img src="${comment.user.headUrl!}">
                    </a>
                    <div class="media-body">
                        <h4 class="media-heading">
                            <small class="date">${comment.createdDate?string('yyyy-MM-dd HH:mm:ss')}
                            </small>
                        </h4>
                        <div>${comment.content!}</div>
                    </div>
                </div>
            </#list>
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
											<a href="${contextPath }/goods/${goods.id}?current=1"
                                               onclick="<#if page.current lte 1>return false</#if>">首页</a>
											</span>
                                </div>
                            </td>


                            <td width="49">
                                <div align="center">
                                <span class="STYLE22">
                                    <a href="${contextPath }/goods/${goods.id}?current=${page.current-1}"
                                       onclick="<#if page.current lte 1>return false</#if>">上一页</a>
                                </span>
                                </div>
                            </td>


                            <td width="49">
                                <div align="center">
                                <span class="STYLE22">
                                    <a href="${contextPath }/goods/${goods.id}?current=${page.current+1}"
                                       onclick="<#if page.current gte page.pages>return false</#if>">下一页</a>
                                </span>
                                </div>
                            </td>


                            <td width="49">
                                <div align="center">
                                <span class="STYLE22">
                                    <a href="${contextPath }/goods/${goods.id}?current=${page.pages }"
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
    <script type="text/javascript">
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

            window.location.href = "${contextPath}/goods/${goods.id}?current="
                + num;

        }

        $(function () {

            // If really is weixin
            $(document).on('WeixinJSBridgeReady', function () {

                $('.weixin-qrcode-dropdown').show();

                var options = {
                    "img_url": "",
                    "link": "http://nowcoder.com/j/wt2rwy",
                    "desc": "",
                    "title": "读《Web 全栈工程师的自我修养》"
                };

                WeixinJSBridge.on('menu:share:appmessage', function (argv) {
                    WeixinJSBridge.invoke('sendAppMessage', options, function (res) {
                        // _report('send_msg', res.err_msg)
                    });
                });

                WeixinJSBridge.on('menu:share:timeline', function (argv) {
                    WeixinJSBridge.invoke('shareTimeline', options, function (res) {
                        // _report('send_msg', res.err_msg)
                    });
                });

                // $(window).on('touchmove scroll', function() {
                //   if ((window.innerHeight + window.scrollY) >= document.body.offsetHeight) {
                //     $('div.backdrop').show();
                //     $('div.share-help').show();
                //   } else {
                //     $('div.backdrop').hide();
                //     $('div.share-help').hide();
                //   }
                // });

            });

        })

    </script>
    <script type="text/javascript" src="${contextPath}/scripts/main/site/detail.js"></script>
</div>
<#include "footer.ftl">
