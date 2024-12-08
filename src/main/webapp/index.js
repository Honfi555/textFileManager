// Получаем все div.file_container
const fileContainers = document.querySelectorAll('.file_container');
const fileContentDiv = document.getElementById('file_content');

fileContainers.forEach(container => {
    container.addEventListener('click', () => {
        fileContainers.forEach(fc => fc.classList.remove('selected'));
        container.classList.add('selected');

        const fileName = container.getAttribute('data-filename');
        console.log(fileName);

        fetch(`GetGospelContentServlet?fileName=${fileName}`)
            .then(response => response.text())
            .then(content => {
                // Вместо текстовой вставки используем innerHTML
                fileContentDiv.innerHTML = content;
            })
            .catch(error => {
                console.error('Ошибка при получении содержимого файла:', error);
                fileContentDiv.textContent = "Ошибка загрузки файла.";
            });
    });
});
