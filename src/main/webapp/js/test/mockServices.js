'use strict';

/* Services */

var ang = angular.module('dartsApp.mockService', []);

ang.factory('chartService', function() {
    return "";
});

ang.factory('scoreCalculator', function() {
  return function(dart) {
    return 2;
  };
})

