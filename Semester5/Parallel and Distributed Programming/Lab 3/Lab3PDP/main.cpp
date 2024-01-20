#include <iostream>
#include <thread>
#include <mutex>
#include <condition_variable>
#include <list>
#include <vector>
#include <sys/time.h>
#include <functional>

#define N 100
#define K 100
#define M 100
#define TASKS 4


class ThreadPool {
public:
    explicit ThreadPool(size_t nrThreads)
            : m_end(false) {
        m_threads.reserve(nrThreads);
        for (size_t i = 0; i < nrThreads; ++i) {
            m_threads.emplace_back([this]() { this->run(); });
        }
    }

    ~ThreadPool() {
        close();
        for (std::thread &t: m_threads) {
            t.join();
        }
    }

    void close() {
        std::unique_lock<std::mutex> lck(m_mutex);
        m_end = true;
        m_cond.notify_all();
    }

    void enqueue(std::function<void()> func) {
        std::unique_lock<std::mutex> lck(m_mutex);
        m_queue.push_back(std::move(func));
        m_cond.notify_one();
    }

    //    template<typename Func, typename... Args>
    //    void enqueue(Func func, Args&&... args) {
    //        std::function<void()> f = [=](){func(args...);};
    //        enqueue(std::move(f));
    //    }
private:
    void run() {
        while (true) {
            std::function<void()> toExec;
            {
                std::unique_lock<std::mutex> lck(m_mutex);
                while (m_queue.empty() && !m_end) {
                    m_cond.wait(lck);
                }
                if (m_queue.empty()) {
                    return;
                }
                toExec = std::move(m_queue.front());
                m_queue.pop_front();
            }
            toExec();
        }
    }

    std::mutex m_mutex;
    std::condition_variable m_cond;
    std::list<std::function<void()> > m_queue;
    bool m_end;
    std::vector<std::thread> m_threads;
};


int matriceA[N][K];
int matriceB[K][M];
int result[N][M];
int nrTasks;


void initializeMatrices() {
    for (int i = 0; i < N; i++)
        for (int j = 0; j < K; j++)
            matriceA[i][j] = 1;

    for (int i = 0; i < K; i++)
        for (int j = 0; j < M; j++)
            matriceB[i][j] = 1;

    nrTasks = TASKS;
}

/*
 * Have a function that computes a single element of the resulting matrix.
 */
int element(int row, int column) {
    int sum = 0;
    for (int i = 0; i < K; i++) {
        sum += matriceA[row][i] * matriceB[i][column];
    }
    return sum;
}

//Have a second function whose each call will constitute a parallel task

//consecutive elements, row after row
void *consecutiveRowAfterRow(void *arg) {
    int id = *(int *) arg;

    printf("i am thread %d\n", id);

    int totalElements = N * M;

    int totalSums = totalElements / nrTasks;

    int i, j;
    i = (id * totalSums) / N;
    j = (id * totalSums) % M;

    if (nrTasks - 1 == id)
        totalSums += totalElements % nrTasks;


    while (totalSums) {
        result[i][j] = element(i, j);
        totalSums--;
        if ((j + 1) % M == 0) {
            i++;
            j = 0;
        } else {
            j++;
        }
    }
    return nullptr;
}

//consecutive elements column after column
void *consecutiveColumnAfterColumn(void *arg) {
    int id = *(int *) arg;

    printf("i am thread %d\n", id);

    int totalElements = N * M;

    int totalSums = totalElements / nrTasks;

    int i, j;
    j = (id * totalSums) / M;
    i = (id * totalSums) % N;

    if (nrTasks - 1 == id)
        totalSums += totalElements % nrTasks;

    while (totalSums) {
        result[i][j] = element(i, j);
        totalSums--;
        if ((i + 1) % N == 0) {
            j++;
            i = 0;
        } else {
            i++;
        }
    }
    return nullptr;

}

//each task takes every kth element
void *eachKthElement(void *arg) {
    int id = *((int *) arg);

    printf("i am thread %d\n", id);

    int i = 0;
    int j = id;

    while (true) {
        int preaDeparte = j / M;
        i += preaDeparte;
        j -= preaDeparte * M;
        if (i >= N)
            break;
        result[i][j] = element(i, j);
        j += nrTasks;
    }

    return nullptr;
}


void showResult() {
    std::cout << "result:\n";
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++)
            std::cout << result[i][j] << " ";
        std::cout << "\n";
    }
}

int main() {

    initializeMatrices();
    std::string method = "simple threads";
    struct timeval startSimpleThreads, endSimpleThreads;
    struct timeval startThreadPool, endThreadPool;

    int *threadIds = (int *) malloc(nrTasks * sizeof(int));

//    if (method == "simple threads") {
//

    gettimeofday(&startSimpleThreads, nullptr);
    pthread_t *threads = (pthread_t *) malloc(nrTasks * sizeof(pthread_t));

    for (int i = 0; i < nrTasks; i++) {
        threadIds[i] = i;
        pthread_create(&threads[i], nullptr, eachKthElement, (void *) (&threadIds[i]));
    }


    for (int i = 0; i < nrTasks; i++) {
        pthread_join(threads[i], nullptr);
    }
    gettimeofday(&endSimpleThreads, nullptr);
    free(threads);

    printf("simple threads time: %u\n", endSimpleThreads.tv_usec - startSimpleThreads.tv_usec);


//    } else {
    gettimeofday(&startThreadPool, nullptr);
    ThreadPool threadPool(nrTasks);
    for (int i = 0; i < nrTasks; ++i) {
        threadIds[i] = i;
        auto aux = (void *) (&threadIds[i]);
        threadPool.enqueue([aux]() {
            eachKthElement(aux);
        });
    }
    threadPool.close();
    gettimeofday(&endThreadPool, nullptr);
    printf("threadpool time: %u\n", endThreadPool.tv_usec - startThreadPool.tv_usec);


    free(threadIds);
    showResult();
    std::cout << "Hello, World!" << std::endl;
    return 0;
}
