<%-- we want to show the most recent date for all the target practices --%>
<div ng-controller="practiceOverviewController">
{{overviewData.title}}
  <div ng-repeat="type in overviewData.types|orderBy:predicate">
    {{type.type}} -- last result: {{type.date}} -- avg score: {{type.averageScore}} -- num darts: {{type.totalNumDarts}}
  </div>
</div>

<jsp:include page="bottomIncludes.jsp"/>
<script src="/js/practiceOverviewController.js"></script>