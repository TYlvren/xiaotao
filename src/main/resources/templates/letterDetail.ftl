<#include "header.ftl">
<div id="main">
    <div class="container">
        <ul class="letter-chatlist">
            <#list  page.records as msg >
                <li id="msg-item-4009580">
                    <a class="list-head" href="${contextPath}/user/${msg.user.id!}">
                        <img alt="头像" src="${msg.user.headUrl!}">
                    </a>
                    <div class="tooltip fade right in">
                        <div class="tooltip-arrow"></div>
                        <div class="tooltip-inner letter-chat clearfix">
                            <div class="letter-info">
                                <p class="letter-time">${msg.createdDate?string('yyyy-MM-dd HH:mm:ss')}</p>
                                <a href="${contextPath}/msg/deleteMessage?messageId=${msg.id!}&conversationId=${msg.conversationId}"
                                   id="del-link" name="4009580" onclick="if(!confirm('删除后将不可恢复，确定吗??')) return false;">删除</a>
                            </div>
                            <p class="chat-content">
                                ${msg.content!}
                            </p>
                        </div>
                    </div>
                </li>
            </#list>
        </ul>

        <div class="post-comment-form">
            <#if user??>
                <span>消息 (${page.records?size!})</span>
                <form method="post" action="${contextPath}/msg/replyMessage">
                    <#list  page.records as msg>
                        <#if msg_index==0>
                            <input name="conversationId" type="hidden" value="${msg.conversationId}">
                        </#if>
                    </#list>

                    <div class="form-group text required comment_content">
                        <label class="text required sr-only">
                            <abbr title="required">*</abbr> 回复</label>
                        <textarea rows="5" class="text required comment-content form-control" name="content"
                                  id="content"></textarea>
                    </div>
                    <div class="text-right">
                        <input type="submit" name="commit" value="发 送" class="btn btn-default btn-info">
                    </div>
                </form>
            <#else>
                <div class="login-actions">
                    <a class="btn btn-success" href="${contextPath}/?pop=1">登录后回复</a>
                </div>
            </#if>
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
											<a href="${contextPath }/msg/detail?conversationId=${conversationId}&current=1"
                                               onclick="<#if page.current lte 1>return false</#if>">首页</a>
											</span>
                                </div>
                            </td>


                            <td width="49">
                                <div align="center">
                                <span class="STYLE22">
                                    <a href="${contextPath }/msg/detail?conversationId=${conversationId}&current=${page.current-1}"
                                       onclick="<#if page.current lte 1>return false</#if>">上一页</a>
                                </span>
                                </div>
                            </td>


                            <td width="49">
                                <div align="center">
                                <span class="STYLE22">
                                    <a href="${contextPath }/msg/detail?conversationId=${conversationId}&current=${page.current+1}"
                                       onclick="<#if page.current gte page.pages>return false</#if>">下一页</a>
                                </span>
                                </div>
                            </td>


                            <td width="49">
                                <div align="center">
                                <span class="STYLE22">
                                    <a href="${contextPath }/msg/detail?conversationId=${conversationId}&current=${page.pages }"
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

            window.location.href = "${contextPath}/msg/detail?conversationId=${conversationId}&current="
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
</div>
<#include "footer.ftl">
