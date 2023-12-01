<%--
Created by IntelliJ IDEA.
User: Thinkpad
Date: 01/06/2023
Time: 1:56 CH
To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglib.jsp" %>
<c:url var="APIurl" value="/api/task"/>
<c:url var="TaskURL" value="/user-search-task">
    <c:param name="page" value="1"></c:param>
    <c:param name="limit" value="4"></c:param>
</c:url>
<html>
<head>
    <title>Chinh sua bai viet</title>
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
            <div class="row">
                <div class="col-xs-12">
                    <div id="message"></div>
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="table-responsive">
                                <form name="formSearch" action="<c:url value="/user-search-task"/>" method="get"
                                      id="formSearch">
                                    <input type="text" placeholder="Search.." name="search" id="search" value="">
                                    <button type="button" id="btnSearch">Submit
                                    </button>
                                </form>
                                <div class="dropdown">
                                    <button class="dropbtn">Trạng thái công việc</button>
                                    <div class="dropdown-content">
                                        <a href="/user-task-list">All</a>
                                        <a href="/user-search-task?search=ready">READY</a>
                                        <a href="/user-search-task?search=working">WORKING</a>
                                        <a href="/user-search-task?search=done">DONE</a>
                                    </div>
                                </div>
                                <form action="<c:url value="/user-search-task"/>" id="formSubmit" method="get">
                                    <div class="pull-right tableTools-container">
                                        <div class="dt-buttons btn-overlap btn-group">
                                            <button id="btnFollow" type="button"
                                                    class="dt-button buttons-html5 btn btn-white btn-primary btn-bold"
                                                    data-toggle="tooltip" title='Xóa bài viết'>
																<span>
																	<i class="fa fa-user-plus" aria-hidden="true"></i>
																</span>
                                            </button>
                                        </div>
                                    </div>
                                    <table class="table table-bordered">
                                        <thead>
                                        <tr>
                                            <th><input type="checkbox" id="checkAll"></th>
                                            <th>Tiêu đề</th>
                                            <th>Nội dung</th>
                                            <th>Người thực hiện</th>
                                            <th>Deadline</th>
                                            <th>Trạng thái</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="item" items="${model.listResult}">
                                            <tr>
                                                <td><input type="checkbox" id="checkbox_${item.id}"
                                                           value="${item.id}">
                                                </td>
                                                <td>${item.title}</td>
                                                <td>${item.content}</td>
                                                <td>${item.performer}</td>
                                                <td>${item.deadlineEnd}</td>
                                                <td>${item.status}</td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                    <input type="hidden" value="" id="page" name="page">
                                    <input type="hidden" value="" id="limit" name="limit">
                                    <input type="hidden" value="${searchResponse}" id="searchResponse"
                                           name="searchResponse">
                                    <ul class="pagination" id="pagination"></ul>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div><!-- /.main-content -->
</body>
</html>



