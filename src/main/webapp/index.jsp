<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<jsp:include page="header.jsp"/>

<script type="text/javascript" src="/js/controllers.js"></script>

<shiro:guest>
    Hi there!  Please <a href="/login">Login</a> or <a href="signup.jsp">Signup</a> today!
</shiro:guest>

Hello sendgrid provisioners o/

<shiro:user>
    <jsp:include page="practice.jsp"/>
</shiro:user>

<jsp:include page="footer.jsp"/>