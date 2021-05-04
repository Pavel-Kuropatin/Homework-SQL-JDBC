-- #1 Вывести список юзеров старше 18 лет и моложе 25 (вычисление возраста сделать без добавления новой колонки age)
-- EXPLAIN ANALYZE
SELECT DATE_PART('year', AGE(birth_date)) AS age
FROM users
WHERE DATE_PART('year', AGE(birth_date)) < 25
  AND DATE_PART('year', AGE(birth_date)) > 18
ORDER BY id;

-- #2 Подсчитать число машин у каждого пользователя. Вывести в формате User full name (username + пробел + user surname) | Число машин у пользователя
-- EXPLAIN ANALYZE
SELECT concat(u.name, ' ', u.surname) AS user_full_name, COUNT(c.id) AS count
FROM users u
         LEFT JOIN cars c ON c.owner = u.id
GROUP BY u.id
ORDER BY count DESC;

-- #3 Вывести список самых популярных моделей (топ-3) машин в каждом диллере
--    Вывести популярные для каждого дилера отдельно или популярные среди всех дилеров (всего 3 модели на выводе)?
--    И в каком формате выводить? Только модель? Или модель/количество?
-- EXPLAIN ANALYZE
SELECT concat(c.name, ' ', c.model), count(c.model) as count
FROM dealer d
         INNER JOIN cars c on d.id = c.dealer_id
         INNER JOIN users u on c.owner = u.id
GROUP BY c.model, c.name
ORDER BY count DESC
LIMIT 3;

-- #4 Определить логины пользователей, имеющих больше 3 машин
-- EXPLAIN ANALYZE
SELECT u.login
FROM users u
         INNER JOIN cars c on u.id = c.owner
GROUP BY u.id
HAVING COUNT(u.id) > 3
ORDER BY u.id;

-- #5 Вывести уникальных диллеров с подсчитанной суммой стоимостей машин, связанных с ними
-- EXPLAIN ANALYZE
SELECT d.name AS dealer_name, SUM(c.price) AS sum
FROM dealer d
         INNER JOIN cars c on d.id = c.dealer_id
GROUP BY d.name
ORDER BY sum DESC;

-- #6 Подсчитать количество уникальных пользователей, владеющих хотя бы одной машиной, стоимость которой превышает среднюю стоимость всех машин
-- EXPLAIN ANALYZE
SELECT COUNT(DISTINCT u.id) AS count
FROM users u
         INNER JOIN cars c on u.id = c.owner
WHERE c.price > (SELECT AVG(price)
                 FROM cars);

-- users age
SELECT AGE(birth_date) AS age
FROM users;

-- avg_price = 55310.785714285714
SELECT AVG(price)
FROM cars;