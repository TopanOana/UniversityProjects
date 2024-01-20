#include "DistributedSharedMemory.h"
#include "globalVars.h"
#include "mpiCommunicationHelpers.h"
#include <iostream>

Variable& DistributedSharedMemory::getVariable(const char* variableName)
{
	if (strcmp(variableName, "varA") == 0)
		return varA;
	if (strcmp(variableName, "varB") == 0)
		return varB;
	if (strcmp(variableName, "varC") == 0)
		return varC;
	throw;
}

void DistributedSharedMemory::setVariable(const char* variableName, int newVal)
{
	std::cout << "Process " << processID << " set variable " << variableName << " with " << newVal << std::endl;
	Variable& aux = getVariable(variableName);
	aux.setValue(newVal);
}

int DistributedSharedMemory::getVariableValue(const char* variableName)
{
	std::cout << "Process " << processID << " get variable " << variableName << std::endl;
	Variable& aux = getVariable(variableName);
	int value = aux.getValue();
	return value;
}

void DistributedSharedMemory::subscribe(const char* variableName)
{
	std::cout << "Process " << processID << " subscribe to " << variableName << " with " << processID << std::endl;
	updateSubscription(variableName, processID);
	for (int id = 0; id < nrProcesses; id++) {
		if (id != processID)
			sendSubscribeMessage(variableName, processID, id);
	}
}

void DistributedSharedMemory::updateSubscription(const char* variableName, int id)
{
	std::cout << "Process " << processID << " update subscription " << variableName << " id " << id << std::endl;
	Variable& aux = getVariable(variableName);
	aux.addSubscriber(id);
}

void DistributedSharedMemory::updateVariable(const char* variableName, int newVal)
{
	std::cout << "Process " << processID << " update variable " << variableName << " with " << newVal << std::endl;
	setVariable(variableName, newVal);
	Variable& aux = getVariable(variableName);
	for (int id : aux.getSubscribers()) {
		if (id != processID)
			sendUpdateMessage(variableName, newVal, id);
	}
	std::cout << "Process " << processID << " done update var " << variableName << " with " << newVal << std::endl;
		
}

void DistributedSharedMemory::checkAndReplace(const char* variableName, int oldVal, int newVal)
{
	std::cout << "Process " << processID << " check and replace variable " << variableName << " with " << newVal << std::endl;
	Variable& aux = getVariable(variableName);
	if (aux.getValue() == oldVal) {
		setVariable(variableName, newVal);
		for (int id : aux.getSubscribers())
			if (id != processID)
				sendUpdateMessage(variableName, newVal, id);
	}
}

void DistributedSharedMemory::close()
{
	std::cout << "Process " << processID << " close " << std::endl;
	for (int id = 0; id < nrProcesses; id++)
		//if (processID != id)
			sendCloseMessage(id);
}
