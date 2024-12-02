package com.example.lab_2;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

@WebFilter("/*")
public class RootFilter implements Filter {
    private static final Logger logger = Logger.getLogger(RootFilter.class.getName());
    private static CopyOnWriteArrayList<String> permittedResources;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.permittedResources = new CopyOnWriteArrayList<>();
        String contextPath = filterConfig.getServletContext().getContextPath();
        this.permittedResources.add(contextPath + "/");
        this.permittedResources.add(contextPath + "/controller");
        this.permittedResources.add(contextPath + "/script.js");
        this.permittedResources.add(contextPath + "/style.css");
        this.permittedResources.add(contextPath + "/table-style.css");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String currUrl = request.getRequestURI();
        logger.info(currUrl);
        if (this.permittedResources.contains(currUrl)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            response.sendRedirect(request.getContextPath() + "/");
        }
    }
}
