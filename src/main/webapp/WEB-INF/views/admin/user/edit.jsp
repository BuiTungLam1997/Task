<%--
  Created by IntelliJ IDEA.
  User: Thinkpad
  Date: 05/06/2023
  Time: 3:09 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglib.jsp" %>
<c:url var="UserAPIURL" value="/api/user"/>
<c:url var="ListURL" value="/admin-user-list">
    <c:param name="page" value="1"></c:param>
    <c:param name="limit" value="4"></c:param>
</c:url>
<html>
<head>
    <title>Chỉnh sửa nhân viên</title>
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
                    <div id="message"></div>
                    <!-- PAGE CONTENT BEGINS -->
                    <form:form class="form-horizontal" role="form" id="formSubmit" modelAttribute="model">
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right" for="username"> Username </label>

                            <div class="col-sm-9">
                                <input type="text" id="username" name="username" value="" Class="col-xs-10 col-sm-5"/>
                            </div>
                        </div>
                        <div class="space-4"></div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right" for="password"> Password </label>

                            <div class="col-sm-9">
                                <input type="text" id="password" name="password" value="" Class="col-xs-10 col-sm-5"/>
                            </div>
                        </div>
                        <div class="space-4"></div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right" for="fullName"> Full Name </label>

                            <div class="col-sm-9">
                                <input type="text" name="fullName" id="fullName" value="" Class="col-xs-10 col-sm-5"/>
                            </div>
                        </div>
                        <div class="space-4"></div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right" for="status"> Status : </label>
                            <div class="col-sm-9">
                                <input type="text" name="status" id="status" value="" Class="col-xs-10 col-sm-5"/>
                            </div>
                        </div>
                        <div class="space-4"></div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right" for="createdBy"> createdBy : </label>
                            <div class="col-sm-9">
                                <input type="text" name="createdBy" id="createdBy" value="" Class="col-xs-10 col-sm-5"/>
                            </div>
                        </div>

                        <div class="space-4"></div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right" for="createdDate"> Created Date
                            </label>
                            <div class="col-sm-9">
                                <input type="datetime-local" id="createdDate" name="createdDate"
                                       value="" Class="col-xs-10 col-sm-5"/>
                            </div>
                        </div>
                        <div class="space-4"></div>
                        <div class="clearfix form-actions">
                            <div class="col-md-offset-3 col-md-9">
                                <c:if test="${not empty id}">
                                    <button class="btn btn-info" type="button" id="btnAddOrUpdateNew">
                                        <i class="ace-icon fa fa-check bigger-110"></i>
                                        Update
                                    </button>
                                </c:if>
                                <c:if test="${empty id}">
                                    <button class="btn btn-info" type="button" id="btnAddOrUpdateNew">
                                        <i class="ace-icon fa fa-check bigger-110"></i>
                                        Create
                                    </button>
                                </c:if>
                                <input class="btn" type="reset">
                                </input>
                            </div>
                        </div>
                        <div class="hr hr-24"></div>
                        <input type="hidden" name="id" id="id" value="${id}"/>
                    </form:form>
                </div><!-- /.col -->
            </div><!-- /.row -->
        </div><!-- /.page-content -->
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>Tiêu đề</th>
                <th>Nội dung</th>
                <th>Ngày bắt đầu</th>
                <th>Ngày kết thúc</th>
                <th>createdBy</th>
                <th>Trạng thái</th>
            </tr>
            </thead>
            <tbody id="listGroup">
            </tbody>
        </table>
        <ul class="pagination" id="pagination"></ul>
    </div>
</div>
<script type='text/javascript' src="/template/custom/admin/js/user/edit.js"></script>
<script type='text/javascript' src="/template/custom/admin/js/user-list.js"></script>
</body>
</html>
