INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('Guest', 'guest@gmail.com', '{noop}guest');

INSERT INTO USER_ROLE (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO RESTAURANT(NAME, RATING)
VALUES ('KFC', 0),
       ('GOURMAND', 0),
       ('Georgia', 0);

INSERT INTO DISH (NAME, MENU_DATE, PRICE, RESTAURANT_ID)
VALUES ('fried eggs', '2024-01-01', 10.5, 1),
       ('hamburger', '2024-01-01', 15.1, 1),
       ('fried potato', '2024-01-01', 10, 1),
       ('coffee', '2024-01-01', 6, 2),

       ('odjahuri', '2024-01-01', 15, 2),
       ('khabidzgina', '2024-01-01', 10, 2),
       ('juice', '2024-01-01', 4.4, 2),

       ('khinkali', '2024-01-01', 8, 3),
       ('plov', '2024-01-01', 12, 3),
       ('tea', '2024-01-01', 4.5, 3),

       ('borsch', '2024-01-02', 14, 1),
       ('mushroom soup', '2024-01-02', 12.1, 1),
       ('popcorn', '2024-01-02', 14, 1),
       ('coffee', '2024-01-02', 6, 2),

       ('beef steak', '2024-01-02', 11, 2),
       ('dranniki', '2024-01-02', 12, 2),
       ('juice', '2024-01-02', 4.3, 2),

       ('lagman', '2024-01-02', 8, 3),
       ('chiken soup', '2024-01-02', 11, 3),
       ('tea', '2024-01-02', 4.7, 3);
