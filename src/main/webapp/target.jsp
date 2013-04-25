<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="header.jsp"/>

<div ng-controller="mainController">
    <div style="float:left; margin-bottom:20px;">
        <div>
          <span id="gameMode">${practiceMode} : {{target.label}}</span>
          <span style="margin:10px 0px 0px 20px;" id="gameAverage" ng-show="targetData.results.length > 0">
            Round average: {{targetData.score|roundAverage:targetData.round.number}}
          </span>
        </div>
        <div ng-hide="targetData.isShowRounds">
            Target:
            <select ng-model="target" ng-options="target.label for target in targetTypes" ng-change="changedTarget()"></select>
            Rounds Per Game:
            <select ng-model="targetData.numRounds" ng-options="numRounds.rounds for numRounds in numRoundsAvailable" ng-change="changedRounds()"></select>
            <span ng-click="showRounds()" id="gameStart" class="smallButton dbGreen" style="margin-left:20px;">Start game</span>
        </div>
        <jsp:include page="buttons.jsp"/>
        <jsp:include page="pastRounds.jsp"/>
        <div id="allTimeAverage" style="margin:30px 0px 0px 20px; float:left;" ng-show="targetData.allGames.length > 0">
            All time average: {{targetData.allGames|lifetimeAverage}}
        </div>
    </div>
    <a href="/practice" style="display:block; float:right;" class="button dbGreen">Practice home</a>
    <div style="clear:both;" ng-show="needsShowAll">
        <span ng-click="showAll()" style="cursor:pointer;" class="button dbGreen">Show all results</span>
    </div>

    <div class="dbGreen smallButton" ng-hide="isShowChart" ng-click="showChart()">Show chart</div>
    <div class="dbGreen smallButton" ng-show="isShowChart" ng-click="isShowChart = false">Hide chart</div>

    <div id="container" class="resultsChart" ng-show="isShowChart"></div>

</div>



<jsp:include page="bottomIncludes.jsp"/>
<script src="/js/targetController.js"></script>
<jsp:include page="footer.jsp"/>


