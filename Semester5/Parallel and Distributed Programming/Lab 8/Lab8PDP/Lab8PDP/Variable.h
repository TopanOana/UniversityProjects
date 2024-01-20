#pragma once
#include <mutex>
#include <vector>

class Variable
{
private:
	int value;
	std::vector<int> subscribers;

public:
	std::mutex varMutex;

	Variable();
	~Variable();

	int getValue() const;

	void setValue(int newVal);

	std::vector<int> getSubscribers();

	void addSubscriber(int id);

};
