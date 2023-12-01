<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/common/taglib.jsp" %>
<%@ page import="com.example.task.utils.SecurityUtils"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<head>
    <link href="/template/admin/css/header.css" rel="stylesheet" type="text/css" media="all"/>
</head>
<div id="navbar" class="navbar navbar-default ace-save-state title-header">
    <div class="navbar-container ace-save-state" id="navbar-container">
        <div class="navbar-header pull-left">
            <a href="#" class="navbar-brand">
                <small>
                    <i class="fa fa-leaf"></i>
                    Trang quản trị
                </small>
            </a>
        </div>
        <div class="navbar-buttons navbar-header pull-right collapse navbar-collapse" role="navigation">
            <ul class="nav ace-nav">
                <li class="light-blue dropdown-modal">
                <li class="nav-item">
                    <a class="nav-link" href="/user-home">Wellcome <%=SecurityUtils.getPrincipal().getFullName()%></a>
            </li>
                <li class="nav-item">
                    <a class="nav-link" href="/admin-change-password">Đổi mật khẩu</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/logout">Thoát</a>
                </li>
                </li>
            </ul>
        </div>
    </div>
</div>
