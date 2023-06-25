--deadlock
--set deadlock_priority high
begin tran
	update Boards set model_name='deadlock'
	where id=9
	waitfor delay '00:00:05'
	update Brands set headquarters='hq!'
	where id=9
commit tran

--solution
retry:
	begin tran
	begin try
		update Boards set model_name='deadlock'
		where id=9
		waitfor delay '00:00:05'
		update Brands set headquarters='hq!'
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
