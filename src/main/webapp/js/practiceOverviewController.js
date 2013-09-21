'use strict';

/* Controllers */

angular.module("dartsApp.controller", []);
function practiceOverviewController($scope, $http, $log, $location, chartService, postDataService, scoreCalculator) {

  $scope.overviewData = { allDarts : { total: 0, thisMonth: 0, thisWeek: 0, today: 0} };
  $scope.overviewData.types = [];
  $scope.overviewData.details = [];
  $scope.overviewData.title = "Target practice overview";
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
          $scope.overviewData.allDarts.total += newResult.totalNumDarts;
          newResult.numDartsLastDay = tempData.numDartsLastDay;
          $scope.overviewData.allDarts.today += newResult.numDartsLastDay;
          newResult.avgScoreLastDay = Math.round(tempData.avgScoreLastDay * 100) / 100;
          newResult.numDartsLastWeek = tempData.numDartsLastWeek;
          $scope.overviewData.allDarts.thisWeek += newResult.numDartsLastWeek;
          newResult.avgScoreLastWeek = Math.round(tempData.avgScoreLastWeek * 100) / 100;
          newResult.numDartsLastMonth = tempData.numDartsLastMonth;
          $scope.overviewData.allDarts.thisMonth += newResult.numDartsLastMonth;
          newResult.avgScoreLastMonth = Math.round(tempData.avgScoreLastMonth * 100) / 100;
          newResult.isShowDetails = false;
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
    type.isShowDetails = !type.isShowDetails;
  }


}



