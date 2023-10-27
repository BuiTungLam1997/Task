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
                    <c:if test="${not empty MESSAGE}">
                        <div class="alert alert-${ALERT}">
                                ${MESSAGE}
                        </div>
                    </c:if>
                    <!-- PAGE CONTENT BEGINS -->
                    <form:form class="form-horizontal" role="form" id="formSubmit" modelAttribute="model">
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="username"> Username </label>
                        <div class="col-sm-9">
                            <form:input path="username" readonly="true" cssClass="col-xs-10 col-sm-5"/>
                        </div>
                    </div>
                    <div class="space-4"></div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="fullName"> Full
                            Name </label>
                        <div class="col-sm-9">
                            <form:input path="fullName" readonly="true" cssClass="col-xs-10 col-sm-5"/>
                        </div>
                    </div>
                    <div class="space-4"></div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="fullName"> Total
                            Point </label>
                        <div class="col-sm-9">
                            <form:input path="totalPoint" readonly="true" cssClass="col-xs-10 col-sm-5"/>
                        </div>
                    </div>
                    <div class="space-4"></div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="fullName"> Period : </label>
                        <div class="col-sm-9">
                            <input type="text" name="period" id="period" value="">
                            <input type="button" name="btnPeriod" id="btnPeriod" value="Select">
                        </div>
                    </div>
                </div>
                <div class="hr hr-24"></div>
                <form:hidden path="id" id="id"/>
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
            <th>Point</th>
        </tr>
        </thead>
        <tbody id="listTask">
        </tbody>
    </table>
    <ul class="pagination" id="pagination"></ul>
</div>
</div>
<script>
    $('#btnPeriod').click(function (e) {
        let period = document.getElementById('period').value;
    });

</script>
<script type='text/javascript' src="/template/custom/admin/js/report.js"></script>
</body>
</html>
