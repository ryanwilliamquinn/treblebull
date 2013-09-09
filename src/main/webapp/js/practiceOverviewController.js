'use strict';

/* Controllers */

angular.module("dartsApp.controller", []);
function practiceOverviewController($scope, $http, $log, $location, chartService, postDataService, scoreCalculator) {

  $scope.overviewData = {};
  $scope.overviewData.types = [];
  $scope.predicate = '-type';


  $http.get("/data/loadPracticeOverview").
    success(function(data, status) {
      if (data && data.length > 0) {
        for (var i=0; i<data.length; i++) {
          var tempData = data[i];
          var newResult = {};
          newResult.type = tempData.type;
          newResult.averageScore = Math.round(tempData.averageScore * 100) / 100;
          newResult.date = tempData.latestResult.dateTimeManagement.displayDateTime;
          newResult.totalNumDarts = tempData.totalNumDarts;
          $scope.overviewData.types.push(newResult);
        }
      }
      /*
      averageScore: 2
      latestResult: Object
      dateTimeManagement: Object
      dateMilliseconds: 1378690563780
      displayDateTime: "Sep 08, 2013"
      __proto__: Object
      id: 593
      numRounds: 5
      score: 10
      __proto__: Object
      totalNumRounds: 255
      totalScore: 650
      type: "18"
      */
    }).
    error(function(data, status) {
      $log.error("failed");
    })


}



