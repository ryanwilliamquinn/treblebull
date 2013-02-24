<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html lang="en" ng-app="dartsApp">
    <head>
        <title>freaking darts</title>
        <meta name="viewport" content="width=device-width">
        <link rel="stylesheet" href="/css/main.css" type="text/css"/>
    </head>
    <body>
        <div class="header">
            <div id="headerContent">
                <h3 id="headerTitle">Darts Practice</h3>

                <div class="headerAccount">
                    <shiro:guest>
                        <a href="/login">Login</a> | <a href="signup.jsp">Signup</a>
                    </shiro:guest>
                    <shiro:user>
                        <c:set var="username">
                        <shiro:principal/>
                        </c:set>
                        <a href="/user/${username}">Your Account</a> | <a href="/logout">Logout</a>
                    </shiro:user>
                </div>
                <div style="clear:both;"> </div>
            </div>
        </div>
        <div class="content">


