package com.example.lab_2;
import java.io.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(value = "/controller")
public class ControllerServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ControllerServlet.class.getName());

    private static volatile String BAD_REQUEST_MESSAGE;
    private CopyOnWriteArrayList<Double> rRange;

    @Override
    public void init() throws ServletException{
        ControllerServlet.BAD_REQUEST_MESSAGE = "The request must contain the following data in application/json format:" +
        " x (a fractional number between -3 and 5)," +
        " y (a fractional number from the following list: -2, -1.5, -1, -0.5, 0, 0.5, 1, 1.5, 2),"+
        " r (fractional number from the following enumeration: 1, 1.5, 2, 2.5, 3)";
        this.rRange = new CopyOnWriteArrayList<>();
        this.rRange.add(1.0);
        this.rRange.add(1.5);
        this.rRange.add(2.0);
        this.rRange.add(2.5);
        this.rRange.add(3.0);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String dataType = request.getHeader("Content-Type");
        LocalDateTime startTime = LocalDateTime.now();
        logger.info(dataType);
        if (dataType != null && dataType.equals("application/json")) {
            try {
                BufferedReader reader = request.getReader();
                ObjectMapper objectMapper = new ObjectMapper();
                HashMap<String, String> postMap = objectMapper.readValue(reader, new TypeReference<HashMap<String, String>>() {});
                if (postMap.get("x") != null && postMap.get("y") != null && postMap.get("r") != null) {
                    Double x = Double.parseDouble(postMap.get("x"));
                    Double y = Double.parseDouble(postMap.get("y"));
                    Double r = Double.parseDouble(postMap.get("r"));
                    if (this.rRange.contains(r)) {
                        Coordinates coords = new Coordinates(x, y, r, startTime);
                        request.setAttribute("coords", coords);
                        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/checkArea");
                        dispatcher.forward(request, response);
                    } else{
                        this.sendBadRequest(response);
                    }
                } else {
                    this.sendBadRequest(response);
                }
            } catch (IOException | NumberFormatException e) {
                logger.info(e.toString());
                this.sendBadRequest(response);
            }
        } else {
            this.sendBadRequest(response);
        }
    }

    public void sendBadRequest(HttpServletResponse response) throws IOException{
        PrintWriter out = response.getWriter();
        response.setStatus(400);
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        out.println(ControllerServlet.BAD_REQUEST_MESSAGE);
        out.close();
    }
}