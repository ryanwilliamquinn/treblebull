'use strict';

/* Controllers */

angular.module("dartsApp.controller", []);
function threeOhOneController($scope, $http, $log, $location, postDataService, ohOneScoreCalculator) {
  $scope.targetData = {};
  $scope.targetData.isShowRounds = false;
  $scope.isShowChart = false;
  $scope.targetData.turnCounter = 1;
  $scope.targetData.dartCounter = 0;
  // placeholder for keeping track of the double/triple state
  $scope.targetData.modifier = "";
  $scope.targetData.numDartsThrown = 0

  // helps display which dart to update
  $scope.targetData.dartToUpdate = "";

  // theoretically we could hold multiple turns or darts in here?
  $scope.targetData.results = [];
  // this will contain an array of turn objects.  each turn object will have a results property, which will be an array of what they hit
  //todo: right now you can only edit a turn, but you should also be able to remove it altogether
  $scope.targetData.turns = [{ turnCount: 1, results: []}];

  // there is a turn score i guess
  $scope.targetData.score = 0;

  // blank container for what we get back from the database
  $scope.targetData.games = [];

  // container for all games for a game mode and player
  $scope.targetData.allGames = [];

  // hold the average data we get from the database
  $scope.targetData.averages = [];

  // hold the combined average data (new darts and db info)
  $scope.targetData.combinedAverages = [];

  // urls required for loading and posting data
  $scope.targetData.postUrl = "/data/301";
  $scope.targetData.loadUrl = "/data/load301";

  // determines if 'show all' button will be present
  $scope.needsShowAll = true;

  // toggling a class name for showing all results
  $scope.displayShowAll = "hide";

  // order by date descending
  $scope.predicate = '-dateMillis';
  $scope.pastPredicate = '-dateMillis';

  // model used in selectEditRound method
  $scope.targetData.selectedEditDart = {};

  $scope.targetData.targetScore = 301;
  $scope.targetData.remainingScore = 301;

  $scope.targetData.isUseTargets = false;

  // the available targets.  this could be expanded.
  $scope.targetTypes = [{id : "bull", label : "bullseye"}, {id : "dbull", label :"double bull"}, {id : "d20", label :"double 20"}, {id : "t20", label :"triple 20"}, {id : "20", label :"20"},
                        {id : "d19", label :"double 19"}, {id : "t19", label :"triple 19"}, {id : "19", label :"19"}, {id : "d18", label :"double 18"}, {id : "t18", label :"triple 18"}, {id : "18", label :"18"},
                        {id : "d17", label :"double 17"}, {id : "t17", label :"triple 17"}, {id : "17", label :"17"}, {id : "d16", label :"double 16"}, {id : "t16", label :"triple 16"}, {id : "16", label :"16"},
                        {id : "d15", label :"double 15"}, {id : "t15", label :"triple 15"}, {id : "15", label :"15"}, {id : "d14", label :"double 14"}, {id : "t14", label :"triple 14"}, {id : "14", label :"14"},
                        {id : "d13", label :"double 13"}, {id : "t13", label :"triple 13"}, {id : "13", label :"13"}, {id : "d12", label :"double 12"}, {id : "t12", label :"triple 12"}, {id : "12", label :"12"},
                        {id : "d11", label :"double 11"}, {id : "t11", label :"triple 11"}, {id : "11", label :"11"}, {id : "d10", label :"double 10"}, {id : "t10", label :"triple 10"}, {id : "10", label :"10"},
                        {id : "d9", label :"double 9"}, {id : "t9", label :"triple 9"}, {id : "9", label :"9"}, {id : "d8", label :"double 8"}, {id : "t8", label :"triple 8"}, {id : "8", label :"8"},
                        {id : "d7", label :"double 7"}, {id : "t7", label :"triple 7"}, {id : "7", label :"7"}, {id : "d6", label :"double 6"}, {id : "t6", label :"triple 6"}, {id : "6", label :"6"},
                        {id : "d5", label :"double 5"}, {id : "t5", label :"triple 5"}, {id : "5", label :"5"}, {id : "d4", label :"double 4"}, {id : "t4", label :"triple 4"}, {id : "4", label :"4"},
                        {id : "d3", label :"double 3"}, {id : "t3", label :"triple 3"}, {id : "3", label :"3"}, {id : "d2", label :"double 2"}, {id : "t2", label :"triple 2"}, {id : "2", label :"2"},
                        {id : "d1", label :"double 1"}, {id : "t1", label :"triple 1"}, {id : "1", label :"1"}];

  // set the default target
  $scope.target = $scope.targetTypes[0];

  $scope.targetData.isEditMode = false;

  $scope.checkRoundsComplete = function() {
    return $scope.targetData.remainingScore == 0;
  }

  /***************** data loading and saving methods **********************/

  /*
  * the 301 darts result is going to take the number you doubled in on, the number you doubled out on, and total number of darts you threw
  * that information can all be derived from sending the darts results in order.  easier to mess with it on the server side
  * what we get back is the number of darts it took to complete (score), the date information, and the primary key
  */
  $scope.postResult = function() {
    // hopefully we can implement the postDataService?  try it just manually first to get it going
    // postDataService($scope.createNewResult, $scope.targetData, $scope.targetData.resetGameData);

    var myjson = JSON.stringify($scope.targetData.results, replacer);
    $http.post($scope.targetData.postUrl, myjson).
      success(function(data, status) {
        data.date = data.dateTimeManagement.displayDateTime;
        data.dateMillis = data.dateTimeManagement.dateMilliseconds;
        $scope.targetData.games.unshift(data);
        $scope.targetData.allGames.unshift(data);
        $scope.targetData.reset();
        $log.info("posted successfully");
      }).
      error(function(data, status) {
        postData.data = data || "Request failed";
        postData.status = status;
      });
  }
  //  postDataService($scope.createNewResult, $scope.targetData, $scope.targetData.reset);
  //}

  /*
  * this is called from postDataService, so that it can create the new result appropriately
  */
  $scope.createNewResult = function(data) {
      // only thing we need to do is update the average, so i guess we either get the total score and number of darts and keep track of it in js, or else we have to
      // constantly hit the database to get the average, which is silly i think.
      //return {'date' : data.displayDateTime, 'score' : data.score, 'dateMillis' : data.dateMilliseconds, 'numRounds' : data.numRounds, 'avg' : avg, 'id' : data.id}
  }

  /*
  * called from cancel game
  */

  $scope.targetData.reset = function() {
    $scope.targetData.isShowRounds = false;
    $scope.targetData.results = [];
    $scope.targetData.turns = [{ turnCount: 1, results: []}]
    $scope.targetData.modifier = "";
    $scope.targetData.remainingScore = 301;
    $scope.targetData.dartCounter = 0;
    $scope.targetData.turnCounter = 1;
    $scope.targetData.numDartsThrown = 0;
    $scope.targetData.isEditMode = false;
  }

  /*
  * gamesContainer is where we store the games that we parse from the response.  for the first request
  * we store them in the view container, for the lifetime stats we store them in a hidden container
  */
  $scope.getResults = function(url, gamesContainer) {
    $http.get(url).
          success(function(data, status) {
            var numResults = data.totalNumResults;
            var tempResults = data.dartsResults;
            if (tempResults) {
              var resultsLength = tempResults.length;
              for (var i=0; i < resultsLength; i++) {
                var tempdata = tempResults[i];
                var oldRound = {};
                oldRound.id = tempdata.id;
                oldRound.score = tempdata.score;
                oldRound.date = tempdata.dateTimeManagement.displayDateTime;
                oldRound.dateMillis = tempdata.dateTimeManagement.dateMilliseconds;
                oldRound.numRounds = tempdata.numRounds;
                oldRound.doubleIn = tempdata.doubleIn;
                oldRound.doubleOut = tempdata.doubleOut;
                oldRound.out = tempdata.out;
                if (tempdata.score && tempdata.dateTimeManagement.displayDateTime) {
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

  $scope.parseResults = function(data) {
    //var aggregateData = data.aggregateData
    /*
    if (aggregateData) {
      for (var i=0; i<aggregateData.length; i++) {
        var tempData = aggregateData[i];
        var avgData = {};
        avgData.type = tempData.type;
        avgData.score = tempData.score;
        avgData.numDarts = tempData.numDarts;
        avgData.targetAverage = (avgData.score / avgData.numDarts).toFixed(1);
        $scope.targetData.averages.push(avgData);
        $scope.targetData.combinedAverages = $scope.targetData.averages.slice(0);
      }
    }
    */
  }


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
      $scope.targetData.dartToUpdate = {};
    } else {
      $scope.targetData.dartToUpdate = dartToUpdate;
    }
  }


  /*
  * cancel a game - clear the current game data
  */
  $scope.cancelGame = function() {
      $scope.targetData.reset();
  }

  /*
  * called from button on front end - loads the chart if there is data
  * not sure a chart makes sense here.
  $scope.showChart = function() {
    $scope.isShowChart = true;
    if ($scope.targetData.allGames.length > 0) {
      chartService($scope.targetData.allGames);
    }
  }
*/

  /*
  * works with front end code to select a round for editing
  * ng-show="selectedEditRound == result"
  */
  $scope.selectEditDart = function(result) {
    $scope.targetData.selectedEditDart = result;
    $scope.targetData.isEditMode = true;
  }

  /*
  * called after saving a round edit - updates a particular round's score
  * and then calls update score to update the game score
  */
  $scope.finishEditing = function(result) {
    $scope.scoreDart($scope.targetData.dartToUpdate.actual, $scope.targetData.dartToUpdate.type); // i dont think this has any side effects...
    $scope.targetData.selectedEditDart = {};
    $scope.targetData.dartToUpdate = "";
    $scope.targetData.isEditMode = false;
  }

  /*
  * flip the visibility of the rounds section, called from the start button on the view
  */
  $scope.showRounds = function() {
    $scope.targetData.isShowRounds = true;
  }

  /*************** score management methods *****************/

  /*
  * this method gets called every time a score button is clicked.
  * every dart should be scored individually and added to the total averages
  */
  $scope.markDart = function(dart) {
    // add here "dart doesn't start with a d or a t"
    dart = dart.toString();
    if (dart != CONST.nohit && $scope.targetData.modifier && dart.lastIndexOf("d", 0) != 0 && dart.lastIndexOf("t", 0) != 0) {
      dart = $scope.targetData.modifier + dart;
    }
    // if we are not in edit mode, go ahead and mark the dart in the results
    if (!$scope.targetData.isEditMode) {
      $scope.targetData.numDartsThrown += 1;
      // $scope.targetData.turns is an array
      // turns[0] should be something like { turnCount: 1, results: []}


      // score after every dart, but only send to the database on save.
      // can maybe temp save in local storage so there is some durability/safety
      var score = $scope.scoreDart(dart);
      var newResult = {'type' : '301', 'actual' : dart, 'score' : score, 'dateMilliseconds' : new Date().getTime(), 'round' : $scope.targetData.turnCounter};
      if ($scope.targetData.isUseTargets) {
        newResult.target = $scope.target.id;
      }
      $scope.targetData.results.push(newResult);
      $scope.targetData.turns[$scope.targetData.turnCounter - 1].results.push(newResult);
      $scope.updateScore();
    } else {
      // edit mode
      $scope.targetData.dartToUpdate.actual = dart;
      $scope.targetData.dartToUpdate.score = $scope.scoreDart(dart);
      $scope.updateScore();
    }

    $scope.targetData.modifier = "";
  }

  /*
  * responsible for updating the score every turn, and managing the cleanup for the next turn
  */
  $scope.scoreDart = function(dart) {
    if (typeof(dart) == "number") {
      dart = dart.toString();
    }
    return ohOneScoreCalculator(dart);
  }

  $scope.hideTriple = function() {
    return false;
  }


  $scope.enableEditMode = function() {
    $scope.targetData.isEditMode = true;
  }

  /*
  *   we need to deal with turns and individual darts here.
  *   the score should be updated on each dart, but it needs to be aware of turns.
  */
  $scope.updateScore = function() {

    // each time we update the score, start off with the full score again
    $scope.targetData.remainingScore = $scope.targetData.targetScore;

    // only start scoring after we are doubled in
    var isDoubledIn = false;

    var numTurns = $scope.targetData.turns.length;
    for (var i=0; i<numTurns; i++) {
      var isIncrementTurn = false;
      var turn = $scope.targetData.turns[i];
      var resultsLength = turn.results.length;
      for (var j=0; j<resultsLength; j++) {
        var result = turn.results[j];
        var score = result.score
        // if we aren't double in yet, check each dart in the results until we are
        if (!isDoubledIn) {
          var dart = result.actual;
          if (typeof(dart) == "number") {
            dart = dart.toString();
          }
          // check if it is a double, if not, continue;
          if (dart.lastIndexOf("d", 0) !== 0) {
            score = 0;
            continue;
          } else {
            isDoubledIn = true;
          }
        }

        if (isDoubledIn) {
          $scope.targetData.remainingScore -= score;
        }
      }
    }

    if ($scope.targetData.remainingScore == 0) {

      // check if the last dart was a double
      var lastTurn = $scope.targetData.turns[numTurns - 1].results;
      var lastTurnLength = lastTurn.length;
      var lastDart = lastTurn[lastTurnLength-1];
      var lastDartResult = lastDart.actual;
      if (typeof(lastDartResult) == "number") {
        lastDartResult = lastDartResult.toString();
      }

      // if the last dart was not a double, zero out the scores for the last turn
      if (lastDartResult.lastIndexOf("d", 0) !== 0) {
        $scope.setLastTurnScoresToZero();
        $scope.nullifyBustedDarts();
        $scope.updateScore();
        isIncrementTurn = true;
      }
    } else if ($scope.targetData.remainingScore < 2) {
      $scope.setLastTurnScoresToZero();
      $scope.nullifyBustedDarts();
      $scope.updateScore();
      isIncrementTurn = true;
    }

    if (isIncrementTurn || $scope.targetData.dartCounter == 2) {
      $scope.targetData.dartCounter = 0;
      if ($scope.targetData.turns[$scope.targetData.turns.length - 1].results.length > 0) {
        $scope.targetData.turnCounter++;
        $scope.targetData.turns.push({ turnCount: $scope.targetData.turnCounter, results: []});
      }
    } else {
      if (!$scope.targetData.isEditMode) {
        $scope.targetData.dartCounter++;
      }
    }
  }

  $scope.nullifyBustedDarts = function() {
    var dartsToNullify = 2 - $scope.targetData.dartCounter; // if we end a turn early (bust), we want to nullify the rest of the turns
    if (!$scope.targetData.isEditMode && dartsToNullify > 0) {
      for (var i=0; i<dartsToNullify; i++) {
        var newResult = {'type' : '301', 'actual' : 'X', 'score' : 0, 'dateMilliseconds' : new Date().getTime(), 'round' : $scope.targetData.turnCounter};
        $scope.targetData.results.push(newResult);
        $scope.targetData.turns[$scope.targetData.turnCounter - 1].results.push(newResult);
      }
    }
  }

  $scope.isHideCancel = function() {
    return !$scope.targetData.isShowRounds;
  }

  $scope.setLastTurnScoresToZero = function() {
    var turnNumToRollBack = $scope.targetData.turnCounter - 1; // turn is 1 based, so if we busted, we want to roll back the existing turn
    var turnToRollBack = $scope.targetData.turns[turnNumToRollBack].results;
    if (turnToRollBack.length < 1) {
      turnToRollBack = $scope.targetData.turns[turnNumToRollBack - 1].results;
    }


    for (var i=0; i<turnToRollBack.length; i++) {
      turnToRollBack[i].score = 0;
    }
  }

  $scope.getResults($scope.targetData.loadUrl, $scope.targetData.games);

  /*
  * load up all results for user and game mode.  we may have to put some limit on this as data gets big
  */
  $scope.loadAll = function() {
    $scope.getResults($scope.targetData.loadAllUrl, $scope.targetData.allGames);
  }

  /*
    * this is the fledgling attempt to bring back round data
    */
    $scope.gameClicked = function(game) {
      // if game.rounds is defined and has some data, just clear it
      if (typeof game.rounds != "undefined" && game.rounds.length > 0) {
        // we already loaded this data, so clicking again should hide it...
        $scope.clearAllRounds();
      } else {
        var url = "/data/gameDetails" + game.id;
        //$log.info("url: " + url);
        //this.game.id gets us the game id.
        //$log.info(this.game);
        $http.get(url).
          success(function(data, status) {
            var rounds = [];
            var round = 0;
            var turn = [];
            var dart = {};
            for (var i=0; i<data.length; i++) {
              dart = data[i];
              round = dart.round - 1;
              if (!rounds[round]) {
                rounds[round] = [];
              }
              rounds[round].unshift(dart);
            }

            game.rounds = rounds;
            // so we need to take this data, which is an array of round/scores, and dump it into the result
          }).
          error(function(data, status) {
            $log.error("failed")
          })
      }
    }

    /*
    * this function makes it so that multiple round details aren't open at the same time
    */
    $scope.clearAllRounds = function() {
      for (var i=0; i<$scope.targetData.games.length; i++) {
        var game = $scope.targetData.games[i];
        game.rounds = [];
      }
    }


}

function isNumber(n) {
  return !isNaN(parseFloat(n)) && isFinite(n);
}

