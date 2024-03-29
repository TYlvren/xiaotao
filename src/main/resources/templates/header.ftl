<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <title>校淘头条 - 淘你想要</title>
    <meta name="viewport"
          content="width=device-width, minimum-scale=1.0, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">


    <link rel="stylesheet" type="text/css" href="${contextPath}/styles/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${contextPath}/styles/font-awesome.min.css">
    <link rel="stylesheet" media="all" href="${contextPath}/styles/style.css">
    <script type="text/javascript" src="${contextPath}/scripts/jquery.js"></script>
    <script type="text/javascript" src="${contextPath}/scripts/main/base/base.js"></script>
    <script type="text/javascript" src="${contextPath}/scripts/main/base/util.js"></script>
    <script type="text/javascript" src="${contextPath}/scripts/main/base/event.js"></script>
    <script type="text/javascript" src="${contextPath}/scripts/main/base/upload.js"></script>
    <script type="text/javascript" src="${contextPath}/scripts/main/component/component.js"></script>
    <script type="text/javascript" src="${contextPath}/scripts/main/component/popup.js"></script>
    <script type="text/javascript" src="${contextPath}/scripts/main/component/popupLogin.js"></script>
    <script type="text/javascript" src="${contextPath}/scripts/main/component/upload.js"></script>
    <script type="text/javascript" src="${contextPath}/scripts/main/component/popupUpload.js"></script>
    <script type="text/javascript" src="${contextPath}/scripts/main/util/action.js"></script>
    <script type="text/javascript" src="${contextPath}/scripts/main/site/home.js"></script>
    <style type="text/css">
        .STYLE22 {
            font-size: 12px;
            color: #295568;
        }
    </style>
</head>
<body class="welcome_index">

<header class="navbar navbar-default navbar-static-top bs-docs-nav" id="top" role="banner">
    <div class="container">
        <div class="navbar-header">
            <button class="navbar-toggle collapsed" type="button" data-toggle="collapse"
                    data-target=".bs-navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>

            <a href="${contextPath}/" class="navbar-brand logo">
                <h1>闲置物品资讯</h1>
                <h3>校园闲置物品分享平台</h3>
            </a>
        </div>

        <nav class="collapse navbar-collapse bs-navbar-collapse" role="navigation">

            <ul class="nav navbar-nav navbar-right" style="font-size: 15px">
                <#if user??>
                    <li class="js-share"><a href="javascript:void(0);">分享</a></li>
                    <li class="">
                        <#if unreadMessage gt 0>
                            <a href="${contextPath}/msg/list">
                                站内信
                                <span class="unreadMessage-num" style="color: red">
                                    ${unreadMessage!}
                                </span>
                            </a>
                        <#else >
                            <a href="${contextPath}/msg/list">站内信</a>
                        </#if>
                    </li>
                    <li class="top-nav-noti zu-top-nav-li ">
                        <a href="${contextPath}/user/tosendmsg"> 发私信 </a>
                    </li>
                    <li class=""><a href="${contextPath}/user/${user.id!}">${user.username!}</a></li>
                    <li class=""><a href="${contextPath}/user/myConcern">我的关注</a></li>
                    <li class=""><a href="${contextPath}/logout">注销</a></li>
                <#else>
                    <li class="js-login"><a href="javascript:void(0);">登陆</a></li>
                </#if>
            </ul>

        </nav>
    </div>
</header>
