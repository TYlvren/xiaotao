(function (window) {
    var PopupLogin = Base.createClass('main.component.PopupLogin');
    var Popup = Base.getClass('main.component.Popup');
    var Component = Base.getClass('main.component.Component');
    var Util = Base.getClass('main.base.Util');


    Base.mix(PopupLogin, Component, {
        _tpl: [
            '<div class="wrapper-content clearfix">',
            '<div class="input-section">',
            '<div class="form-group">',
            '<label class="control-label">用户名</label>',
            '<div class="control-group js-username"><input type="text" placeholder="请输入用户名" id="username"></div>',
            '</div>',
            '<div class="form-group">',
            '<label class="control-label">密码</label>',
            '<div class="control-group js-pwd"><input type="password" placeholder="请输入密码" id="password"></div>',
            '</div>',
            '<div class="form-group about-pwd">',
            '<div class="keep-pwd">',
            //'<label><input type="checkbox" class="js-remember" checked="checked"> 记住登录</label>',
            '</div>',
            '</div>',
            '<div class="form-group">',
            '<div class="col-input-login">',
            '<a class="btn btn-info js-login" href="javascript:void(0);">登陆</a>',
            '<a class="btn btn-info js-register" href="javascript:void(0);">注册</a>',
            '</div>',
            '</div>',
            '</div>',
            '</div>'].join(''),
        listeners: [{
            name: 'render',
            type: 'custom',
            handler: function () {
                var that = this;
                var oEl = that.getEl();
                that.usernameIpt = oEl.find('div.js-username');
                that.pwdIpt = oEl.find('div.js-pwd');
                that.rememberIpt = oEl.find('div.js-remember').checked;
                that.initCpn();
            }
        }, {
            name: 'click a.js-login',
            handler: function (oEvent) {
                oEvent.preventDefault();
                var that = this;
                // 值检查
                if (!that.checkVal()) {
                    return;
                }
                var oData = that.val();
                $.ajax({
                    url: '/login',
                    type: 'post',
                    dataType: 'json',
                    data: {
                        username: oData.username,
                        password: oData.pwd,
                        remember: oData.remember ? 1 : 0
                    }
                }).done(function (oResult) {
                    if (oResult.code === 0) {
                       /* if (that.rememberIpt === "1") {
                            //若复选框勾选,则添加Cookie,记录密码

                            var name = getCookie("username");
                            if (name !== that.usernameIpt) {
                                delCookie("username");
                                delCookie("password");
                            }
                            setCookie("username", that.usernameIpt, 30);
                            //base64编码
                            setCookie("password", base64decode(that.pwdIpt), 30);

                        } else {  //否则清除Cookie
                            delCookie("username");
                            delCookie("password")
                        }
                       */
                        window.location.reload();
                        that.emit('login');
                    } else {
                        oResult.msgname && that.iptError(that.usernameIpt, oResult.msgname);
                        oResult.msgpwd && that.iptError(that.pwdIpt, oResult.msgpwd);
                    }
                }).fail(function () {
                    alert('出现错误，请重试');
                });
            }
        }, {
            name: 'click a.js-register',
            handler: function (oEvent) {
                oEvent.preventDefault();
                var that = this;
                // 值检查
                if (!that.checkVal()) {
                    return;
                }
                var oData = that.val();
                $.ajax({
                    url: '/reg',
                    type: 'post',
                    dataType: 'json',
                    data: {
                        username: oData.username,
                        password: oData.pwd
                    }
                }).done(function (oResult) {
                    if (oResult.code === 0) {
                        window.location.reload();
                        that.emit('register');
                    } else {
                        oResult.msgname && that.iptError(that.usernameIpt, oResult.msgname);
                        oResult.msgpwd && that.iptError(that.pwdIpt, oResult.msgpwd);
                    }
                }).fail(function () {
                    alert('出现错误，请重试');
                });
            }
        }, {
            name: 'click .js-close',
            handler: function (oEvent) {
                oEvent.preventDefault();
                var that = this;
                that.close();
            }
        }],
        show: fStaticShow
    }, {
        initialize: fInitialize,
        initCpn: fInitCpn,
        val: fVal,
        checkVal: fCheckVal,
        iptSucc: fIptSucc,
        iptError: fIptError,
        iptNone: fIptNone
    });

    function fStaticShow(oConf) {
        var that = this;
        var oLogin = new PopupLogin(oConf);
        var oPopup = new Popup({
            width: 540,
            content: oLogin.html()
        });
        oLogin._popup = oPopup;
        Component.setEvents();
    }

    function fInitialize(oConf) {
        var that = this;
        delete oConf.renderTo;
        PopupLogin.superClass.initialize.apply(that, arguments);
    }

    function fInitCpn() {
        var that = this;
        that.usernameIpt.find('input').on('focus', Base.bind(that.iptNone, that, that.usernameIpt));
        that.pwdIpt.find('input').on('focus', Base.bind(that.iptNone, that, that.pwdIpt));
    }

    function fVal(oData) {
        var that = this;
        var oEl = that.getEl();
        var oUsernameIpt = that.usernameIpt.find('input');
        var oPwdIpt = that.pwdIpt.find('input');
        var oRememberChk = oEl.find('.js-remember');
        if (arguments.length === 0) {
            return {
                username: $.trim(oUsernameIpt.val()),
                pwd: $.trim(oPwdIpt.val()),
                remember: oRememberChk.prop('checked')
            };
        } else {
            oUsernameIpt.val($.trim(oData.username));
            oPwdIpt.val($.trim(oData.pwd));
            oRememberChk.prop('checked', !!oData.remember);
        }
    }

    function fCheckVal() {
        var that = this;
        var oData = that.val();
        var bRight = true;
        /*
        if (!Util.isUsername(oData.username)) {
            that.iptError(that.usernameIpt, '请填写正确的邮箱');
            bRight = false;
        }*/

        if (!oData.username) {
            that.iptError(that.usernameIpt, '用户名不能为空');
            bRight = false;
        } else if (oData.username.length > 10 || oData.username.length<2) {
            that.iptError(that.usernameIpt, '用户名应为2-10位');
            bRight = false;
        }

        if (!oData.pwd) {
            that.iptError(that.pwdIpt, '密码不能为空');
            bRight = false;
        } else if (oData.pwd.length < 6 || oData.pwd.length > 16) {
            that.iptError(that.pwdIpt, '密码长度应为6-16位');
            bRight = false;
        }
        return bRight;
    }

    function fIptSucc(oIpt) {
        var that = this;
        oIpt = $(oIpt);
        that.iptNone(oIpt);
        oIpt.addClass('success');
        if (!oIpt.find('.icon-ok-sign').get(0)) {
            oIpt.append('<i class="input-icon icon-ok-sign"></i>');
        }
    }

    function fIptError(oIpt, sMsg) {
        var that = this;
        oIpt = $(oIpt);
        that.iptNone(oIpt);
        oIpt.addClass('error');
        if (!oIpt.find('.icon-remove-sign').get(0)) {
            oIpt.append('<i class="input-icon icon-remove-sign"></i>');
        }
        var oSpan = oIpt.find('.input-tip');
        if (!oSpan.get(0)) {
            oSpan = $('<span class="input-tip"></span>');
            oIpt.append(oSpan);
        }
        oSpan.html($.trim(sMsg));
    }

    function fIptNone(oIpt) {
        var that = this;
        $(oIpt).removeClass('error success');
    }

    //设置cookie
    function setCookie(name, value, expdays) {
        var expdate = new Date();
        //设置Cookie过期日期
        expdate.setDate(expdate.getDate() + expdays);
        //添加Cookie并转码
        document.cookie = encodeURI(name) + "=" + escape(value) + ";expires=" + expdate.toUTCString();

    }

    function setUsername() {
        document.getElementById("username").value = getCookie("username")
    }

    function setPassword() {
        document.getElementById("password").value = base64decode(getCookie("password"))
    }

    //得到cookie
    function getCookie(name) {
        //获取name在Cookie中起止位置
        var start = document.cookie.indexOf(name + "=");
        if (start != -1) {
            start = start + name.length + 1;
            //获取value的终止位置
            var end = document.cookie.indexOf(";", start);
            if (end == -1)
                end = document.cookie.length;
            //截获cookie的value值,并返回
            return unescape(document.cookie.substring(start, end));
        }
        return "";
    }

    //删除cookie
    function delCookie(name) {
        setCookie(name, "", -1);
    }


    //base64加密
    //参数设置
    var base64EncodeChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    var base64DecodeChars = [-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63,
        52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1,
        -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14,
        15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1,
        -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
        41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1];

    //加密方法
    function base64encode(str) {
        var out, i, len;
        var c1, c2, c3;
        len = str.length;
        i = 0;
        out = "";
        while (i < len) {
            c1 = str.charCodeAt(i++) & 0xff;
            if (i === len) {
                out += base64EncodeChars.charAt(c1 >> 2);
                out += base64EncodeChars.charAt((c1 & 0x3) << 4);
                out += "==";
                break;
            }
            c2 = str.charCodeAt(i++);
            if (i === len) {
                out += base64EncodeChars.charAt(c1 >> 2);
                out += base64EncodeChars.charAt(((c1 & 0x3) << 4) | ((c2 & 0xF0) >> 4));
                out += base64EncodeChars.charAt((c2 & 0xF) << 2);
                out += "=";
                break;
            }
            c3 = str.charCodeAt(i++);
            out += base64EncodeChars.charAt(c1 >> 2);
            out += base64EncodeChars.charAt(((c1 & 0x3) << 4) | ((c2 & 0xF0) >> 4));
            out += base64EncodeChars.charAt(((c2 & 0xF) << 2) | ((c3 & 0xC0) >> 6));
            out += base64EncodeChars.charAt(c3 & 0x3F);
        }
        return out;
    }

    //解密方法
    function base64decode(str) {
        var c1, c2, c3, c4;
        var i, len, out;
        len = str.length;
        i = 0;
        out = "";
        while (i < len) {
            /* c1 */
            do {
                c1 = base64DecodeChars[str.charCodeAt(i++) & 0xff];
            }
            while (i < len && c1 === -1);
            if (c1 === -1)
                break;
            /* c2 */
            do {
                c2 = base64DecodeChars[str.charCodeAt(i++) & 0xff];
            }
            while (i < len && c2 === -1);
            if (c2 === -1)
                break;
            out += String.fromCharCode((c1 << 2) | ((c2 & 0x30) >> 4));
            /* c3 */
            do {
                c3 = str.charCodeAt(i++) & 0xff;
                if (c3 === 61)
                    return out;
                c3 = base64DecodeChars[c3];
            }
            while (i < len && c3 === -1);
            if (c3 === -1)
                break;
            out += String.fromCharCode(((c2 & 0XF) << 4) | ((c3 & 0x3C) >> 2));
            /* c4 */
            do {
                c4 = str.charCodeAt(i++) & 0xff;
                if (c4 === 61)
                    return out;
                c4 = base64DecodeChars[c4];
            }
            while (i < len && c4 === -1);
            if (c4 === -1)
                break;
            out += String.fromCharCode(((c3 & 0x03) << 6) | c4);
        }
        return out;
    }

})(window);