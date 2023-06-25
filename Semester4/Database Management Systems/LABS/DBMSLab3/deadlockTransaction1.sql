--deadlock:
--when transaction A has a lock on the row r1
-- and then transaction B has a lock on the row r2
-- and then transaction A wants to acquire the lock
-- for the row r2 and it has to wait for transaction B
-- who is also waiting for the lock on row r1 from 
-- transaction A
--select * from Brands

insert into Brands(id,brand_name, headquarters)
Values (9,'brand','hq!')
select * from Boards
insert into Boards
values(9, 90, 25, 'Maple', 75, 'deadlock', 'Skateboard')

begin tran
	update Brands set headquarters='cluj?'
	where id=9
	waitfor delay '00:00:05'
	update Boards set model_name='rip me'
	where id=9
commit tran



---sol
retry:
	begin tran
	begin try
		update Brands set headquarters='cluj?'
		where id=9
		waitfor delay '00:00:05'
		update Boards set model_name='rip me'
		where id=9
		commit tran
	end try
	begin catch 
		rollback tran
		if ERROR_NUMBER()=1205
		begin 
			waitfor delay '00:00:05'
			goto retry
		end
	end catch
