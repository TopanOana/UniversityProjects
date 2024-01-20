#include <iostream>
#include <vector>
#include <algorithm>
#include <random>
#include <future>
#include <pthread.h>
#include <sys/time.h>

#define NR_NODES 1000
#define NR_EDGES 1500
#define STARTING_NODE 0
#define PARALLEL_DEPTH 1

using namespace std;

vector<vector<int>> listOfNeighbours;

vector<int> globalPath;

void addEdge(int a, int b) {
    listOfNeighbours[a].push_back(b);
}

bool checkIfEdgeExists(int a, int b) {
    return (std::find(listOfNeighbours[a].begin(), listOfNeighbours[a].end(), b) != listOfNeighbours[a].end());
}

void initializeGraph() {
    listOfNeighbours = vector<vector<int>>(NR_NODES);
    listOfNeighbours.clear();

    vector<int> nodes;
    for (int i = 0; i < NR_NODES; i++) {
        nodes.push_back(i);
    }

    for (int i = 0; i < NR_NODES - 1; i++) {
        addEdge(i, i + 1);
    }

    addEdge(NR_NODES - 1, 0);

    int restOfEdges = NR_EDGES - NR_NODES;

    for (int i = 0; i < restOfEdges; i++) {

        int a = rand() % NR_NODES;
        int b = rand() % NR_NODES;
        while (checkIfEdgeExists(a, b)) {
            a = rand() % (NR_NODES - 1);
            b = rand() % (NR_NODES - 1);
        }
        addEdge(a, b);
    }

    for (int i = 0; i < NR_NODES; i++) {
        cout << "vertex " << i << ": ";
        std::for_each(listOfNeighbours[i].begin(), listOfNeighbours[i].end(), [](int value) { cout << value << " "; });
        cout << endl;
    }
}

void initializeGraph1() {
    listOfNeighbours = vector<vector<int>>(NR_NODES);
    listOfNeighbours.clear();

    vector<int> nodes;
    for (int i = 0; i < NR_NODES; i++) {
        nodes.push_back(i);
    }

    addEdge(0, 1);
    addEdge(1, 0);
    addEdge(1, 2);
    addEdge(2, 1);
    addEdge(2, 3);
    addEdge(3, 2);
    addEdge(3, 0);
    addEdge(0, 3);
    addEdge(4, 3);
    addEdge(3, 4);
    addEdge(4, 2);
    addEdge(2, 4);

//    addEdge(NR_NODES - 1, 0);
//
//    for (int i = 0; i < NR_NODES / 2; i++) {
//        int a = rand() % (NR_NODES - 1);
//        int b = rand() % (NR_NODES - 1);
//        addEdge(a, b);
//    }

    for (int i = 0; i < NR_NODES; i++) {
        cout << "vertex " << i << ": ";
        std::for_each(listOfNeighbours[i].begin(), listOfNeighbours[i].end(), [](int value) { cout << value << " "; });
        cout << endl;
    }
}


bool isSafe(int node, int pos) {
    //check if previously added vertex is neighbours with the current choice
    bool ok = false;
    for (int i = 0; i < listOfNeighbours[globalPath[pos - 1]].size(); i++) {
        if (node == listOfNeighbours[globalPath[pos - 1]][i])
            ok = true;
    }

    if (!ok)
        return false;


    //checks if the current choice has already been added to the path
    for (int i = 0; i < pos; i++) {
        if (globalPath[i] == node)
            return false;
    }

    return true;
}

//recursive function for the hamiltonian cycle ~ backtracking time
bool hamiltonianCycleUtil(int pos) {
    //base case
    // all the vertices are included in the Hamiltonian cycle
    if (pos == NR_NODES) {
        //the last vertex added had an edge to the first one
        if (find(listOfNeighbours[globalPath[pos - 1]].begin(), listOfNeighbours[globalPath[pos - 1]].end(),
                 globalPath[0]) !=
            listOfNeighbours[globalPath[pos - 1]].end())
            return true;
        else return false;
    }

    for (int v: listOfNeighbours[globalPath[pos - 1]]) {
        if (isSafe(v, pos)) {

            globalPath[pos] = v;

            if (hamiltonianCycleUtil(pos + 1))
                return true;

            //if it isn't hamiltonian remove the vertex
            globalPath[pos] = -1;

        }
    }
    //if nothing has been found, then it is false
    return false;
}

