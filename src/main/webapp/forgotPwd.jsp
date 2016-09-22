<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="header.jsp"/>

<div style="margin-bottom:20px;">
    Fill out the captcha and your email address, and we will send you an email with instructions on how to reset your password.
</div>
<form action="requestPwdReset" method="post">
    <div style="margin-top:20px;">
        <label for="usrEmail">Enter your email</label>
        <input type="email" name="usrEmail" id="usrEmail" value="${usrEmail}"/>
        <div class="g-recaptcha" data-sitekey="6LcGY-gSAAAAALRTpB9mm7WYUqFTBz74WCXzs-gN"></div>
        <input type="submit" value="submit" style="margin-left:5px;"/>
    </div>
</form>

<c:if test="${captchaFailure}">
    <div>
        Please try again
    </div>
</c:if>
<jsp:include page="footer.jsp"/>