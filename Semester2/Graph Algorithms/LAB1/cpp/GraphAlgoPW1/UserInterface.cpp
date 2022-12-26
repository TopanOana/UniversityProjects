//
// Created by Oana on 3/29/2022.
//

#include "UserInterface.h"
#include <iostream>
#include <fstream>

UserInterface::UserInterface() = default;

void UserInterface::print_menu() {
    std::cout<<"1. Create an empty graph.\n";
    std::cout<<"2. Create a graph with n vertices.\n";
    std::cout<<"3. Create a graph with n vertices and m random edges.\n";
    std::cout<<"4. Read a graph from a file.\n";
    std::cout<<"5. Write graph to a file.\n";
    std::cout<<"6. Add a vertex.\n";
    std::cout<<"7. Add an edge.\n";
    std::cout<<"8. Remove a vertex.\n";
    std::cout<<"9. Remove an edge.\n";
    std::cout<<"10. Get the cost of an edge.\n";
    std::cout<<"11. Set the cost of an edge.\n";
    std::cout<<"12. Get the in degree of a vertex.\n";
    std::cout<<"13. Get the out degree of a vertex.\n";
    std::cout<<"14. Get the number of vertices.\n";
    std::cout<<"15. Get the number of edges.\n";
    std::cout<<"16. Print the list of vertices.\n";
    std::cout<<"17. Print the list of outbound neighbours of a vertex.\n";
    std::cout<<"18. Print the list of inbound neighbours of a vertex.\n";
    std::cout<<"19. Print the list of edges.\n";
    std::cout<<"20. Exit.\n";
}

void UserInterface::create_an_empty_graph() {
    this->graph = Graph();
    std::cout<<"Created an empty graph.\n\n";
}

void UserInterface::create_a_graph_with_n_vertices() {
    int n;
    std::cout<<"Number of vertices n = ";
    std::string n_input;
    std::cin>>n_input;
    if (n_input=="" || !(n_input.find_first_not_of("01223456789")==std::string::npos))
    {
        std::cout<<"Invalid number of vertices.\n\n";
        return;
    }
    n = std::stoi(n_input);
    if (n<0)
    {
        std::cout<<"Invalid number of vertices.\n\n";
        return;
    }
    graph = Graph(n);
    std::cout<<"Created a graph with n vertices.\n\n";
}

void UserInterface::create_a_graph_with_n_vertices_and_m_random_edges() {
    int n;
    std::cout<<"Number of vertices n = ";
    std::string n_input;
    std::cin>>n_input;
    if (n_input=="" || !(n_input.find_first_not_of("01223456789")==std::string::npos))
    {
        std::cout<<"Invalid number of vertices.\n\n";
        return;
    }
    n = std::stoi(n_input);
    if (n<0)
    {
        std::cout<<"Invalid number of vertices.\n\n";
        return;
    }
    int m;
    std::cout<<"Number of edges m = ";
    std::string m_input;
    std::cin.get();
    std::cin>>m_input;
    if(m_input=="" || !(m_input.find_first_not_of("0123456789")==std::string::npos))
    {
        std::cout<<"Invalid number of edges.\n\n";
        return;
    }
    m=std::stoi(m_input);
    if (m<0)
    {
        std::cout<<"Invalid number of edges.\n\n";
        return;
    }
    graph = Graph(n,m);
    std::cout<<"Created a graph with n vertices and m random edges.\n\n";
}


void UserInterface::read_from_file() {
    std::string file_input;
    std::cout<<"Input file: ";
    std::cin>>file_input;
    std::ifstream file;
    file.open(file_input);
    if (!file) {
        std::cout << "file didnt open.\n\n";
        file.close();
        return;
    }
    int n,m;
    file>>n>>m;
    graph = Graph(n);
    for (int i=0;i<m;i++)
    {
        int v1, v2, c;
        file>>v1>>v2>>c;
        graph.add_edge(v1,v2,c);
    }
    std::cout<<"Read graph from file.\n\n";
    file.close();
}

