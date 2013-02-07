<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="header.jsp"/>
<script src="/js/simplePracticeController.js"></script>
<div ng-controller="mainController">
<div style="float:left; margin-bottom:20px;">
    <div>Game mode: ${practiceMode}</div>
<a href="/practice" style="display:block; float:right;" class="button blue">Practice home</a>
</div>
<jsp:include page="footer.jsp"/>


