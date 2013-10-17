
<jsp:include page="angHeader.jsp"/>

<script src="/js/uiControllers.js"></script>
<script type="text/javascript" src="/js/angularUI/module.js"></script>
<script type="text/javascript" src="/js/angularUI/validate.js"></script>
<script type="text/javascript" src="/js/jquery/jquery-1.8.3.js"></script>
<script type="text/javascript" src="/js/jquery/jquery-ui-1.9.2.custom.js"></script>
    <div>${errorMessage}
    <form name="signupform" id="signupform" novalidate method="post" ng-submit="newUser(this)" ng-controller="userController">
        <table align="left" border="0" cellspacing="0" cellpadding="3">
            <tr>
                <td>Username:</td>
                <td><input type="text" name="username" maxlength="30" ng-model="user.name" required ></td>
            </tr>
            <tr>
                <td>Email:</td>
                <td><input type="email" name="email" maxlength="60" ng-model="user.email" required ></td>
            </tr>
            <tr>
                <td>Password:</td>
                <td><input type="password" name="password" maxlength="30" ng-model="user.password" required ui-validate="validateLength"></td>
            </tr>
            <tr>
                <td>Verify Password:</td>
                <td><input type="password" name="passwordConfirm" maxlength="30" ng-model="user.passwordConfirm" required ui-validate="{vp : validatePasswords, vl : validateLength}"></td>
            </tr>
            <tr>
                <td colspan="2" align="right"><input type="submit" name="signup" value="Sign up"></td>
            </tr>
        </table>
    </form>


<jsp:include page="footer.jsp"/>