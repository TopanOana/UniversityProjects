#include "mpiCommunicationHelpers.h"
#include <string.h>
#include <iostream>
#include <mpi.h>
#include "globalVars.h"

void sendUpdateMessage(const char* variableName, int newVal, int id)
{
	int variableID;
	std::cout << "Process " << processID << " I AM HERE IN SENDUPDATEMESSAGE" << std::endl;
	if (strcmp(variableName, "varA") == 0)
		variableID = 0;
	if (strcmp(variableName, "varB") == 0)
		variableID = 1;
	if (strcmp(variableName, "varC") == 0)
		variableID = 2;
	int array[3] = { 0, variableID, newVal };
	MPI_Request request;
	std::cout << "Process " << processID << " sending update message " << array[0] << " " << array[1] << " " << array[2] << " to " << id << std::endl;
	MPI_Isend(array, 3, MPI_INT, id, 0, MPI_COMM_WORLD, &request);
}

void sendSubscribeMessage(const char* variableName, int newID, int id)
{
	int variableID;
	if (strcmp(variableName, "varA") == 0)
		variableID = 0;
	if (strcmp(variableName, "varB") == 0)
		variableID = 1;
	if (strcmp(variableName, "varC") == 0)
		variableID = 2;
	int array[3] = { 1, variableID, newID };
	MPI_Request request;
	std::cout << "Process " << processID << " sending subscriber message " << array[0] << " " << array[1] << " " << array[2] << " to " << id << std::endl;
	MPI_Isend(array, 3, MPI_INT, id, 0, MPI_COMM_WORLD, &request);

}

void sendCloseMessage(int id)
{
	int array[3] = { 2, 0, 0 };
	std::cout << "Process " << processID << " sending close message " << array[0] << " " << array[1] << " " << array[2] << " to " << id << std::endl;
	MPI_Request request;
	MPI_Isend(array, 3, MPI_INT, id, 0, MPI_COMM_WORLD, &request);
}

int* getMessage()
{
	int* array = new int[3];
	std::cout << "Process " << processID << " I AM HERE IN getmessage" << std::endl;
	MPI_Status status;
	MPI_Recv(array, 3, MPI_INT, MPI_ANY_SOURCE, 0, MPI_COMM_WORLD, &status);
	std::cout << "Process " << processID << " receiving message " << array[0] << " " << array[1] << " " << array[2] << std::endl;
	return array;
}


