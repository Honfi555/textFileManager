<%@ page contentType="text/html;charset=UTF-8" %>
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
            <%@ page import="java.util.List" %>
            <%@ page import="com.textFileManager.GospelInfo" %>
            <%@ page import="com.textFileManager.GospelsManager" %>

            <%
                GospelsManager manager = new GospelsManager();
                List<GospelInfo> gospelsInfo = manager.getGospelsInfo();
            %>

            <form id="files_selector">
                <%
                    for (GospelInfo info : gospelsInfo) {
                %>
                    <div class="file_container" data-filename="<%= info.getFileName() %>">
                        <%= info.getDisplayName() %>
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
