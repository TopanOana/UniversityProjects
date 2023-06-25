--dirty reads: when a transaction reads data
-- that hasn't been commited by another transaction
--transaction 2:
--solution: 
--set transaction isolation level read committed

set transaction isolation level read uncommitted
BEGIN TRANSACTION
	SELECT * FROM Boards
	waitfor delay '00:00:15'
	select * from Boards
commit tran