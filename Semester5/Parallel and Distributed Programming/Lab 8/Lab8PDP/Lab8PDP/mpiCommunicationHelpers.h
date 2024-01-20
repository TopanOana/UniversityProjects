#pragma once

void sendUpdateMessage(const char* variableName, int newVal, int id);

void sendSubscribeMessage(const char* variableName, int newID, int id);

void sendCloseMessage(int id);

int* getMessage();