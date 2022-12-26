use LAB5;

--Ta(aid,a2,...)
create table Student(
	StudentID INT PRIMARY KEY,
	numar_matricol int UNIQUE,
	last_name varchar(20),
	first_name varchar(20)
)
--Tb(bid,b2,...)
create table Dorm(
	DormID int primary key,
	number_of_rooms int,
	dorm_address varchar(30)
)
--Tc(cid,aid,bid,...)
create table Repartizare(
	RepartizareID int primary key,
	StudentID int references Student(StudentID),
	DormID int references Dorm(DormID)
)

--inserts
insert into Student(StudentID, numar_matricol,last_name,first_name) values
(1, 3222,'LastName1','FirstName1'),
(2, 3223,'LastName2','FirstName2'),
(3, 3224,'LastName3','FirstName3'),
(4, 3225,'LastName4','FirstName4'),
(5, 3226,'LastName5','FirstName5');

insert into Dorm(DormID, number_of_rooms, dorm_address) values
(1, 20,'Address1'),
(2, 25,'Address2'),
(3, 30,'Address3'),
(4, 15,'Address4');

insert into Repartizare(RepartizareID,StudentID,DormID) values
(1, 1, 1),
(2, 2, 1),
(3, 3, 4),
(4, 4, 2),
(5, 5, 3);



--a. Write queries on Ta such that their execution plans contain the following operators:
---clustered index scan;



--clustered index is automatically made around a primary key
--scan means getting all the data from columns that are not in the search key
select * from Student order by StudentID;

---clustered index seek;
select * from Student where StudentID=1;

---

create nonclustered index indexStudent on Student(last_name);
drop index indexStudent on Student;

---nonclustered index scan;
select last_name from Student order by last_name;

---nonclustered index seek;
select last_name from Student where last_name='LastName4';

---key lookup;
select last_name from Student where numar_matricol='3222'

---b. Write a query on table Tb with a WHERE clause of the form 
-- WHERE b2 = value and analyze its execution plan. 
---Create a nonclustered index that can speed up the query. 
--Examine the execution plan again.

create nonclustered index indexDorm on Dorm(number_of_rooms) include (DormID, dorm_address);
drop index indexDorm on Dorm;

select DormID, dorm_address, number_of_rooms from Dorm where  number_of_rooms=30;



---c. Create a view that joins at least 2 tables. 
--Check whether existing indexes are helpful; 
--if not, reassess existing indexes /
--examine the cardinality of the tables.

create view [ViewStudentDormRepartizare] as
	select R.RepartizareID, R.StudentID, S.numar_matricol, R.DormID, D.number_of_rooms from Repartizare R
	join Student S on S.StudentID=R.StudentID join Dorm D on D.DormID=R.DormID
	where D.number_of_rooms>15 and S.numar_matricol<3226;


select * from ViewStudentDormRepartizare;


