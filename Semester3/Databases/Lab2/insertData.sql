use SkateShop;

INSERT INTO Clients
VALUES(1,'Oana','Topan','oana@gmail.com');

INSERT INTO Clients
VALUES(2,'Dana','Hudema','dana@gmail.com');

INSERT INTO Clients
VALUES(3,'Andrei','Varga','andi@gmail.com');

INSERT INTO Clients
VALUES(4,'Iannis','Taravinas','iannis@gmail.com');

INSERT INTO Clients
VALUES(5,'Daniel','Timoficiuc','dani@yahoo.com');

INSERT INTO Brands
VALUES(1, 'Landyachtz', 'Vancouver,CA');

INSERT INTO Brands
VALUES(2, 'Madrid', 'Huntington Beach,USA');

INSERT INTO Brands
VALUES(3, 'Stella', 'Rancho Cucamonga,USA');

INSERT INTO Brands
VALUES(4, 'Sector 9', 'Carlsbad,USA');

INSERT INTO Brands
VALUES(5, 'SkatePro', 'Aarhus,DK');

INSERT INTO Brands
VALUES(6, 'Santa Cruz', 'Santa Cruz,USA');

INSERT INTO Brands
VALUES(7, 'Enuff', 'London,UK');

INSERT INTO Parts
VALUES(1,'Mag Light','Truck');

INSERT INTO Parts
VALUES(2,'Decade Pro Satin','Truck');

INSERT INTO Parts
VALUES(3,'Lord Nerm','Wheels');

INSERT INTO Parts
VALUES(4,'Corelites','Wheels');

INSERT INTO Parts
VALUES(5,'Grip Tape','Grip Tape');

INSERT INTO Parts
VALUES(6,'Checkered','Grip Tape');

INSERT INTO Parts
VALUES(7,'Reds','Bearings');

INSERT INTO Parts
VALUES(8,'Swiss','Bearings');

INSERT INTO Parts
VALUES(9,'Truck Bolts','Truck Bolts');

INSERT INTO Parts
VALUES(10,'Shock pads 1mm','Riser Pads');

INSERT INTO Accessories
VALUES(1, 'Rib beanie');

INSERT INTO Accessories
VALUES(2, 'Gibsland beanie');

INSERT INTO Accessories
VALUES(3, 'Baseball cap');

INSERT INTO Accessories
VALUES(4, 'Classic Trucker cap');

INSERT INTO Accessories
VALUES(5, 'Street sunglasses');

INSERT INTO Accessories
VALUES(6, 'Spicoli 4 Sunglasses');

INSERT INTO Employees
VALUES(1,'George','Pop',2022-01-20);

INSERT INTO Employees
VALUES(2,'Alex','Ionescu',2022-02-10);

INSERT INTO Employees
VALUES(2,'Alexandru','Ionescu',2022-02-10);

INSERT INTO Boards
VALUES(1,75,19,'Maple,7-Ply',54,'Skully Complete','Skateboard');

INSERT INTO Boards
VALUES(2,80,20,'Maple,7-Ply',52,'Star Wars Complete','Skateboard');

INSERT INTO Boards
VALUES(3,98,25,'Canadian Maple,8-Ply',72,'Drop Cat Complete', 'Longboard');

INSERT INTO Boards
VALUES(4,102,24,'Maple,7-Ply',70,'Drop-Thru Complete Ethereal','Longboard');

INSERT INTO Brands
VALUES(8,'Elements','Irvine,USA');

ALTER TABLE Products
	DROP CONSTRAINT DF__Products__access__3E1D39E1;

ALTER TABLE Products
	DROP CONSTRAINT DF__Products__board___3C34F16F;

ALTER TABLE Products
	DROP CONSTRAINT DF__Products__part_i__3D2915A8;

INSERT INTO Products(id,board_id,brand_id,price)
VALUES(1,3,1,300);

INSERT INTO Products(id,board_id,brand_id,price)
VALUES(2,2,8,97);

INSERT INTO Products(id,part_id,brand_id,price)
VALUES(3,6,7,20);

INSERT INTO Products(id,accessory_id,brand_id,price)
VALUES(4,5,5,10);

INSERT INTO Stocked
VALUES(1, 1, 3);

INSERT INTO Stocked
VALUES(2,3,20);

INSERT INTO Stocked
VALUES(3,2,5);

INSERT INTO Stocked
VALUES(4,4,15);

INSERT INTO Purchases
VALUES(1,'2022-03-16',1,1);

INSERT INTO Purchases
VALUES(2,'2022-04-20',3,2);

INSERT INTO Has_Been_Purchased
VALUES(1,1,1,2);

INSERT INTO Has_Been_Purchased
VALUES(2,2,2,2);

INSERT INTO Has_Been_Purchased
VALUES(3,4,1,1);

INSERT INTO Has_Been_Purchased
VALUES(4,3,2,3);

ALTER TABLE Products
ADD FOREIGN KEY (board_id) REFERENCES Boards(id);

ALTER TABLE Products
ADD FOREIGN KEY (part_id) REFERENCES Parts(id);

ALTER TABLE Products
ADD FOREIGN KEY (accessory_id) REFERENCES Accessories(id);