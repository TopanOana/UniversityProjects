--update conflict
use SkateShop;

insert into Boards
values(10,80,20,'Maple', 68,'conflicts','Longboard')


waitfor delay '00:00:05'
begin tran 
update Boards set model_name='conflicts' where id=10
waitfor delay '00:00:05'
commit tran

select * from Boards