--Выбрать в таблице Orders заказы, которые были доставлены после 5 мая 1998 года (колонка ShippedDate)
--включительно и которые доставлены с ShipVia >= 2. Формат указания даты должен быть верным при любых
--региональных настройках. Этот метод использовать далее для всех заданий. Запрос должен высвечивать
--только колонки OrderID, ShippedDate и ShipVia. Пояснить почему сюда не попали заказы с NULL-ом в колонке ShippedDate.

SELECT orderId, shippedDate, shipvia FROM orders WHERE shippedDate >= '05/05/1998' and shipVia >= 2;


--Написать запрос, который выводит только недоставленные заказы из таблицы Orders. В результатах запроса высвечивать
--для колонки ShippedDate вместо значений NULL строку 'Not Shipped' – необходимо использовать CASЕ выражение. Запрос
--должен высвечивать только колонки OrderID и ShippedDate.

SELECT orderId, CASE WHEN shippedDate IS NULL THEN 'Not Shipped' ELSE CAST(shippedDate AS CHAR(50)) END
FROM orders WHERE shippedDate IS NULL;

--Выбрать в таблице Orders заказы, которые были доставлены после 5 мая 1998 года (ShippedDate) не включая эту дату или которые
--еще не доставлены. В запросе должны высвечиваться только колонки OrderID (переименовать в Order Number) и ShippedDate 
--(переименовать в Shipped Date). В результатах запроса высвечивать для колонки ShippedDate вместо значений NULL строку
--'Not Shipped' - необходимо использовать функцию NVL, для остальных значений высвечивать дату в формате "ДД.ММ.ГГГГ".

SELECT orderId AS "Order Number", nvl(to_char(shippedDate, 'DD.MM.YYYY'), 'Not Shipped') AS "Shipped Date" 
FROM orders WHERE shippeddate > '05/05/1998' or shippeddate IS NULL;

--Выбрать из таблицы Customers всех заказчиков, проживающих в USA или Canada. Запрос сделать только c помощью оператора IN.
--Высвечивать колонки с именем пользователя и названием страны в результатах запроса. Упорядочить результаты запроса по имени
--заказчиков и по месту проживания.

SELECT contactName, country FROM customers WHERE country in ('USA', 'Canada') ORDER BY contactName, country;


--Выбрать из таблицы Customers всех заказчиков, не проживающих в USA и Canada. Запрос сделать с помощью оператора IN. 
--Высвечивать колонки с именем пользователя и названием страны в результатах запроса. 
--Упорядочить результаты запроса по имени заказчиков.

SELECT contactName, country FROM customers WHERE country NOT IN ('USA', 'Canada') ORDER BY contactName;

-- Выбрать из таблицы Customers все страны, в которых проживают заказчики. Страна должна быть упомянута только один раз 
-- и список отсортирован по убыванию. Не использовать предложение GROUP BY. 
--Высвечивать только одну колонку в результатах запроса.

SELECT DISTINCT country from customers ORDER BY country DESC;

-- Выбрать все заказы (OrderID) из таблицы Order_Details (заказы не должны повторяться), где встречаются продукты
-- с количеством от 3 до 10 включительно – это колонка Quantity в таблице Order_Details. Использовать оператор BETWEEN.
-- Запрос должен высвечивать только колонку OrderID.
 
SELECT DISTINCT orderId FROM order_details WHERE quantity BETWEEN 3 AND 10;

--Выбрать всех заказчиков из таблицы Customers, у которых название страны начинается на буквы из диапазона B и G.
--Использовать оператор BETWEEN. Проверить, что в результаты запроса попадает Germany. Запрос должен высвечивать только
--колонки CustomerID и Country и отсортирован по Country.

SELECT customerId, country FROM customers WHERE country BETWEEN 'B%' AND 'H%' ORDER BY country;

--Выбрать всех заказчиков из таблицы Customers, у которых название страны начинается на буквы из диапазона B и G,
--не используя оператор BETWEEN. Запрос должен высвечивать только колонки CustomerID и Country и отсортирован по Country.
--С помощью опции "Execute Explain Plan" определить какой запрос предпочтительнее 3.2 или 3.3, необходимо объяснить почему
--и написать ответ в комментариях к текущему запросу. 
 
SELECT customerId, country FROM customers WHERE country >= 'B%' and country <= 'H%' ORDER BY country;

--Выполнив запрос в режиме "Explain plan" я увидел одинаковые показатели по данным запросам. Также, я увидел, что
--поиск через BETWEEN эквивалентен поиску через >= <=.

--В таблице Products найти все продукты (колонка ProductName), где встречается подстрока 'chocolade'. 
--Известно, что в подстроке 'chocolade' может быть изменена одна буква 'c' в середине - найти все продукты,
--которые удовлетворяют этому условию. Подсказка: в результате должны быть найдены 2 строки.

