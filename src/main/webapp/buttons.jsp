<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- what do i need to add to the controller to make this just work?  can i do this in a directive somehow? seems like it would be a lot of things. --%>
<div class="rounds unselectable" <c:if test="${practiceMode != 'free'}">ng-show="targetData.isShowRounds"</c:if>>
  <div <c:if test="${practiceMode != 'free'}">ng-hide="checkRoundsComplete()"</c:if>>
    <div>
      <div style="display:inline-block; margin:5px;" class="sTarget" ng-click="markDart(target.id)" ng-show="target.id">{{target.id}}</div>
      <div style="display:inline-block; margin:5px;" class="sdtTarget" ng-click="markDart('d' + target.id)" ng-show="target.id">double {{target.id}}</div>
      <div style="display:inline-block; margin:5px;" class="sdtTarget" ng-click="markDart('t' + target.id)" ng-show="target.id && target.id != 'bull'">triple {{target.id}}</div>
    </div>
    <div style="width:300px;">
      <%-- need to make it so that the bull is unclickable if the triple is selected --%>
      <span class="dtTarget" ng-click="toggleModifier('d')" ng-class="{activeModifier: targetData.modifier=='d'}">D</span>
      <span class="dtTarget" ng-click="toggleModifier('t')" ng-class="{activeModifier: targetData.modifier=='t'}" ng-hide="hideTriple()">T</span>
      <span style="display:inline-block; margin:5px;" ng-repeat="n in [] | reverseRangeWithBull:20" class="sTarget" ng-click="markDart(n)">{{n}}</span>
    </div>
  </div>
  <div><span ng-repeat="dart in roundResult" style="margin-right:10px;">{{dart}}</span></div>
    <span ng-click="postResult()" style="margin-top:10px;" class="smallButton green unselectable" ng-show="checkRoundsComplete()">
      Save game
    </span>
    <div style="margin-top:10px;">
      <div id="roundResults">
        <c:choose>
          <c:when test="${practiceMode == 'free'}">
            <span name="roundResult" ng-hide="targetData.selectedEditRound == result" ng-click="selectEditRound(result)">Target: {{result.type}}, <span style="margin-left:20px;">Result: {{result.dart}},</span> <span style="margin-left:20px;">score: {{result.score}}</span></span>
            <span name="targetInput" class="sTarget" ng-show="targetData.selectedEditRound == result" ng-click="toggleDartToUpdate('target')" ng-class="{activeModifier: targetData.dartToUpdate == 'target'}">{{result.type}}:</span>
            <span name="resultInput" class="sTarget" ng-show="targetData.selectedEditRound == result" ng-click="toggleDartToUpdate('dartResult')" ng-class="{activeModifier: targetData.dartToUpdate == 'dartResult'}" style="margin-left:20px;">{{result.dart}}</span>
          </c:when>
          <c:when test="${practiceMode == '301'}">
          <div ng-click="enableEditMode()">Edit a dart</div>
          <div ng-repeat="turn in targetData.turns">
            <span ng-repeat="result in turn.results">
              <span name="roundResult" ng-hide="targetData.isEditMode"><span style="margin-left:20px;">{{result.dart}}</span></span>
              <span name="resultInput" class="sTarget" ng-show="targetData.isEditMode" ng-click="toggleDartToUpdate(result)" ng-class="{activeModifier: targetData.dartToUpdate == result}" style="margin-left:20px;">{{result.dart}}</span>
            </span>
          </div>
          <span class="dbGreen smallButton unselectable" ng-show="targetData.isEditMode" ng-click="finishEditing(result)" style="float:right;">Save</span>

         </c:when>
         <c:otherwise>
          Round: <span>{{result.round}}</span>
          <span name="roundResult" style="margin-left:20px;" ng-hide="targetData.selectedEditRound == result" ng-click="selectEditRound(result)">{{result.firstDart}}, {{result.secondDart}}, {{result.thirdDart}}  <span style="margin-left:20px;">round score: {{result.score}}</span></span>
          <span name="firstDartInput" class="sTarget" ng-show="targetData.selectedEditRound == result" ng-click="toggleDartToUpdate('first')" ng-class="{activeModifier: targetData.dartToUpdate=='first'}">{{result.firstDart}}</span>
          <span name="secondDartInput" class="sTarget" ng-show="targetData.selectedEditRound == result" ng-click="toggleDartToUpdate('second')" ng-class="{activeModifier: targetData.dartToUpdate=='second'}">{{result.secondDart}}</span>
          <span name="thirdDartInput" class="sTarget" ng-show="targetData.selectedEditRound == result" ng-click="toggleDartToUpdate('third')" ng-class="{activeModifier: targetData.dartToUpdate=='third'}">{{result.thirdDart}}</span>
         </c:otherwise>
        </c:choose>

      </div>
    </div>
    <span ng-click="cancelGame()" id="cancelGame" class="smallButton red unselectable" style="margin-top:15px; display:inline-block;" ng-hide="isHideCancel()">Cancel game</span>
</div>