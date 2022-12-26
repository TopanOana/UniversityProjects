//
// Created by Oana on 3/28/2022.
//

#include "Graph.h"
#include <cstdlib>
#include <ctime>
#include <algorithm>
#include <queue>

Graph::Graph() {
}

Graph::Graph(int nr_vertices) {
    for (int i=0;i<nr_vertices;i++)
        add_vertex(i);
}

Graph::Graph(int nr_vertices, int nr_edges) {
    for (int i=0;i<nr_vertices;i++)
        add_vertex(i);
    srand (time(NULL));
    for (int i=0;i<nr_edges;i++)
    {
        int vertex1 = rand()%nr_vertices;
        int vertex2 = rand()%nr_vertices;
        while (is_edge(vertex1, vertex2))
        {
            vertex1 = rand()%nr_vertices;
            vertex2 = rand()%nr_vertices;
        }
        int cost = rand()%101;
        add_edge(vertex1, vertex2, cost);

    }
}

bool Graph::is_edge(int vertex1, int vertex2) const {
    if (*this->outbound.find(vertex1)->second.find(vertex2)!=*this->outbound.find(vertex1)->second.end())
        return true;
    else
        return false;
}

bool Graph::is_vertex(int vertex) const {
    if (this->vertices.find(vertex)!=this->vertices.end())
        return true;
    else
        return false;
}

bool Graph::add_vertex(int vertex) {
    if (is_vertex(vertex))
        return false;
    this->vertices.insert(vertex);
    std::set<int> inbound_;
    this->inbound.insert({vertex, inbound_});
    std::set<int> outbound_;
    this->outbound.insert({vertex, outbound_});
    return true;
}

bool Graph::add_edge(int vertex1, int vertex2, int cost) {
    if (is_edge(vertex1, vertex2))
        return false;
    if (!is_vertex(vertex1) || !is_vertex(vertex2))
        return false;
    this->outbound.at(vertex1).insert(vertex2);
    this->inbound.at(vertex2).insert(vertex1);
    //this->edges.insert(Edge(vertex1, vertex2, cost));
    std::pair<int, int> key(vertex1, vertex2);
    this->cost.insert({key, cost});
    return true;
}

int Graph::get_number_of_vertices() const {
    return this->vertices.size();
}

int Graph::get_number_of_edges() const {
    return this->cost.size();
}

int Graph::get_out_degree(int vertex) const {
    if (!is_vertex(vertex))
        return -1;
    return this->outbound.at(vertex).size();
}

int Graph::get_in_degree(int vertex) const {
    if(!is_vertex(vertex))
        return -1;
    return this->inbound.at(vertex).size();
}

int Graph::get_edge_cost(int vertex1, int vertex2) const {
    if(!is_vertex(vertex1) || !is_vertex(vertex2))
        return INT_MIN;
    if(!is_edge(vertex1, vertex2))
        return INT_MIN;
    std::pair<int, int> key(vertex1, vertex2);
    return this->cost.at(key);
}

int Graph::set_edge_cost(int vertex1, int vertex2, int new_cost) {
    if(!is_vertex(vertex1) || !is_vertex(vertex2))
        return INT_MIN;
    if(!is_edge(vertex1, vertex2))
        return INT_MIN;
    std::pair<int, int> key(vertex1, vertex2);
    if (this->cost.find(key)!=this->cost.end()) {
        this->cost.find(key)->second = new_cost;
        return 1;
    }
    return 0;
}

int Graph::remove_edge(int vertex1, int vertex2) {
    if (!is_vertex(vertex1) || !is_vertex(vertex2))
        return -1;
    if (!is_edge(vertex1, vertex2))
        return -1;
    std::pair<int, int>key(vertex1, vertex2);
    if (this->cost.find(key)!=this->cost.end())
    {
        this->cost.erase(key);
        this->inbound.at(vertex2).erase(vertex1);
        this->outbound.at(vertex1).erase(vertex2);
        return 1;
    }
    return -1;
}

int Graph::remove_vertex(int vertex) {
    if (!is_vertex(vertex))
        return -1;
    std::vector<int> temporary;
    auto it = this->outbound.at(vertex).begin();
    while (it != this->outbound.at(vertex).end())
    {
        temporary.push_back(*it);
        it++;
    }

    for(auto it = std::begin(temporary);it!=std::end(temporary); it++)
        remove_edge(vertex, *it);

    it = this->inbound.at(vertex).begin();
    temporary.clear();
    while (it!= this->inbound.at(vertex).end())
    {
        temporary.push_back(*it);
        it++;
    }

    for(auto it = std::begin(temporary);it!=std::end(temporary); it++)
        remove_edge(*it, vertex);

    this->inbound.erase(vertex);
    this->outbound.erase(vertex);
    this->vertices.erase(vertex);
    return 1;
}

