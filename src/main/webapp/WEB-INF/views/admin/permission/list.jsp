<%--
Created by IntelliJ IDEA.
User: Thinkpad
Date: 01/06/2023
Time: 1:56 CH
To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglib.jsp" %>
<c:url var="APIurl" value="/api/permission"/>
<c:url var="UserGroupAPIurl" value="/api/user-group"/>
<c:url var="PermissionURL" value="/admin-permission-list">
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
                                <form action="<c:url value="/admin-search-permission"/>" method="get" id="formSearch">
                                    <input type="text" placeholder="Search.." name="search" id="search" value="">
                                    <button type="submit" onclick="fun()" id="btnSearch">Submit
                                    </button>
                                </form>

                                <form action="<c:url value="${APIurl}"/>" method="put" id="formGroup">
                                    <select id="groupId" name="groupId">
                                        <c:forEach var="item" items="${modelGroup.listResult}">
                                            <option value="${item.id}">${item.name}</option>
                                        </c:forEach>
                                    </select>
                                    <button type="submit" id="btnAdd"> Add</button>
                                </form>

                                <form action="<c:url value="/admin-permission-list"/>" id="formSubmit" method="get">
                                    <div class="pull-right tableTools-container">
                                        <div class="dt-buttons btn-overlap btn-group">
                                            <c:url var="createUserURL" value="/admin-permission-edit"/>
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
                                            <th>Id</th>
                                            <th>Name</th>
                                            <th>Code</th>
                                            <th>Mote</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="item" items="${model.listResult}">
                                            <tr>
                                                <td><input type="checkbox" id="checkbox_${item.id}"
                                                           value="${item.id}">
                                                </td>
                                                <td>${item.id}</td>
                                                <td>${item.name}</td>
                                                <td>${item.code}</td>
                                                <td>${item.note}</td>
                                                <td>
                                                    <c:url var="updateTaskURL" value="/admin-permission-edit">
                                                        <c:param name="id" value="${item.id}"/>
                                                    </c:url>
                                                    <a class="btn btn-sm btn-primary btn-edit" data-toggle="tooltip"
                                                       title="Cập nhật bài viết" href='${updateTaskURL}'>
                                                        <i class="fa fa-pencil-square-o" aria-hidden="true"></i>
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                    <ul class="pagination" id="pagination"></ul>
                                    <input type="hidden" value="" id="page" name="page">
                                    <input type="hidden" value="" id="limit" name="limit">
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
                if (currentPage != page) {
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

    $('#btnAdd').click(function (e) {
        e.preventDefault();
        const formData = $('#formGroup').serializeArray();
        const data = {};
        $.each(formData, function (i, v) {
            data["" + v.name + ""] = v.value;
        });
        const dataArray = $('tbody input[type=checkbox]:checked').map(function () {
            return $(this).val();
        }).get();
        data ['permissionIds'] = dataArray;
        addGroup(data);

    });

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
                window.location.href = '${PermissionURL}&message=delete_success';
            },
            error: function (error) {
                window.location.href = '${PermissionURL}&message=error_system';
            },
        });
    }

    function addGroup(data) {
        $.ajax({
            url: '${UserGroupAPIurl}',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function (result) {
                window.location.href = '${PermissionURL}&message=delete_success';
            },
            error: function (error) {
                window.location.href = '${PermissionURL}&message=error_system';
            },
        });
    }
</script>
</body>
</html>



