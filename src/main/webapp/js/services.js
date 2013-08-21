'use strict';

/* Services */

var ang = angular.module('dartsApp.services', []);

ang.factory('postDataService', ['$http', function($http) {
  // save data to database, push it into games
  return function(createNewResult, postData, reset) {
    if (postData.results && postData.results.length > 0) {
      var myjson = JSON.stringify(postData.results, replacer);
      $http.post(postData.postUrl, myjson).
        success(function(data, status) {
          if (data) {
            var newResult = createNewResult(data);
            postData.games.unshift(newResult);
            postData.allGames.unshift(newResult);
          }
          reset(postData);
        }).
        error(function(data, status) {
          postData.data = data || "Request failed";
          postData.status = status;
        });
    }
  }
}]);

ang.factory('scoreCalculator', [function() {
  // take a dart and return its score, in the form of 19, d19, t19
  return function(dart) {
    var score = 0;
    if (typeof dart != "undefined" && dart != "") {
      if (dart.lastIndexOf("d", 0) === 0) {
        score = 2;
      } else if (dart.lastIndexOf("t",0) === 0) {
        score = 3;
      } else {
        score = 1;
      }
    }
    return score;
  }
}]);

ang.factory('ohOneScoreCalculator', [function() {
  return function(dart) {
    var score = 0;
    if (typeof dart != "undefined" && dart != "") {
      if (dart != CONST.nohit) { // if dart == CONST.nohit, we missed the board, so leave score at 0
        if (dart.lastIndexOf("d", 0) === 0) {
          dart = dart.substring(1);
          if (dart == 'bull')  {
            score = 50;
          } else {
            score = dart * 2;
          }
        } else if (dart.lastIndexOf("t",0) === 0) {
          dart = dart.substring(1);
          score = dart * 3;
        } else if (dart == 'bull') {
          score = 25;
        } else {
          score = dart;
        }
      }

    }
    return score;
  }
}])

ang.factory('chartService', [function() {
  return function(gamesContainer) {
    if (gamesContainer.length > 0) {
      var chart1; // globally available
      $(document).ready(function() {
        var dates = [];
        var scores = [];
        var length = gamesContainer.length-1;
        //console.log(gamesContainer);
        for (var i=length; i > -1; i--) {
          dates.push(gamesContainer[i].date);
          scores.push(gamesContainer[i].avg);
        }
        chart1 = new Highcharts.Chart({
           chart: {
              renderTo: 'container',
              type: 'line'
           },
           title: {
              text: ''
           },
           xAxis: {
              title: {
                  text: ''
              },
              categories: ""//dates
           },
           yAxis: {
              title: {
                 text: ''
              }
           },
           series: [{
              name: 'Average',
              data: scores
           }]
        });
      });
    }
  }
}]);