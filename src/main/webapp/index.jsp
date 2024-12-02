<%@ page import="java.util.concurrent.CopyOnWriteArrayList" %>
<%@ page import="com.example.lab_2.Coordinates" %>
<%@ page import="java.time.Duration" %>
<%@ page import="java.util.LinkedList" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
  LinkedList<Double> rValues = new LinkedList<Double>();
  rValues.add(1.0);
  rValues.add(1.5);
  rValues.add(2.0);
  rValues.add(2.5);
  rValues.add(3.0);
  CopyOnWriteArrayList<Coordinates> coordsList = (CopyOnWriteArrayList<Coordinates>) application.getAttribute("CoordsList");
  if (coordsList == null){
    coordsList = new CopyOnWriteArrayList<>();
    application.setAttribute("CoordsList", coordsList);
  }
%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>Lab 2</title>
  <link rel="stylesheet" href="./style.css">
</head>
<body>
<header>
  <div class="header-text">Медведев Ярослав Александрович</div>
  <div class="header-text">Группа Р3212</div>
  <div class="header-text">Вариант 409111</div>
</header>
<main>
  <form id="form">
    <div class = "field">
      <div class="label">X: </div>
      <input id="X" type="text" name="x" placeholder="-3 <= X <= 5" required>
      <div id="xError-message" class="err-message">Значение X должно быть указано в промежутке от -3 до 5</div>
    </div>
    <div class="field">
      <div id="input-Y">
        <div class="label">Y: </div>
        <div id="Y-button">
          <input type="button" value="-2" onclick="yButtonHandler(-2)">
          <input type="button" value="-1.5" onclick="yButtonHandler(-1.5)">
          <input type="button" value="-1" onclick="yButtonHandler(-1)">
          <input type="button" value="-0.5" onclick="yButtonHandler(-0.5)">
          <input type="button" value="0" onclick="yButtonHandler(0)">
          <input type="button" value="0.5" onclick="yButtonHandler(0.5)">
          <input type="button" value="1" onclick="yButtonHandler(1)">
          <input type="button" value="1.5" onclick="yButtonHandler(1.5)">
          <input type="button" value="2" onclick="yButtonHandler(2)">
        </div>
      </div>
      <div id="yError-message" class="err-message">Значение Y должно быть обязательно указано</div>
    </div>
    <div class="field">
      <div class="label">R: </div>
      <select id="r-select" required name="r">
        <%
          Double lastR = null;
          if (coordsList.size() > 0){
            lastR = coordsList.get(Math.max(coordsList.size() - 1, 0)).getR();
          }
        %>
        <option value=""></option>
        <%for (Double currR: rValues){
          if (currR.equals(lastR)){
        %>
        <option value="<%=currR%>" selected><%=currR%></option>
        <%} else{%>
        <option value="<%=currR%>" ><%=currR%></option>
          <%}
          }%>
      </select>
      <div id="rError-message" class="err-message">Значение R должно быть обязательно указано</div>
    </div>
    <div id="button-block">
      <button type="submit">Отправить</button>
      <button type="reset" onclick="clearFull()">Сброс</button>
    </div>
  </form>
  <canvas id="graphic" width="500px" height="500px">
    Брраузер не поддерживает canvas
  </canvas>
</main>
<footer>
  <table>
      <thead>
      <tr>
        <td>Время запроса</td> <td>Время обработки скрипта</td> <td>X</td> <td>Y</td> <td>R</td> <td>Попадание</td>
      </tr>
      </thead>
      <tbody id="table-body">
        <%for (Coordinates coords: coordsList){%>
        <tr class="table-line">
        <td><%=coords.getStartTime()%></td><td><%=Duration.between(coords.getStartTime(), coords.getFinishTime()).toMillis() + "ms"%></td><td class="x-table"><%=coords.getX()%></td><td class="y-table"><%=coords.getY()%></td><td class="r-table"><%=coords.getR()%></td><td class="hitting-table"><%=coords.isHitting()%></td>
        </tr>
        <%}%>
      </tbody>
  </table>
</footer>
<script src="script.js"></script>
</body>
</html>
