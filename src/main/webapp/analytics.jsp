<jsp:include page="header.jsp"/>
<div ng-controller="analyticsController">
<h4>Analytics</h4>

  <div ng-repeat="type in aData">
  type: {{type.type}} -- total: {{type.total}} -- hits: {{type.hits}} -- misses: {{type.totalMisses}} -- avg: {{type.average}}
    <div>misses:</div>
    <div style="margin-left:15px;" ng-repeat="misses in type.misses">
      actual: {{misses.actual}} -- total: {{misses.total}}
    </div>
  </div>

</div>
<jsp:include page="bottomIncludes.jsp"/>
<script src="/js/analyticsController.js"></script>
<jsp:include page="footer.jsp"/>