// Lab8PDP.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include <iostream>
#include <mpi.h>
#include <mutex>
#include <thread>
#include <Windows.h>
#include "DistributedSharedMemory.h"
#include "globalVars.h"
#include "mpiCommunicationHelpers.h"

using namespace std;

mutex currentLock;
DistributedSharedMemory distributedSharedMeme;
int close = 0;

void listener() {
	while (true) {
		cout << "Process " << processID << " waiting" << endl;

		int* message;
		message = getMessage();
		const char* variableName = nullptr;
		if (message[1] == 0)
			variableName = "varA";
		if (message[1] == 1)
			variableName = "varB";
		if (message[1] == 2)
			variableName = "varC";
		cout << "Process " << processID << " message arr " << message[0] << message[1] << message[2] << endl;
		if (message[0] == 0) {
			//updating variable
			cout << "Process " << processID << ": updating variable " << variableName << " with value " << message[2] << endl;
			currentLock.lock();
			distributedSharedMeme.setVariable(variableName, message[2]);
			currentLock.unlock();
		}
		else if (message[0] == 1) {
			//subscribing to variable
			cout << "Process " << processID << ": subscribing to variable " << variableName << " with id " << message[2] << endl;
			currentLock.lock();
			distributedSharedMeme.updateSubscription(variableName, message[2]);
			currentLock.unlock();
		}
		else if (message[0] == 2) {
			//quitting
			cout << "Process " << processID << ": quitting" << endl;
			break;
		}
		delete message;
	}
}


int main()
{
	MPI_Init(nullptr, nullptr);
	int id, nrProcs;
	MPI_Comm_size(MPI_COMM_WORLD, &nrProcs);
	MPI_Comm_rank(MPI_COMM_WORLD, &id);
	setCurrentID(id);
	setNrProcesses(nrProcs);
	thread listenerThread(listener);
	if (processID == 0) {
		currentLock.lock();
		distributedSharedMeme.subscribe("varA");
		cout << "0 sub varA" << endl;
		currentLock.unlock();
		currentLock.lock();
		distributedSharedMeme.subscribe("varB");
		cout << "0 sub varB" << endl;
		currentLock.unlock();
		currentLock.lock();
		distributedSharedMeme.subscribe("varC");
		cout << "0 sub varC" << endl;
		currentLock.unlock();
		this_thread::sleep_for(std::chrono::milliseconds(2000));
		currentLock.lock();
		distributedSharedMeme.updateVariable("varA", 2);
		cout << "0 update varA = 2" << endl;
		currentLock.unlock();
		currentLock.lock();
		distributedSharedMeme.updateVariable("varC", 4);
		cout << "0 update varC = 4" << endl;
		currentLock.unlock();
		this_thread::sleep_for(std::chrono::milliseconds(5000));
		currentLock.lock();
		distributedSharedMeme.checkAndReplace("varC", 4, 8);
		cout << "0 check and replace varC old 4 new 8" << endl;
		currentLock.unlock();
		DistributedSharedMemory::close();
	}
	else if (processID == 1) {
		currentLock.lock();
		distributedSharedMeme.subscribe("varA");
		cout << "1 sub varA" << endl;
		currentLock.unlock();
		currentLock.lock();
		distributedSharedMeme.subscribe("varB");
		cout << "1 sub varB" << endl;
		currentLock.unlock();
		this_thread::sleep_for(std::chrono::milliseconds(1000));
		currentLock.lock();
		distributedSharedMeme.updateVariable("varA", 6);
		cout << "1 update varA = 6" << endl;
		currentLock.unlock();
	}
	else if (processID == 2) {
		currentLock.lock();
		distributedSharedMeme.subscribe("varC");
		cout << "2 sub varC" << endl;
		currentLock.unlock();
		this_thread::sleep_for(std::chrono::milliseconds(1000));
		currentLock.lock();
		cout << "Process " << processID << " : varC = " << distributedSharedMeme.getVariableValue("varC") << endl;
		currentLock.unlock();
	}
	
	
	listenerThread.join();


	cout << "Process " << processID << " : varA = " << distributedSharedMeme.getVariableValue("varA") << endl;
	cout << "Process " << processID << " : varB = " << distributedSharedMeme.getVariableValue("varB") << endl;
	cout << "Process " << processID << " : varC = " << distributedSharedMeme.getVariableValue("varC") << endl;
	MPI_Finalize();
	return 0;
}

