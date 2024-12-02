<%@ page import="com.example.lab_2.Coordinates" %>
<%@ page import="java.time.Duration" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Coordinates coords = (Coordinates) request.getAttribute("coords");
%>
<html>
<head>
    <title>Results</title>
    <link rel="stylesheet" href="./table-style.css">
</head>
<body>
    <table>
        <thead>
        <tr>
            <td>Время запроса</td> <td>Время обработки запроса</td> <td>X</td> <td>Y</td> <td>R</td> <td>Попадание</td>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td><%=coords.getStartTime().toString()%></td><td><%=Duration.between(coords.getStartTime(), coords.getFinishTime()).toMillis() + "ms"%></td><td><%=coords.getX()%></td><td><%=coords.getY()%></td><td><%=coords.getR()%></td><td><%=coords.isHitting()%></td>
        </tr>
        </tbody>
    </table>
    <form action="../" method="GET">
    <input type="submit" value="Return to the homepage">
    </form>
</body>
</html>
