/*use SkateShop;*/

/*a*/
CREATE PROCEDURE setDateFromPurchasesToDateTime AS
	ALTER TABLE Purchases ALTER COLUMN date_of_purchase datetime
GO

CREATE PROCEDURE setDateFromPurchasesToDate AS
	ALTER TABLE Purchases ALTER COLUMN date_of_purchase date
GO

/*EXEC setDateFromPurchasesToDate*/

/*b*/


CREATE PROCEDURE addCountryToBrand AS
	ALTER TABLE Brands ADD country varchar(20)
GO

CREATE PROCEDURE removeCountryFromBrand AS
	ALTER TABLE Brands DROP COLUMN country 
GO

/*EXEC removeCountryFromBrand*/

/*c*/

CREATE PROCEDURE addDefaultConstraintCountryFromBrand AS
	ALTER TABLE Brands ADD CONSTRAINT DEFAULT0 DEFAULT('N/A') FOR country
GO

CREATE PROCEDURE removeDefaultConstraintCountryFromBrand AS
	ALTER TABLE Brands DROP CONSTRAINT DEFAULT0 
GO

/*EXEC removeDefaultConstraintCountryFromBrand*/

/*d*/

CREATE PROCEDURE removePKHasBeenPurchased AS
	ALTER TABLE Has_Been_Purchased DROP CONSTRAINT PK__Has_Been_Purchased
GO

CREATE PROCEDURE addPKHasBeenPurchased AS
	ALTER TABLE Has_Been_Purchased ADD CONSTRAINT PK__Has_Been_Purchased PRIMARY KEY (id)
GO

/*EXEC addPKHasBeenPurchased*/

/*e*/

CREATE PROCEDURE addCandidateKeyHasBeenPurchased AS
	ALTER TABLE Has_Been_Purchased ADD CONSTRAINT Candidate_Key UNIQUE (id, product_id, purchase_id)
GO

CREATE PROCEDURE removeCandidateKeyHasBeenPurchased AS
	ALTER TABLE Has_Been_Purchased DROP CONSTRAINT Candidate_Key 
GO

/*EXEC removeCandidateKeyHasBeenPurchased*/

/*f*/

CREATE PROCEDURE addForeignKeyStocked AS
	ALTER TABLE Stocked ADD CONSTRAINT FK_Stocked_Product_ID FOREIGN KEY(product_id) REFERENCES Products(id)
GO

CREATE PROCEDURE removeForeignKeyStocked AS
	ALTER TABLE Stocked DROP CONSTRAINT FK_Stocked_Product_ID 
GO

/*EXEC removeForeignKeyStocked*/

/*g*/

CREATE OR ALTER PROCEDURE createReviewTable AS
	CREATE TABLE Reviews(
		client_id int NOT NULL,
		product_id int NOT NULL,
		review_text varchar(MAX),
		rating int NOT NULL,
		CONSTRAINT RATING_CONSTRAINT CHECK (rating<=10 AND rating>=0),
		CONSTRAINT CANDIDATE_KEY0 UNIQUE(client_id,product_id),
		CONSTRAINT FK_CLIENT_ID FOREIGN KEY(client_id) REFERENCES Clients(id),
		CONSTRAINT FK_PRODUCT_ID FOREIGN KEY(product_id) REFERENCES Products(id)
	)
GO


CREATE PROCEDURE dropReviewTable AS
	DROP TABLE Reviews
GO

/*EXEC dropReviewTable*/


CREATE TABLE VersionTable(
	version int
)

INSERT INTO VersionTable values(1)



CREATE TABLE ProceduresTable(
	from_version int,
	to_version int,
	PRIMARY KEY(from_version, to_version),
	name_procedure varchar(MAX)
)


INSERT INTO ProceduresTable VALUES

(1,2,'setDateFromPurchasesToDateTime'),
(2,1,'setDateFromPurchasesToDate'),
(2,3,'addCountryToBrand'),
(3,2,'removeCountryFromBrand'),
(3,4,'addDefaultConstraintCountryFromBrand'),
(4,3,'removeDefaultConstraintCountryFromBrand'),
(5,4,'removePKHasBeenPurchased'),
(4,5,'addPKHasBeenPurchased'),
(5,6,'addCandidateKeyHasBeenPurchased'),
(6,5,'removeCandidateKeyHasBeenPurchased'),
(6,7,'addForeignKeyStocked'),
(7,6,'removeForeignKeyStocked'),
(7,8,'createReviewTable'),
(8,7,'dropReviewTable')


CREATE OR ALTER PROCEDURE go_to_version(@new_version int) AS
	DECLARE @current int
	DECLARE @variable varchar(max)
	SELECT @current=version from VersionTable

	IF @new_version > (SELECT MAX(to_version) from ProceduresTable)
		RAISERROR('Bad version',10,1)

	WHILE @current > @new_version BEGIN
		SELECT @variable=name_procedure FROM ProceduresTable WHERE from_version=@current AND to_version=@current-1
		EXEC (@variable)
		SET @current=@current-1
	END

	WHILE @current < @new_version BEGIN
		SELECT @variable=name_procedure FROM ProceduresTable WHERE from_version=@current AND to_version=@current+1
		EXEC (@variable)
		SET @current=@current+1
	END

	UPDATE VersionTable SET version=@new_version
GO

SELECT * FROM VersionTable

SELECT * FROM ProceduresTable

EXEC go_to_version 1