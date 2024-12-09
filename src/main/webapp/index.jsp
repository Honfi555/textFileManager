<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.textFileManager.CompositionInfo" %>
<%@ page import="com.textFileManager.CompositionManager" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Менеджер текстовых файлов</title>
    <link rel="icon" type="image/png" href="icons/books-32.png"/>
    <link rel="stylesheet" type="text/css" href="index.css" />
</head>
<body>
    <div class="container">
        <div class="left-panel">
            <%
                CompositionManager manager = new CompositionManager();
                Map<String, List<CompositionInfo>> allGroups = manager.getAllGroupsAndCompositions();
            %>

            <form id="files_selector">
                <%
                    // Перебираем группы
                    for (Map.Entry<String, List<CompositionInfo>> entry : allGroups.entrySet()) {
                        String groupName = entry.getKey();
                        List<CompositionInfo> compositions = entry.getValue();
                %>
                    <div class="group_container">
                        <div class="group_name"><%= groupName %></div>
                        <div class="compositions">
                            <%
                                for (CompositionInfo info : compositions) {
                            %>
                                <div class="file_container" data-group="<%= groupName %>" data-filename="<%= info.getFileName() %>">
                                    <%= info.getDisplayName() %>
                                </div>
                            <%
                                }
                            %>
                        </div>
                    </div>
                <%
                    }
                %>
            </form>
        </div>
        <div id="content_view_area">
            <div id="file_content" class="file_content"></div>
        </div>
    </div>

    <script src="index.js"></script>
</body>
</html>
