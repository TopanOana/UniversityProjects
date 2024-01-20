#include <iostream>
#include <pthread.h>
#include <random>
#include <unistd.h>

using namespace std;

const int MAXIMUM_NR_VARIABLES = 21;



struct Variable{
    int value;
    pthread_mutex_t *mutex;
    int parent = -1;
    int children[10];
    int nrChildren = 0;
};

Variable variables[MAXIMUM_NR_VARIABLES];
int nrVariables;
int nrPrimaryVariables;
pthread_cond_t* conditionalVariable;

//auditPass = 0 -> audit failed
//          = 1 -> audit ongoing
//          = 2 -> audit passed
int auditPass = 2;
pthread_mutex_t *auditMutex;

void initializeNode(int index, int parent, int value){
    variables[index].parent = parent;
    if(parent != -1){
        variables[parent].children[variables[parent].nrChildren] = index;
        variables[parent].nrChildren++;
    }
    if(index < 7)
        variables[index].value = rand() % 20;
    else {
        variables[index].value = value;
    }
    variables[index].mutex = (pthread_mutex_t*) (malloc(sizeof(pthread_mutex_t)));
    pthread_mutex_init(variables[index].mutex, nullptr);
}

void initialization(){
    nrVariables = 21;
    nrPrimaryVariables = 7;

    //nodul 0 parinte = 7
    initializeNode(0, 7, 0);
    //nodul 1 parinte = 8
    initializeNode(1, 8, 0);
    //nodul 2 parinte = 9
    initializeNode(2, 9, 0);
    //nodul 3 parinte = 10
    initializeNode(3, 10, 0);
    //nodul 4 parinte = 11
    initializeNode(4, 11, 0 );
    //nodul 5 parinte = 12
    initializeNode(5, 12, 0);
    //nodul 6 parinte = 13
    initializeNode(6, 13, 0);
    //nodul 7 parinte = 14
    initializeNode(7, 14, variables[0].value);
    //nodul 8 parinte = 14
    initializeNode(8, 14, variables[1].value);
    //nodul 9 parinte = 15
    initializeNode(9, 15, variables[2].value);
    //nodul 10 parinte = 15
    initializeNode(10, 15, variables[3].value);
    //nodul 11 parinte = 16
    initializeNode(11, 16, variables[4].value);
    //nodul 12 parinte = 16
    initializeNode(12, 16, variables[5].value);
    //nodul 13 parinte = 17
    initializeNode(13, 17, variables[6].value);
    //nodul 14 parinte = 18
    initializeNode(14, 18, variables[7].value + variables[8].value);
    //nodul 15 parinte = 18
    initializeNode(15, 18, variables[9].value + variables[10].value);
    //nodul 16 parinte = 19
    initializeNode(16, 19, variables[11].value + variables[12].value);
    //nodul 17 parinte = 19
    initializeNode(17, 19, variables[13].value );
    //nodul 18 parinte = 20
    initializeNode(18, 20, variables[14].value + variables[15].value);
    //nodul 19 parinte = 20
    initializeNode(19, 20, variables[16].value + variables[17].value);
    //nodul 20 parinte -1
    initializeNode(20, -1, variables[18].value + variables[19].value);
}

void changeValues(int index, int delta){
    pthread_mutex_lock(variables[index].mutex);

    variables[index].value += delta;

    int parent = variables[index].parent;

    if (parent != -1){
        changeValues(parent, delta);
    }

    pthread_mutex_unlock(variables[index].mutex);
}


void *workerThread(void *arg){
    while (true){
        int sleepTime = rand()%5;
        sleep(sleepTime);

        pthread_mutex_lock(auditMutex);
        while(auditPass!=2){
            pthread_cond_wait(conditionalVariable, auditMutex);
        }

        if(auditPass==0){
            pthread_mutex_unlock(auditMutex);
            pthread_exit(nullptr);
        }

        pthread_mutex_unlock(auditMutex);

        printf("start workerthread!\n");

        int chooseElement = rand() % nrPrimaryVariables;

        int delta = rand() % 1000000;


        changeValues(chooseElement, delta);
    }

}


bool checkValue(int index){
    int nrChildren = variables[index].nrChildren;
    if (nrChildren != 0){
        int sum = 0;
        for (int i = 0; i < nrChildren; i++) {
            if (!checkValue(variables[index].children[i])){
                return false;
            }
            sum += variables[variables[index].children[i]].value;
        }

        if (sum != variables[index].value){
            return false;
        }

    }
    return true;
}

void *auditThread(void *arg){

    while(true){
        int sleepTime = rand()%20;
        sleep(sleepTime);
        printf("start audit\n");

        pthread_mutex_lock(auditMutex);
        auditPass = 1;
        pthread_mutex_unlock(auditMutex);



        for (int i = 0; i < nrVariables; i++){
            pthread_mutex_lock(variables[i].mutex);
        }

        //parintele mare 20
        if(checkValue(20)){
            printf("audit passed!\n");
            pthread_mutex_lock(auditMutex);
            auditPass = 2;
            pthread_cond_broadcast(conditionalVariable);
            pthread_mutex_unlock(auditMutex);
        }
        else{
            printf("audit failed!\n");
            pthread_mutex_lock(auditMutex);
            auditPass = 0;
            pthread_cond_broadcast(conditionalVariable);
            pthread_mutex_unlock(auditMutex);
        }

        for (int i = 0; i< nrVariables; i++){
            printf("variable %d has value %d\n", i, variables[i].value);
        }



        for (int i = 0; i < nrVariables; i++){
            pthread_mutex_unlock(variables[i].mutex);
        }

        pthread_mutex_lock(auditMutex);
        if (auditPass == 0){
            pthread_mutex_unlock(auditMutex);
            pthread_exit(nullptr);
        }
        pthread_mutex_unlock(auditMutex);

    }

}


int main() {
    initialization();


    int workerThreads = 1000;
    auto *workers = (pthread_t *)(malloc(sizeof(pthread_t) * workerThreads));
    auto *auditor = (pthread_t *)(malloc(sizeof(pthread_t) * 1));

    conditionalVariable = (pthread_cond_t*)(malloc(sizeof(pthread_cond_t)));
    pthread_cond_init(conditionalVariable, nullptr);
    auditMutex = (pthread_mutex_t*)(malloc(sizeof(pthread_mutex_t)));
    pthread_mutex_init(auditMutex, nullptr);

    pthread_create(auditor, nullptr, auditThread, nullptr);

    for (int i = 0; i < workerThreads; i++){
        pthread_create(&workers[i], nullptr, workerThread, nullptr);
    }

    pthread_join(*auditor, nullptr);

    for (int i = 0; i < workerThreads; i++){
        pthread_join(workers[i], nullptr);
    }


    free(workers);
    free(auditor);
    free(conditionalVariable);
    free(auditMutex);
    for (int i = 0; i < nrVariables; i++){
        free(variables[i].mutex);
    }


    std::cout << "Hello, World!" << std::endl;
    return 0;
}
