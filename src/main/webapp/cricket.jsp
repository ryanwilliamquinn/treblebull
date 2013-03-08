<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="header.jsp"/>

<div ng-controller="mainController">
    <div style="float:left; margin-bottom:20px;">
        <div>Game mode: ${practiceMode}</div>
        <div ng-hide="targetData.isShowRounds">
            <span ng-click="showInputs()" class="smallButton blue">Start a game of cricket</span>
        </div>

        <jsp:include page="buttons.jsp"/>
        <div ng-show="targetData.isShowRounds" style="clear:both;">
            <table>
                <tr ng-repeat="target in targets | orderBy:predicate">
                    <td><div style="margin-right:15px;">{{target.label}}</div></td>
                    <td><image src="/images/mark_{{target.num}}.png"/ style="vertical-align:middle; position:relative; bottom:1px;"></td>
                </tr>
            </table>
        </div>
        <jsp:include page="pastRounds.jsp"/>
        <div style="margin:30px 0px 0px 20px; float:left;" ng-show="targetData.allGames.length > 0">
            All time average: {{targetData.allGames|runningAverage}}
        </div>
    </div>
    <a href="/practice" style="display:block; float:right;" class="button blue">Practice home</a>
    <div style="clear:both;" ng-show="needsShowAll">
        <span ng-click="showAll()" style="cursor:pointer;" class="button blue">Show all results</span>
    </div>

</div>

<div id="container" style="width: 100%; height: 400px"></div>

<jsp:include page="bottomIncludes.jsp"/>
<script src="/js/cricketController.js"></script>
<jsp:include page="footer.jsp"/>


