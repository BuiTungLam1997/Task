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
            <div id="message">
            </div>
            <!-- PAGE CONTENT BEGINS -->
            <form:form class="form-horizontal" role="form" id="formSubmitTask" modelAttribute="model">
                <br>
                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right" for="title"> Title </label>

                    <div class="col-sm-9">
                        <input type="text" id="title" name="title" readonly Class="col-xs-10 col-sm-5"/>
                    </div>
                </div>
                <div class="space-4"></div>

                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right" for="content"> Content </label>

                    <div class="col-sm-9">
                        <input type="text" id="content" name="content" readonly Class="col-xs-10 col-sm-5"/>
                    </div>
                </div>
                <div class="space-4"></div>


                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right" for="status"> Status : </label>
                    <div class="col-sm-9">
                        <input type="text" id="status" name="status" readonly Class="col-xs-10 col-sm-5"/>
                    </div>
                </div>
                <div class="space-4"></div>
                <div class="space-4"></div>
                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right" for="performer"> Performer </label>

                    <div class="col-sm-9">
                        <input type="text" id="tags" name="performer" cssClass="col-xs-10 col-sm-5"/>
                        <script>
                            $(function () {
                                const availableTutorials = [
                                    <c:forEach var="item" items="${listUsername}">
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
                <div class="clearfix form-actions">
                    <div class="col-md-offset-3 col-md-9">
                        <button class="btn btn-info" type="button" id="btnSend">
                            <i class="ace-icon fa fa-check bigger-110"></i>
                            Submit
                        </button>
                    </div>
                </div>
                <input type="hidden" value="${id}" id="id" name="id">
            </form:form>
        </div><!-- /.col -->
    </div><!-- /.row -->
</div>
</div>
<script type='text/javascript' src="/template/custom/admin/js/task/giveAJob.js"></script>
</body>
</html>

