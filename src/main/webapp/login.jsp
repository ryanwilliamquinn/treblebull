<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="header.jsp"/>
<script src="/js/controllers.js"></script>

<form name="loginform" action="/login" method="post">
    <table align="left" border="0" cellspacing="0" cellpadding="3">
        <tr>
            <td>Username:</td>
            <td><input type="text" name="username" id="username" ng-model="username" maxlength="30" autofocus></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input type="password" name="password" id="password" ng-model="password" maxlength="30"></td>
        </tr>
        <tr>
            <td colspan="2" align="left"><input type="checkbox" name="rememberMe" ng-model="rememberMe"><font size="2">Remember Me</font></td>
        </tr>
        <tr>
            <td colspan="2" align="right"><input type="submit" name="submit" id="submit" value="Login"></td>
        </tr>
    </table>
</form>

<c:if test='${!empty loginFailure && loginFailure != ""}'>
   <div style="error">Incorrect username or password, please try again.</div>
   ${loginFailure}
</c:if>

<jsp:include page="footer.jsp"/>