<%--
  Created by IntelliJ IDEA.
  User: Thinkpad
  Date: 05/06/2023
  Time: 3:09 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglib.jsp" %>
<c:url var="APIurl" value="/api-admin-device"/>
<c:url var="DeviceURL" value="/admin-device-list">
    <c:param name="page" value="1"></c:param>
    <c:param name="maxPageItem" value="3"></c:param>
    <c:param name="type" value="list"></c:param>
</c:url>
<html>
<head>
    <title>Chỉnh sửa bài viết</title>
</head>
<body>
<div class="main-content">
    <div class="main-content-inner">
        <div class="breadcrumbs" id="breadcrumbs">
            <script type="text/javascript">
                try {
                    ace.settings.check('breadcrumbs', 'fixed')
                } catch (e) {
                }
            </script>
            <ul class="breadcrumb">
                <li>
                    <i class="ace-icon fa fa-home home-icon"></i>
                    <a href="#">Trang chủ</a>
                </li>
                <li class="active">Chỉnh sửa bài viết</li>
            </ul><!-- /.breadcrumb -->
        </div>
        <div class="page-content">
            <div class="row">
                <div class="col-xs-12">
                    <c:if test="${not empty messageResponse}">
                        <div class="alert alert-${alert}">
                                ${messageResponse}
                        </div>
                    </c:if>
                    <form id="formSubmit">
                        <br/>
                        <br/>
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right">Thông tin thiết bị</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" id="information" name="information"
                                       value="${model.information}"/>
                            </div>
                        </div>
                        <br/>
                        <br/>
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right">Lịch sử sửa chữa</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" id="history" name="history"
                                       value="${model.history}"/>
                            </div>
                        </div>
                        <br/>
                        <br/>
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right">Site</label>
                            <div class="col-sm-9">
                                <select class="form-control" id="siteCode" name="siteCode">
                                    <c:if test="${empty model.siteCode}">
                                        <option>Chọn khu vực</option>
                                        <c:forEach var="item" items="${side}">
                                            <option value="${item.code}">${item.name}</option>
                                        </c:forEach>
                                    </c:if>
                                    <c:if test="${not empty model.siteCode}">
                                        <c:forEach var="item" items="${side}">
                                            <option value="${item.code}"
                                                    <c:if test="${item.code == model.siteCode}">selected="selected"</c:if>>${item.name}</option>
                                            <option>Chọn khu vực</option>
                                        </c:forEach>
                                    </c:if>
                                </select>
                            </div>
                        </div>
                        <br/>
                        <br/>
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right">Người sử dụng</label>
                            <div class="col-sm-9">
                                <textarea rows="" cols="" id="userName" name="userName"
                                          style="width: 820px;height: 175px">${model.userName}</textarea>
                            </div>
                        </div>
                        <br/>
                        <br/>
                        <div class="form-group">
                            <div class="col-sm-12">
                                <c:if test="${not empty model.id}">
                                    <input type="button" value="Cap nhat bai viet" id="btnAddOrUpdateNew"
                                           name=""></input>
                                </c:if>
                                <c:if test="${empty model.id}">
                                    <input type="button" value="Them moi bai viet" id="btnAddOrUpdateNew"
                                           name=""></input>
                                </c:if>
                            </div>
                        </div>
                        <input type="hidden" value="${model.id}" id="id" name="id">
                        <input type="hidden" value="0" id="isDelete" name="isDelete">
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $('#btnAddOrUpdateNew').click(function (e) {
        e.preventDefault();
        var data = {};
        var formData = $('#formSubmit').serializeArray();
        $.each(formData, function (i, v) {
            data["" + v.name + ""] = v.value;
        });
        var id = $('#id').val();
        if (id == "") {
            addDevice(data);
        } else {
            updateDevice(data);
        }
    });

    function addDevice(data) {
        $.ajax({
            url: '${APIurl}',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            dataType: 'json',
            success: function (result) {
                window.location.href = '${DeviceURL}&id=' + result.id + '&message=insert_success';
            },
            error: function (error) {
                window.location.href = '${DeviceURL}&message=error_system';
            },
        });
    }

    function updateDevice(data) {
        $.ajax({
            url: '${APIurl}',
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(data),
            dataType: 'json',
            success: function (result) {
                window.location.href = '${DeviceURL}&id=' + result.id + '&message=update_success';
            },
            error: function (error) {
                window.location.href = '${DeviceURL}&message=error_system';
            },
        });
    }
</script>
</body>
</html>
