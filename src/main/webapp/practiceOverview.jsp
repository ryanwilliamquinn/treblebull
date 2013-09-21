<%-- we want to show the most recent date for all the target practices --%>
<div ng-controller="practiceOverviewController">

  <h3 style="margin-top:15px;">Your target practice results:</h3>
  <div id="overviewData">
    <div id="overviewHeaderRow">
      <span class="ovType">Type</span>|
      <span class="ovDate">Last Result</span>|
      <span class="ovScore">Avg Score</span>|
      <span class="ovNumDarts">Num Darts</span>
    </div>
    <div ng-repeat="type in overviewData.types|orderBy:predicate" ng-click="toggleTypeDetails(type)" style="margin:5px 0px;">
      <span class="ovType">{{type.type}}</span>
      <span class="ovDate">{{type.date}}</span>
      <span class="ovScore">{{type.averageScore}}</span>
      <span class="ovNumDarts">{{type.totalNumDarts}}</span>
      <div style="margin-left:40px;" ng-show="type.isShowDetails">
      <div>Darts thrown in the last month: {{type.numDartsLastMonth}} -- avg score: {{type.avgScoreLastMonth}}</div>
      <div>Darts thrown in the last week: {{type.numDartsLastWeek}} -- avg score: {{type.avgScoreLastWeek}}</div>
      <div>Darts thrown in the last day: {{type.numDartsLastDay}} -- avg score: {{type.avgScoreLastDay}}</div>
      </div>
    </div>
  </div>

  <div class="ovActivity" style="margin-top:25px;">Darts thrown all time: {{overviewData.allDarts.total}}</div>
  <div class="ovActivity">Darts thrown this month: {{overviewData.allDarts.thisMonth}}</div>
  <div class="ovActivity">Darts thrown this week: {{overviewData.allDarts.thisWeek}}</div>
  <div class="ovActivity">Darts thrown today: {{overviewData.allDarts.today}}</div>

</div>

<jsp:include page="bottomIncludes.jsp"/>
<script src="/js/practiceOverviewController.js"></script>