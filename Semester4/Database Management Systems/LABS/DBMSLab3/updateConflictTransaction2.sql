use SkateShop
set transaction isolation level snapshot

begin tran 
waitfor delay '00:00:05'
update Boards set model_name='big conflict' where id=10
commit tran

ALTER DATABASE SkateShop SET ALLOW_SNAPSHOT_ISOLATION on;
