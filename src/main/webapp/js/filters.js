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
