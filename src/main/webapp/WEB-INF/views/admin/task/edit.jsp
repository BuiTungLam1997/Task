<%--
  Created by IntelliJ IDEA.
  User: Thinkpad
  Date: 05/06/2023
  Time: 3:09 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglib.jsp" %>
<c:url var="APIurl" value="/manager/api/task"/>
<c:url var="DeviceURL" value="/admin-task-list">
    <c:param name="page" value="1"></c:param>
    <c:param name="limit" value="3"></c:param>
</c:url>
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
                    <!-- PAGE CONTENT BEGINS -->
                    <form class="form-horizontal" role="form" id="formSubmit">
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right" for="title"> Title </label>

                            <div class="col-sm-9">
                                <input type="text" id="title" name="title" placeholder="title" class="col-xs-10 col-sm-5"/>
                            </div>
                        </div>
                        <div class="space-4"></div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right" for="content"> Content </label>

                            <div class="col-sm-9">
                                <input type="text" id="content" name="content" placeholder="content" class="col-xs-10 col-sm-5"/>
                            </div>
                        </div>
                        <div class="space-4"></div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right" for="performer"> Performer </label>

                            <div class="col-sm-9">
                                <input type="text" id="performer" name="performer" placeholder="performer" class="col-xs-10 col-sm-5"/>
                            </div>
                        </div>
                        <div class="space-4"></div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right" for="status"> Status : </label>
                            <div class="col-sm-9">
                                <select name="status" id="status">
                                    <option value="Ready">Ready</option>
                                    <option value="Working">Working</option>
                                    <option value="Done">Done</option>
                                </select>
                            </div>
                        </div>
                        <div class="space-4"></div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right" for="deadlineStart"> Deadline
                                Start </label>

                            <div class="col-sm-9">
                                <input type="datetime-local" id="deadlineStart" name="deadlineStart" placeholder="deadlineStart"
                                       class="col-xs-10 col-sm-5"/>
                            </div>
                        </div>
                        <div class="space-4"></div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right" for="deadlineEnd"> Deadline
                                End </label>

                            <div class="col-sm-9">
                                <input type="datetime-local" id="deadlineEnd" name="deadlineEnd" placeholder="deadlineEnd"
                                       class="col-xs-10 col-sm-5"/>
                            </div>
                        </div>
                        <div class="space-4"></div>


                        <div class="clearfix form-actions">
                            <div class="col-md-offset-3 col-md-9">
                                <button class="btn btn-info" type="button" id="btnAddOrUpdateNew">
                                    <i class="ace-icon fa fa-check bigger-110"></i>
                                    Submit
                                </button>

                                &nbsp; &nbsp; &nbsp;
                                <input class="btn" type="reset">
                                    <i class="ace-icon fa fa-undo bigger-110"></i>
                                    Reset
                                </input>
                            </div>
                        </div>
                        <div class="hr hr-24"></div>
                    </form>
                </div><!-- /.col -->
            </div><!-- /.row -->
        </div><!-- /.page-content -->
    </div>
</div>
<script>
    $('#btnAddOrUpdateNew').click(function (e) {
        e.preventDefault();
        const formData = $('#formSubmit').serializeArray();
        console.log(formData);
    });

    function addDevice(data) {
    }

    function updateDevice(data) {
    }
</script>
</body>
</html>
