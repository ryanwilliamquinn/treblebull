<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="header.jsp"/>

<div ng-controller="threeOhOneController">

  <div style="margin:10px 0px;">Game mode: ${practiceMode}</div>

  <a href="/practice" style="display:block; float:right;" class="button dbGreen">Practice home</a>

  <div ng-hide="targetData.isShowRounds">
    <div style="margin:10px 0px;">Double in/double out. </div>
    <span ng-click="showRounds()" id="gameStart" class="smallButton dbGreen" style="margin-left:20px;">Start game</span>
  </div>
  <div ng-show="targetData.isShowRounds">
      <div style="margin:10px 0px;"> You have: {{targetData.remainingScore}} points left</div>
      <div style="margin:10px 0px;">Number of darts thrown so far: {{targetData.numDartsThrown}}</div>
  </div>

  <jsp:include page="buttons.jsp"/>
  <jsp:include page="past301Rounds.jsp"/>


</div>



<jsp:include page="bottomIncludes.jsp"/>
<script src="/js/threeOhOneController.js"></script>
<jsp:include page="footer.jsp"/>


