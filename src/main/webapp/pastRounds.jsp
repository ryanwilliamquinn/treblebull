<div style="clear:both; float:left; margin-top:30px;">
    <div style="border-bottom:solid 1px #000; margin-bottom:20px; padding-bottom:2px; width:200px;">Past ${practiceMode} totals:</div>
    <div ng-repeat="game in targetData.games | orderBy:predicate" ng-click="gameClicked(game)">
        <span>{{game.date}}</span>
        <span style="margin-left:16px;">Average score: {{game.avg}}</span>
        <div ng-repeat="round in game.rounds"  style="margin-left:10px; width:200px;">
          Round:{{$index + 1}}
          <span ng-repeat="dart in round"><span style="float:right;">{{dart.actual}}<span ng-hide="$first" style="display:inline-block; margin-right:5px;">,</span></span></span>
        </div>
    </div>
</div>