//
// Created by Oana on 3/29/2022.
//

#pragma once


#include "Graph.h"

class UserInterface {
private:
    Graph graph;
public:
    UserInterface();

    void print_menu();

    void create_an_empty_graph();

    void create_a_graph_with_n_vertices();

    void create_a_graph_with_n_vertices_and_m_random_edges();

    ///TO-DO READ GRAPH/WRITE GRAPH TO FILE

    void add_vertex();

    void add_edge();

    void remove_vertex();

    void remove_edge();

    void get_cost_of_an_edge();

    void set_cost_of_an_edge();

    void get_in_degree();

    void get_out_degree();

    void get_number_of_vertices();

    void get_number_of_edges();

    void print_list_of_vertices();

    void print_outbound_neighbours();

    void print_inbound_neighbours();

    void print_list_of_edges();

    void start();

    void read_from_file();

    void write_to_file();


    void lab_4_scheduling_problem();
};


