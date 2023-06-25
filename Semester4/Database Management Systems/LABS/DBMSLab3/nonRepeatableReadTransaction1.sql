--non-repeatable reads:
--when a transaction reads the same row twice and 
-- gets different results

--select * from Boards
--insert into boards
--values(6, 100, 25, 'Maple,6-Ply', 75, 'nonrepeatable', 'Longboard');

begin tran 
	waitfor delay '00:00:05'
	update Boards set model_name='aaa'
	where id=6
commit tran


