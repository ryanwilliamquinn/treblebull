<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="rounds unselectable" <c:if test="${practiceMode != 'free'}">ng-show="targetData.isShowRounds"</c:if>>
  <div <c:if test="${practiceMode != 'free'}">ng-hide="checkRoundsComplete()"</c:if>>
    <div>
      <div style="display:inline-block; margin:5px;" class="sTarget" ng-click="markDart(target.id)" ng-show="target.id">{{target.id}}</div>
      <div style="display:inline-block; margin:5px;" class="sdtTarget" ng-click="markDart('d' + target.id)" ng-show="target.id">double {{target.id}}</div>
      <div style="display:inline-block; margin:5px;" class="sdtTarget" ng-click="markDart('t' + target.id)" ng-show="target.id && target.id != 'bull'">triple {{target.id}}</div>
    </div>
    <div ng-show="target.firstMiss && isShowCloseMissTargets">
        <div style="display:inline-block; margin:5px;" class="sTarget" ng-click="markDart(target.firstMiss)" ng-show="target.id">{{target.firstMiss}}</div>
        <div style="display:inline-block; margin:5px;" class="sdtTarget" ng-click="markDart('d' + target.firstMiss)" ng-show="target.id">double {{target.firstMiss}}</div>
        <div style="display:inline-block; margin:5px;" class="sdtTarget" ng-click="markDart('t' + target.firstMiss)" ng-show="target.id && target.id != 'bull'">triple {{target.firstMiss}}</div>
    </div>
    <div ng-show="target.secondMiss && isShowCloseMissTargets">
        <div style="display:inline-block; margin:5px;" class="sTarget" ng-click="markDart(target.secondMiss)" ng-show="target.id">{{target.secondMiss}}</div>
        <div style="display:inline-block; margin:5px;" class="sdtTarget" ng-click="markDart('d' + target.secondMiss)" ng-show="target.id">double {{target.secondMiss}}</div>
        <div style="display:inline-block; margin:5px;" class="sdtTarget" ng-click="markDart('t' + target.secondMiss)" ng-show="target.id && target.id != 'bull'">triple {{target.secondMiss}}</div>
    </div>
    <div ng-show="target.firstMiss">
      <span ng-click="isShowCloseMissTargets=false" ng-show="isShowCloseMissTargets">Hide close miss targets</span>
      <span ng-click="isShowCloseMissTargets=true" ng-show="!isShowCloseMissTargets">Show close miss targets</span>
    </div>
    <div style="width:300px;">
      <%-- need to make it so that the bull is unclickable if the triple is selected --%>
      <span class="dtTarget" ng-click="toggleModifier('d')" ng-class="{activeModifier: targetData.modifier=='d'}">D</span>
      <span class="dtTarget" ng-click="toggleModifier('t')" ng-class="{activeModifier: targetData.modifier=='t'}" ng-hide="hideTriple()">T</span>
      <span style="display:inline-block; margin:5px;" ng-repeat="n in [] | reverseRangeWithBull:20" class="sTarget" ng-click="markDart(n)">{{n}}</span>
    </div>
  </div>
  <div><span ng-repeat="dart in roundResult" style="margin-right:10px;">{{dart}}</span></div>
    <span ng-click="postResult()" style="margin-top:10px;" class="smallButton green unselectable" ng-show="checkRoundsComplete() && targetData.isEditMode == false">
      Save game
    </span>
    <div style="margin-top:10px;">
      <div id="roundResults">
        <c:choose>
          <c:when test="${practiceMode == 'free'}">
            <div ng-repeat="result in targetData.results | reverse">
              <span name="roundResult" ng-hide="targetData.isEditMode" ng-click="selectEditRound(result)">Target: {{result.target}}, <span style="margin-left:20px;">Result: {{result.actual}},</span> <span style="margin-left:20px;">score: {{result.score}}</span></span>
              <span name="targetInput" class="sTarget" ng-show="targetData.isEditMode" ng-click="toggleDartToUpdate(result, 'target')" ng-class="{activeModifier: targetData.dartToUpdate == result && targetData.editMode == 'target'}">{{result.target}}:</span>
              <span name="resultInput" class="sTarget" ng-show="targetData.isEditMode" ng-click="toggleDartToUpdate(result, 'actual')" ng-class="{activeModifier: targetData.dartToUpdate == result && targetData.editMode != 'target'}" style="margin-left:20px;">{{result.actual}}</span>
            </div>
          </c:when>
          <c:when test="${practiceMode == '301'}">
            <div ng-repeat="turn in targetData.turns | reverse">
              <span ng-repeat="result in turn.results">
                <span name="roundResult" ng-hide="targetData.isEditMode"><span style="margin-left:20px;">{{result.actual}}</span></span>
                <span name="resultInput" class="sTarget" ng-show="targetData.isEditMode" ng-click="toggleDartToUpdate(result)" ng-class="{activeModifier: targetData.dartToUpdate == result}" style="margin-left:20px;">{{result.actual}}</span>
              </span>
            </div>
          </c:when>
          <c:otherwise>
            <div ng-repeat="result in targetData.turns | reverse">
              Round: <span>{{result.round}}</span>
              <span name="roundResult" style="margin-left:20px;" ng-hide="targetData.isEditMode">{{result.firstDart.actual}}, {{result.secondDart.actual}}, {{result.thirdDart.actual}}</span>
              <span name="firstDartInput" class="sTarget" ng-show="targetData.isEditMode" ng-click="toggleDartToUpdate(result.firstDart)" ng-class="{activeModifier: targetData.dartToUpdate==result.firstDart}">{{result.firstDart.actual}}</span>
              <span name="secondDartInput" class="sTarget" ng-show="targetData.isEditMode" ng-click="toggleDartToUpdate(result.secondDart)" ng-class="{activeModifier: targetData.dartToUpdate==result.secondDart}">{{result.secondDart.actual}}</span>
              <span name="thirdDartInput" class="sTarget" ng-show="targetData.isEditMode" ng-click="toggleDartToUpdate(result.thirdDart)" ng-class="{activeModifier: targetData.dartToUpdate==result.thirdDart}">{{result.thirdDart.actual}}</span>
            </div>
          </c:otherwise>
        </c:choose>
        <div ng-click="enableEditMode()" ng-show="targetData.turns.length > 0 || targetData.results.length > 0">Edit a dart</div>
        <span class="dbGreen smallButton unselectable" ng-show="targetData.isEditMode" ng-click="finishEditing()" style="float:right;">Save changes</span>
      </div>
    </div>
    <span ng-click="cancelGame()" id="cancelGame" class="smallButton red unselectable" style="margin-top:15px; display:inline-block;" ng-hide="isHideCancel()">Cancel game</span>
</div>