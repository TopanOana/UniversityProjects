--any test


create or alter procedure any_test
	@test_name varchar(30)
as
begin
	declare @testID int
	set @testID = (select TestID from Tests where Name=@test_name)

	declare @start_time_all datetime
	set @start_time_all = getdate()

	declare @max_position int
	set @max_position = (select max(Position) from TestTables where TestID=@testID)

	insert into TestRuns(Description,StartAt) values ('TestRun for ' + @test_name, @start_time_all)
	declare @testrunID int
	set @testrunID = (select max(TestRunID) from TestRuns)

	declare @position_aux int
	declare @table_name varchar(30)
	declare @nr_of_rows varchar(30)

	--deletion
	set @position_aux = 1
	while @position_aux <= @max_position begin
		set @table_name = (select Name from Tables where TableID= (select TableID from TestTables where TestID=@testID and Position=@position_aux))
		set @nr_of_rows = (select NoOfRows from TestTables where TestID=@testID and Position=@position_aux)
		execute delete_rows @nr_of_rows, @table_name 
		set @position_aux = @position_aux+1
	end
	--insertion
	set @position_aux = @max_position
	while @position_aux > 0 begin
		declare @start_time_table datetime
		declare @end_time_table datetime

		set @start_time_table = getdate()
		set @table_name = (select Name from Tables where TableID= (select TableID from TestTables where TestID=@testID and Position=@position_aux))
		set @nr_of_rows = (select NoOfRows from TestTables where TestID=@testID and Position=@position_aux)
		execute insert_rows @nr_of_rows, @table_name 
		set @end_time_table = getdate()
		insert into TestRunTables(TestRunID,TableID,StartAt,EndAt) values (@testrunID, (select TableID from TestTables where TestID=@testID and Position=@position_aux), @start_time_table, @end_time_table)
		set @position_aux = @position_aux-1
	end

	declare @view_id int
	declare @view_name varchar(30)
	declare @start_view datetime
	declare @end_view datetime

	declare view_cursor cursor
	for select ViewID from TestViews where TestID=@testID

	open view_cursor
	fetch next from view_cursor into @view_id
	while @@FETCH_STATUS =0 
	begin
		set @view_name = (select Name from Views where ViewID=@view_id)
		set @start_view = getdate()
		execute select_view @view_name
		set @end_view = getdate()
		insert into TestRunViews(TestRunID, ViewID, StartAt, EndAt) values (@testrunID, @view_id, @start_view, @end_view)
		fetch next from view_cursor into @view_id
	end
	close view_cursor
	deallocate view_cursor
	
	update TestRuns
	set EndAt=@end_view
	where TestRunID=@testrunID
end




select * from TestRuns;

exec any_test 'test3'