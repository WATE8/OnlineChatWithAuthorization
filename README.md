💬 Онлайн-чат на Spring Boot + WebSocket + JWT + MySQL
📌 Описание
Это простой, но полноценный онлайн-чат с авторизацией, регистрацией, WebSocket-соединением и хранением пользователей в MySQL. После входа пользователю выдаётся JWT-токен, с которым он может подключиться к чату через WebSocket.

🚀 Функциональность
Регистрация и вход пользователей

JWT-аутентификация

WebSocket-чат с автоопределением отправителя

Уведомления о входе/выходе участников

Хранение пользователей в базе данных MySQL

⚙️ Технологии
Java 17+

Spring Boot 3

Spring Web

Spring WebSocket

Spring Data JPA

MySQL

JWT (JJWT)

HTML + JS (Vanilla)

📂 Структура проекта
csharp
Копировать
Редактировать
chat/
├── MainApp.java                 # Точка входа + WebSocket конфигурация
├── ChatWebSocketHandler.java    # Обработка сообщений по WebSocket
├── AuthController.java          # REST-контроллер для авторизации
├── User.java                    # JPA-сущность пользователя
├── UserRepository.java          # Репозиторий JPA
├── resources/
│   └── application.properties   # Настройки БД и Hibernate
└── static/
└── index.html               # Основной интерфейс чата
🔧 Настройка
1. 📦 Зависимости (пример pom.xml)
   Убедись, что у тебя в pom.xml есть:

xml
Копировать
Редактировать
<dependencies>
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-websocket</artifactId>
</dependency>
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
<groupId>com.mysql</groupId>
<artifactId>mysql-connector-j</artifactId>
</dependency>
<dependency>
<groupId>io.jsonwebtoken</groupId>
<artifactId>jjwt-api</artifactId>
<version>0.11.5</version>
</dependency>
<dependency>
<groupId>io.jsonwebtoken</groupId>
<artifactId>jjwt-impl</artifactId>
<version>0.11.5</version>
<scope>runtime</scope>
</dependency>
<dependency>
<groupId>io.jsonwebtoken</groupId>
<artifactId>jjwt-jackson</artifactId>
<version>0.11.5</version>
<scope>runtime</scope>
</dependency>
</dependencies>
2. ⚙️ application.properties
   properties
   Копировать
   Редактировать
   spring.datasource.url=jdbc:mysql://localhost:3306/chatdb
   spring.datasource.username=root
   spring.datasource.password=4444
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
3. 🧰 MySQL
   Создай базу данных:

sql
Копировать
Редактировать
CREATE DATABASE chatdb;
4. ▶️ Запуск
   bash
   Копировать
   Редактировать
   ./mvnw spring-boot:run
   Открой браузер и перейди на http://localhost:8080

🔒 Авторизация
После входа пользователь получает JWT, который прикрепляется как параметр к WebSocket-соединению.

Сервер валидирует токен, определяет пользователя и добавляет его в чат.

🖼️ Интерфейс
HTML + JS интерфейс (в index.html):

Регистрация и вход

Отправка сообщений

Прокрутка сообщений

Отображение имени отправителя (выделение себя)

✅ Пример входа
bash
Копировать
Редактировать
POST /auth/login
{
"username": "user1",
"password": "pass1"
}
Ответ:

json
Копировать
Редактировать
{
"token": "eyJhbGciOiJIUzI1..."
}
WebSocket-подключение:

bash
Копировать
Редактировать
ws://localhost:8080/chat?token=eyJhbGciOi...
📌 TODO (что можно улучшить):
Хэширование паролей (например, с BCrypt)

Подключение к фронтенду через WebSocket из внешнего клиента

Хранение сообщений в БД

Возможность личных сообщений

Список активных пользователей

👤 Автор
Создан вручную для изучения WebSocket и Spring Boot.