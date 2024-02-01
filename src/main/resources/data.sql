INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('Guest', 'guest@gmail.com', '{noop}guest');

INSERT INTO USER_ROLE (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO RESTAURANT(NAME, ADDRESS)
VALUES ('KFC', 'Moscow, Red Square'),
       ('GOURMAND', 'Moscow, Pushkina st. 6');

INSERT INTO DISH (NAME, MENU_DATE, PRICE, RESTAURANT_ID)
VALUES ('fried eggs', '2024-01-01', 10.5, 1),
       ('hamburger', '2024-01-01', 15.1, 1),
       ('fried potato', '2024-01-01', 10, 1),
       ('coffee', '2024-01-01', 6, 1),

       ('khinkali', '2024-01-01', 15, 2),
       ('plov', '2024-01-01', 10, 2),
       ('juice', '2024-01-01', 4.4, 2),

       ('borsch', current_date, 14, 1),
       ('mushroom soup', current_date, 12.1, 1),
       ('popcorn', current_date, 14, 1),

       ('beef steak', current_date, 11, 2),
       ('dranniki', current_date, 12, 2),
       ('juice', current_date, 4.4, 2);


INSERT INTO VOTE(MENU_DATE, RESTAURANT_ID, USER_ID)
VALUES ('2024-01-01', 2, 1),
       ('2024-01-01', 1, 2),
       ('2024-01-01', 1, 3),
       ('2024-01-02', 2, 3),
       ('2024-01-02', 2, 2),
       ('2024-01-02', 1, 1),
       (current_date, 1, 1);