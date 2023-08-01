<%--
  Created by IntelliJ IDEA.
  User: Thinkpad
  Date: 25/05/2023
  Time: 10:17 SA
  To change this template use File | Settings | File Templates.
--%>
<%@page import="com.example.task.utils.SecurityUtils" %>
<%@include file="/common/taglib.jsp" %>
<%@ page language="java" contentType="text/html;charset=UTF-8" %>

<!-- Responsive navbar-->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container px-5">
        <a class="navbar-brand" href="#!">Start Bootstrap</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation"><span class="navbar-toggler-icon"></span></button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
                <security:authorize access="isAnonymous()">
                    <li class="nav-item"><a class="nav-link" href="#">Đăng nhập</a></li>
                    <li class="nav-item"><a class="nav-link" href="#">Đăng ký</a></li>
                </security:authorize>
                <security:authorize access="isAuthenticated()">
                    <li class="nav-item"><a class="nav-link" href="#">Wellcome <%=SecurityUtils.getPrincipal().getFullName()%></a></li>
                    <li class="nav-item"><a class="nav-link" href="<c:url value="/thoat"/>">Thoát</a></li>
                </security:authorize>
            </ul>
        </div>
    </div>
</nav>
