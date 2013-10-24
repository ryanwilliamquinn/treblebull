<jsp:include page="../angHeader.jsp"/>

<script src="../js/uiControllers.js"></script>
<script type="text/javascript" src="../js/angularUI/module.js"></script>
<script type="text/javascript" src="../js/angularUI/validate.js"></script>
<script type="text/javascript" src="../js/jquery/jquery-1.8.3.js"></script>
<script type="text/javascript" src="../js/jquery/jquery-ui-1.9.2.custom.js"></script>
<div>${errorMessage}</div>
<form name="pwUpdateForm" id="pwUpdateForm" novalidate method="post" ng-submit="changePassword(this)" ng-controller="userController">
    <table align="left" border="0" cellspacing="0" cellpadding="3">
        <tr>
            <td>Old Password: </td>
            <td><input type="password" name="password" maxlength="30" ng-model="user.oldPassword" required /></td>
        </tr>
        <tr>
            <td>New Password:</td>
            <td><input type="password" name="newPassword" maxlength="30" ng-model="user.password" required ui-validate="validateLength" /></td>
        </tr>
        <tr>
            <td>Verify New Password:</td>
            <td>
                <input type="password" name="passwordConfirm" maxlength="30" ng-model="user.passwordConfirm" required ui-validate="{vp : validatePasswords, vl : validateLength}" />
                <input type="hidden" name="rid" value="${rid}" />
            </td>
        </tr>

        <tr>
            <td colspan="2" align="right"><input type="submit" name="signup" value="Reset password"></td>
        </tr>
    </table>
</form>

<jsp:include page="../footer.jsp"/>