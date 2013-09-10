'use strict'

//angular.module('dartsApp', ['practiceNameService']);

describe('301 controller', function() {

  describe('mainController', function(){

  var scope, ctrl, $httpBackend;

    beforeEach(module('dartsApp.services'));

    beforeEach(module('dartsApp.mockService'));

    beforeEach(inject(function(_$httpBackend_, $rootScope, $controller) {
      $httpBackend = _$httpBackend_;

      $httpBackend.expectGET('/data/load301').
              respond(
                {});

      scope = $rootScope.$new();
      ctrl = $controller(threeOhOneController, {$scope: scope});
    }));

    it('should calculate the score', function() {
      scope.markDart('t20');
      expect(scope.targetData.remainingScore).toBe(301);
      scope.targetData.results = [];

      scope.markDart('d20');
      scope.markDart('t20');
      //start of turn 2
      scope.markDart('t20');
      scope.markDart('t20');
      scope.markDart(11);
      //start of turn 3
      scope.markDart('d19');
      scope.markDart(CONST.nohit);
      scope.markDart(CONST.nohit);
      // after the first two turns there is 32 left
      scope.updateScore();
      expect(scope.targetData.remainingScore).toBe(32);

      scope.markDart(16);
      scope.markDart(15);
      // remaining score is 1 here, so turn 3 should get rolled back
      expect(scope.targetData.remainingScore).toBe(32);



    });
  });
});

