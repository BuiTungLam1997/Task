<%--
  Created by IntelliJ IDEA.
  User: Thinkpad
  Date: 05/06/2023
  Time: 3:09 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglib.jsp" %>
<c:url var="GroupAPIURL" value="/api/group"/>
<c:url var="ListURL" value="/admin-group-list">
    <c:param name="page" value="1"></c:param>
    <c:param name="limit" value="4"></c:param>
</c:url>

<c:url var="EditURL" value="/admin-group-edit"/>
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
                    <c:if test="${not empty MESSAGE}">
                        <div class="alert alert-${ALERT}">
                                ${MESSAGE}
                        </div>
                    </c:if>
                    <!-- PAGE CONTENT BEGINS -->
                    <form:form class="form-horizontal" role="form" id="formSubmit" modelAttribute="model">
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right" for="name"> Name </label>

                            <div class="col-sm-9">
                                <form:input path="name" cssClass="col-xs-10 col-sm-5"/>
                            </div>
                        </div>
                        <div class="space-4"></div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right" for="code"> Code </label>

                            <div class="col-sm-9">
                                <form:input path="code" cssClass="col-xs-10 col-sm-5"/>
                            </div>
                        </div>
                        <div class="space-4"></div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right" for="note"> Note </label>

                            <div class="col-sm-9">
                                <form:input path="note" cssClass="col-xs-10 col-sm-5"/>
                            </div>
                        </div>
                        <div class="space-4"></div>


                        <div class="clearfix form-actions">
                            <div class="col-md-offset-3 col-md-9">
                                <c:if test="${not empty model.id}">
                                    <button class="btn btn-info" type="button" id="btnAddOrUpdateNew">
                                        <i class="ace-icon fa fa-check bigger-110"></i>
                                        Update
                                    </button>
                                </c:if>
                                <c:if test="${empty model.id}">
                                    <button class="btn btn-info" type="button" id="btnAddOrUpdateNew">
                                        <i class="ace-icon fa fa-check bigger-110"></i>
                                        Create
                                    </button>
                                </c:if>
                                <input class="btn" type="reset">
                                <i class="ace-icon fa fa-undo bigger-110"></i>
                                </input>
                            </div>
                        </div>
                        <div class="hr hr-24"></div>
                        <form:hidden path="id" id="id"/>
                    </form:form>
                </div><!-- /.col -->
            </div><!-- /.row -->
        </div><!-- /.page-content -->
    </div>
</div>
<script>
    $('#btnAddOrUpdateNew').click(function (e) {
        e.preventDefault();
        const formData = $('#formSubmit').serializeArray();
        const data = {};
        $.each(formData, function (i, v) {
            data["" + v.name + ""] = v.value;
        });
        const id = $('#id').val();


        if (id === "") {
            create(data);
        } else {
            update(data);
        }
    });

    function create(data) {
        $.ajax({
            url: '${GroupAPIURL}',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            dataType: 'json',
            success: function (result) {
                window.location.href = '${ListURL}&message=insert_success';
            },
            error: function (error) {
                window.location.href = '${EditURL}?message=error_system';
            },
        });
    }

    $(".alert").delay(2000).slideUp(200, function () {
        $(this).alert('close');
    });

    function update(data) {
        $.ajax({
            url: '${GroupAPIURL}',
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(data),
            dataType: 'json',
            success: function (result) {
                window.location.href = '${ListURL}&message=update_success';
            },
            error: function (error) {
                window.location.href = '${ListURL}&message=error_system';
            },
        });
    }

</script>
</body>
</html>
