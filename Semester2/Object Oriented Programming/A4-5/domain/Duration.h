#pragma once

class Duration {
private:
    int minutes;
    int seconds;

public:
    Duration();
    Duration(int minutes, int seconds);
    Duration(const Duration& copy);
    int get_minutes() const;
    int get_seconds() const;
    void set_minutes(int minutes);
    void set_seconds(int seconds);
};
