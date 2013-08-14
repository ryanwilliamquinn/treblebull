'use strict';

/* Filters */
var ang = angular.module('dartsApp.filters', []);
ang.filter('runningAverage', function() {
        return function(input) {
            // need to add up the number of rounds and the score, and show the average round score.
            var sumScore = 0;
            var length = input.length;
            if (length < 1) {
                return "";
            }
            for (var i=0; i<length; i++) {
                sumScore += Number(input[i].score);
            }
            var average = sumScore / length;
            return average.toFixed(1);
        }
    });

ang.filter('lifetimeAverage', function() {
    return function(allGames) {
        var sumScore = 0;
        var sumRounds = 0;
        if (allGames.length < 1) {
            return "";
        }
        for (var i=0; i<allGames.length; i++) {
            sumScore += Number(allGames[i].score);
            sumRounds += Number(allGames[i].numRounds);
        }

        var average = sumScore / sumRounds;
        return average.toFixed(1);
    }
})
ang.filter('cricketLifetimeAverage', function() {
   return function(allGames) {
       var sumScore = 0;
       var sumRounds = 0;
       if (allGames.length < 1) {
           return "";
       }
       for (var i=0; i<allGames.length; i++) {
           sumScore += Number(allGames[i].score);
           sumRounds += Number(allGames[i].numRounds);
       }

       var average = sumScore / sumRounds;
       return average.toFixed(1);
   }
})

ang.filter('reverseRange', function() {
    return function(input, total) {
      total = parseInt(total);
      for (var i=total; i>0; i--) {
        input.push(i);
      }
      return input;
    }
  })

ang.filter('reverseRangeWithBull', function() {
    return function(input, total) {
      total = parseInt(total);
      input.push("bull");
      for (var i=total; i>0; i--) {
        input.push(i);
      }
      input.push("no hit")
      return input;
    }
  })


ang.filter('dartAverage', function() {
  return function(input, numDarts) {
    if (numDarts > 0) {
      var avg = input / numDarts;
      return avg.toFixed(1);
    } else {
      return "";
    }
  }
})

// crap how do i deal with the round average if we calculate this score every dart?  hrm.  i guess best to just update this score per round...
ang.filter('roundAverage', function() {
  return function(input, roundNumber) {
    if (roundNumber > 0) {
      var avg = input / (roundNumber - 1);
      return avg.toFixed(1);
    } else {
      return "";
    }
  }
})
