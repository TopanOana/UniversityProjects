#include "Variable.h"

using namespace std;

Variable::Variable()
{
	value = 0;
	subscribers = vector<int>();
}

Variable::~Variable()
{
}

int Variable::getValue() const
{
	int currentVal = value;
	return currentVal;
}

void Variable::setValue(int newVal)
{
	value = newVal;
}

std::vector<int> Variable::getSubscribers()
{
	return subscribers;
}

void Variable::addSubscriber(int id)
{
	subscribers.push_back(id);
}
