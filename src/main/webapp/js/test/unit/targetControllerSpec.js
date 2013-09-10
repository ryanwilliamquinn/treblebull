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
            dartsResults : [{'id': 1, dateTimeManagement:{'displayDateTime' : "Jan 1, 2012", 'dateMilliseconds' : 1234123412}, 'score' : 5, 'numRounds' : 3, 'avg' : 2}]
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
    it('should have 1 result', function() {
      expect(scope.targetData.round.number).toBe(1);
      expect(scope.targetData.results.length).toBe(0);
      expect(scope.targetData.games.length).toBe(0);
      $httpBackend.flush();
      expect(scope.targetData.games.length).toBe(1);
    });

    it('should have a good tally method', function() {
      // note - for this test the scoreCalculator service is mocked to always return 2
      var turn = ["d20", "d20", "18"];
      scope.target.id = "20";
      expect(scope.tally(turn)).toBe(4);

      turn = ["d20", "d20", "d20"];
      expect(scope.tally(turn)).toBe(6);

      turn = ["t19", "d18", "17"];
      expect(scope.tally(turn)).toBe(0);
    });

    it('should update the score correctly', function() {
      // fyi the score calculator service is mocked here, so it returns 2 no matter what : )
      scope.target.id = "20";
      scope.targetData.results = [];
      scope.targetData.results.push({'actual' : "d20", 'target' : "20", 'round' : 1});
      scope.updateScore();
      expect(scope.targetData.score).toBe(2);
      scope.targetData.results.push({'actual' : "d20", 'target' : "20", 'round' : 2});
      scope.updateScore();
      expect(scope.targetData.score).toBe(4);
    });

    it('should mark the darts correctly', function() {
      scope.markDart("dbull");
      expect(scope.roundResult.length).toBe(1);
      expect(scope.roundResult[0]).toBe("dbull");
      scope.markDart("20");
      expect(scope.roundResult.length).toBe(2);
      expect(scope.roundResult[0]).toBe("dbull");
      expect(scope.roundResult[1]).toBe("20");
      scope.markDart("19");
      expect(scope.roundResult.length).toBe(0);
    });

  });
});

