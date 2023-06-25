--phantom read

set transaction isolation level repeatable read
begin tran
	select * from Boards
	waitfor delay '00:00:05'
	select * from Boards
commit tran

