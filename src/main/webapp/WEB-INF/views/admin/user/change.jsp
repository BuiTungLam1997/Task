<%--
  Created by IntelliJ IDEA.
  User: Thinkpad
  Date: 05/06/2023
  Time: 3:09 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglib.jsp" %>
<%@ page import="com.example.task.utils.SecurityUtils" %>
<c:url var="UserAPIURL" value="/api/user"/>
<c:url var="ListURL" value="/admin-user-list">
    <c:param name="page" value="1"></c:param>
    <c:param name="limit" value="4"></c:param>
</c:url>
<c:url var="UserURL" value="/admin-user-edit"/>
<html>
<head>
    <title>Chỉnh sửa bài viết</title>
</head>
<body>
<div class="main-content">
    <div class="main-content-inner">
        <div class="breadcrumbs ace-save-state" id="breadcrumbs">
            <ul class="breadcrumb">
                <li>
                    <i class="ace-icon fa fa-home home-icon"></i>
                    <a href="#">Home</a>
                </li>

                <li>
                    <a href="#">Forms</a>
                </li>
                <li class="active">Form Elements</li>
            </ul><!-- /.breadcrumb -->

            <div class="nav-search" id="nav-search">
                <form class="form-search">
								<span class="input-icon">
									<input type="text" placeholder="Search ..." class="nav-search-input"
                                           id="nav-search-input" autocomplete="off"/>
									<i class="ace-icon fa fa-search nav-search-icon"></i>
								</span>
                </form>
            </div><!-- /.nav-search -->
        </div>

        <div class="page-content">
            <div class="page-header">
                <h1>
                    Form Elements
                    <small>
                        <i class="ace-icon fa fa-angle-double-right"></i>
                        Common form elements and layouts
                    </small>
                </h1>
            </div><!-- /.page-header -->

            <div class="row">
                <div class="col-xs-12">
                    <!-- PAGE CONTENT BEGINS -->
                    <div id="message"></div>
                    <form:form action="/admin-change-password-edit" class="form-horizontal" role="form" id="formSubmit"
                               modelAttribute="model"
                               method="get">

                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right" for="username"> Username </label>

                            <div class="col-sm-9">
                                <input type="text" class="form-control" readonly id="username" name="username"
                                       value="<%=SecurityUtils.getPrincipal().getUsername()%>"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right" for="password"> Password </label>

                            <div class="col-sm-9">
                                <input type="password" class="form-control" id="password" name="password" value=""/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right" for="newPassword">New
                                Password </label>

                            <div class="col-sm-9">
                                <input type="password" class="form-control" id="newPassword" name="newPassword"
                                       value=""/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right" for="retypePassword"> Enter the
                                password </label>

                            <div class="col-sm-9">
                                <input type="password" class="form-control" id="retypePassword" name="retypePassword"
                                       value=""/>
                            </div>
                        </div>
                        <div class="space-4"></div>
                        <div class="clearfix form-actions">
                            <div class="col-md-offset-3 col-md-9">
                                <button class="btn btn-info" type="button" id="btnChange">
                                    <i class="ace-icon fa fa-check bigger-110"></i>
                                    Submit
                                </button>
                            </div>
                        </div>
                        <div class="hr hr-24"></div>
                        <input type="hidden" value="" id="id" name="id"/>
                    </form:form>
                </div><!-- /.col -->
            </div><!-- /.row -->
        </div><!-- /.page-content -->
    </div>
</div>
<script>
    $(".alert").delay(2000).slideUp(200, function () {
        $(this).alert('close');
    });
</script>
<script type='text/javascript' src="/template/custom/admin/js/user/change.js"></script>
</body>
</html>
