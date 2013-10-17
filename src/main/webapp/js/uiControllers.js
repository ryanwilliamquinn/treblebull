'use strict';

angular.module("dartsApp.controller", ["ui"]);

function userController($scope) {

    $scope.user = {"name" : "", "password" : "", "passwordConfirm" : "", "email" : ""};
    // would be great to be able to do this without referencing the document...
    $scope.newUser = function(myForm) {
        if ($scope.signupform.$valid) {
            var signupForm = document.forms['signupform'];
            signupForm.action = "processSignup";
            signupForm.submit();
        }
    };

    $scope.validateLength = function(value) {
        if (value) {
            return value.length > 7;
        }
    }

    $scope.validatePasswords = function(value) {
        if (value) {
            return (value == $scope.user.password);
        }
    }


    $scope.blackList = ['bad@domain.com','verybad@domain.com'];
    $scope.notBlackListed = function(value) {
        return $scope.blackList.indexOf(value) === -1;
    }

    $scope.updatePassword = function(myForm) {
        if ($scope.pwUpdateForm.$valid) {
            var pwResetForm = document.forms['pwUpdateForm'];
            console.log("wwtttfff?");
            console.log(pwResetForm);
            pwResetForm.action = 'resetPwd';
            pwResetForm.submit();
        } else {
            console.log("whatever??");
        }
    }
};

