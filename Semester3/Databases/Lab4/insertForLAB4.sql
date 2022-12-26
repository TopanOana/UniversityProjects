use SkateShop;

--tests config
insert into Tables(Name) values
('Accessories'),
('Boards'),
('Brands'),
('Clients'),
('Employees'),
('Parts'),
('Products'),
('Stocked'),
('Purchases'),
('Has_Been_Purchased')




insert into Tests( Name) values 
('test1'),
('test2'),
('test3');

/*
create view [View_1] as
	select * from Boards



create view [View_2] as
	select B.model_name, B.type_of_board, P.price from Products P
	inner join Boards B on P.board_id=B.id


create view [View_3] as
	select P.brand_id, COUNT(*) as NrProductsFromBrand from Products P
	inner join Brands B on P.brand_id=B.id
	group by P.brand_id

*/


insert into Views(Name) values
('View_1'),
('View_2'),
('View_3')

SELECT * FROM TestTables

--testView
insert into TestViews(TestID, ViewId) values
(1,1),
(2,2),
(3,3)

--testTables
insert into TestTables(TestID, TableID, NoOfRows,Position) values
(1, 13, 500,1),
(2, 13, 500, 5),
(2, 12, 500, 4),
(2, 17, 500, 3),
(2, 14, 500, 2),
(2, 18, 600, 1),
(3, 13, 500, 5),
(3, 12, 500, 4),
(3, 17, 500, 3),
(3, 14, 500, 2),
(3, 18, 600, 1)



