'use strict';

var dartsApp = angular.module('dartsApp', []);

/* Controllers */

dartsApp.controller("mainController", function mainController($scope, $http) {
    $scope.round = {"number" : 1};
    $scope.results = [];
    $scope.url = "/data/twenties"
    $scope.games = [];

    $scope.recordResult = function(result) {
        if (result && isNumber(result.score)) {
            var newResult = {"score" : result.score, "round" : $scope.round.number}
            $scope.results.push(newResult);
            $scope.round.number++;
            result.score = "";

        }
    }
    $scope.postResult = function() {
        // Create the http post request
        // the data holds the keywords
        // The request is a JSON request.
        var myjson = JSON.stringify($scope.results, replacer);

        $http.post($scope.url, myjson).
            success(function(data, status) {
                //$scope.status = status;
                //$scope.data = data;
                //$scope.postResult = data; // Show result from server in our <pre></pre> element
                $scope.results = [];
                $scope.round.number = 1;
                if (data) {
                    var newResult = {'date' : data.displayDateTime, 'score' : data.score};
                    $scope.games.push(newResult);
                }
            }).
            error(function(data, status) {
                $scope.data = data || "Request failed";
                $scope.status = status;
            });

    };



    var replacer = function(key, value) {
        if (key=="$$hashKey") {
            return undefined;
        } else {
            return value;
        }
    }
});

function isNumber(n) {
  return !isNaN(parseFloat(n)) && isFinite(n);
}
