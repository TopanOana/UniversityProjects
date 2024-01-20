#include <iostream>
#include <pthread.h>
#include <queue>
//#include <unistd.h>
#include <windows.h>
#include <zconf.h>

int a[100];
int b[100];
int n;

std::queue<int> transport;
pthread_mutex_t *mutexTransport;
pthread_cond_t *conditionalVariable;


void initialize1() {
    n = 100;
    for (int i = 0; i < n; i++) {
        a[i] = 1;
        b[i] = 1;
    }
}

void initialize2() {
    n = 100;
    for (int i = 0; i < n; i++) {
        a[i] = i + 1;
        b[i] = i + 1;
    }
}


void *producer(void *arg) {

    for (int i = 0; i < n; i++) {


        pthread_mutex_lock(mutexTransport);

        std::cout << "produced on step " << i << " value = " << a[i] * b[i] << std::endl;
        transport.push(a[i] * b[i]);

        pthread_cond_signal(conditionalVariable);
        pthread_mutex_unlock(mutexTransport);
        usleep(1000);
    }

    return nullptr;
}


void *consumer(void *arg) {
    int sum = 0;
    for (int i = 0; i < n; i++) {

//        sleep(10);
        pthread_mutex_lock(mutexTransport);

        while (transport.empty()) {
            pthread_cond_wait(conditionalVariable, mutexTransport);
        }
        sum += transport.front();
        std::cout << "consumer on step " << i << " sum = " << sum << std::endl;

        transport.pop();

        pthread_mutex_unlock(mutexTransport);
    }

    std::cout << "the scalar product is = " << sum << std::endl;

    return nullptr;

}

int main() {

    initialize2();


    pthread_t *producerThread = (pthread_t *) (malloc(sizeof(pthread_t) * 1));
    pthread_t *consumerThread = (pthread_t *) (malloc(sizeof(pthread_t) * 1));


    conditionalVariable = (pthread_cond_t *) (malloc(sizeof(pthread_cond_t)));
    pthread_cond_init(conditionalVariable, nullptr);
    mutexTransport = (pthread_mutex_t *) (malloc(sizeof(pthread_mutex_t)));
    pthread_mutex_init(mutexTransport, nullptr);

    pthread_create(producerThread, nullptr, producer, nullptr);
    pthread_create(consumerThread, nullptr, consumer, nullptr);


    pthread_join(*producerThread, nullptr);
    pthread_join(*consumerThread, nullptr);

    pthread_cond_destroy(conditionalVariable);

    free(producerThread);
    free(consumerThread);
    free(conditionalVariable);
    free(mutexTransport);

    std::cout << "Hello, World!" << std::endl;
    return 0;
}
