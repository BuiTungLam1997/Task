<%--
  Created by IntelliJ IDEA.
  User: Thinkpad
  Date: 24/05/2023
  Time: 12:49 CH
  To change this template use File | Settings | File Templates.
--%>
<%--
  Created by IntelliJ IDEA.
  User: Thinkpad
  Date: 21/04/2023
  Time: 9:22 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.task.utils.SecurityUtils" %>
<%@include file="/common/taglib.jsp" %>
>
<html>
<head>
    <title>admin</title>
</head>
<body>
<div class="main-content">
    <div class="main-content-inner">
        <div class="breadcrumbs ace-save-state" id="breadcrumbs">
            <ul class="breadcrumb">
                <li>
                    <i class="ace-icon fa fa-home home-icon"></i>
                    <a href="#">Trang chủ</a>
                </li>
            </ul><!-- /.breadcrumb -->
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
                    <div class="alert alert-success">
                        Đăng nhập thành công ,xin chào <%=SecurityUtils.getPrincipal().getFullName()%>
                    </div>
                    <!-- PAGE CONTENT BEGINS -->
                    <form:form class="form-horizontal" role="form" id="formSubmit">
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right" for="username"> Username :</label>
                            <div class="col-sm-9">
                                <input type="text" id="username" name="username"
                                       value="<%=SecurityUtils.getPrincipal().getUsername()%>" readonly
                                       Class="col-xs-10 col-sm-5"/>
                            </div>
                        </div>
                        <div class="space-4"></div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right" for="fullName"> Full Name :</label>
                            <div class="col-sm-9">
                                <input type="text" id="fullName" name="fullName" value="" readonly
                                       Class="col-xs-10 col-sm-5"/>
                            </div>
                        </div>
                        <div class="space-4"></div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right" for="status"> Status : </label>
                            <div class="col-sm-9">
                                <input type="text" id="status" name="status" value="" readonly
                                       Class="col-xs-10 col-sm-5"/>
                            </div>
                        </div>
                        <div class="space-4"></div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right" for="createdBy"> createdBy : </label>
                            <div class="col-sm-9">
                                <input type="text" id="createdBy" name="createdBy" value="" readonly
                                       Class="col-xs-10 col-sm-5"/>
                            </div>
                        </div>
                        <div class="space-4"></div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right" for="createdBy"> Email : </label>
                            <div class="col-sm-9">
                                <input type="text" id="email" name="email" value="" readonly
                                       Class="col-xs-10 col-sm-5"/>
                            </div>
                        </div>
                        <div class="space-4"></div>

                        <table class="table table-bordered">
                            <thead>
                            <tr>
                                <th>Id</th>
                                <th>Name</th>
                                <th>Code</th>
                            </tr>
                            </thead>
                            <tbody id="listPermission">
                            </tbody>
                        </table>
                        <!-- /.col -->
                    </form:form>
                </div><!-- /.row -->
            </div><!-- /.page-content -->
        </div>
    </div><!-- /.main-content -->
</div>
<script type='text/javascript' src="/template/custom/user/js/home.js"></script>
</body>
</html>

