'use strict';

/* Controllers */

angular.module("dartsApp.controller", []);
function mainController($scope, $http, $log, chartService, postDataService) {
    $scope.targetData = {};
    $scope.targetData.round = {"number" : 1};
    $scope.initialNumGames = 10;
    $scope.targetData.isShowRounds = false;
    $scope.targetData.results = [];
    $scope.predicate = 'rank';
    $scope.targetData.postUrl = "/data/cricket";
    $scope.targetData.loadUrl = "/data/loadCricket";
    $scope.targetData.loadAllUrl = "/data/loadAllCricket";
    $scope.needsShowAll = false;
    $scope.predicate = '-dateMillis';
    $scope.selectedEditRound = {};

    $scope.targetData.games = [];
    $scope.targetData.allGames = [];

    $scope.acceptedInput = {
            "t20" : ["20","20","20"], "d20" : ["20","20"], "20" : ["20"], "t19" : ["19","19","19"], "d19" : ["19","19"], "19" : ["19"], "t18" : ["18","18","18"], "d18" : ["18","18"], "18" : ["18"],
            "t17" : ["17","17","17"], "d17" : ["17","17"], "17" : ["17"], "t16" : ["16","16","16"], "d16" : ["16","16"], "16" : ["16"], "t15" : ["15","15","15"], "d15" : ["15","15"], "15" : ["15"],
            "db" : ["b", "b"], "b" : ["b"],
            "t14" : [], "d14" : [], "14" : [], "t13" : [], "d13" : [], "13" : [], "t12" : [], "d12" : [], "12" : [], "t11" : [], "d11" : [], "11" : [], "t10" : [], "d10" : [], "10" : [], "t9" : [], "d9" : [], "9" : [],
            "t8" : [], "d8" : [], "8" : [], "t7" : [], "d7" : [], "7" : [], "t6" : [], "d6" : [], "6" : [], "t5" : [], "d5" : [], "5" : [], "t4" : [], "d4" : [], "4" : [], "t3" : [], "d3" : [], "3" : [], "t2" : [],
            "d2" : [], "2" : [], "t1" : [], "d1" : [], "1" : [], "0" : []
        };

    $scope.targets = [
        {"label" : "twenty", "num" : 0, "rank" : 1}, {"label" : "nineteen", "num" : 0, "rank" : 2}, {"label" : "eighteen", "num" : 0, "rank" : 3}, {"label" : "seventeen", "num" : 0, "rank" : 4},
        {"label" : "sixteen", "num" : 0, "rank" : 5}, {"label" : "fifteen", "num" : 0, "rank" : 6}, {"label" : "bull", "num" : 0, "rank" : 7}
    ];

    $scope.targetIndexes = {
        "20" : 0, "19" : 1, "18" : 2, "17" : 3, "16" : 4, "15" : 5, "b" : 6
    }

    $scope.cricket = { "twenty" : 0, "nineteen" : 0, "eighteen" : 0, "seventeen" : 0, "sixteen" : 0, "fifteen" : 0, "bullseye" : 0};

    // cancel a game
    $scope.cancelGame = function() {
        $scope.targetData.results = [];
        $scope.targetData.round.number = 1;
        $scope.targetData.isShowRounds = false;
    }

    $scope.showInputs = function() {
        $scope.targetData.isShowRounds = true;
    }

    $scope.selectEditRound = function(item) {
        $scope.selectedEditRound = item;
    }

    // record a single round/turn
    $scope.recordResult = function(result) {
        if ($scope.validateTurn(result)) {
            var newResult = {"firstDart" : result.firstDart, "secondDart" : result.secondDart, "thirdDart" : result.thirdDart, "round" : $scope.targetData.round.number};
            $scope.targetData.results.push(newResult);
            $scope.markTargets(result.firstDart, result.secondDart, result.thirdDart);
            $scope.targetData.round.number++;
            result.firstDart = "";
            result.secondDart = "";
            result.thirdDart = "";
            $("#firstDartInput").focus();
        }
    }

    $scope.validateTurn = function(result) {
        if (!result) {
            return false;
        }
        var darts = [result.firstDart, result.secondDart, result.thirdDart];

        // check for undefined darts  -- important if we start counting darts instead of rounds
        /*
        if ((!undefinedOrEmpty(result.firstDart) && undefinedOrEmpty(result.secondDart) && undefinedOrEmpty(result.thirdDart)) ||
            (!undefinedOrEmpty(result.firstDart) && !undefinedOrEmpty(result.secondDart) && undefinedOrEmpty(result.thirdDart))) {
            // if the game is over after marking the targets, fine, otherwise switch the undefineds to emptys?  crap for brains, just use emptys as darts for now
        } else if (!undefinedOrEmpty(result.firstDart) && !undefinedOrEmpty(result.secondDart) && !undefinedOrEmpty(result.thirdDart)) {
        } else {
            return false;
        }
        */

        // a turn should have at least one defined, not empty dart
        var isNotEmpty = false;
        var isValid = true;
        for (var i=0; i<darts.length;i++) {
            var dart = darts[i];
            if ((typeof dart != "undefined") && dart != "") {
                isNotEmpty = true;
            }
            isValid = $scope.validateDart(dart);
            if (!isValid) {
                return false;
            }
        }
        return isValid && isNotEmpty;
    }

    $scope.validateDart = function(dart) {
        return (undefinedOrEmpty(dart) || $scope.acceptedInput.hasOwnProperty(dart));
    }

    $scope.finishEditing = function(result) {
        $scope.selectedEditRound = {};
        $scope.markTargets();
    }


    // reset the target each time, then run through all the rounds and mark the targets
    // arguments are strings that are in the acceptedInput object
    $scope.markTargets = function() {
        $scope.resetTargets();
        for (var i=0; i<$scope.targetData.results.length;i++) {
            var result = $scope.targetData.results[i];
            $scope.tally(result.firstDart, result.secondDart, result.thirdDart);
        }
    }


    $scope.tally = function(firstDart, secondDart, thirdDart) {
        var darts = [firstDart, secondDart, thirdDart];
        for (var i=0; i<darts.length; i++) {
            var dart = darts[i];
            if (typeof dart != "undefined" && dart != "") {
                var hits = $scope.acceptedInput[dart];
                for (var j=0; j<hits.length; j++) {
                    var target = hits[j];
                    var targetIndex = $scope.targetIndexes[target];
                    if ($scope.targets[targetIndex].num < 3) {
                        $scope.targets[targetIndex].num++;
                    }
                }
            }
        }
    }

    $scope.gameFinished = function() {
        for(var i=0; i<$scope.targets.length; i++) {
            var target = $scope.targets[i];
            if (target.num < 3) {
                return false;
            }
        }
        return true;
    }

    $scope.createNewResult = function(data) {
        return {'date' : data.displayDateTime, 'dateMillis' : data.dateMilliseconds, 'numRounds' : data.numRounds, 'score' : data.score, 'avg' : data.score};
    }

    $scope.resetAfterPost = function(data) {
        data.isShowRounds = false;
        data.results = [];
        data.round.number = 1;
        $scope.resetTargets();
    }

    // save data to database, push it into games
    $scope.postResult = function() {
        postDataService($scope.createNewResult, $scope.targetData, $scope.resetAfterPost);
    }


    // gamesContainer is where we store the games that we parse from the response.  for the first request
    // we store them in the view container, for the lifetime stats we store them in a hidden container
    $scope.getResults = function(url, gamesContainer) {
        $http.get(url).
                success(function(data, status) {
                    $log.info(data);
                    var numResults = data.totalNumResults;
                    var tempResults = data.dartsResults;
                    if (tempResults) {
                        var resultsLength = tempResults.length;
                        //$log.info("results length: " + resultsLength + ", total number of results: " + $scope.numResults);

                        for (var i=0; i < resultsLength; i++) {
                            var tempdata = tempResults[i];
                            var oldRound = {};
                            oldRound.id = tempdata.id;
                            oldRound.date = tempdata.displayDateTime;
                            oldRound.score = tempdata.score;
                            oldRound.avg = tempdata.score;
                            oldRound.dateMillis = tempdata.dateMilliseconds;
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
                        // if we get all the results, load the data
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

    $scope.targetData.reset = function() {
        $scope.isShowRounds = false;
        $scope.targetData.results = [];
        $scope.targetData.round.number = 1;
        $scope.resetTargets();

    }

    $scope.resetTargets = function() {
        for (var i=0; i<$scope.targets.length; i++) {
           $scope.targets[i].num = 0;
        }
    }

    $scope.getResults($scope.targetData.loadUrl, $scope.targetData.games);

    $scope.$watch(
        function() {return $scope.targetData.allGames.length},
        function() {
            chartService($scope.targetData.allGames);
        }
    );
}

