# RenderFarmClient

![ClientImage](https://github.com/olliestyle/RenderFarmClient/blob/master/images/regLog.png)

![ClientImage](https://github.com/olliestyle/RenderFarmClient/blob/master/images/addTasks.png)

![ClientImage](https://github.com/olliestyle/RenderFarmClient/blob/master/images/showTasksAndExit.png)

Проект, реализующий следующий функционал:
- Регистрация пользователя
- Создание новой задачи
- Отображение списка созданных задач
- Отображение истории смены статусов задачи

Сборка проекта производится командой "mvn package"
<br> Запуск сервера производится командой "java -jar RenderFarmClient-1.0-jar-with-dependencies.jar ipAddress portNumber", 
где ipAddress - адрес, на котором запущен сервер, portNumber - это порт,
на котором запущен сервер.

Передача сообщений происходит с помощью TCP, используя функционал класса Socket.
<br> Для сериализации/десериализации сообщений используется функционал пакета java.io.*