'use strict'

//angular.module('dartsApp', ['practiceNameService']);

describe('301 controller', function() {

  describe('mainController', function(){

  var scope, ctrl, $httpBackend;

    beforeEach(module('dartsApp.services'));

    beforeEach(module('dartsApp.mockService'));

    beforeEach(inject(function(_$httpBackend_, $rootScope, $controller) {
      $httpBackend = _$httpBackend_;

      scope = $rootScope.$new();
      ctrl = $controller(threeOhOneController, {$scope: scope});
    }));

    it('should calculate the score', function() {
      scope.targetData.results.push({score:60, dart:'t20'});
      expect(scope.targetData.remainingScore).toBe(301);

      scope.targetData.results = [];

      scope.targetData.results.push({score:40, dart:'d20'}); // turn 1
      scope.targetData.results.push({score:60, dart:'t20'}); // turn 1
      scope.targetData.results.push({score:60, dart:'t20'}); // turn 1
      scope.targetData.results.push({score:60, dart:'t20'}); // turn 2
      scope.targetData.results.push({score:11, dart:11}); // turn 2
      scope.targetData.results.push({score:38, dart:'d19'}); // turn 2
      // after the first two turns there is 32 left
      scope.updateScore();
      expect(scope.targetData.remainingScore).toBe(32);

      scope.targetData.results.push({score:16, dart:16});
      scope.targetData.results.push({score:15, dart:15});
      // remaining score is 1 here, so turn 3 should get rolled back
      // crap now we have to manage the number of odd turns here since the modulo trick will only work once.  daaang.  time to make some turn meta data

      expect(scope.targetData.remainingScore).toBe(32);



    });
  });
});