void UserInterface::write_to_file() {
    std::string file_input;
    std::cout<<"Input file: ";
    std::cin>>file_input;
    std::ofstream file;
    file.open(file_input);
    file<<graph.get_number_of_vertices()<<" "<<graph.get_number_of_edges()<<"\n";
    for (auto it = graph.cost_begin(); it!=graph.cost_end();++it)
    {
        int v1 = it->first.first;
        int v2 = it->first.second;
        int c = it->second;
        file<<v1<<" "<<v2<<" "<<c<<"\n";
    }
    file.close();
}



void UserInterface::add_vertex() {
    int vertex;
    std::string vertex_input;
    std::cout<<"Add a vertex v= ";
    std::cin>>vertex_input;
    if (vertex_input=="" || !(vertex_input.find_first_not_of("01223456789")==std::string::npos))
    {
        std::cout<<"Invalid vertex.\n\n";
        return;
    }
    vertex = std::stoi(vertex_input);
    if (vertex<0)
    {
        std::cout<<"Invalid vertex.\n\n";
        return;
    }
    if (graph.add_vertex(vertex)) {
        std::cout << "Vertex added.\n\n";
        return;
    }
    else {
        std::cout << "Vertex already in graph\n\n";
    }
}

void UserInterface::add_edge() {
    int vertex1, vertex2, cost;
    std::string vertex1_input, vertex2_input, cost_input;
    std::cout<<"Vertex1 = ";
    std::cin>>vertex1_input;
    if (vertex1_input=="" || !(vertex1_input.find_first_not_of("01223456789")==std::string::npos))
    {
        std::cout<<"Invalid vertex.\n\n";
        return;
    }
    vertex1 = std::stoi(vertex1_input);
    if (vertex1<0)
    {
        std::cout<<"Invalid vertex.\n\n";
        return;
    }
    std::cout<<"Vertex2 = ";
    std::cin>>vertex2_input;
    if (vertex2_input=="" || !(vertex2_input.find_first_not_of("01223456789")==std::string::npos))
    {
        std::cout<<"Invalid vertex.\n\n";
        return;
    }
    vertex2 = std::stoi(vertex2_input);
    if (vertex2<0)
    {
        std::cout<<"Invalid vertex.\n\n";
        return;
    }
    std::cout<<"Cost = ";
    std::cin>>cost_input;
    if (cost_input=="" || !(cost_input.find_first_not_of("01223456789")==std::string::npos))
    {
        std::cout<<"Invalid cost.\n\n";
        return;
    }
    cost = std::stoi(cost_input);
    if(graph.add_edge(vertex1, vertex2, cost))
    {
        std::cout<<"Edge added to graph.\n\n";
        return;
    }
    else{
        std::cout<<"Edge not added.\n\n";
    }


}

void UserInterface::remove_vertex() {
    int vertex;
    std::string vertex_input;
    std::cout<<"Remove a vertex v= ";
    std::cin>>vertex_input;
    if (vertex_input=="" || !(vertex_input.find_first_not_of("01223456789")==std::string::npos))
    {
        std::cout<<"Invalid vertex.\n\n";
        return;
    }
    vertex = std::stoi(vertex_input);
    if (vertex<0)
    {
        std::cout<<"Invalid vertex.\n\n";
        return;
    }
    if (graph.remove_vertex(vertex)==1)
    {
        std::cout<<"Vertex removed from graph.\n\n";
        return;
    }
    else{
        std::cout<<"Vertex not removed from graph/ not in graph.\n\n";

    }
}

void UserInterface::remove_edge() {
    int vertex1, vertex2;
    std::string vertex1_input, vertex2_input, cost_input;
    std::cout<<"Vertex1 = ";
    std::cin>>vertex1_input;
    if (vertex1_input=="" || !(vertex1_input.find_first_not_of("01223456789")==std::string::npos))
    {
        std::cout<<"Invalid vertex.\n\n";
        return;
    }
    vertex1 = std::stoi(vertex1_input);
    if (vertex1<0)
    {
        std::cout<<"Invalid vertex.\n\n";
        return;
    }
    std::cout<<"Vertex2 = ";
    std::cin>>vertex2_input;
    if (vertex2_input=="" || !(vertex2_input.find_first_not_of("01223456789")==std::string::npos))
    {
        std::cout<<"Invalid vertex.\n\n";
        return;
    }
    vertex2 = std::stoi(vertex2_input);
    if (vertex2<0)
    {
        std::cout<<"Invalid vertex.\n\n";
        return;
    }
    if (graph.remove_edge(vertex1, vertex2)==1)
    {
        std::cout<<"Edge removed from graph.\n\n";
        return;
    }
    else {
        std::cout << "Edge not removed from graph/not in graph.\n\n";
    }
}

