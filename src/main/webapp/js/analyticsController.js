'use strict';

/* Controllers */

angular.module("dartsApp.controller", []);
function analyticsController($scope, $http) {

  $scope.aData = {};

  $http.get("/data/loadAnalytics").
      success(function(data, status) {
        //console.log(data);
        $scope.aData = data;
      }).
      error(function(data, status) {
        //console.log("we have a problem with the http get");
      })

}