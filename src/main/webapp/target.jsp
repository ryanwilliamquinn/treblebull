<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="header.jsp"/>

<div ng-controller="mainController">
    <div style="float:left; margin-bottom:20px;">
        <div>Game mode: ${practiceMode} : {{target.label}}</div>
        <div ng-hide="targetData.isShowRounds">
            Target:
            <select ng-model="target" ng-options="target.label for target in targetTypes" ng-change="changedTarget()"></select>
            Rounds Per Game:
            <select ng-model="targetData.numRounds" ng-options="numRounds.rounds for numRounds in numRoundsAvailable" ng-change="changedRounds()"></select>
            <span ng-click="showRounds()" class="smallButton blue" style="margin-left:20px;">Start game</span>
        </div>
        <div class="rounds" ng-show="targetData.isShowRounds">
          <div ng-hide="checkRoundsComplete()">
            <div style="margin-left:5px;">
              <div class="dtTarget" ng-click="toggleModifier('d')" ng-class="{activeModifier: targetData.modifier=='d'}">D</div>
              <div class="dtTarget" ng-click="toggleModifier('t')" ng-class="{activeModifier: targetData.modifier=='t'}">T</div>
              <div style="display:inline-block; margin:5px;" class="sTarget" ng-click="markDart(target.id)">{{target.id}}</div>
            </div>
            <div style="width:200px;">
              <span style="display:inline-block; margin:5px;" ng-repeat="n in [] | reverseRangeWithBull:20" class="sTarget" ng-click="markDart(n)">{{n}}</span>
            </div>
          </div>
          <div><span ng-repeat="dart in targetData.turn" style="margin-right:10px;">{{dart}}</span></div>
            <%--
            <form name="simplePracticeForm" id="simplePracticeForm"  ng-submit="recordResult(result)" ng-hide="checkRoundsComplete()">
                <label for="simplePracticeInput">Round {{targetData.round.number}} of {{targetData.numRounds.id}}:</label><input type="text" id="simplePracticeInput" class="scoreInput" data-ng-model="result.score"/>
                <span style="margin-left:5px;" class="blue smallButton" ng-click="recordResult(result)">Next</span>
            </form>
            --%>
            <span ng-click="postResult()" style="margin-top:10px;" class="smallButton green" ng-show="checkRoundsComplete()">Save game</span>
            <div style="margin-top:10px;">
                <div ng-repeat="result in targetData.results">
                    Round: <span>{{result.round}}</span>
                    <span style="margin-left:20px;" ng-hide="selectedEditRound == result" ng-click="selectEditRound(result)">score...{{result.score}}</span>
                    <input type="text" ng-show="selectedEditRound == result" ng-model="result.score"/>
                    <span class="blue smallButton" ng-show="selectedEditRound == result" ng-click="finishEditing(result)">Save</span>
                </div>
            </div>
            <span ng-click="cancelGame()" class="smallButton red" style="margin-top:15px; display:inline-block;" ng-hide="isEditRound">Cancel game</span>
        </div>
        <div style="float:left; margin:10px 0px 0px 20px;" ng-show="targetData.results.length > 0">
            Round average: {{targetData.roundScore|roundAverage:targetData.round.number}}
        </div>
        <div style="clear:both; float:left; margin-top:30px;">
            <div style="border-bottom:solid 1px #000; margin-bottom:20px; padding-bottom:2px; width:200px;">Past ${practiceMode} totals:</div>
            <div ng-repeat="game in targetData.games | orderBy:predicate" ng-click="gameClicked()">
                <span>{{game.date}}</span>
                <span style="margin-left:16px;">Average score: {{game.avg}}</span>
            </div>
        </div>
        <div style="margin:30px 0px 0px 20px; float:left;" ng-show="targetData.allGames.length > 0">
            All time average: {{targetData.allGames|lifetimeAverage}}
        </div>
    </div>
    <a href="/practice" style="display:block; float:right;" class="button blue">Practice home</a>
    <div style="clear:both;" ng-show="needsShowAll">
        <span ng-click="showAll()" style="cursor:pointer;" class="button blue">Show all results</span>
    </div>

    <div class="blue smallButton" ng-hide="isShowChart" ng-click="showChart()">Show chart</div>
    <div class="blue smallButton" ng-show="isShowChart" ng-click="isShowChart = false">Hide chart</div>

    <div id="container" class="resultsChart" ng-show="isShowChart"></div>

</div>



<script src="/js/jquery/jquery-1.8.3.js" type="text/javascript"></script>
<script src="/js/highcharts.js" type="text/javascript"></script>
<script src="/js/angular/angular.js"></script>
<script src="/js/directives.js"></script>
<script src="/js/services.js"></script>
<script src="/js/filters.js"></script>
<script src="/js/utils.js"></script>
<script src="/js/dartsApp.js"></script>
<script src="/js/targetController.js"></script>
<jsp:include page="footer.jsp"/>


