#include "globalVars.h"
int processID;
int nrProcesses;

void setCurrentID(int id)
{
	processID = id;
}

void setNrProcesses(int nr)
{
	nrProcesses = nr;
}

