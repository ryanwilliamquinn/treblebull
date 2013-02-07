'use strict';

/* Controllers */

angular.module("dartsApp.controller", []);
function mainController($scope, $http, $log, chartService, postDataService) {
    $scope.targetData = {};
    $scope.targetData.round = {"number" : 1};
    $scope.numRoundsAvailable = [{id : "5", rounds : "five", num : 5}, {id : "10", rounds : "ten", num : 10}];
    $scope.targetData.numRounds = $scope.numRoundsAvailable[1];
    $scope.targetData.isShowRounds = false;
    $scope.initialNumGames = 10;

    $scope.targetData.results = [];
    $scope.targetData.games = [];
    $scope.targetData.allGames = [];
    $scope.targetData.postUrl = "";
    $scope.targetData.loadUrl = "";
    $scope.targetData.loadAllUrl = "";

    $scope.needsShowAll = true;
    $scope.allDataLoaded = false;
    $scope.displayShowAll = "hide";
    $scope.predicate = '-dateMillis';

    //$scope.targetTypes = [{id : "bull", label : "bullseye"}, {id : "t20", label : "triple 20"}, {id : "d20", label : "double 20"}, {id : "20", label :"20"},
    //                        {id : "t19", label : "triple 19"}, {id : "d19", label : "double 19"}, {id : "19", label : "19"}];

    $scope.targetTypes = [{id : "bull", label : "bullseye"}, {id : "20", label :"20"}, {id : "19", label :"19"}, {id : "18", label :"18"}, {id : "17", label :"17"},
                            {id : "16", label :"16"}, {id : "15", label :"15"}]

    $scope.target = $scope.targetTypes[0];

    $scope.setUpUrls = function() {
        $scope.targetData.games = [];
        $scope.targetData.practiceType = $scope.target.id;
        $scope.targetData.urlPracticeType = capitaliseFirstLetter($scope.targetData.practiceType)
        $scope.targetData.postUrl = "/data/" + $scope.targetData.practiceType;
        $scope.targetData.loadUrl = "/data/load" + $scope.targetData.urlPracticeType;
        $scope.targetData.loadAllUrl = "/data/loadAll" + $scope.targetData.urlPracticeType;
    }

    $scope.setUpUrls();

    $scope.showRounds = function() {
        $scope.targetData.isShowRounds = true;
    }

    $scope.changedTarget = function() {
        $scope.setUpUrls();
        $scope.reset();
        $scope.getData();
    }

    $scope.reset = function() {
        $scope.targetData.games = [];
        $scope.targetData.allGames = [];
        $scope.targetData.results = [];
    }

    $scope.resetAfterPost = function(data) {
        data.isShowRounds = false;
        data.results = [];
        data.round.number = 1;
    }

    // cancel a game
    $scope.cancelGame = function() {
        $scope.targetData.results = [];
        $scope.targetData.round.number = 1;
        $scope.targetData.isShowRounds = false;
    }

    // record a single round/turn
    $scope.recordResult = function(result) {
        if (result && isNumber(result.score)) {
            var newResult = {score : result.score, round : $scope.targetData.round.number, isEditRound : false};
            $scope.targetData.results.push(newResult);
            $scope.targetData.round.number++;
            result.score = "";
            //var editRoundText = "isEditRound" + $scope.targetData.round.number;
        }
    }

    // method for hiding rounds input once we finish the correct number of turns
    $scope.checkRoundsComplete = function() {
        return $scope.targetData.round.number > $scope.targetData.numRounds.num;
    }

    $scope.createNewResult = function(data) {
        var avg = data.score / data.numRounds;
        return {'date' : data.displayDateTime, 'score' : data.score, 'dateMillis' : data.dateMilliseconds, 'numRounds' : data.numRounds, 'avg' : avg}
    }

    // save data to database, push it into games
    $scope.postResult = function() {
        postDataService($scope.createNewResult, $scope.targetData, $scope.resetAfterPost);
    }

    // called from "show all" button click
    $scope.showAll = function() {
        $scope.targetData.games = [];
        if ($scope.targetData.allGames.length == 0) {
            $scope.getResults($scope.targetData.loadAllUrl, $scope.targetData.games);
        } else if ($scope.targetData.allGames.length > 0) {
            $scope.targetData.games = $scope.targetData.allGames.slice();
        }
        $scope.needsShowAll = false;
    }

    $scope.loadAll = function() {
        $scope.getResults($scope.targetData.loadAllUrl, $scope.targetData.allGames);
    }

    // gamesContainer is where we store the games that we parse from the response.  for the first request
    // we store them in the view container, for the lifetime stats we store them in a hidden container
    $scope.getResults = function(url, gamesContainer) {
        $http.get(url).
                success(function(data, status) {
                    // $log.info(data);
                    var numResults = data.totalNumResults;
                    var tempResults = data.dartsResults;
                    if (tempResults) {
                        var resultsLength = tempResults.length;
                        //$log.info("results length: " + resultsLength + ", total number of results: " + numResults);

                        for (var i=0; i < resultsLength; i++) {
                            var tempdata = tempResults[i];
                            var oldRound = {};
                            oldRound.id = tempdata.id;
                            oldRound.date = tempdata.displayDateTime;
                            oldRound.score = tempdata.score;
                            oldRound.dateMillis = tempdata.dateMilliseconds;
                            oldRound.numRounds = tempdata.numRounds;
                            oldRound.avg = (oldRound.score / oldRound.numRounds);
                            if (tempdata.score && tempdata.displayDateTime) {
                                gamesContainer.push(oldRound);
                            }
                        }
                        // if there are more results than we show, we need the show all button, and we also need to load up the rest of the data for calculating averages
                        if ($scope.initialNumGames <= resultsLength && resultsLength < numResults) {
                            $scope.needsShowAll = true;
                            $scope.loadAll();
                        // if there are fewer total results than we ask for, then just copy the data over into the structure for calculating averages.
                        } else if ($scope.initialNumGames >= resultsLength && resultsLength >= numResults) {
                            $scope.needsShowAll = false;
                            $scope.targetData.allGames = gamesContainer.slice();
                        } else {
                            // if we get here, do we have to set allGames?
                            $scope.targetData.allGames = gamesContainer.slice();
                        }
                    }
                }).
                error(function(data, status) {
                    $log.error("failed")
                })
    }

    $scope.getData = function() {
        // first, load the last 10 results, show this right away
        $scope.getResults($scope.targetData.loadUrl, $scope.targetData.games);
    }

    $scope.getData();

    $scope.gameClicked = function() {
        var url = "/data/gameDetails" + this.game.id;
        $log.info("url: " + url);
        // this.game.id gets us the game id.
        //$log.info(this.game);
        $http.get(url).
            success(function(data, status) {
                $log.info(data);
            }).
            error(function(data, status) {
                $log.error("failed")
            })

    }

    $scope.$watch(
        function() {return $scope.targetData.allGames.length},
        function() {
            chartService($scope.targetData.allGames);
        }
    );



}

//mainController.$inject = [];

function isNumber(n) {
  return !isNaN(parseFloat(n)) && isFinite(n);
}
