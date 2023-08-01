<%--
  Created by IntelliJ IDEA.
  User: Thinkpad
  Date: 03/06/2023
  Time: 9:53 SA
  To change this template use File | Settings | File Templates.
--%>
<%@include file="/common/taglib.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Đăng nhâp</title>
</head>
<body id="LoginForm">
<div class="container">
    <c:if test="${param.incorrectAccount != null}">
        <div class="alert alert-danger" role="alert" id="alert">
                Username or password incorrect !
        </div>
    </c:if>
    <c:if test="${param.accessDenied != null}">
        <div class="alert alert-danger" role="alert" id="alert">
            You Not authorize!
        </div>
    </c:if>
    <div class="d-flex justify-content-center h-100">
        <div class="card">
            <div class="card-header">
                <h3>Sign In</h3>
                <div class="d-flex justify-content-end social_icon">
                    <span><i class="fab fa-facebook-square"></i></span>
                    <span><i class="fab fa-google-plus-square"></i></span>
                    <span><i class="fab fa-twitter-square"></i></span>
                </div>
            </div>
            <form action="/j_spring_security_check" id="formLogin" method="POST">
                <div class="card-body">
                    <form>
                        <div class="input-group form-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text"><i class="fas fa-user"></i></span>
                            </div>
                            <input type="text" class="form-control" id="userName" name="j_username"
                                   placeholder="username">

                        </div>
                        <div class="input-group form-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text"><i class="fas fa-key"></i></span>
                            </div>
                            <input type="password" class="form-control" id="password" name="j_password"
                                   placeholder="password">
                        </div>
                        <div class="row align-items-center remember">
                            <input type="checkbox">Remember Me
                        </div>
                        <input type="hidden" value="login" id="action" name="action">
                        <div class="form-group">
                            <button type="submit" value="Login" class="btn float-right login_btn">Đăng nhập</button>
                        </div>
                    </form>
                </div>
            </form>
            <div class="card-footer">
                <div class="d-flex justify-content-center links">
                    Don't have an account?<a href="#">Sign Up</a>
                </div>
                <div class="d-flex justify-content-center">
                    <a href="#">Forgot your password?</a>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(".alert").delay(2000).slideUp(200, function () {
        $(this).alert('close');
    });
</script>
</body>
</html>
