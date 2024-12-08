package com.textFileManager;

import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.*;

@WebServlet(name = "GetGospelContentServlet", urlPatterns = {"/GetGospelContentServlet"})
public class GetGospelContentServlet extends HttpServlet {

    private final GospelsManager manager = new GospelsManager();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException {

        String fileName = request.getParameter("fileName");
        if (fileName == null || fileName.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Имя файла не указано");
            return;
        }

        String content = manager.getGospelContent(fileName);
        if (content == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Файл не найден");
            return;
        }

        response.setContentType("text/plain; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(content);
    }
}
