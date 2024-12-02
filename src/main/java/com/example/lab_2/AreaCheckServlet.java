package com.example.lab_2;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@WebServlet("/checkArea")
public class AreaCheckServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        Coordinates coords = (Coordinates) request.getAttribute("coords");
        coords.setHitting(this.checkArea(coords));
        ServletContext context = this.getServletContext();
        CopyOnWriteArrayList<Coordinates> coordsList = (CopyOnWriteArrayList<Coordinates>) context.getAttribute("CoordsList");
        if (coordsList == null) {
            coordsList = new CopyOnWriteArrayList<>();
        }
        coordsList.add(coords);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/table.jsp");
        coords.setFinishTime(LocalDateTime.now());
        dispatcher.forward(request, response);
    }

    public Boolean checkArea(Coordinates coords){
        if (coords.getX() >= 0){
            if (coords.getY() >= 0) {
                return (coords.getX()*coords.getX() + coords.getY()* coords.getY()) <= coords.getR()*coords.getR();
            } else {
                return coords.getY() >= 2* coords.getX() - coords.getR();
            }
        } else {
            if (coords.getY() > 0) {
                return false;
            } else {
                return coords.getX() >= -1 * coords.getR() && coords.getY() >= -1 * (coords.getR() / 2);
            }
        }
    }
}
