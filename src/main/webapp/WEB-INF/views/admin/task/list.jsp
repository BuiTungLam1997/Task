<%--
  Created by IntelliJ IDEA.
  User: Thinkpad
  Date: 01/06/2023
  Time: 1:56 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglib.jsp" %>
<html>
<head>
    <title>Chinh sua bai viet</title>
</head>
<body>
<div class="main-content">
    <form action="<c:url value="#"/>" id="formSubmit" method="get">
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
                        <c:if test="${not empty messageResponse}">
                            <div class="alert alert-${alert}">
                                    ${messageResponse}
                            </div>
                        </c:if>
                        <div class="row">
                            <div class="col-xs-12">
                                <div class="table-responsive">
                                    <div class="pull-right tableTools-container">
                                        <div class="dt-buttons btn-overlap btn-group">
                                            <a flag="info"
                                               class="dt-button buttons-colvis btn btn-white btn-primary btn-bold"
                                               data-toggle="tooltip"
                                               title='Thêm bài viết'
                                               href='<c:url value="/?type=edit"/>'>
															<span>
																<i class="fa fa-plus-circle bigger-110 purple"></i>
															</span>
                                            </a>
                                            <button id="btnDelete" type="button"
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
                                            <%--                                            <th><input type="checkbox" id="checkAll"></th>--%>
                                            <th>title</th>
                                            <th>content</th>
                                            <th>performer</th>
                                            <th>deadlineStart</th>
                                            <th>deadlineEnd</th>
                                            <th>createdBy</th>
                                            <th>Thao tác</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="item" items="${model.listResult}">
                                            <tr>
                                                    <%--                                                <td><input type="checkbox" id="checkbox_${item.id}" value="${item.id}"></td>--%>
                                                <td>${item.title}</td>
                                                <td>${item.content}</td>
                                                <td>${item.performer}</td>
                                                <td>${item.deadlineStart}</td>
                                                <td>${item.deadlineEnd}</td>
                                                <td>${item.createdBy}</td>
                                                <td>
                                                    <a class="btn btn-sm btn-primary btn-edit" data-toggle="tooltip"
                                                       title="Cập nhật bài viết" href='#'><i
                                                            class="fa fa-pencil-square-o" aria-hidden="true"></i>
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                    <%--                                    <ul class="pagination" id="pagination"></ul>--%>
                                    <%--                                    <input type="hidden" value="" id="page" name="page">--%>

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
</script>
</body>
</html>



