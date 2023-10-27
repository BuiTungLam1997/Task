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
<%@include file="/common/taglib.jsp"%>>
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
                    <c:if test="${not empty MESSAGE}">
                        <div class="alert alert-${alert}">
                                ${MESSAGE}
                        </div>
                    </c:if>
                    <!-- PAGE CONTENT BEGINS -->
                    <form:form class="form-horizontal" role="form" id="formSubmit" modelAttribute="model">
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="username"> Username :</label>
                        <div class="col-sm-9">
                            <form:input path="username" readonly="true" cssClass="col-xs-10 col-sm-5"/>
                        </div>
                    </div>
                    <div class="space-4"></div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="fullName"> Full Name :</label>
                        <div class="col-sm-9">
                            <form:input path="fullName" readonly="true" cssClass="col-xs-10 col-sm-5"/>
                        </div>
                    </div>
                    <div class="space-4"></div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="status"> Status : </label>
                        <div class="col-sm-9">
                            <form:input path="status" readonly="true" cssClass="col-xs-10 col-sm-5"/>
                        </div>
                    </div>
                    <div class="space-4"></div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="createdBy"> createdBy : </label>
                        <div class="col-sm-9">
                            <form:input path="createdBy" readonly="true" cssClass="col-xs-10 col-sm-5"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="createdBy"> Email : </label>
                        <div class="col-sm-9">
                            <form:input path="email" readonly="true" cssClass="col-xs-10 col-sm-5"/>
                        </div>
                    </div>
                    <div class="space-4"></div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="createdDate"> Created Date :
                        </label>
                        <div class="col-sm-9">
                            <input type="datetime-local" id="createdDate" name="createdDate" path="createdDate"
                                   value="${model.createdDate}" readonly="true" cssClass="col-xs-10 col-sm-5"/>
                        </div>
                    </div>
                    <div class="space-4"></div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="createdDate"> List Permission :
                        </label>
                        <div class="col-sm-9">
                            <form:select path="listPermission" id="listPermission" cssClass="col-xs-10 col-sm-5">
                                <form:options items="${model.listPermission}"/>
                            </form:select>
                        </div>
                        <div class="hr hr-24"></div>
                        <form:hidden path="id" id="id"/>
                        </form:form>
                    </div><!-- /.col -->
                </div><!-- /.row -->
            </div><!-- /.page-content -->
        </div>
    </div><!-- /.main-content -->
</body>
</html>

