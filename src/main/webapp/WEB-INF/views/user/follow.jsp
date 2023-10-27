<%--
Created by IntelliJ IDEA.
User: Thinkpad
Date: 01/06/2023
Time: 1:56 CH
To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglib.jsp" %>
<c:url var="UserAPI" value="/api-user"/>
<html>
<head>
    <title>Chinh sua bai viet</title>
</head>
<body>
<div class="main-content">
    <form action="<c:url value="/admin-task-follow"/>" id="formSubmit" method="get" modelAttribute="model">
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
                                    <div class="pull-right tableTools-container">
                                        <div class="dt-buttons btn-overlap btn-group">
                                            <button id="btnUnFollow" type="button" onclick="unFollow()"
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
                                            <th>Ngày bắt đầu</th>
                                            <th>Ngày kết thúc</th>
                                            <th>Trạng thái</th>
                                        </tr>
                                        </thead>
                                        <tbody id="listTaskFollow">
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
<script type="text/javascript">
    $(".alert").delay(2000).slideUp(200, function () {
        $(this).alert('close');
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
                window.location.href = '${UserURL}&message=delete_success';
            },
            error: function (error) {
                window.location.href = '${UserURL}&message=error_system';
            },
        });
    }

    let unFollow = () => {
        let data = {};
        data ['taskIds'] = $('tbody input[type=checkbox]:checked').map(function () {
            return $(this).val();
        }).get();
        $.ajax({
            url: `/api/task-follow/delete`,
            type: 'DELETE',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function (result) {
                window.location.href = '/user-task-follow?message=delete_success';
            },
            error: function (error) {
                window.location.href = '/user-task-follow?message=error!';
            },
        });
    }
</script>
<script type='text/javascript' src="/template/custom/user/js/task-follow.js"></script>
</body>
</html>



