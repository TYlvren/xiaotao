/**
var oPopupUpload = new PopupUpload({
    
});
 */
(function (window) {
    var PopupUpload = Base.createClass('main.component.PopupUpload');
    var Popup = Base.getClass('main.component.Popup');
    var Upload = Base.getClass('main.component.Upload');
    var Component = Base.getClass('main.component.Component');
    var Util = Base.getClass('main.base.Util');

    Base.mix(PopupUpload, Component, {
        _tpl: [
            '<div>',
              '<div class="form-group">',
                '<div class="form-group">',
                    '<label class="col-sm-2 control-label">上传图片</label>',
                    '<div class="js-image-container col-sm-10">',
                        '<a href="javascript:void(0);" class="btn btn-info btn-upload js-upload-btn" style="display:inline-block;position:relative;">上传图片</a>',
                    '</div>',
                '</div>',
                  '<div class="form-group"><label class="col-sm-2 control-label">物品名</label><div class="col-sm-10"><input class="js-goodsName form-control" type="text" placeholder="请在20字以内"></div></div>',
                  '<div class="form-group"><label class="col-sm-2 control-label">描述</label><div class="col-sm-10"><input class="js-desc form-control" type="text" placeholder="请在120字以内"></div></div>',
                  '<div class="form-group"><label class="col-sm-2 control-label">期望价格</label><div class="col-sm-10"><input class="js-price form-control" type="number"></div></div>',
                  '<div class="form-group"><label class="col-sm-2 control-label">类别</label><div class="col-sm-10"><input class="js-category form-control" type="text" placeholder="请输入左侧已有分类"></div></div>',
                  '<div class="form-group">',
                        '<div class="col-lg-10 col-lg-offset-2">',
                            '<input type="submit" value="提交" class="js-submit btn btn-default btn-info">',
                        '</div>',
                    '</div>',
            '</div>'].join(''),
        listeners: [{
            name: 'render',
            type: 'custom',
            handler: function () {
                var that = this;
                var oEl = that.getEl();
                var oUploadBtn = oEl.find('a.js-upload-btn');
                new Upload({
                    targetEl: oUploadBtn,
                    url: '/uploadImage/',
                    check: function (oFile, sType, nFileSize) {
                        var sMsg = nFileSize === 0 ? '文件大小不能为0' : /image/gi.test(sType || '') ? '' : '文件格式不正确';
                        sMsg && alert(sMsg);
                        return !sMsg;
                    },
                    call: function (oResult) {
                        var sUrl = $.trim(oResult.msg);
                        if (oResult.code !== 0) {
                            return alert('出现错误，请重试');
                        }
                        that.image = sUrl;
                        that.showImage(sUrl);
                    }
                });
            }
        }, {
            name: 'click input.js-submit',
            handler: function () {
                var that = this;
                var oEl = that.getEl();
                var sGoodsName = $.trim(oEl.find('input.js-goodsName').val());
                var sDesc = $.trim(oEl.find('input.js-desc').val());
                var sPrice = $.trim(oEl.find('input.js-price').val());
                var sCategoryName = $.trim(oEl.find('input.js-category').val());
                if (!sGoodsName) {
                    return alert('物品名不能为空');
                }else if(sGoodsName.length > 20){
                    return alert('物品名请在20字以内');
                }
                if (!sDesc) {
                    return alert('描述不能为空');
                }else if(sDesc.length > 120){
                    return alert('描述请在120字以内')
                }
                if (!sPrice) {
                    return alert('价格不能为空');
                }
                if (!sCategoryName) {
                    return alert('类别不能为空');
                }
                if (!that.image) {
                    return alert('图片不能为空');
                }
                if (that.requesting) {
                    return;
                }
                that.requesting = true;
                $.ajax({
                    url: '/goods/addGoods/',
                    method: 'post',
                    data: {imageUrl: that.image, goodsName: sGoodsName, desc: sDesc,expectedPrice: sPrice,categoryName:sCategoryName},
                    dataType: 'json'
                }).done(function (oResult) {
                    that.emit('done');
                }).fail(function (oResult) {
                    alert('出现错误，请重试');
                }).always(function () {
                    that.requesting = false;
                });
            }
        }],
        show: fStaticShow
    }, {
        initialize: fInitialize,
        showImage: fShowImage
    });

    function fStaticShow(oConf) {
        var that = this;
        var oLogin = new PopupUpload(oConf);
        var oPopup = new Popup({
            title: '分享',
            width: 700,
            content: oLogin.html()
        });
        oLogin._popup = oPopup;
        Component.setEvents();
    }

    function fInitialize(oConf) {
        var that = this;
        delete oConf.renderTo;
        PopupUpload.superClass.initialize.apply(that, arguments);
    }

    function fShowImage(sUrl) {
        var that = this;
        var oEl = that.getEl();
        var sHtml = [
            '<div class="letter-pic-box">',
                '<a href="javascript:void(0);" class="icon-remove-circle"></a>',
                '<div class="mask"></div>',
                '<img src="' + sUrl + '" alt="请在5MB以内">',
            '</div>'].join('');
        oEl.find('div.letter-pic-box').remove();
        oEl.find('div.js-image-container').prepend(sHtml);
    }

})(window);