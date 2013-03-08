<%-- what do i need to add to the controller to make this just work?  can i do this in a directive somehow? seems like it would be a lot of things. --%>
<div class="rounds unselectable" ng-show="targetData.isShowRounds">
  <div ng-hide="checkRoundsComplete()">
    <div>
      <div style="display:inline-block; margin:5px;" class="sTarget" ng-click="markDart(target.id)" ng-show="target.id">{{target.id}}</div>
      <div style="display:inline-block; margin:5px;" class="sdtTarget" ng-click="markDart('d' + target.id)" ng-show="target.id">double {{target.id}}</div>
      <div style="display:inline-block; margin:5px;" class="sdtTarget" ng-click="markDart('t' + target.id)" ng-show="target.id && target.id != 'bull'">triple {{target.id}}</div>
    </div>
    <div style="width:300px;">
      <span class="dtTarget" ng-click="toggleModifier('d')" ng-class="{activeModifier: targetData.modifier=='d'}">D</span>
      <span class="dtTarget" ng-click="toggleModifier('t')" ng-class="{activeModifier: targetData.modifier=='t'}" ng-hide="target.id == 'bull'">T</span>
      <span style="display:inline-block; margin:5px;" ng-repeat="n in [] | reverseRangeWithBull:20" class="sTarget" ng-click="markDart(n)">{{n}}</span>
    </div>
  </div>
  <div><span ng-repeat="dart in roundResult" style="margin-right:10px;">{{dart}}</span></div>
    <span ng-click="postResult()" style="margin-top:10px;" class="smallButton green unselectable" ng-show="checkRoundsComplete()">Save game</span>
    <div style="margin-top:10px;">
      <div id="roundResults" ng-repeat="result in targetData.results">
        Round: <span>{{result.round}}</span>
        <span name="roundResult" style="margin-left:20px;" ng-hide="selectedEditRound == result" ng-click="selectEditRound(result)">{{result.firstDart}}, {{result.secondDart}}, {{result.thirdDart}}  <span style="margin-left:20px;">round score: {{result.score}}</span></span>
        <span name="firstDartInput" class="sTarget" ng-show="selectedEditRound == result" ng-click="toggleDartToUpdate('first')" ng-class="{activeModifier: targetData.dartToUpdate=='first'}">{{result.firstDart}}</span>
        <span name="secondDartInput" class="sTarget" ng-show="selectedEditRound == result" ng-click="toggleDartToUpdate('second')" ng-class="{activeModifier: targetData.dartToUpdate=='second'}">{{result.secondDart}}</span>
        <span name="thirdDartInput" class="sTarget" ng-show="selectedEditRound == result" ng-click="toggleDartToUpdate('third')" ng-class="{activeModifier: targetData.dartToUpdate=='third'}">{{result.thirdDart}}</span>
        <span class="blue smallButton unselectable" ng-show="selectedEditRound == result" ng-click="finishEditing(result)">Save</span>
      </div>
    </div>
    <span ng-click="cancelGame()" id="cancelGame" class="smallButton red unselectable" style="margin-top:15px; display:inline-block;" ng-hide="isEditRound">Cancel game</span>
</div>