void hamiltonianCycle() {
    globalPath = vector<int>(NR_NODES);
    globalPath.clear();
    for (int i = 0; i < NR_NODES; i++)
        globalPath[i] = -1;

    struct timeval start, end;

    gettimeofday(&start, nullptr);

    globalPath[0] = STARTING_NODE;
    if (!hamiltonianCycleUtil(1)) {
        gettimeofday(&end, nullptr);
        printf("hamiltonian cycle serial time = %ld\n", end.tv_usec - start.tv_usec);
        cout << "Solution doesn't exist" << endl;
        return;
    }

    gettimeofday(&end, nullptr);
    printf("hamiltonian cycle serial time = %ld\n", end.tv_usec - start.tv_usec);

    //print path
    cout << "Hamiltonian cycle found! Solution serial: \n";

    for (int i = 0; i < NR_NODES; i++)
        cout << globalPath[i] << " ";
    cout << globalPath[0] << endl;

}


///PARALLELIZED VERSION!!

pthread_mutex_t *pathMutex;
bool foundCycle = false;

bool isSafe(int node, int pos, const vector<int> &path) {
    //check if previously added vertex is neighbours with the current choice
    bool ok = false;
    for (int i = 0; i < listOfNeighbours[globalPath[pos - 1]].size(); i++) {
        if (node == listOfNeighbours[globalPath[pos - 1]][i])
            ok = true;
    }

    if (!ok)
        return false;


    //checks if the current choice has already been added to the path
    for (int i = 0; i < pos; i++) {
        if (path[i] == node)
            return false;
    }

    return true;
}


//recursive function for the hamiltonian cycle ~ backtracking time
bool hamiltonianCycleUtilParallelized(int pos, const vector<int> &path) {
    //base case
    // all the vertices are included in the Hamiltonian cycle
    pthread_mutex_lock(pathMutex);
    if (foundCycle)
    {
        pthread_mutex_unlock(pathMutex);
        return foundCycle;
    }
    pthread_mutex_unlock(pathMutex);
    if (pos == NR_NODES) {
        //the last vertex added had an edge to the first one
        if (find(listOfNeighbours[path[pos - 1]].begin(), listOfNeighbours[path[pos - 1]].end(), path[0]) !=
            listOfNeighbours[path[pos - 1]].end()) {
            pthread_mutex_lock(pathMutex);
            if (!foundCycle) {
                cout << "Hamiltonian cycle found! Solution parallelized: \n";

                for (int i = 0; i < NR_NODES; i++)
                    cout << path[i] << " ";
                cout << path[0] << endl;
            }
            pthread_mutex_unlock(pathMutex);
            return true;
        } else return false;
    }

    if (pos < PARALLEL_DEPTH) {
        vector<future<bool>> futures;

        for (int v: listOfNeighbours[path[pos - 1]]) {
            if (isSafe(v, pos, path)) {

                vector<int> newPath(NR_NODES);
                for (int i = 0; i < path.size(); i++) {
                    newPath[i] = path[i];
                }
                newPath[pos] = v;

                futures.push_back(async(hamiltonianCycleUtilParallelized, pos + 1, newPath));

            }
        }

        for (future<bool> &item: futures) {
            if (item.get())
                return true;
        }
        //if nothing has been found, then it is false
        return false;
    } else {
        for (int v: listOfNeighbours[path[pos - 1]]) {
            if (isSafe(v, pos, path)) {

//                vector<int> newPath(NR_NODES);
//                for (int i = 0; i < path.size(); i++) {
//                    newPath[i] = path[i];
//                }
//                newPath[pos] = v;
//
//                hamiltonianCycleUtilParallelized(pos + 1, newPath);
                vector<int> newPath = path;
                newPath[pos] = v;
                if (hamiltonianCycleUtilParallelized(pos + 1, newPath)) {
                    return true;
                }
            }
        }
        return false;
    }
}

void hamiltonianCycleParallelized() {
    pathMutex = (pthread_mutex_t *) malloc(sizeof(pthread_mutex_t));
    pthread_mutex_init(pathMutex, nullptr);

    vector<int> path(NR_NODES);
    for (int i = 0; i < NR_NODES; i++)
        path[i] = -1;

    path[0] = STARTING_NODE;

    struct timeval start, end;

    gettimeofday(&start, nullptr);

    if (!hamiltonianCycleUtilParallelized(1, path)) {
        cout << "No Hamiltonian cycle has been found!" << endl;
    }
    gettimeofday(&end, nullptr);
    printf("hamiltonian cycle parallelized time = %ld\n", end.tv_usec - start.tv_usec);

    pthread_mutex_destroy(pathMutex);
    free(pathMutex);
}


int main() {
    initializeGraph();

    hamiltonianCycle();

    hamiltonianCycleParallelized();

    std::cout << "Hello, World!" << std::endl;
    return 0;
}
