USE SkateShop
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO


CREATE or alter PROCEDURE delete_rows
	@nr_of_rows varchar(30),
	@table_name varchar(30)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	if ISNUMERIC(@nr_of_rows) != 1
	BEGIN
		print('Not a number-delete'+@nr_of_rows)
		return 1 
	END
	
	SET @nr_of_rows = cast(@nr_of_rows as INT)

	declare @last_row int
	if @table_name = 'Has_Been_Purchased'
	begin
		delete from Has_Been_Purchased
	end
	
	if @table_name = 'Purchases'
	begin 
		set @last_row = (select max(id) from Purchases) - @nr_of_rows
		delete from Purchases where id > @last_row
	end

	if @table_name = 'Stocked' 
	begin
		set @last_row = (select max(id) from Stocked) - @nr_of_rows
		delete from Stocked where id > @last_row
	end

	if @table_name = 'Products'
	begin 
		set @last_row = (select max(id) from Products) - @nr_of_rows
		delete from Products where id > @last_row
	end

	if @table_name = 'Parts'
	begin 
		set @last_row = (select max(id) from Parts) - @nr_of_rows
		delete from Parts where id > @last_row
	end
	
	if @table_name = 'Employees'
	begin 
		set @last_row = (select max(id) from Employees) - @nr_of_rows
		delete from Employees where id > @last_row
	end

	if @table_name = 'Clients'
	begin 
		set @last_row = (select max(id) from Clients) - @nr_of_rows
		delete from Clients where id > @last_row
	end

	if @table_name = 'Brands'
	begin 
		set @last_row = (select max(id) from Brands) - @nr_of_rows
		delete from Brands where id > @last_row
	end
	
	if @table_name = 'Boards'
	begin 
		set @last_row = (select max(id) from Boards) - @nr_of_rows
		delete from Boards where id > @last_row
	end

	if @table_name = 'Accessories'
	begin 
		set @last_row = (select max(id) from Accessories) - @nr_of_rows
		delete from Accessories where id > @last_row
	end

END