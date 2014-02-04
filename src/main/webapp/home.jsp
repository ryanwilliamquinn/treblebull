<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<jsp:include page="angHeader.jsp"/>

<script type="text/javascript" src="/js/controllers.js"></script>

<shiro:guest>
    <p>Hello - Treblebull.com is a site for tracking your darts practice results.</p>
    <p>It has an easy system for entering results, graphs (currently ugly), and is very free.</p>
    <p>Please <a href="${pageData.httpsToggle}://${pageData.domain}/login">Login</a> or <a href="signup.jsp">Signup</a></p>
</shiro:guest>

<shiro:user>
    <jsp:include page="practice.jsp"/>

    <jsp:include page="practiceOverview.jsp"/>
</shiro:user>

<jsp:include page="footer.jsp"/>