Graph::Graph(const Graph &gr) {
    //std::copy(gr.vertices.begin(), gr.vertices.end(), this->vertices.begin());
    for (auto it = gr.vertices.begin();it!=gr.vertices.end();it++)
        this->vertices.insert(*it);
    //std::copy(gr.cost.begin(), gr.cost.end(), this->cost.begin());
    for (auto it = gr.cost.begin(); it!=gr.cost.end();it++)
        this->cost.insert(*it);
    //std::copy(gr.outbound.begin(), gr.outbound.end(), this->outbound.begin());
    for (auto it = gr.inbound.begin();it!=gr.inbound.end();it++)
        this->inbound.insert(*it);
    //std::copy(gr.inbound.begin(), gr.inbound.end(), this->inbound.begin());
    for (auto it = gr.outbound.begin();it!=gr.outbound.end();it++)
        this->outbound.insert(*it);
}

std::set<int>::const_iterator Graph::vertices_begin() {
    return this->vertices.begin();
}

std::set<int>::const_iterator Graph::vertices_end() {
    return this->vertices.end();
}

std::set<int>::const_iterator Graph::outbound_begin(int vertex) {
    return this->outbound.at(vertex).begin();
}

std::set<int>::const_iterator Graph::outbound_end(int vertex) {
    return this->outbound.at(vertex).end();
}

std::set<int>::const_iterator Graph::inbound_begin(int vertex) {
    return this->inbound.at(vertex).begin();
}

std::set<int>::const_iterator Graph::inbound_end(int vertex) {
    return this->inbound.at(vertex).end();
}

std::map<std::pair<int, int>, int>::const_iterator Graph::cost_begin() {
    return this->cost.begin();
}

std::map<std::pair<int, int>, int>::const_iterator Graph::cost_end() {
    return this->cost.end();
}


std::vector<int> topological_Sort(Graph& g){
    std::vector<int> in_degree(g.get_number_of_vertices(), 0);
    //gets the in degree of each vertex
    for (auto edge = g.cost_begin();edge != g.cost_end();++edge)
        ++in_degree[edge->first.second];
    std::queue<int> q;
    for(int i = 0; i<g.get_number_of_vertices();++i)
    {
        if(in_degree[i] == 0)
            q.push(i);
    }
    //topo returns a predecessor list
    //the topological sort
    std::vector<int> topo;
    while(!q.empty())
    {
        int vertex = q.front();
        q.pop();
        topo.push_back(vertex);
        for(auto x = g.outbound_begin(vertex); x!=g.outbound_end(vertex);++x)
        {
            --in_degree[*x];
            if(in_degree[*x]==0)
                q.push(*x);
        }
    }
    return topo;
}




void scheduling_problem(std::string file_path)
{
    std::ifstream f(file_path);
    int n;
    f>>n;
    Graph g(n);
    ///Reading the input data
    std::vector<int> duration;
    for(int i = 0; i < n; ++i)
    {
        int pre, time;
        f>>pre>>time;
        duration.push_back(time);
        for(int j=0;j<pre;j++)
        {
            int x;
            f>>x;
            g.add_edge(x,i,0);
        }
    }
    auto order = topological_Sort(g);
    //the topological sort returns a vector of the predecessors
    if (order.size() != g.get_number_of_vertices()) {
        std::cout<<"Not a DAG.\n";
        return;
    }
    std::vector<int> earliest(g.get_number_of_vertices()), latest(g.get_number_of_vertices());
    int total_time =0;

    for (auto x : order)
    {
        earliest[x] = 0;
        for(auto it = g.inbound_begin(x); it!=g.inbound_end(x);++it)
        {
            earliest[x] = std::max(earliest[x], earliest[*it]+duration[*it]);
        }
    }
    for(auto x: earliest)
    {
        total_time = std::max(total_time, earliest[x]+duration[x]);
    }
    for(auto it = order.rbegin();it!=order.rend();++it)
    {
        latest[*it] = total_time - duration[*it];
        for (auto x = g.outbound_begin(*it); x != g.outbound_end(*it);++x)
        {
            latest[*it] = std::min(latest[*it],latest[*x]-duration[*it]);
        }
    }
    for(int i=0;i<g.get_number_of_vertices();++i)
        std::cout << "For activity " << i << " the earliest starting time is " << earliest[i] << " and the latest is " << latest[i] << ".\n";
    std::cout << "The total time to finish the project is " << total_time << ".\n";
    std::cout << "The critical activities are ";
    for (int i = 0; i < g.get_number_of_vertices(); ++i)
        if (earliest[i] == latest[i])
            std::cout << i << " ";
    std::cout << "\n\n";
    f.close();
}