SELECT * FROM products WHERE productName LIKE '%cho_olade%';

--Найти общую сумму всех заказов из таблицы Order_Details с учетом количества закупленных товаров и скидок по ним.
--Результат округлить до сотых и отобразить в стиле:
--$X,XXX.XX где "$" - валюта доллары, "," – разделитель групп разрядов, "." – разделитель целой и дробной части,
--при этом дробная часть должна содержать цифры до сотых, пример: $1,234.00
--Скидка (колонка Discount) составляет процент из стоимости для данного товара. Для определения действительной цены
--на проданный продукт надо вычесть скидку из указанной в колонке UnitPrice цены. Результатом запроса должна быть одна
--запись с одной колонкой с названием колонки 'Totals'.

SELECT to_char(SUM((unitPrice - unitPrice * discount) * quantity), '$999,999,999.99') as Totals FROM order_details;

--По таблице Orders найти количество заказов, которые еще не были доставлены (т.е. в колонке ShippedDate нет значения
--даты доставки). Использовать при этом запросе только оператор COUNT. Не использовать предложения WHERE и GROUP.

SELECT COUNT(orderId) FROM orders WHERE shippedDate IS NULL;


--По таблице Orders найти количество различных покупателей (CustomerID), сделавших заказы. Использовать функцию COUNT
--и не использовать предложения WHERE и GROUP.

SELECT COUNT(DISTINCT customerId) FROM orders;

--По таблице Orders найти количество заказов с группировкой по годам. В результатах запроса надо высвечивать две колонки
--c названиями Year и Total. Написать проверочный запрос, который вычисляет количество всех заказов.

SELECT COUNT(orderId) FROM orders;
SELECT to_char(orderDate, 'YYYY') AS "Year", COUNT(orderId) AS "Total" FROM orders GROUP BY to_char(orderDate, 'YYYY');

--По таблице Orders найти количество заказов, cделанных каждым продавцом. Заказ для указанного продавца – это любая запись
--в таблице Orders, где в колонке EmployeeID задано значение для данного продавца. В результатах запроса надо высвечивать
--колонку с именем продавца (Должно высвечиваться имя полученное конкатенацией LastName & FirstName. Эта строка LastName &
--FirstName должна быть получена отдельным запросом в колонке основного запроса. Также основной запрос должен использовать
--группировку по EmployeeID.) с названием колонки 'Seller' и колонку c количеством заказов высвечивать с названием 'Amount'.
--Результаты запроса должны быть упорядочены по убыванию количества заказов.

SELECT COUNT(orderId) as "Amount",
CONCAT(CONCAT((SELECT emp.lastname FROM employees emp WHERE orders.employeeid = emp.employeeid), ' '),
(SELECT emp.firstname FROM employees emp WHERE orders.employeeid = emp.employeeid)) AS "Seller" FROM orders 
GROUP BY employeeId ORDER BY COUNT(orderId) DESC;

--Выбрать 5 стран в которых проживает наибольшее количество заказчиков. Список должен быть отсортирован по убыванию популярности.
--Необходимо выводить два столбца - Country и Count (количество заказчиков).

SELECT COUNT(DISTINCT customerid) AS "Count", shipCountry AS "Country" FROM orders GROUP BY shipCountry ORDER BY "Count" DESC;

-- По таблице Orders найти количество заказов, cделанных каждым продавцом и для каждого покупателя.
-- Необходимо определить это только для заказов сделанных в 1998 году. В результатах запроса надо высвечивать колонку
-- с именем продавца (название колонки 'Seller'), колонку с именем покупателя (название колонки 'Customer') и колонку c
-- количеством заказов высвечивать с названием 'Amount'. В запросе необходимо использовать специальный оператор языка PL/SQL
-- для работы с выражением GROUP (Этот же оператор поможет выводить строку "ALL" в результатах запроса). Подсказка: использовать
-- операторы: ROLLUP, CUBE, GROUPING.


SELECT CASE WHEN employeeId IS NULL THEN 'ALL' ELSE 
CONCAT(CONCAT((SELECT emp.firstname FROM employees emp WHERE emp.employeeid = orders.employeeid), ' '),
(SELECT emp.lastname FROM employees emp WHERE emp.employeeid = orders.employeeid)) END AS "Seller",
CASE WHEN customerId IS NULL THEN 'ALL' ELSE 
(SELECT c.companyname FROM customers c WHERE c.customerId = orders.customerid) END AS "Customer", count(orderId) AS "Amount" 
FROM orders WHERE EXTRACT(YEAR FROM TO_DATE(orderDate)) = 1998 GROUP BY 
GROUPING SETS((), (customerId), (employeeId),(employeeId, customerId) ) ORDER BY "Amount" DESC;

