--dirty read
--transaction 1

--select * from Boards
--insert into boards
--values(5, 100, 25, 'Maple,8-Ply', 75, 'TestModel', 'Longboard');


begin transaction 
	update boards set model_name='testModel1' 
	where model_name='TestModel'
	waitfor delay '00:00:10'
rollback transaction