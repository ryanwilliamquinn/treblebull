'use strict'

//angular.module('dartsApp', ['practiceNameService']);

describe('target controllers', function() {

  describe('mainController', function(){

  var scope, ctrl, $httpBackend;

    beforeEach(module('dartsApp.services'));

    beforeEach(module('dartsApp.mockService'));

    beforeEach(inject(function(_$httpBackend_, $rootScope, $controller) {
      $httpBackend = _$httpBackend_;
      $httpBackend.expectGET('/data/loadBull').
        respond(
          {
            totalNumResults : 1,
            dartsResults : [{'date' : "Jan 1, 2012", 'score' : 5, 'dateMillis' : 1234123412, 'numRounds' : 3, 'avg' : 2}]
          });
        scope = $rootScope.$new();
        ctrl = $controller(mainController, {$scope: scope});
    }));

    it('should set up some urls', function() {
      scope.setUpUrls();
      expect(scope.targetData.postUrl).toBe("/data/bull");
      expect(scope.targetData.loadUrl).toBe("/data/loadBull");
      expect(scope.targetData.loadAllUrl).toBe("/data/loadAllBull");
    });

    it('should have 1 result'), function() {
      expect(scope.targetData.round.number).toBe(1);
      expect(scope.targetData.results.length).toBe(0);
      expect(scope.targetData.games.length).toBe(0);
      $httpBackend.flush();
      expect(scope.targetData.games.length).toBe(1);
    }
  });
});

