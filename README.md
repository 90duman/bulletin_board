# bulletin_board

Тестовое задание

Ссылка на swagger: http://localhost:8085/swagger-ui.html

проект работает на порту: http://localhost:8085/

Для запуска проекта наберите команду: docker-compose up --build

Пример запроса на API:
    curl --location --request GET 'http://localhost:8085/api/ads' \
    --header 'token: Bearer:тут токен'