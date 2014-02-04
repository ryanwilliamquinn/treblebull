angular.module('dartsApp.directives', []).
  directive('googleChart', function() {
    return {
      restrict: 'A', // only activate on element attribute
      link: function(scope, element, attrs) {
      scope.$watch(
        function() {return scope.allGames},
        function() {
        if (scope.allGames.length > 0) {
            var chart1; // globally available
            $(document).ready(function() {
                  var dates = [];
                  var scores = [];
                  var length = scope.allGames.length-1;
                  for (var i=length; i>=0; i--) {
                    dates.push(scope.allGames[i].date);
                    scores.push(scope.allGames[i].avg);
                  }
                  //console.log(dates);
                  //console.log(scores);
                  chart1 = new Highcharts.Chart({
                     chart: {
                        renderTo: 'container',
                        type: 'line'
                     },
                     title: {
                        text: 'Darts!'
                     },
                     xAxis: {
                        categories: dates
                     },
                     yAxis: {
                        title: {
                           text: 'Avg'
                        }
                     },
                     series: [{
                        name: 'Average',
                        data: scores
                     }]
                  });
               });
           }
         });
      }
    }
  });

