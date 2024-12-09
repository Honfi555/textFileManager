package com.textFileManager;

import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "GetCompositionContentServlet", urlPatterns = {"/GetCompositionContentServlet"})
public class GetCompositionContentServlet extends HttpServlet {

    private final CompositionManager manager = new CompositionManager();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException {

        // Получение необработанного запроса
        String query = request.getQueryString();
        String groupName = null;
        String fileName = null;

        if (query != null) {
            String[] params = query.split("&");
            for (String param : params) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2) {
                    String key = URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8);
                    String value = URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8);
                    if ("groupName".equals(key)) {
                        groupName = value;
                    } else if ("fileName".equals(key)) {
                        fileName = value;
                    }
                }
            }
        }

        if (groupName == null || groupName.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Имя группы не указано");
            return;
        }

        if (fileName == null || fileName.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Имя файла не указано");
            return;
        }

        String content = manager.getCompositionContent(groupName, fileName);
        if (content == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Файл не найден");
            return;
        }

        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(content);
    }
}
