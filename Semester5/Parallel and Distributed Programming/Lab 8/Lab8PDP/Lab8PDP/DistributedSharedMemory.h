#pragma once
#include "Variable.h"

class DistributedSharedMemory
{
private:
	Variable varA;
	Variable varB;
	Variable varC;
	
	Variable& getVariable(const char* variableName);
public:
	void setVariable(const char* variableName, int newVal);
	int getVariableValue(const char* variableName);
	void subscribe(const char* variableName);
	void updateSubscription(const char* variableName, int id);
	void updateVariable(const char* variableName, int newVal);
	void checkAndReplace(const char* variableName, int oldVal, int newVal);
	static void close();
};

