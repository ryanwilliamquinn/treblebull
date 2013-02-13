<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="header.jsp"/>

<div ng-controller="mainController">
    <div style="float:left; margin-bottom:20px;">
        <div>Game mode: ${practiceMode}</div>
        <div ng-hide="targetData.isShowRounds">
            <span ng-click="showInputs()" class="smallButton blue">Start a game of cricket</span>
        </div>

        <div class="rounds" ng-show="targetData.isShowRounds">
            <form name="simplePracticeForm" id="simplePracticeForm"  ng-submit="recordResult(result)" ng-hide="gameFinished()">
                <label for="firstDartInput">First dart:</label><input type="text" id="firstDartInput" class="scoreInput" ng-model="result.firstDart"/>
                <label for="secondDartInput">Second dart:</label><input type="text" id="secondDartInput" class="scoreInput" ng-model="result.secondDart"/>
                <label for="thirdDartInput">Third dart:</label><input type="text" id="thirdDartInput" class="scoreInput" ng-model="result.thirdDart"/>
                <span style="margin-left:5px;" class="blue smallButton" ng-click="recordResult(result)">Next</span>
            </form>
            <span ng-click="postResult()" style="margin-top:10px;" class="smallButton green" ng-show="gameFinished()">Save game</span>
            <div style="margin-top:10px;">
                <div ng-repeat="result in targetData.results">
                    Round: <span>{{result.round}}</span>
                    <span style="margin-left:20px;" ng-hide="selectedEditRound == result" ng-click="selectEditRound(result)">{{result.firstDart}}, {{result.secondDart}}, {{result.thirdDart}}</span>
                    <input type="text" id="firstDartInput" class="scoreInput" ng-model="result.firstDart" ng-show="selectedEditRound == result"/>
                    <input type="text" id="secondDartInput" class="scoreInput" ng-model="result.secondDart" ng-show="selectedEditRound == result"/>
                    <input type="text" id="thirdDartInput" class="scoreInput" ng-model="result.thirdDart" ng-show="selectedEditRound == result"/>
                    <span class="blue smallButton" ng-show="selectedEditRound == result" ng-click="finishEditing(result)">Save</span>
                </div>
            </div>

            <span ng-click="cancelGame()" class="smallButton red" style="margin-top:15px; display:inline-block;">Cancel game</span>
        </div>
        <div ng-show="targetData.isShowRounds" style="clear:both;">
            <table>
                <tr ng-repeat="target in targets | orderBy:predicate">
                    <td><div style="margin-right:15px;">{{target.label}}</div></td>
                    <td><image src="/images/mark_{{target.num}}.png"/ style="vertical-align:middle; position:relative; bottom:1px;"></td>
                </tr>
            </table>
        </div>
        <div style="clear:both; float:left; margin-top:30px;">
            <div style="border-bottom:solid 1px #000; margin-bottom:20px; padding-bottom:2px; width:200px;">Past ${practiceMode} totals:</div>
            <div ng-repeat="game in targetData.games | orderBy:predicate" ng-click="gameClicked()">
                <span>{{game.date}}</span>
                <span style="margin-left:16px;">Average score: {{game.score}}</span>
            </div>
        </div>
        <div style="margin:30px 0px 0px 20px; float:left;" ng-show="targetData.allGames.length > 0">
            All time average: {{targetData.allGames|runningAverage}}
        </div>
    </div>
    <a href="/practice" style="display:block; float:right;" class="button blue">Practice home</a>
    <div style="clear:both;" ng-show="needsShowAll">
        <span ng-click="showAll()" style="cursor:pointer;" class="button blue">Show all results</span>
    </div>

</div>

 <div id="container" style="width: 100%; height: 400px"></div>

<script src="/js/jquery/jquery-1.8.3.js" type="text/javascript"></script>
<script src="/js/highcharts.js" type="text/javascript"></script>
<script src="/js/angular/angular.js"></script>
<script src="/js/directives.js"></script>
<script src="/js/services.js"></script>
<script src="/js/filters.js"></script>
<script src="/js/utils.js"></script>
<script src="/js/dartsApp.js"></script>
<script src="/js/cricketController.js"></script>
<jsp:include page="footer.jsp"/>


