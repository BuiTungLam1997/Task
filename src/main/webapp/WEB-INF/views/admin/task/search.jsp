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
<c:url var="TaskURL" value="/admin-search-task">
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
                    <div id="message">
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="table-responsive">
                                <form name="formSearch" action="<c:url value="/admin-search-task"/>" method="get"
                                      id="formSearch">
                                    <input type="text" placeholder="Search.." name="search" id="search" value="">
                                    <button type="submit" onclick="fun()" id="btnSearch">Submit
                                    </button>
                                </form>
                                <div class="dropdown">
                                    <button class="dropbtn">Trạng thái công việc</button>
                                    <div class="dropdown-content">
                                        <a href="/admin-task-list">All</a>
                                        <a href="/admin-search-task?search=ready">READY</a>
                                        <a href="/admin-search-task?search=working">WORKING</a>
                                        <a href="/admin-search-task?search=done">DONE</a>
                                    </div>
                                </div>
                                <form action="<c:url value="/admin-search-task"/>" id="formSubmit" method="get">
                                    <div class="pull-right tableTools-container">
                                        <div class="dt-buttons btn-overlap btn-group">
                                            <c:url var="createUserURL" value="/admin-task-edit"/>
                                            <a flag="info"
                                               class="dt-button buttons-colvis btn btn-white btn-primary btn-bold"
                                               data-toggle="tooltip"
                                               title='Thêm bài viết'
                                               href='${createUserURL}'>
															<span>
																<i class="fa fa-plus-circle bigger-110 purple"></i>
															</span>
                                            </a>
                                            <button id="btnDelete" type="button" onclick="warningBeforeDelete()"
                                                    class="dt-button buttons-html5 btn btn-white btn-primary btn-bold"
                                                    data-toggle="tooltip" title='Xóa bài viết'>
																<span>
																	<i class="fa fa-trash-o bigger-110 pink"></i>
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
                                            <th>Thao tác</th>
                                        </tr>
                                        </thead>
                                        <tbody id="listTask">
                                        </tbody>
                                    </table>
                                    <input type="hidden" value="" id="page" name="page">
                                    <input type="hidden" value="" id="limit" name="limit">
                                    <input type="hidden" value="${searchResponse}" id="searchResponse"
                                           class="searchResponse"
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
<script type='text/javascript' src="/template/custom/admin/js/task/search.js"></script>
</body>
</html>



