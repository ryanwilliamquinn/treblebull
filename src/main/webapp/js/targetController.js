'use strict';

/* Controllers */

angular.module("dartsApp.controller", []);
function mainController($scope, $http, $log, $location, chartService, postDataService, scoreCalculator) {
  $scope.targetData = {};
  $scope.targetData.round = {"number" : 1};
  $scope.numRoundsAvailable = [{id : "5", rounds : "five", num : 5}, {id : "10", rounds : "ten", num : 10}];
  $scope.targetData.numRounds = $scope.numRoundsAvailable[1];
  $scope.targetData.isShowRounds = false;
  $scope.initialNumGames = 10;
  $scope.isShowChart = false;
  $scope.targetData.turn = [];
  // placeholder for keeping track of the double/triple state
  $scope.targetData.modifier = "";

  $scope.targetData.dartToUpdate = "";

  $scope.roundResult = [];
  $scope.targetData.results = [];

  //
  $scope.targetData.score = 0;

  // games contains first 10 games
  $scope.targetData.games = [];
  // allgames is not just a clever name.  container for all games for a game mode and player
  $scope.targetData.allGames = [];

  // urls required for loading and posting data
  $scope.targetData.postUrl = "";
  $scope.targetData.loadUrl = "";
  $scope.targetData.loadAllUrl = "";

  // determines if 'show all' button will be present
  $scope.needsShowAll = true;

  // toggling a class name for showing all results
  $scope.displayShowAll = "hide";

  // order by date descending
  $scope.predicate = '-dateMillis';

  // model used in selectEditRound method
  $scope.selectedEditRound = {};

  // here is an old array that included doubles and triples.  not sure how i feel about that.
  //$scope.targetTypes = [{id : "bull", label : "bullseye"}, {id : "t20", label : "triple 20"}, {id : "d20", label : "double 20"}, {id : "20", label :"20"},
  //                        {id : "t19", label : "triple 19"}, {id : "d19", label : "double 19"}, {id : "19", label : "19"}];

  // the available targets.  this could be expanded.
  $scope.targetTypes = [{id : "bull", label : "bullseye"}, {id : "20", label :"20"}, {id : "19", label :"19"}, {id : "18", label :"18"}, {id : "17", label :"17"},
                          {id : "16", label :"16"}, {id : "15", label :"15"}]

  // set the default target
  $scope.target = $scope.targetTypes[0];

  $scope.targetData.isEditMode = false;

  /*
  * method to set up all the urls relative to the current practice type
  */
  $scope.setUpUrls = function() {
    $scope.targetData.games = [];
    $scope.targetData.practiceType = $scope.target.id;
    $scope.targetData.urlPracticeType = capitaliseFirstLetter($scope.targetData.practiceType)
    $scope.targetData.postUrl = "/data/" + $scope.targetData.practiceType;
    $scope.targetData.loadUrl = "/data/load" + $scope.targetData.urlPracticeType;
    $scope.targetData.loadAllUrl = "/data/loadAll" + $scope.targetData.urlPracticeType;
  }

  /*
  * method for hiding rounds input once we finish the correct number of turns
  */
  $scope.checkRoundsComplete = function() {
    return $scope.targetData.round.number > $scope.targetData.numRounds.num;
  }


  /***************** data loading and saving methods **********************/

  /*
  * save data to database, and push it into games and allgames arrays
  */
  $scope.postResult = function() {
    postDataService($scope.createNewResult, $scope.targetData, $scope.targetData.resetGameData);
  }

  /*
  * this is called from postDataService, so that it can create the new result appropriately
  */
  $scope.createNewResult = function(data) {
      var avg = data.score / data.numRounds;
      return {'date' : data.displayDateTime, 'score' : data.score, 'dateMillis' : data.dateMilliseconds, 'numRounds' : data.numRounds, 'avg' : avg}
  }

  /*
  * called when changing targets - clears all historical game data
  */

  $scope.targetData.reset = function() {
     $scope.targetData.games = [];
     $scope.targetData.allGames = [];
     $scope.targetData.results = [];
  }

  /*
  * reset the existing game data, but don't touch the historical game data
  */
  $scope.targetData.resetGameData = function(data) {
    data.isShowRounds = false;
    data.results = [];
    data.round.number = 1;
    data.score = 0;
    data.modifier = "";
    data.turn = [];
  }

  /*
  * gamesContainer is where we store the games that we parse from the response.  for the first request
  * we store them in the view container, for the lifetime stats we store them in a hidden container
  */
  $scope.getResults = function(url, gamesContainer) {
    $http.get(url).
          success(function(data, status) {
            $log.info(data);
            var numResults = data.totalNumResults;
            var tempResults = data.dartsResults;
            if (tempResults) {
              var resultsLength = tempResults.length;
              $log.info("results length: " + resultsLength + ", total number of results: " + numResults);
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

  /*
  * standardized method to load the initial data.  not sure it is totally necessary.
  */
  $scope.getData = function() {
    // first, load the last 10 results, show this right away
    $scope.getResults($scope.targetData.loadUrl, $scope.targetData.games);
  }

  /*
  * load up all results for user and game mode.  we may have to put some limit on this as data gets big
  */
  $scope.loadAll = function() {
    $scope.getResults($scope.targetData.loadAllUrl, $scope.targetData.allGames);
  }

  /*
  * set the data loading/posting urls to be appropriate for the current target
  */
  $scope.setUpUrls();

  /*
  * load up the data when we get here...
  */
  $scope.getData();





  /************** User/view initiated methods *****************/

  /*
  * toggleModifier allows the modifier (double/triple button) to be turned on or off, even if repeatedly clicked
  */
  $scope.toggleModifier = function(modifier) {
    if ($scope.targetData.modifier == modifier) {
      $scope.targetData.modifier = "";
    } else {
      $scope.targetData.modifier = modifier;
    }
  }

  /*
  * method to help visualize which dart is being edited (in a previously entered result)
  */
  $scope.toggleDartToUpdate = function(dartToUpdate) {
    if ($scope.targetData.dartToUpdate == dartToUpdate) {
      $scope.targetData.dartToUpdate = "";
    } else {
      $scope.targetData.dartToUpdate = dartToUpdate;
    }
  }

  /*
  * get all the games into targetData.games array, either by copying or loading
  * called from "show all" button click
  */
  $scope.showAll = function() {
      $scope.targetData.games = [];
      if ($scope.targetData.allGames.length == 0) {
          $scope.getResults($scope.targetData.loadAllUrl, $scope.targetData.games);
      } else if ($scope.targetData.allGames.length > 0) {
          $scope.targetData.games = $scope.targetData.allGames.slice();
      }
      $scope.needsShowAll = false;
  }

  /*
  * cancel a game - clear the current game data
  */
  $scope.cancelGame = function() {
      $scope.targetData.resetGameData($scope.targetData);
  }

  /*
  * called when a user picks a new target from select box.
  * resets urls and data, and reloads data
  */
  $scope.changedTarget = function() {
      $scope.setUpUrls();
      $scope.targetData.reset();
      $scope.getData();
  }

  /*
  * called from button on front end - loads the chart if there is data
  */
  $scope.showChart = function() {
    $scope.isShowChart = true;
    if ($scope.targetData.allGames.length > 0) {
      chartService($scope.targetData.allGames);
    }
  }

  /*
  * works with front end code to select a round for editing
  * ng-show="selectedEditRound == result"
  */
  $scope.selectEditRound = function(item) {
    $scope.selectedEditRound = item;
    $scope.targetData.isEditMode = true;
  }

  /*
  * called after saving a round edit - updates a particular round's score
  * and then calls update score to update the game score
  */
  $scope.finishEditing = function(result) {
    var targetArray = [result.firstDart, result.secondDart, result.thirdDart];
    var score = $scope.tally(targetArray);
    result.score = score;
    $scope.selectedEditRound = {};
    $scope.updateScore();
    $scope.targetData.dartToUpdate = "";
    $scope.targetData.isEditMode = false;
  }

  /*
  * flip the visibility of the rounds section, called from the start button on the view
  */
  $scope.showRounds = function() {
    $scope.targetData.isShowRounds = true;
  }

  /*
  * this is the fledgling attempt to bring back round data
  */
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


  /*************** score management methods *****************/

  /*
  * this method gets called every time a score button is clicked.
  * every set of darts is then tallied and added to the score.
  */
  $scope.markDart = function(result) {
    var dart = result;
    if ($scope.targetData.modifier) {
      dart = $scope.targetData.modifier + result;
    }
    // if we are not in edit mode, go ahead and mark the dart in the results
    if (!$scope.targetData.isEditMode) {
      // round result is used to show the darts that have been selected in the current turn
      $scope.roundResult.push(dart);

      // at the end of every turn submit the turn to the scoring.
      if ($scope.roundResult.length >= 3) {
        $scope.scoreTurn();
      }
    } else {
      // what to do with the result?
      if ($scope.targetData.dartToUpdate == "first") {
        $scope.selectedEditRound.firstDart = dart;
      } else if ($scope.targetData.dartToUpdate == "second") {
        $scope.selectedEditRound.secondDart = dart;
      } else if ($scope.targetData.dartToUpdate == "third") {
        $scope.selectedEditRound.thirdDart = dart;
      }
    }
    $scope.targetData.modifier = "";
  }

  /*
  * responsible for updating the score every turn, and managing the cleanup for the next turn
  */
  $scope.scoreTurn = function() {
    var score = $scope.tally($scope.roundResult);
    var newResult = {"firstDart" : $scope.roundResult[0], "secondDart" : $scope.roundResult[1], "thirdDart" : $scope.roundResult[2], "round" : $scope.targetData.round.number, "score" : score};
    $scope.targetData.results.push(newResult);
    $scope.targetData.round.number++;
    // only update this every round, so the average doesn't get screwy...
    $scope.updateScore();
    $scope.roundResult = [];
  }

  /*
  * tally takes a particular turn and returns the aggregate score for three darts
  */
  $scope.tally = function(turn) {
    // turn.length sure ought to be 3 here
    if (turn.length != 3) {
      $log.info("turn is not 3, this is weird, man");
    }
    var total = 0;
    for (var i=0; i<turn.length; i++) {
      var dart = turn[i];
      if (typeof(dart) == "number") {
        dart = dart.toString();
      }
      if (dart.indexOf($scope.target.id) !== -1) {
        var score = scoreCalculator(dart);

        total = total + score;
      }
    }
    return total;
  }

  /*
  * update score resets the total score to zero, and then re-sums all of the round scores
  */
  $scope.updateScore = function() {
    $scope.targetData.score = 0;
    for (var i=0; i<$scope.targetData.results.length;i++) {
        var result = $scope.targetData.results[i];
        $scope.targetData.score = $scope.targetData.score + result.score;
    }
  }


  /*
  * this watcher updates the chart each time the 'allGames' array changes length,
  * but only calls the chart service if the chart is visible
  */
  $scope.$watch(
    function() {return $scope.targetData.allGames.length},
    function() {
      if ($scope.isShowChart) {
        chartService($scope.targetData.allGames);
      }
    }
  )
}

//mainController.$inject = [];

function isNumber(n) {
  return !isNaN(parseFloat(n)) && isFinite(n);
}


// idea for editing rounds
/*
you should click on the actual dart you want to change
when you click on it, it gets highlighted
then you select the score you want to replace it with
then you click save
*/
