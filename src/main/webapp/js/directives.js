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

        //console.log("directive!! all games loaded");
        //console.log(scope.allGames);
        //console.log(scope.games);

    }
  });

  /*
  scope.$watch(
                  function() {return scope.allGames},
                  function() {
                      if (scope.allGames.length > 0) {
                          google.load('visualization', '1.0', {'packages':['corechart']});
                          google.setOnLoadCallback(drawChart);
                          // Callback that creates and populates a data table,
                          // instantiates the pie chart, passes in the data and
                          // draws it.
                          function drawChart() {

                              // Create the data table.
                              var data = new google.visualization.DataTable();
                              data.addColumn('string', 'Topping');
                              data.addColumn('number', 'Slices');
                              data.addRows([
                                ['Mushrooms', 3],
                                ['Onions', 1],
                                ['Olives', 1],
                                ['Zucchini', 1],
                                ['Pepperoni', 2]
                              ]);

                              // Set chart options
                              var options = {'title':'How Much Pizza I Ate Last Night',
                                             'width':400,
                                             'height':300};

                              // Instantiate and draw our chart, passing in some options.
                              var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
                              chart.draw(data, options);
                          }
                          //console.log("directive!! all games loaded");
                          //console.log(scope.allGames);
                          //console.log(scope.games);

                      }
                  }
              );
        }
      }
    });
*/