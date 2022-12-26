use SkateShop;

CREATE TABLE Clients(
	id int NOT NULL,
	first_name varchar(30) NOT NULL,
	last_name varchar(30) NOT NULL,
	email varchar(50) NOT NULL,
	PRIMARY KEY(id)
);


CREATE TABLE Parts(
	id int NOT NULL,
	name varchar(30) NOT NULL,
	type varchar(30) NOT NULL,
	PRIMARY KEY(id)
);

CREATE TABLE Boards(
	id int NOT NULL,
	deck_length int NOT NULL,
	deck_width int NOT NULL,
	material varchar(30) NOT NULL,
	wheel_diameter int NOT NULL,
	model_name varchar(30) NOT NULL,
	type_of_board varchar(30) NOT NULL,
	PRIMARY KEY(id)
);

CREATE TABLE Accessories(
	id int NOT NULL,
	item_name varchar(30),
	PRIMARY KEY(id)
);

CREATE TABLE Brands(
	id int NOT NULL,
	brand_name varchar(30) NOT NULL,
	headquarters varchar(30) NOT NULL,
	PRIMARY KEY(id)
);

CREATE TABLE Products(
	id int NOT NULL,
	board_id int DEFAULT '0',
	part_id int DEFAULT '0',
	accessory_id int DEFAULT '0',
	brand_id int NOT NULL, 
	price float NOT NULL,
	PRIMARY KEY(id),
	FOREIGN KEY(board_id)
		REFERENCES Boards(id)
		ON DELETE CASCADE,
	FOREIGN KEY(part_id)
		REFERENCES Parts(id)
		ON DELETE CASCADE,
	FOREIGN KEY(accessory_id)
		REFERENCES Accessories(id)
		ON DELETE CASCADE,
	FOREIGN KEY(brand_id)
		REFERENCES Brands(id)
		ON DELETE CASCADE,
	CHECK ((board_id='0' AND part_id='0' AND accessory_id!='0') OR (board_id='0' AND part_id!='0' AND accessory_id='0') OR (board_id!='0' AND part_id='0' AND accessory_id='0'))
);

CREATE TABLE Employees(
	id int NOT NULL,
	first_name varchar(30) NOT NULL,
	last_name varchar(30) NOT NULL,
	date_hired datetime NOT NULL,
	PRIMARY KEY(id)
);

CREATE TABLE Purchases(
	id int NOT NULL,
	date_of_purchase date NOT NULL,
	client_id int NOT NULL,
	employee_id int NOT NULL,
	PRIMARY KEY(id),
	FOREIGN KEY(client_id)
		REFERENCES Clients(id)
		ON DELETE CASCADE,
	FOREIGN KEY(employee_id)
		REFERENCES Employees(id)
		ON DELETE CASCADE
);



CREATE TABLE Stocked(
	id int NOT NULL,
	product_id int NOT NULL,
	quantity int NOT NULL,
	PRIMARY KEY(id),
	FOREIGN KEY(product_id)
		REFERENCES Products(id)
		ON DELETE CASCADE
);

CREATE TABLE Has_Been_Purchased(
	id int NOT NULL,
	product_id int NOT NULL,
	purchase_id int NOT NULL,
	quantity int NOT NULL,
	PRIMARY KEY(id),
	FOREIGN KEY(product_id)
		REFERENCES Products(id)
		ON DELETE CASCADE,
	FOREIGN KEY(purchase_id)
		REFERENCES Purchases(id)
		ON DELETE CASCADE
);