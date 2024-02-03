Restaurant-voting application

### Java приложения на стеке: Spring Boot 3.2, Spring Data Rest/HATEOAS, Lombok, JPA, H2, Caffeine Cache, Swagger/OpenAPI 3.0


(EN)Technical requirement:
Design and implement a REST API using Hibernate/Spring/SpringMVC (Spring-Boot preferred!) without frontend.
The task is:
Build a voting system for deciding where to have lunch.
•	2 types of users: admin and regular users
•	Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
•	Menu changes each day (admins do the updates)
•	Users can vote for a restaurant they want to have lunch at today
•	Only one vote counted per user
•	If user votes again the same day:
o	If it is before 11:00 we assume that he changed his mind.
o	If it is after 11:00 then it is too late, vote can't be changed
Each restaurant provides a new menu each day.

(RU)Постройте систему голосования для принятия решения, где пообедать.
• 2 типа пользователей: администратор и обычные пользователи
• Администратор может вводить ресторан и его обеденное меню на день (обычно 2-5 пунктов, просто название блюда и цена)
• Меню меняется каждый день (админы вносят обновления)
• Пользователи могут голосовать за ресторан, в котором они хотят пообедать сегодня
• Один голос учитывается на пользователя
• Если пользователь голосует снова в тот же день:
• Если это до 11:00, предполагаем, что он передумал.
• Если это после 11:00, то уже поздно, голос изменить нельзя
Каждый ресторан предоставляет новое меню каждый день.



- Stack: [JDK 21](http://jdk.java.net/21/), Spring Boot 3.2.x, Lombok, H2, Caffeine Cache, Swagger/OpenAPI 3.0
- Run: `mvn spring-boot:run` in root directory.
- 
[REST API documentation](http://localhost:8080/)  
Credentials / Креденшелы:

```
Admin: admin@gmail.com / admin
User:  user@yandex.ru / password
Guest: guest@gmail.com / guest
```
