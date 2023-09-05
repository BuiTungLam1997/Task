<%--
  Created by IntelliJ IDEA.
  User: Thinkpad
  Date: 25/05/2023
  Time: 10:03 SA
  To change this template use File | Settings | File Templates.
--%>
<%@include file="/common/taglib.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title><dec:title default="Trang chủ"/></title>
    <link rel="stylesheet" href="/template/admin/assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="/template/admin/assets/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="/template/admin/font-awesome/4.5.0/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="/template/admin/assets/css/ace.min.css" class="ace-main-stylesheet"
          id="main-ace-style"/>
    <link rel="stylesheet" href="/template/admin/css/menu.css">
    <script src="/template/admin/assets/js/ace-extra.min.js"></script>
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script type='text/javascript' src="/template/admin/js/jquery-2.2.3.min.js"></script>
    <script src="/template/admin/assets/js/jquery.2.1.1.min.js"></script>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script src="/template/paging/jquery.twbsPagination.js"></script>
<%--  css  js wseetalert  --%>
    <link rel="stylesheet" href="/template/admin/sweetalert/sweetalert2.min.css">
    <script src="/template/admin/sweetalert/sweetalert2.min.js"></script>
<%--  CSS COMMENT  --%>
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/template/comment/style.css">
</head>
<body class="no-skin">
<!-- header -->
<%@ include file="/common/admin/header.jsp" %>
<!-- header -->

<div class="main-container" id="main-container">
    <script type="text/javascript">
        try {
            ace.settings.check('main-container', 'fixed')
        } catch (e) {
        }
    </script>
    <!-- header -->
    <%@ include file="/common/admin/menu.jsp" %>
    <!-- header -->

    <dec:body/>

    <!-- footer -->
    <%@ include file="/common/admin/footer.jsp" %>
    <!-- footer -->

    <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse display">
        <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
    </a>
</div>

<script src="/template/admin/assets/js/bootstrap.min.js"></script>
<script src="/template/admin/assets/js/jquery-ui.custom.min.js"></script>
<script src="/template/admin/assets/js/jquery.ui.touch-punch.min.js"></script>
<script src="/template/admin/assets/js/jquery.easypiechart.min.js"></script>
<script src="/template/admin/assets/js/jquery.sparkline.min.js"></script>
<script src="/template/admin/assets/js/jquery.flot.min.js"></script>
<script src="/template/admin/assets/js/jquery.flot.pie.min.js"></script>
<script src="/template/admin/assets/js/jquery.flot.resize.min.js"></script>
<script src="/template/admin/assets/js/ace-elements.min.js"></script>
<script src="/template/admin/assets/js/ace.min.js"></script>
<script src="/template/admin/assets/js/bootstrap.min.js"></script>

<!-- page specific plugin scripts -->
<script src="/template/admin/assets/js/jquery-ui.min.js"></script>
</body>
</html>
