<#include "header.ftl">
<link rel="stylesheet" href="${contextPath}/styles/detail.css">
<script type="text/javascript" async="" src="${contextPath}/scripts/za-0.1.1.min.js"></script>
<script src="${contextPath}/scripts/instant.e7a17de6.js"></script>

        <div class="modal-dialog-bg" aria-hidden="true" style="opacity: 0.5; width: 1366px; height: 1196px;"></div>

        <div class="modal-wrapper" aria-hidden="true">
            <div class="modal-dialog absolute-position" tabindex="0" role="dialog" aria-labelledby=":i" style="width: 550px;left:50%">
                <div class="modal-dialog-title">
                    <span class="modal-dialog-title-text" id=":i" role="heading">发送私信</span>
                    <span class="modal-dialog-title-close" role="button" tabindex="0" aria-label="Close"></span>
                </div>
                <div class="modal-dialog-content">
                    <div class="zh-add-question-form">
                        <form class="js-add-question-form" style="display: block;" >
                            <div class="zg-section-big clearfix">
                                <div id="zm-modal-dialog-info-wrapper"></div>
                                <div style="display: none;position: relative;" id="zm-modal-dialog-warnmsg-wrapper">
                                    <div class="zm-modal-dialog-warnmsg zm-modal-dialog-guide-warn-message zg-r5px"></div>
                                    <a name="close" title="关闭" href="javascript:;" class="zu-global-notify-close" style="display:none">x</a>
                                    <span class="zm-modal-dialog-guide-title-msg"></span>
                                </div>

                                <div class="add-question-section-title">发给：</div>
                                <div class="zg-form-text-input add-question-title-form" style="position: relative;">
                                    <textarea  id="msg_to" rows="1" class="zg-editor-input zu-seamless-input-origin-element" title="在这里输入问题" id="zh-question-suggest-title-content" aria-label="写下你的问题" placeholder="姓名" role="combobox" aria-autocomplete="list" autocomplete="off" style="height: 22px;"></textarea>
                                </div>
                            </div>
                            <div class="zg-section-big">
                                <div class="add-question-section-title">内容：</div>
                                <div id="zh-question-suggest-detail-container" class="zm-editable-status-editing">
                                    <div class="zm-editable-editor-wrap no-toolbar" style="">
                                        <div class="zm-editable-editor-outer">
                                            <div class="zm-editable-editor-field-wrap">
                                                <div id="mock:k" class="zm-editable-editor-field-element editable" g_editable="true" role="textbox" contenteditable="true" style="font-style: italic;">
                                                    <p>
                                                        <span id="msg_content" style="font-style: normal;color: #999;" aria-placeholder="私信内容x"></span></p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="zm-command">
                                <span id="zh-question-form-tag-err"></span>
                                <!--<a href="javascript:;" name="cancel" class="zm-command-cancel">取消</a>-->
                                <a href="javascript:sendMsg();" name="addq" class="zg-r5px zu-question-form-add zg-btn-blue">发送</a>
                                <a name="jumpq" class="zg-r5px zg-btn-blue zu-question-form-jump" style="display:none;">查看问题</a></div>
                        </form>
                    </div>
                </div>
                <div class="modal-dialog-buttons" style="display: none;"></div>
            </div>
        </div>

<script type="text/javascript">
    function sendMsg() {

        var elementById = document.getElementById("msg_to");
        var to=elementById.value;
        var elementById2 = document.getElementById("msg_content");
        var content=elementById2.innerText;
        //alert(content)

        var  errtag =document.getElementById("zh-question-form-tag-err");

        // contentType:"application/text;charset=utf-8",
        $.ajax(
            {
                type:"POST",
                url:"/user/msg/addMessage",
                data: 'toName='+to+'&&content='+content ,
                success:function(data){
                    //alert(data)
                    var dataobj = eval('(' + data + ')');
                    //alert(dataobj.code)
                    //alert(dataobj.msg)

                    if (dataobj.code == '1') {
                        errtag.innerText=dataobj.msg
                    } else  {
                        alert("发送成功！")
                        window.location.href = '${contextPath}/msg/list';
                    }
                },
                fail:function () {
                    alert('出现错误，请重试');
                }


            }
        );

    }


</script>


<#include "footer.ftl">