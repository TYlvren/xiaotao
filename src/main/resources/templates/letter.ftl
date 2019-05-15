<#include "header.ftl">
<div id="main">
    <div class="container">
        <ul class="letter-list">
            <#list  page.records as conversation >
                <li id="conversation-item-10005_622873">
                    <a class="letter-link"
                       href="${contextPath}/msg/detail?conversationId=${conversation.conversationId!}"></a>
                    <div class="letter-info">
                        <span class="l-time">${conversation.createdDate?string('yyyy-MM-dd HH:mm:ss')}</span>
                        <div class="l-operate-bar">
                            <a href="${contextPath}/msg/deleteConversation?conversationId=${conversation.conversationId!}&current=${page.current}"
                               class="sns-action-del" data-id="10005_622873"
                               onclick="if(!confirm('删除后将不可恢复，确定吗??')) return false;">
                                删除
                            </a>
                            <a href="${contextPath}/msg/detail?conversationId=${conversation.conversationId!}">
                                共${conversation.messageNum!}条会话
                            </a>
                        </div>
                    </div>
                    <div class="chat-headbox">
                        <span class="msg-num">
                            ${conversation.unread!}
                        </span>
                        <a class="list-head" href="${contextPath}/user/${conversation.user.id!}">
                            <img alt="头像" src="${conversation.user.headUrl!}">
                        </a>
                    </div>
                    <div class="letter-detail">
                        <a title="${conversation.user.username!}" class="letter-name level-color-1">
                            ${conversation.user.username!}
                        </a>
                        <p class="letter-brief">
                            <a href="${contextPath}/msg/detail?conversationId=${conversation.conversationId!}">
                                ${conversation.content!}
                            </a>
                        </p>
                    </div>
                </li>
            </#list>
        </ul>

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
											<a href="${contextPath }/msg/list?current=1"
                                               onclick="<#if page.current lte 1>return false</#if>">首页</a>
											</span>
                                </div>
                            </td>


                            <td width="49">
                                <div align="center">
                                <span class="STYLE22">
                                    <a href="${contextPath }/msg/list?current=${page.current-1}"
                                       onclick="<#if page.current lte 1>return false</#if>">上一页</a>
                                </span>
                                </div>
                            </td>


                            <td width="49">
                                <div align="center">
                                <span class="STYLE22">
                                    <a href="${contextPath }/msg/list?current=${page.current+1}"
                                       onclick="<#if page.current gte page.pages>return false</#if>">下一页</a>
                                </span>
                                </div>
                            </td>


                            <td width="49">
                                <div align="center">
                                <span class="STYLE22">
                                    <a href="${contextPath }/msg/list?current=${page.pages }"
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

            window.location.href = "${contextPath}/msg/list?current="
                + num;

        }

        $(function () {

            // If really is weixin
            $(document).on('WeixinJSBridgeReady', function () {

                $('.weixin-qrcode-dropdown').show();

                var options = {
                    "img_url": "",
                    "link": "http://xiaotao.com/j/wt2rwy",
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
</div>
<#include "footer.ftl">
