<%-- we want to show the most recent date for all the target practices --%>
<div ng-controller="practiceOverviewController">
{{overviewData.title}}
  <div ng-repeat="type in overviewData.types|orderBy:predicate" ng-click="toggleTypeDetails(type)">
    {{type.type}} -- last result: {{type.date}} -- avg score: {{type.averageScore}} -- num darts: {{type.totalNumDarts}}
    <div style="margin-left:40px;" ng-show="overviewData.details == type">
    <div>Darts thrown in the last month: {{type.numDartsLastMonth}} -- avg score: {{type.avgScoreLastMonth}}</div>
    <div>Darts thrown in the last week: {{type.numDartsLastWeek}} -- avg score: {{type.avgScoreLastWeek}}</div>
    <div>Darts thrown in the last day: {{type.numDartsLastDay}} -- avg score: {{type.avgScoreLastDay}}</div>
    </div>
  </div>
  <div>Darts thrown all time: {{overviewData.allDarts.total}}</div>
  <div>Darts thrown this month: {{overviewData.allDarts.thisMonth}}</div>
  <div>Darts thrown this week: {{overviewData.allDarts.thisWeek}}</div>
  <div>Darts thrown today: {{overviewData.allDarts.today}}</div>
</div>

<jsp:include page="bottomIncludes.jsp"/>
<script src="/js/practiceOverviewController.js"></script>