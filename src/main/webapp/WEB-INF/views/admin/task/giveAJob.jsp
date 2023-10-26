<%--
  Created by IntelliJ IDEA.
  User: Thinkpad
  Date: 30/08/2023
  Time: 11:50 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglib.jsp" %>
<%@ page import="com.example.task.utils.SecurityUtils" %>
<c:url var="TaskAPI" value="/api/task"/>
<c:url var="TaskURL" value="/admin-task-list"/>
<html>
<head>
    <title>Comment</title>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-xs-12">
            <c:if test="${not empty MESSAGE}">
                <div class="alert alert-${ALERT}">
                        ${MESSAGE}
                </div>
            </c:if>
            <!-- PAGE CONTENT BEGINS -->
            <form:form class="form-horizontal" role="form" id="formSubmitTask" modelAttribute="model">
                <br>
                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right" for="title"> Title </label>

                    <div class="col-sm-9">
                        <form:input path="title" readonly="true" cssClass="col-xs-10 col-sm-5"/>
                    </div>
                </div>
                <div class="space-4"></div>

                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right" for="content"> Content </label>

                    <div class="col-sm-9">
                        <form:input path="content" readonly="true" cssClass="col-xs-10 col-sm-5"/>
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
                <div class="space-4"></div>
                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right" for="performer"> Performer </label>

                    <div class="col-sm-9">
                        <form:input id="tags" path="performer" cssClass="col-xs-10 col-sm-5"/>
                        <script>
                            $(function () {
                                const availableTutorials = [
                                    <c:forEach var="item" items="${model.listUsername}">
                                    {label: "${item}"},
                                    </c:forEach>
                                ];
                                $("#tags").autocomplete({
                                    source: availableTutorials
                                });
                            });
                        </script>
                    </div>
                </div>
                <div class="hr hr-24"></div>
                <button class="btn btn-info" type="button" id="btnSend">
                    <i class="ace-icon fa fa-check bigger-110"></i>
                    Submit
                </button>
                <input type="hidden" value="${model.id}" id="id" name="id">
            </form:form>
        </div><!-- /.col -->
    </div><!-- /.row -->
</div>
</div>
<script>
    $('#btnSend').click(function (e) {
        e.preventDefault();
        const formData = $('#formSubmitTask').serializeArray();
        const data = {};
        $.each(formData, function (i, v) {
            data["" + v.name + ""] = v.value;
        });
        data["status"] = 'WORKING';
        update(data);
    });

    function update(data) {
        $.ajax({
            url: '${TaskAPI}',
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(data),
            dataType: 'json',
            success: function (result) {
                window.location.href = '${TaskURL}?message=insert_success';
            },
            error: function (error) {
                window.location.href = '${TaskURL}?message=error_system';
            },
        });
    }

    $(".alert").delay(2000).slideUp(200, function () {
        $(this).alert('close');
    });
</script>
</body>
</html>

