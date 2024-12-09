// Получаем все div.file_container
const fileContainers = document.querySelectorAll('.file_container');
const fileContentDiv = document.getElementById('file_content');

fileContainers.forEach(container => {
    container.addEventListener('click', () => {
        // Снимаем выделение со всех
        fileContainers.forEach(fc => fc.classList.remove('selected'));
        // Выделяем текущий
        container.classList.add('selected');

        const fileName = container.getAttribute('data-filename');
        const groupName = container.getAttribute('data-group');

        // Выполняем запрос к сервлету с указанием группы и имени файла
        fetch(`GetCompositionContentServlet?groupName=${encodeURIComponent(groupName)}&fileName=${encodeURIComponent(fileName)}`)
            .then(response => response.text())
            .then(content => {
                fileContentDiv.innerHTML = content;
            })
            .catch(error => {
                console.error('Ошибка при получении содержимого файла:', error);
                fileContentDiv.textContent = "Ошибка загрузки файла.";
            });
    });
});