void UserInterface::get_cost_of_an_edge() {
    int vertex1, vertex2;
    std::string vertex1_input, vertex2_input, cost_input;
    std::cout<<"Vertex1 = ";
    std::cin>>vertex1_input;
    if (vertex1_input=="" || !(vertex1_input.find_first_not_of("01223456789")==std::string::npos))
    {
        std::cout<<"Invalid vertex.\n\n";
        return;
    }
    vertex1 = std::stoi(vertex1_input);
    if (vertex1<0)
    {
        std::cout<<"Invalid vertex.\n\n";
        return;
    }
    std::cout<<"Vertex2 = ";
    std::cin>>vertex2_input;
    if (vertex2_input=="" || !(vertex2_input.find_first_not_of("01223456789")==std::string::npos))
    {
        std::cout<<"Invalid vertex.\n\n";
        return;
    }
    vertex2 = std::stoi(vertex2_input);
    if (vertex2<0) {
        std::cout << "Invalid vertex.\n\n";
        return;
    }
    int val = graph.get_edge_cost(vertex1,vertex2);
    if (val == INT_MIN)
    {
        std::cout<<"Error, edge not in graph.\n\n";
        return;
    }
    else{
        std::cout << "Cost = "<<val<<"\n\n";
    }

}

void UserInterface::set_cost_of_an_edge() {
    int vertex1, vertex2, cost;
    std::string vertex1_input, vertex2_input, cost_input;
    std::cout<<"Vertex1 = ";
    std::cin>>vertex1_input;
    if (vertex1_input=="" || !(vertex1_input.find_first_not_of("01223456789")==std::string::npos))
    {
        std::cout<<"Invalid vertex.\n\n";
        return;
    }
    vertex1 = std::stoi(vertex1_input);
    if (vertex1<0)
    {
        std::cout<<"Invalid vertex.\n\n";
        return;
    }
    std::cout<<"Vertex2 = ";
    std::cin>>vertex2_input;
    if (vertex2_input=="" || !(vertex2_input.find_first_not_of("01223456789")==std::string::npos))
    {
        std::cout<<"Invalid vertex.\n\n";
        return;
    }
    vertex2 = std::stoi(vertex2_input);
    if (vertex2<0)
    {
        std::cout<<"Invalid vertex.\n\n";
        return;
    }
    std::cout<<"New cost = ";
    std::cin>>cost_input;
    if (cost_input=="" || !(cost_input.find_first_not_of("01223456789")==std::string::npos))
    {
        std::cout<<"Invalid cost.\n\n";
        return;
    }
    cost = std::stoi(cost_input);
    int val = graph.set_edge_cost(vertex1,vertex2,cost);
    if (val == INT_MIN)
    {
        std::cout<<"Edge not in graph.\n\n";
        return;
    }
    std::cout<<"Cost modified in graph.\n\n";
}

void UserInterface::get_in_degree() {
    int vertex;
    std::string vertex_input;
    std::cout<<"Vertex v= ";
    std::cin>>vertex_input;
    if (vertex_input=="" || !(vertex_input.find_first_not_of("01223456789")==std::string::npos))
    {
        std::cout<<"Invalid vertex.\n\n";
        return;
    }
    vertex = std::stoi(vertex_input);
    if (vertex<0)
    {
        std::cout<<"Invalid vertex.\n\n";
        return;
    }
    int val = graph.get_in_degree(vertex);
    if (val == -1){
        std::cout<<"Invalid vertex.\n\n";
        return;
    }
    std::cout<<"In degree of vertex: "<<val<<"\n\n";
}

