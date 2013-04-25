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
        <div>
            Target:
            <select ng-model="target" ng-options="target.label for target in targetTypes"></select>
        </div>
        <jsp:include page="buttons.jsp"/>
        <%--
        could be interesting to eventually have an overriding average of all darts.  but it would just come from the database i suppose
        <div id="allTimeAverage" style="margin:30px 0px 0px 20px; float:left;" ng-show="targetData.allGames.length > 0">
            All time average: {{targetData.allGames|lifetimeAverage}}
        </div>
        --%>
    </div>
    <a href="/practice" style="display:block; float:right;" class="button dbGreen">Practice home</a>
    <div style="clear:both;" ng-repeat="avg in targetData.combinedAverages">
    {{avg.type}}, {{avg.targetAverage}}
    </div>



</div>


<jsp:include page="bottomIncludes.jsp"/>

<script src="/js/freeTargetController.js"></script>
<jsp:include page="footer.jsp"/>


