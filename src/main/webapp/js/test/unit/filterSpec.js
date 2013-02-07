describe('filter spec', function() {

    //var avg;

    /*
    beforeEach(inject(function(average) {
        avg = average;
    }));
    */
    beforeEach(module('dartsApp.filters'));

    describe('test average filter', function() {
        it('should calculate average', inject(function(runningAverageFilter) {
            // test empty
            expect(runningAverageFilter([])).toBe("");
            // test simple
            expect(runningAverageFilter([{'score' : 1}, {'score' : 2}, {'score' : 3}])).toBe("2.0");
            // test rounding
            expect(runningAverageFilter([{'score' : 0}, {'score' : 2}, {'score' : 0}])).toBe("0.7");
        }));
    });
});


