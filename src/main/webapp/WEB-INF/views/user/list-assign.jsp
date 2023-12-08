<%--
Created by IntelliJ IDEA.
User: Thinkpad
Date: 01/06/2023
Time: 1:56 CH
To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglib.jsp" %>
<c:url var="APIurl" value="/api-task"/>
<c:url var="UserURL" value="/admin-task-list">
    <c:param name="page" value="1"></c:param>
    <c:param name="limit" value="3"></c:param>
</c:url>
<html>
<head>
    <title>Chinh sua bai viet</title>
</head>
<body>
<div class="main-content">
    <form action="<c:url value="/user-task-list-assign"/>" id="formSubmit" method="get">
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
                <div class="row">
                    <div class="col-xs-12">
                        <div id="message"></div>
                        <div class="row">
                            <div class="col-xs-12">
                                <div class="table-responsive">
                                    <div class="pull-right tableTools-container">
                                    </div>
                                    <table class="table table-bordered">
                                        <thead>
                                        <tr>
                                            <th><input type="checkbox" id="checkAll"></th>
                                            <th>Tiêu đề</th>
                                            <th>Nội dung</th>
                                            <th>Ngày bắt đầu</th>
                                            <th>Ngày kết thúc</th>
                                            <th>createdBy</th>
                                            <th>Trạng thái</th>
                                            <th>Thao tác</th>
                                        </tr>
                                        </thead>
                                        <tbody id="listAssign">
                                        </tbody>
                                    </table>
                                    <ul class="pagination" id="pagination"></ul>
                                    <input type="hidden" value="" id="page" name="page">
                                    <input type="hidden" value="" id="limit" name="limit">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div><!-- /.main-content -->
<script type='text/javascript' src="/template/custom/user/js/list-assign.js"></script>
</body>
</html>



