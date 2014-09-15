'use strict';

/* Controllers */

var app = angular.module("dartsApp.controller", []);
app.controller('analyticsController', function ($scope, $http) {

  $scope.aData = {};

  $http.get("/data/loadAnalytics").
      success(function(data, status) {
        //console.log(data);
        for (var i=0; i<data.length; i++) {
          // do some processing of the raw data, to set the number of hits and average
          var type = data[i];
          console.log("type: " + type.type + ", total: " + type.total + ", total misses: " + type.totalMisses);
          if (type.total > 0) {
            type.hits = type.total - type.totalMisses;
            type.average = (type.hits / type.total).toFixed(2);
          } else {
            type.hits = 0;
            type.average = 0;
          }
        }
        $scope.aData = data;
      }).
      error(function(data, status) {
        //console.log("we have a problem with the http get");
      })

});