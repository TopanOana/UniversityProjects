use SkateShop;

SELECT model_name FROM Boards
UNION ALL 
SELECT name FROM Parts
UNION ALL
SELECT item_name FROM Accessories;

SELECT brand_name, headquarters FROM Brands
WHERE headquarters LIKE '%USA' OR brand_name LIKE 'S%';

SELECT c.id FROM Clients c
INTERSECT 
SELECT p.client_id FROM Purchases p;

SELECT * FROM Clients c1
WHERE c1.id IN (SELECT p1.client_id FROM Purchases p1 );

SELECT c.id FROM Clients c
EXCEPT
SELECT p.client_id FROM Purchases p;

SELECT * FROM Boards b
WHERE b.id NOT IN 
	(SELECT bp.id FROM Boards bp
		WHERE bp.material LIKE '%7-Ply' OR bp.material LIKE '%5-Ply') ;

SELECT c.first_name, c.last_name FROM Clients c
INNER JOIN Purchases p
ON c.id=p.client_id;

SELECT p.id, p.board_id, b.model_name, p.part_id, pa.name, a.id, a.item_name FROM Products p
FULL OUTER JOIN Boards b ON
p.board_id = b.id
FULL OUTER JOIN Accessories a
ON p.accessory_id=a.id
FULL OUTER JOIN Parts pa
ON pa.id = p.part_id;

SELECT * FROM Has_Been_Purchased hbp
LEFT JOIN Products pr 
ON hbp.product_id=pr.id
LEFT JOIN Purchases pu
ON hbp.purchase_id=pu.id
WHERE hbp.quantity>=2;

SELECT p.accessory_id, p.part_id, p.price, s.quantity FROM Products p
RIGHT JOIN Stocked s 
ON s.product_id=p.id
WHERE p.board_id IS NULL;

SELECT * FROM Brands b
WHERE b.id IN (SELECT p.brand_id FROM Products p);


SELECT pr.PID, b.model_name AS BoardName, pa.name AS PartName, a.item_name AS AccessoryName FROM (
	SELECT DISTINCT p.id AS PID, p.price AS PRICE, p.board_id, p.accessory_id, p.part_id FROM Products p
	WHERE p.id IN (SELECT hbp.product_id FROM  Has_Been_Purchased hbp)) AS pr
LEFT JOIN Boards b
ON b.id = pr.board_id
LEFT JOIN Parts pa
ON pa.id = pr.part_id
LEFT JOIN Accessories a
ON a.id = pr.accessory_id;

SELECT b.brand_name FROM Brands b
WHERE EXISTS (SELECT p.id FROM Products p WHERE p.price>50 AND b.id=p.brand_id);

SELECT DISTINCT b.brand_name FROM Brands b
WHERE EXISTS (SELECT p.id FROM Products p 
				INNER JOIN Has_Been_Purchased hbp
				ON hbp.product_id=p.id
				WHERE hbp.quantity>1 AND b.id=p.brand_id);

SELECT * FROM Has_Been_Purchased;


SELECT DISTINCT e.first_name, e.last_name FROM  (
	SELECT p.employee_id FROM (
		SELECT t.purchase_id FROM(
			SELECT purchase_id , COUNT(purchase_id) AS Occurences FROM Has_Been_Purchased 
			GROUP BY purchase_id) AS t
		WHERE t.Occurences>=2) AS t1
	INNER JOIN Purchases p
	ON p.id=t1.purchase_id) AS t2
INNER JOIN Employees e
ON e.id=t2.employee_id;



SELECT p.date_of_purchase FROM  Purchases p 
INNER JOIN (
	SELECT COUNT(*) AS totalPurchasesWithQuantity2, hbp.purchase_id FROM Has_Been_Purchased hbp
	WHERE hbp.quantity>1
	GROUP BY hbp.purchase_id
	HAVING COUNT(*)>1) AS t
ON t.purchase_id = p.id;



SELECT SUM(hbp.quantity*p.price) AS TOTAL FROM Has_Been_Purchased hbp
INNER JOIN Products p
ON p.id=hbp.product_id
GROUP BY hbp.purchase_id
HAVING SUM(hbp.quantity*p.price)>100
ORDER BY TOTAL DESC;

SELECT b.brand_name, T.MinimumPrice FROM (
	SELECT MIN(p.price) AS MinimumPrice, p.brand_id FROM Products p
	GROUP BY p.brand_id
	HAVING MIN(p.price)>50) AS t
INNER JOIN Brands b
ON t.brand_id=b.id;


SELECT MAX(s.quantity) AS MaximumQuantity, s.product_id FROM Stocked s
GROUP BY s.product_id
HAVING MAX(s.quantity) = (SELECT MAX(s1.quantity) FROM Stocked s1);

INSERT INTO Products(id, part_id,brand_id,price)
VALUES(5,3,2,25);

INSERT INTO Stocked(id,product_id,quantity)
VALUES(5,5,20);

SELECT TOP 3 AVG(p.price) AS AveragePrice FROM Products p 
GROUP BY p.board_id, p.part_id, p.accessory_id
HAVING AVG(p.price) > (SELECT AVG(p1.price) FROM Products p1)
ORDER BY AveragePrice ASC;
	
SELECT TOP 4 p.price, p.id FROM Products p
WHERE p.id = ANY (SELECT hbp.product_id FROM Has_Been_Purchased hbp
	WHERE hbp.quantity>=2)
ORDER BY p.price ASC;

SELECT p.id, p.price FROM Products p
WHERE p.id IN (SELECT hbp.product_id FROM Has_Been_Purchased hbp
	WHERE hbp.quantity>=2)
ORDER BY p.price ASC;

SELECT p.id, p.price FROM Products p
WHERE p.id = ANY (SELECT s.product_id FROM Stocked s
	WHERE s.quantity>5)
ORDER BY p.price DESC;

SELECT p.id, p.price FROM Products p
WHERE p.id IN (SELECT s.product_id FROM Stocked s
	WHERE s.quantity>5)
ORDER BY p.price DESC;

SELECT p.id, p.price FROM Products p
INNER JOIN Stocked s1
on s1.product_id=p.id
WHERE s1.quantity > (SELECT MIN(s.quantity) AS MinQuantity FROM Stocked s
	WHERE s.quantity>5)
ORDER BY p.price DESC;

SELECT * FROM Products p
WHERE p.price <> ALL (SELECT pr.price FROM Products pr WHERE pr.price>50);

SELECT * FROM Products p
WHERE p.price NOT IN (SELECT pr.price FROM Products pr WHERE pr.price>50);

SELECT * FROM Has_Been_Purchased hbp
WHERE hbp.quantity > ALL(SELECT hbp1.quantity FROM Has_Been_Purchased hbp1 WHERE hbp1.quantity=1);

SELECT * FROM Has_Been_Purchased hbp
WHERE hbp.quantity NOT IN (SELECT hbp1.quantity FROM Has_Been_Purchased hbp1 WHERE hbp1.quantity=1);

SELECT * FROM Has_Been_Purchased hbp
WHERE hbp.quantity > (SELECT MAX(hbp1.quantity) FROM Has_Been_Purchased hbp1 WHERE hbp1.quantity=1);

