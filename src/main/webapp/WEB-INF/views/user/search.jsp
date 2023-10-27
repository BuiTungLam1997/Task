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
                    <c:if test="${not empty MESSAGE}">
                        <div class="alert alert-${ALERT}">
                                ${MESSAGE}
                        </div>
                    </c:if>
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="table-responsive">
                                <form name="formSearch" action="<c:url value="/user-search-task"/>" method="get"
                                      id="formSearch">
                                    <input type="text" placeholder="Search.." name="search" id="search" value="">
                                    <button type="submit" onclick="fun()" id="btnSearch">Submit
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
                                            <button id="btnFollow" type="button" onclick="clickFunction()"
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
                                    <input type="hidden" value="" id="searchTws" class="searchTws"
                                           name="searchTws">
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
<script type="text/javascript">
    var totalPage = ${model.totalPage};
    var currentPage = ${model.page};
    var limit =  ${model.limit};
    $(function () {
        window.pagObj = $('#pagination').twbsPagination({
            totalPages: totalPage,
            visiblePages: limit,
            startPage: currentPage,
            onPageClick: function (event, page) {
                if (currentPage !== page) {
                    $('#limit').val(limit);
                    $('#page').val(page);
                    $('#formSubmit').submit();
                }
            }
        });
    });
    $(".alert").delay(2000).slideUp(200, function () {
        $(this).alert('close');
    });

    function fun() {
        $('#search').val();
        $('#btnSearch').submit();
    }

    function warningBeforeDelete() {
        swal({
            title: "Mài có chắc chắn xóa nó không ?",
            text: "Thấy câu hỏi ở trên không ,ừ chỗ này giống nó đó ,trả lời đi!",
            type: "warning",
            showCancelButton: true,
            confirmButtonClass: "btn-success",
            cancelButtonClass: "btn-danger",
            confirmButtonText: "Có, Con đồng ý xóa thưa ngài!",
            cancelButtonText: "Không , Con cần thời gian suy nghĩ!",
            closeOnConfirm: false,
            closeOnCancel: false
        }).then(function (isConfirm) {
            if (isConfirm) {
                var data = {};
                var dataArray = $('tbody input[type=checkbox]:checked').map(function () {
                    return $(this).val();
                }).get();
                data ['ids'] = dataArray;
                deleteDevice(data);
            }
        });
    }

    function deleteDevice(data) {
        $.ajax({
            url: '${APIurl}',
            type: 'DELETE',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function (result) {
                window.location.href = '${UserTaskURL}&message=delete_success';
            },
            error: function (error) {
                window.location.href = '${UserTaskURL}&message=error_system';
            },
        });
    }

    clickFunction = () => {
        let data = {};
        data ['taskIds'] = $('tbody input[type=checkbox]:checked').map(function () {
            return $(this).val();
        }).get();
        $.ajax({
            url: `/api/task-follow/create`,
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function (result) {
                window.location.href = '${UserTaskURL}&message=insert_success';
            },
            error: function (error) {
                window.location.href = '${UserTaskURL}&message=error_system';
            },

        });
    }
</script>
</body>
</html>



