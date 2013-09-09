'use strict';

/* Controllers */

angular.module("dartsApp.controller", []);
function practiceOverviewController($scope, $http, $log, $location, chartService, postDataService, scoreCalculator) {

  $scope.overviewData = {};
  $scope.overviewData.types = [];
  $scope.overviewData.details = {};
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
          newResult.numDartsLastDay = tempData.numDartsLastDay;
          newResult.avgScoreLastDay = Math.round(tempData.avgScoreLastDay * 100) / 100;
          newResult.numDartsLastWeek = tempData.numDartsLastWeek;
          newResult.avgScoreLastWeek = Math.round(tempData.avgScoreLastWeek * 100) / 100;
          newResult.numDartsLastMonth = tempData.numDartsLastMonth;
          newResult.avgScoreLastMonth = Math.round(tempData.avgScoreLastMonth * 100) / 100;
          $scope.overviewData.types.push(newResult);
        }
      }
      /*
      {"type":"18","totalNumDarts":795,"averageScore":2.550943396226415,"totalNumRounds":265,"numDartsLastDay":45,"avgScoreLastDay":2.4,"numDartsLastWeek":45,"avgScoreLastWeek":2.4,
       "totalScore":676,"latestResult":{"id":594,"score":26,"numRounds":10,"dateTimeManagement":{"displayDateTime":"Sep 09, 2013","dateMilliseconds":1378741027159}}}
      */
    }).
    error(function(data, status) {
      $log.error("failed");
    })

  $scope.toggleTypeDetails = function(type) {
    if ($scope.overviewData.details == type) {
      $scope.overviewData.details = {};
    } else {
      $scope.overviewData.details = type;
    }
  }


}



