<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="header.jsp"/>

<div ng-controller="threeOhOneController">
  <div>Game mode: ${practiceMode}</div>
  <a href="/practice" style="display:block; float:right;" class="button blue">Practice home</a>
  <div ng-hide="targetData.isShowRounds">
    <span ng-click="showRounds()" id="gameStart" class="smallButton blue" style="margin-left:20px;">Start game</span>
  </div>

  <jsp:include page="buttons.jsp"/>

  <div>Double in/double out.  You have: {{targetData.remainingScore}} points left</div>
  <div>Turn count: {{targetData.turnCounter}} ::: {{targetData.dartCounter}}
</div>



<jsp:include page="bottomIncludes.jsp"/>
<script src="/js/threeOhOneController.js"></script>
<jsp:include page="footer.jsp"/>


