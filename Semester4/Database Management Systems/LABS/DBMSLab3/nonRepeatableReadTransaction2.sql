--nonrepeatable reads
--solution : 
--set transaction isolation level repeatable read
set transaction isolation level read committed
begin tran 
	select * from Boards WHERE id=6
	waitfor delay '00:00:05'
	select * from Boards WHERE id=6
commit tran 