describe('services spec', function() {

  beforeEach(module('dartsApp.services'));

  describe('test the score calculator service', function() {
    it('should work', inject(function(scoreCalculator) {
      // test empty
      expect(scoreCalculator).toBeDefined();
      expect(scoreCalculator("20")).toBe(1);
      expect(scoreCalculator("dbull")).toBe(2);
      expect(scoreCalculator("t5")).toBe(3);
      // anything that starts with t should be 3
      expect(scoreCalculator("tractor")).toBe(3);
      expect(scoreCalculator("")).toBe(0);
    }));
  });

  describe('test the chart service', function() {
    it('should exist', inject(function(chartService) {
      expect(chartService).toBeDefined();
    }))
  })
});