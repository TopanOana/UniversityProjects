use SkateShop;

CREATE TABLE LogLocks(
	currentTime DATETIME,
	info VARCHAR(100),
	resourceType VARCHAR(100),
	requestMode VARCHAR(100),
	requestType VARCHAR(100),
	requestStatus VARCHAR(100),
	requestSessionID INT
);


CREATE TABLE LogChanges(
	currentTime DATETIME,
	info VARCHAR(100),
	oldData VARCHAR(100),
	newData VARCHAR(100),
	error VARCHAR(100)
);


GO
CREATE OR ALTER PROCEDURE specifyLogLocks @info VARCHAR(100) AS
BEGIN
	INSERT INTO LogLocks(currentTime, info, resourceType, requestMode, requestType, requestStatus, requestSessionID)
	SELECT GETDATE(), @info, d.resource_type, d.request_mode, d.request_type, d.request_status, d.request_session_id 
	FROM sys.dm_tran_locks as d WHERE request_owner_type='TRANSACTION'
END



GO 
CREATE OR ALTER PROCEDURE specifyLogChanges @oldData VARCHAR(100), @newData VARCHAR(100), @info VARCHAR(100), @error VARCHAR(100) AS
BEGIN
	INSERT INTO LogChanges(currentTime, info, oldData, newData, error)
	VALUES (GETDATE(), @info, @oldData, @newData, @error)
	
END
