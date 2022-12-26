USE SkateShop
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE or alter PROCEDURE [dbo].[insert_rows]
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
		print('Not a number -insert')
		return 1 
	END

	SET @nr_of_rows = cast(@nr_of_rows as INT)
	
	declare @counter int
	set @counter = 1

	--accessories
	declare @item_name varchar(10)
	--boards
	declare @deck_length int
	declare @deck_width int
	declare @material varchar(30)
	declare @wheel_diameter int
	declare @model_name varchar(30)
	declare @type_of_board varchar(30)
	--brands
	declare @brand_name varchar(30)
	declare @headquarters varchar(30)
	--clients
	declare @first_name_client varchar(30)
	declare @last_name_client varchar(30)
	declare @email_client varchar(50)
	--employees
	declare @first_name_employee varchar(30)
	declare @last_name_employee varchar(30)
	declare @date_hired_employee datetime
	--has_been_purchased
	declare @quantity_hbp int
	declare @product_id int
	declare @purchase_id int
	--parts
	declare @name_part varchar(30)
	declare @type_part varchar(30)
	--products
	declare @price float
	declare @brand_id int
	--purchases
	declare @date_purchase datetime
	declare @client_id int
	declare @employee_id int
	--stocked
	declare @quantity_stocked int


	while @counter <= @nr_of_rows
	begin
		if @table_name = 'Accessories' 
		begin 
			set @item_name = 'Accessory ' + convert(varchar(4), (@counter))
			insert into Accessories(id,item_name) values (@counter, @item_name)
		end
		
		if @table_name = 'Boards'
		begin
			set @deck_length = rand()*(200-50)+50;
			set @deck_width = rand()*(30-15)+15;
			set @material = 'Material'+convert(varchar(4), (@counter))
			set @wheel_diameter = rand()*(15-5)+5
			set @model_name = 'Model name '+convert(varchar(4), (@counter))
			set @type_of_board = 'Type' +convert(varchar(4), (@counter))
			insert into Boards(id, deck_length,deck_width,material,wheel_diameter,model_name,type_of_board) values
				(@counter, @deck_length,@deck_width,@material,@wheel_diameter,@model_name,@type_of_board)
		end

		if @table_name = 'Brands'
		begin
			set @brand_name = 'Brand name' + convert(varchar(4), (@counter))
			set @headquarters = 'Headquarters'+convert(varchar(4), (@counter))
			insert into Brands(id, brand_name, headquarters) values (@counter, @brand_name,@headquarters)
		end

		if @table_name = 'Clients' 
		begin
			set @first_name_client = 'FNClient'+convert(varchar(4), (@counter))
			set @last_name_client = 'LNClient'+convert(varchar(4), (@counter))
			set @email_client = 'Email'+convert(varchar(4), (@counter))
			insert into Clients(id, first_name,last_name,email) values (@counter,@first_name_client,@last_name_client,@email_client)
		end

		if @table_name = 'Employees'
		begin
			set @first_name_employee = 'FNEmployee'+convert(varchar(4), (@counter))
			set @last_name_employee = 'LNEmployee'+convert(varchar(4), (@counter))
			set @date_hired_employee = getdate()
			insert into Employees(id, first_name,last_name,date_hired) values (@counter, @first_name_employee, @last_name_employee, @date_hired_employee)
		end

		if @table_name = 'Parts'
		begin
			set @name_part = 'NamePart'+convert(varchar(4), (@counter))
			set @type_part = 'TypePart'+convert(varchar(4), (@counter))
			insert into Parts(id, name, type) values (@counter, @name_part,@type_part)
		end

		if @table_name = 'Products'
		begin
			set @price = rand()*(300-15)+15
			set @brand_id = (select max(id) from Brands)
			set @brand_id = rand()*(@brand_id-1)+1
			if @counter % 3 =0 
			begin
				declare @board_id int 
				set @board_id = (select max(id) from Boards)
				set @board_id = rand()*(@board_id-1)+1
				insert into Products(id,board_id,part_id,accessory_id,brand_id,price) values (@counter, @board_id, NULL, NULL,@brand_id,@price)
			end
			if @counter % 3 =1
			begin
				declare @part_id int 
				set @part_id = (select max(id) from Parts)
				set @part_id = rand()*(@part_id-1)+1
				insert into Products(id,board_id,part_id,accessory_id,brand_id,price) values (@counter, NULL, @part_id,NULL,@brand_id,@price)
			end
			if @counter %3 =2
			begin
				declare @accessory_id int 
				set @accessory_id = (select max(id) from Accessories)
				set @accessory_id = rand()*(@accessory_id-1)+1
				insert into Products(id,board_id,part_id,accessory_id,brand_id,price) values (@counter,NULL, NULL,@accessory_id,@brand_id,@price)
			end
		end

		if @table_name = 'Stocked'
		begin
			set @quantity_stocked = @counter*100
			set @product_id = (select max(id) from Products)
			set @product_id = rand()*(@product_id-1)+1
			insert into Stocked(id,product_id,quantity) values (@counter,@product_id, @quantity_stocked)
		end

		if @table_name = 'Purchases'
		begin
			set @client_id = (select max(id) from Clients)
			set @client_id = rand()*(@client_id-1)+1
			set @employee_id = (select max(id) from Employees)
			set @employee_id = rand()*(@employee_id-1)+1
			set @date_purchase = getdate()
			insert into Purchases(id, date_of_purchase,client_id,employee_id) values (@counter, @date_purchase, @client_id, @employee_id)
		end

		if @table_name = 'Has_Been_Purchased'
		begin
			set @product_id = (select max(id) from Products)
			set @product_id = rand()*(@product_id-1)+1
			set @purchase_id = (select max(id) from Purchases)
			set @purchase_id = rand()*(@purchase_id-1)+1
			set @quantity_hbp = rand()*(100-5)+5
			insert into Has_Been_Purchased(product_id, purchase_id, quantity) values (@product_id, @purchase_id, @quantity_hbp)
		end

		set @counter = @counter+1
	end

END