void UserInterface::get_out_degree() {
    int vertex;
    std::string vertex_input;
    std::cout<<"Vertex v= ";
    std::cin>>vertex_input;
    if (vertex_input=="" || !(vertex_input.find_first_not_of("01223456789")==std::string::npos))
    {
        std::cout<<"Invalid vertex.\n\n";
        return;
    }
    vertex = std::stoi(vertex_input);
    if (vertex<0)
    {
        std::cout<<"Invalid vertex.\n\n";
        return;
    }
    int val = graph.get_out_degree(vertex);
    if (val == -1){
        std::cout<<"Invalid vertex.\n\n";
        return;
    }
    std::cout<<"Out degree of vertex: "<<val<<"\n\n";
}

void UserInterface::get_number_of_vertices() {
    int val = graph.get_number_of_vertices();
    std::cout<<"Number of vertices: "<<val<<"\n\n";
}

void UserInterface::get_number_of_edges() {
    int val = graph.get_number_of_edges();
    std::cout<<"Number of edges: "<<val<<"\n\n";
}

void UserInterface::print_list_of_vertices() {
    auto end = graph.vertices_end();
    std::cout<<"Vertices: ";
    for (auto it = graph.vertices_begin();it!=end;it++)
    {
        std::cout<<*it<<" ";
    }
    std::cout<<"\n\n";
}

void UserInterface::print_outbound_neighbours() {
    int vertex;
    std::string vertex_input;
    std::cout<<"Vertex v= ";
    std::cin>>vertex_input;
    if (vertex_input=="" || !(vertex_input.find_first_not_of("01223456789")==std::string::npos))
    {
        std::cout<<"Invalid vertex.\n\n";
        return;
    }
    vertex = std::stoi(vertex_input);
    if (vertex<0)
    {
        std::cout<<"Invalid vertex.\n\n";
        return;
    }
    std::cout<<"Outbound neighbours: ";
    for (auto it = graph.outbound_begin(vertex); it != graph.outbound_end(vertex);it++)
        std::cout<<*it<<" ";
    std::cout<<"\n\n";
}

void UserInterface::print_inbound_neighbours() {
    int vertex;
    std::string vertex_input;
    std::cout<<"Vertex v= ";
    std::cin>>vertex_input;
    if (vertex_input=="" || !(vertex_input.find_first_not_of("01223456789")==std::string::npos))
    {
        std::cout<<"Invalid vertex.\n\n";
        return;
    }
    vertex = std::stoi(vertex_input);
    if (vertex<0)
    {
        std::cout<<"Invalid vertex.\n\n";
        return;
    }
    std::cout<<"Inbound neighbours: ";
    for (auto it = graph.inbound_begin(vertex); it != graph.inbound_end(vertex);it++)
        std::cout<<*it<<" ";
    std::cout<<"\n\n";
}

void UserInterface::print_list_of_edges() {
    std::cout<<"Edges: \n";
    auto it = graph.cost_begin();
    while(it!=graph.cost_end()) {
        int v1 = it->first.first;
        int v2 = it->first.second;
        int c = it->second;
        std::cout<<"("<<v1<<","<<v2<<") - "<<c<<"\n";
        ++it;
    }
}

void UserInterface::start() {
    std::cout<<"Graph c++ bonus!\n";
    while(true)
    {
        print_menu();
        int option;
        std::cin>>option;
        switch (option){
            case 1:
                create_an_empty_graph();
                break;
            case 2:
                create_a_graph_with_n_vertices();
                break;
            case 3:
                create_a_graph_with_n_vertices_and_m_random_edges();
                break;
            case 4:
                read_from_file();
                break;
            case 5:
                write_to_file();
                break;
            case 6:
                add_vertex();
                break;
            case 7:
                add_edge();
                break;
            case 8:
                remove_vertex();
                break;
            case 9:
                remove_edge();
                break;
            case 10:
                get_cost_of_an_edge();
                break;
            case 11:
                set_cost_of_an_edge();
                break;
            case 12:
                get_in_degree();
                break;
            case 13:
                get_out_degree();
                break;
            case 14:
                get_number_of_vertices();
                break;
            case 15:
                get_number_of_edges();
                break;
            case 16:
                print_list_of_vertices();
                break;
            case 17:
                print_outbound_neighbours();
                break;
            case 18:
                print_inbound_neighbours();
                break;
            case 19:
                print_list_of_edges();
                break;
            case 20:
                return;
        }
    }
}






