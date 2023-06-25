use SkateShop;

GO
CREATE OR ALTER PROCEDURE addEmployeeRowPartial @firstname VARCHAR(30), @lastname VARCHAR(30), @datehired DATETIME AS
BEGIN
	SET NOCOUNT ON
	BEGIN TRAN
	BEGIN TRY
		declare @idToAdd int
		set @idToAdd = 1
		select top 1 @idToAdd = id+1 from Employees order by id desc
		declare @error VARCHAR(100)
		set @error=''
		if (@firstname is null or @firstname='')
		begin
			set @error = 'Employee first name cannot be null'
			raiserror('Employee first name cannot be null', 16,1)
		end
		if (@lastname is null or @lastname='')
		begin
			set @error = 'Employee last name cannot be null'
			raiserror('Employee last name cannot be null', 16,1)
		end
		if (@datehired is null)
		begin
			set @error = 'Employee date hired cannot be null'
			raiserror('Employee date hired cannot be null', 16,1)
		end
	
		INSERT INTO Employees(id, first_name,last_name,date_hired)
		VALUES(@idToAdd, @firstname, @lastname, @datehired)
	
		exec specifyLogChanges '', @firstname, 'Add row to Employees', @error
		COMMIT TRAN
	END TRY
	BEGIN CATCH 
		ROLLBACK TRAN
		exec specifyLogChanges '', '', 'roll back employee data', @error
	END CATCH
END

GO
CREATE OR ALTER PROCEDURE addClientRowPartial @firstname VARCHAR(30), @lastname VARCHAR(30), @email VARCHAR(50) AS
BEGIN
	SET NOCOUNT ON
	begin tran
	begin try
		declare @idToAdd int
		set @idToAdd = 1
		select top 1 @idToAdd = id+1 from Clients order by id desc
		declare @error VARCHAR(100)
		set @error=''
		if (@firstname is null or @firstname='')
		begin
			set @error = 'Client first name cannot be null'
			raiserror('Client first name cannot be null', 16,1)
		end
		if (@lastname is null or @lastname='')
		begin
			set @error = 'Client last name cannot be null'
			raiserror('Client last name cannot be null', 16,1)
		end
		if (@email is null or @email='')
		begin
			set @error = 'Client email cannot be null'
			raiserror('Client email cannot be null', 16,1)
		end
	
		INSERT INTO Clients(id, first_name,last_name,email)
		VALUES(@idToAdd, @firstname, @lastname, @email)
	
		exec specifyLogChanges '', @firstname, 'Add row to Clients', @error
		commit tran
	end try
	begin catch
		rollback tran
		exec specifyLogChanges '', '', 'roll back client data', @error
	end catch
	
END

GO
CREATE OR ALTER PROCEDURE addPurchaseRowPartial @firstnameEmployee VARCHAR(30), @firstnameClient VARCHAR(30), @datep VARCHAR(50) AS
BEGIN
	SET NOCOUNT ON
	begin tran
	begin try
		declare @idToAdd int
		set @idToAdd = 1
		select top 1 @idToAdd = id+1 from Purchases order by id desc
		declare @error VARCHAR(100)
		set @error=''
		if (@firstnameEmployee is null)
		begin
			set @error = 'Employee first name cannot be null'
			raiserror('Employee first name cannot be null', 16,1)
		end
		declare @employeeID int
		set @employeeID = (select id from Employees where first_name=@firstnameEmployee)
		if(@employeeID is null)
		begin
			set @error = 'No employee with the given first name'
			raiserror(@error, 16,1)
		end

		if (@firstnameClient is null)
		begin
			set @error = 'Client first name cannot be null'
			raiserror('Client first name cannot be null', 16,1)
		end
		declare @clientID int
		set @clientID = (select id from Clients where first_name=@firstnameClient)
		if(@clientID is null)
		begin
			set @error = 'No client with the given first name'
			raiserror(@error, 16,1)
		end

		if (@datep is null)
		begin
			set @error = 'Date of purchase cannot be null'
			raiserror('Date of purchase cannot be null', 16,1)
		end
	
		INSERT INTO Purchases(id, date_of_purchase, client_id, employee_id)
		VALUES(@idToAdd, @datep, @clientID, @employeeID)
	
		exec specifyLogChanges '', @datep, 'Add row to Purchases', @error
		commit tran
	end try
	begin catch 
		rollback tran
		exec specifyLogChanges '', '', 'rollback purchase data', @error
	end catch
END

GO 
CREATE OR ALTER PROCEDURE goodPartialRollback AS
BEGIN
	exec addClientRowPartial 'testagainfn', 'testagainln', 'email@gmail.com'
	exec addEmployeeRowPartial 'testemagainfn', 'testemagainln', '2023-02-10'
	exec addPurchaseRowPartial 'testemagainfn', 'testagainfn', '2023-05-08'
END

GO 
CREATE OR ALTER PROCEDURE badPartialRollback AS
BEGIN
	exec addClientRowPartial '', 'testagainln', 'email@gmail.com'
	exec addEmployeeRowPartial 'testemagainfn', 'testemagainln', '2023-02-10'
	exec addPurchaseRowPartial 'testemagainfn', 'test', '2023-05-08'
END

delete from LogChanges

exec goodPartialRollback
select * from LogChanges
exec badPartialRollback
select * from LogChanges

