<div style="clear:both; float:left; margin-top:30px;">
    <div style="border-bottom:solid 1px #000; margin-bottom:20px; padding-bottom:2px; width:200px;">Past ${practiceMode} totals:</div>
    <div ng-repeat="game in targetData.games | orderBy:predicate" ng-click="gameClicked(game)">
        <span>{{game.date}}</span>
        <span style="margin-left:16px;">Average score: {{game.avg}}</span>
        <div ng-repeat="round in game.rounds" style="margin-left:10px; width:200px;">Round:{{round.round}}<span style="float:right;">{{round.firstDart}}, {{round.secondDart}}, {{round.thirdDart}}</span></div>
    </div>
</div>