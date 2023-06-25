--phantom reads:
--whena row that matches the search criteria isn't 
--initially found but seen afterwards

delete from Boards where id>6
select * from Boards
begin tran 
	waitfor delay '00:00:05'
	insert into boards
	values(7, 90, 25, 'Maple', 75, 'phantom', 'Skateboard');
commit tran

begin tran 
	waitfor delay '00:00:05'
	insert into boards
	values(8, 95, 25, 'Maple', 75, 'boo!', 'Skateboard');
commit tran