--Найти покупателей и продавцов, которые живут в одном городе. Если в городе живут только один или несколько продавцов или
--только один или несколько покупателей, то информация о таких покупателя и продавцах не должна попадать в результирующий набор.
--Не использовать конструкцию JOIN. В результатах запроса необходимо вывести следующие заголовки для результатов запроса: 
--'Person', 'Type' (здесь надо выводить строку 'Customer' или 'Seller' в завимости от типа записи), 'City'. 
--Отсортировать результаты запроса по колонке 'City' и по 'Person'.

select c.companyName as "Person", 'Customer' as "Type" , c.city as "City" from customers c 
union select CONCAT(CONCAT(e.lastname, ' '), e.firstname), 
 'Seller', e.city from employees e ORDER BY "City", "Person";

--Найти всех покупателей, которые живут в одном городе. В запросе использовать соединение таблицы 
--Customers c собой - самосоединение. Высветить колонки CustomerID и City. Запрос не должен высвечивать дублируемые записи.
--Отсортировать результаты запроса по колонке City. Для проверки написать запрос, который высвечивает города, которые встречаются
--более одного раза в таблице Customers. Это позволит проверить правильность запроса.

SELECT DISTINCT c1.customerid AS "CustomerId", c1.city AS "City" FROM customers c1, customers c2 
where c1.city = c2.city AND c1.customerid <> c2.customerid ORDER BY "City";

SELECT COUNT(city) AS "Amount", city FROM customers c GROUP BY city HAVING COUNT(city) > 1;

-- По таблице Employees найти для каждого продавца его руководителя, т.е. кому он делает репорты. Высветить колонки с
-- именами 'User Name' (LastName) и 'Boss'. В колонках должны быть высвечены имена из колонки LastName. Высвечены ли все
-- продавцы в этом запросе? Да, все.

select lastName as "User Name", (select lastname from employees where employeeId = e.reportsTo) as "Boss" from employees e;

--Определить продавцов, которые обслуживают регион 'Western' (таблица Region). Результаты запроса должны высвечивать 
--два поля: 'LastName' продавца и название обслуживаемой территории ('TerritoryDescription' из таблицы Territories). 
--Запрос должен использовать JOIN в предложении FROM. Для определения связей между таблицами Employees и Territories надо
--использовать графическую схему для базы Southwind.

SELECT e.lastname, t.territorydescription FROM employees e 
JOIN employeeterritories et ON e.employeeid = et.employeeid
JOIN territories t ON et.territoryId = t.territoryId
JOIN region r ON r.regionId = t.regionId and r.regionid = 2;

--Высветить в результатах запроса имена всех заказчиков из таблицы Customers и суммарное количество их заказов из таблицы Orders.
--Принять во внимание, что у некоторых заказчиков нет заказов, но они также должны быть выведены в результатах запроса.
--Упорядочить результаты запроса по возрастанию количества заказов.

SELECT c.customerid, COUNT(o.orderId) AS "Amount" FROM customers c 
LEFT OUTER JOIN orders o ON c.customerId = o.customerId GROUP BY c.customerid ORDER BY "Amount";

--Высветить всех поставщиков колонка CompanyName в таблице Suppliers, у которых нет хотя бы одного продукта на складе
--(UnitsInStock в таблице Products равно 0). �?спользовать вложенный SELECT для этого запроса с использованием оператора IN.
--Можно ли использовать вместо оператора IN оператор '=' ? Нет, оператор in проверяет вхождение а не равенство.

SELECT * FROM suppliers s WHERE s.supplierId in (select p.supplierId FROM products p WHERE p.unitsInStock = 0);

--Высветить всех продавцов, которые имеют более 150 заказов. �?спользовать вложенный коррелированный SELECT.

SELECT * FROM employees e WHERE (select count(orderId) FROM orders o WHERE e.employeeId = o.employeeId) > 150;

--Высветить всех заказчиков (таблица Customers), которые не имеют ни одного заказа (подзапрос по таблице Orders). 
--�?спользовать коррелированный SELECT и оператор EXISTS.

SELECT * FROM customers c WHERE NOT EXISTS(select customerId FROM orders o WHERE c.customerId = o.customerId);

--Для формирования алфавитного указателя Employees высветить из таблицы Employees список только тех букв алфавита, с которых
--начинаются фамилии Employees (колонка LastName ) из этой таблицы. Алфавитный список должен быть отсортирован по возрастанию.

SELECT SUBSTR(lastname, 1, 1) AS "My Column" FROM employees;
