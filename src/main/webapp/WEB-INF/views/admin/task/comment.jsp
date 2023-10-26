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
<c:url var="CommentAPI" value="/api/manager/comment"/>
<c:url var="CommentURL" value="/comment"/>
<c:url var="TaskAPI" value="/api/task/done-task"/>
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
            <form:form class="form-horizontal" role="form" id="formSubmitTask" modelAttribute="modelTask">
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
                    <label class="col-sm-3 control-label no-padding-right" for="performer"> Performer </label>

                    <div class="col-sm-9">
                        <form:input path="performer" readonly="true" cssClass="col-xs-10 col-sm-5"/>
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
                    <label class="col-sm-3 control-label no-padding-right" for="performer">
                        Level of difficult : </label>
                    <div class="col-sm-9">
                        <form:input path="levelOfDifficulty"  cssClass="col-xs-10 col-sm-5"/>
                        <button class="btn btn-info" type="button" id="btnUpdatePoint">
                            <i class="ace-icon fa fa-check bigger-110"></i>
                            Cập nhật
                        </button>
                    </div>
                </div>
                <div class="hr hr-24"></div>
                <input type="hidden" id="id" name="id" value="${modelTask.id}">
            </form:form>
        </div><!-- /.col -->
    </div><!-- /.row -->
    <form:form modelAttribute="modelComment">
    <div class="be-comment-block">
        <h1 class="comments-title">Comments (${modelComment.totalComment})</h1>
        <c:forEach var="item" items="${modelComment.listResult}">
            <div class="be-comment">
                <div class="be-img-comment">
                    <a href="blog-detail-2.html">
                        <img src="https://bootdey.com/img/Content/avatar/avatar1.png" alt="" class="be-ava-comment">
                    </a>
                </div>
                <div class="be-comment-content">

				<span class="be-comment-name">
					<a href="blog-detail-2.html" id="fullName" name="fullName">${item.fullName}</a>
					</span>
                    <span class="be-comment-time">
                            <i class="fa fa-clock-o">
                                <a>${item.createdDate}</a>
                            </i>
				        </span>
                    <p class="be-comment-text">
                        <a>${item.content}</a>
                    </p>
                </div>
            </div>
        </c:forEach>
        </form:form>
        <form:form class="form-block" role="form" id="formSubmitComment" modelAttribute="modelTask">
            <div class="row">
                <div class="col-xs-12 col-sm-12">
                    <div class="form-group fl_icon">
                        <div class="icon"><i class="fa fa-user"></i></div>
                        <input class="form-input" type="text" name="username" readonly placeholder="Your name"
                               value="<%=SecurityUtils.getPrincipal().getFullName()%>">
                    </div>
                </div>
                <div class="col-xs-12">
                    <div class="form-group">
                        <textarea class="form-input" required="" placeholder="Your text" name="content"></textarea>
                    </div>
                </div>
            </div>
            <button class="btn btn-info" type="button" id="btnSendComment">
                <i class="ace-icon fa fa-check bigger-110"></i>
                Submit
            </button>
            <button class="btn btn-info" type="button" id="btnDoneTask">
                <i class="ace-icon fa fa-check bigger-110"></i>
                Hoàn thành
            </button>
            <input type="hidden" value="${modelTask.id}" id="taskId" name="taskId">
        </form:form>
    </div>
</div>
<script>
    $('#btnSendComment').click(function (e) {
        e.preventDefault();
        const formData = $('#formSubmitComment').serializeArray();
        const data = {};
        $.each(formData, function (i, v) {
            data["" + v.name + ""] = v.value;
        });
        create(data);
    });
    $('#btnDoneTask').click(function (e) {
        e.preventDefault();
        const data = {};
        data["id"] = document.getElementById(`taskId`).value;
        update(data);
    });

    function create(data) {
        $.ajax({
            url: '${CommentAPI}',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            dataType: 'json',
            success: function (result) {
                window.location.href = '${CommentURL}?id=' + result.data.taskId + '&message=insert_success';
            },
            error: function (error) {
                window.location.href = '${CommentURL}?message=error_system';
            },
        });
    }

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
<script type='text/javascript' src="/template/custom/admin/js/comment.js"></script>
</body>
</html>
