use SkateShop;

UPDATE Clients
SET first_name='Andi'
WHERE id=3;

UPDATE Parts
SET name='Grip'
WHERE id=5;

UPDATE Employees
SET first_name='Alexandru'
WHERE id=2 AND last_name LIKE 'I%';

DELETE Brands
WHERE brand_name IN ('Santa Cruz', 'Sector 9');

DELETE Parts
WHERE type IS NOT NULL AND name='Grip';

DELETE Clients
WHERE first_name='Daniel' AND last_name IS NOT NULL;

UPDATE Employees
SET date_hired=DATEFROMPARTS(2022,01,20)
WHERE id=1;

UPDATE Employees
SET date_hired=DATEFROMPARTS(2022,02,10)
WHERE id=2;

