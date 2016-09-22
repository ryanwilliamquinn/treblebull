<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="header.jsp"/>

<div style="margin-bottom:20px;">
    Fill out the captcha and your email address, and we will send you an email with your username.
</div>
<form action="requestUsername" method="post">
    <div style="margin-top:20px;">
        <label for="usrEmail">Enter your email</label>
        <input type="email" name="usrEmail" id="usrEmail" value="${usrEmail}"/>
        <div class="g-recaptcha" data-sitekey="6LcGY-gSAAAAALRTpB9mm7WYUqFTBz74WCXzs-gN" style="margin-top: 10px;"></div>
        <input type="submit" value="submit" style="margin: 10px 0px 0px 5px;"/>
    </div>
</form>

<c:if test="${captchaFailure}">
    <div style="margin-top: 10px;">
        Captcha was not completed correctly, please try again
    </div>
</c:if>
<jsp:include page="footer.jsp"/>