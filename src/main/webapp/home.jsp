<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<jsp:include page="angHeader.jsp"/>

<script type="text/javascript" src="/js/controllers.js"></script>

<shiro:guest>
    Hi there!  Please <a href="${pageData.httpsToggle}://${pageData.domain}/login">Login</a> or <a href="signup.jsp">Signup</a> today!
</shiro:guest>

<shiro:user>
    <jsp:include page="practice.jsp"/>

    <jsp:include page="practiceOverview.jsp"/>
</shiro:user>

<jsp:include page="footer.jsp"/>