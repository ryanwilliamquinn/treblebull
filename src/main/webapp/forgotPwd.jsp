<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="net.tanesha.recaptcha.ReCaptcha" %>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory" %>

<jsp:include page="header.jsp"/>

<div>
    Fill out the captcha and your email address, and we will send you a new password.
</div>
<form action="resetPwd" method="post">
    <%
      ReCaptcha c = ReCaptchaFactory.newReCaptcha("6LcGY-gSAAAAALRTpB9mm7WYUqFTBz74WCXzs-gN", "6LcGY-gSAAAAACFtP57eO62Zh3cDWNZuSBnA7-wT", false);
      out.print(c.createRecaptchaHtml(null, null));
    %>

    <label for="usrEmail">Enter your email</label>
    <input type="email" name="usrEmail" id="usrEmail" value="${usrEmail}"/>
    <input type="submit" value="submit"/>
</form>

<c:if test="${captchaFailure}">
    <div>
        Please try again
    </div>
</c:if>
<jsp:include page="footer.jsp"/>