<%-- we want to show the most recent date for all the target practices --%>
<div ng-controller="practiceOverviewController">
{{overviewData.title}}
  <div ng-repeat="type in overviewData.types|orderBy:predicate">
    {{type.type}} -- last result: {{type.date}} -- avg score: {{type.averageScore}} -- num darts: {{type.totalNumDarts}}
    <div style="margin-left:40px;">Darts thrown in the last day: {{type.numDartsLastDay}} -- avg score: {{type.avgScoreLastDay}}</div>
    <div style="margin-left:40px;">Darts thrown in the last week: {{type.numDartsLastWeek}} -- avg score: {{type.avgScoreLastWeek}}</div>
    <div style="margin-left:40px;">Darts thrown in the last month: {{type.numDartsLastMonth}} -- avg score: {{type.avgScoreLastMonth}}</div>
  </div>
</div>

<jsp:include page="bottomIncludes.jsp"/>
<script src="/js/practiceOverviewController.js"></script>