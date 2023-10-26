<%--
  Created by IntelliJ IDEA.
  User: Thinkpad
  Date: 25/05/2023
  Time: 10:18 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@include file="/common/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div id="sidebar" class="sidebar                  responsive                    ace-save-state">
    <script type="text/javascript">
        try {
            ace.settings.loadState('sidebar')
        } catch (e) {
        }
    </script>
    <div class="sidebar-shortcuts">
        <div class="sidebar-shortcuts-large">
            <button class="btn btn-success">
                <i class="ace-icon fa fa-signal"></i>
            </button>

            <button class="btn btn-info">
                <i class="ace-icon fa fa-pencil"></i>
            </button>
            <button class="btn btn-warning">
                <i class="ace-icon fa fa-users"></i>
            </button>
            <button class="btn btn-danger">
                <i class="ace-icon fa fa-cogs"></i>
            </button>
        </div>
        <div class="sidebar-shortcuts-mini">
            <span class="btn btn-success"></span>

            <span class="btn btn-info"></span>

            <span class="btn btn-warning"></span>

            <span class="btn btn-danger"></span>
        </div>
    </div>
    <ul class="nav nav-list">
        <li>
            <a href="#" class="dropdown-toggle">
                <i class="menu-icon fa fa-list"></i>
                <span class="menu-text"></span>
                Quản trị
                <b class="arrow fa fa-angle-down"></b>
            </a>
            <b class="arrow"></b>
            <ul class="submenu">
                <li>
                    <a href='<c:url value="/admin-task-list?page=1&limit=4"/>'>
                        <i class="menu-icon fa fa-caret-right"></i>
                        Danh sách công việc
                    </a>
                    <b class="arrow"></b>
                </li>
                <li>
                    <a href='<c:url value="/admin-user-list?page=1&limit=4"/>'>
                        <i class="menu-icon fa fa-caret-right"></i>
                        Danh sách người dùng
                    </a>
                    <b class="arrow"></b>
                </li>
                <li>
                    <a href='<c:url value="/admin-permission-list"/>'>
                        <i class="menu-icon fa fa-caret-right"></i>
                        Danh sách quyền hạn
                    </a>
                    <b class="arrow"></b>
                </li>
                <li>
                    <a href='<c:url value="/admin-group-list?page=1&limit=4"/>'>
                        <i class="menu-icon fa fa-caret-right"></i>
                        Danh sách group
                    </a>
                    <b class="arrow"></b>
                </li>
                <li>
                    <a href="#" class="dropdown-toggle">
                        <i class="menu-icon fa fa-list"></i>
                        <span class="menu-text"></span>
                        Danh sách admin group
                        <b class="arrow fa fa-angle-down"></b>
                    </a>
                    <ul class="submenu">
                        <li>
                            <a href='<c:url value="/admin-group-user-list"/>'>
                                <i class="menu-icon fa fa-caret-right"></i>
                                Admin User
                            </a>
                            <b class="arrow"></b>
                        </li>
                        <li>
                            <a href='<c:url value="/admin-group-task-list"/>'>
                                <i class="menu-icon fa fa-caret-right"></i>
                                Admin Task
                            </a>
                            <b class="arrow"></b>
                        </li>
                    </ul>
                </li>
            </ul>
        </li>
    </ul>
    <div class="sidebar-toggle sidebar-collapse">
        <i class="ace-icon fa fa-angle-double-left ace-save-state" data-icon1="ace-icon fa fa-angle-double-left"
           data-icon2="ace-icon fa fa-angle-double-right"></i>
    </div>
</div>
