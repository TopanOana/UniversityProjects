USE SkateShop
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE or alter  PROCEDURE [dbo].[select_view]
	@view_name varchar(30)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	if @view_name = 'View_1'
	begin 
		select * from View_1
	end

	if @view_name = 'View_2'
	begin
		select * from View_2
	end

	if @view_name = 'View_3'
	begin
		select * from View_3
	end
END

