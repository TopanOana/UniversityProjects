import math
import random
import copy
import numpy


def read_from_file(file_path):
    """
    The function reads a graph from the file
    the function creates and returns a graph from the file.
    :param file_path:
    :return:
    """
    file = open(file_path, "r")
    first_line = file.readline().split(maxsplit=1)
    n = int(first_line[0])
    m = int(first_line[1])
    graph = Graph(n)
    for edge in range(m):
        line = file.readline().split(maxsplit=2)
        vertex1 = int(line[0])
        vertex2 = int(line[1])
        cost = int(line[2])
        graph.add_edge(vertex1, vertex2, cost)
    file.close()
    return graph


def write_to_file(file_path, graph):
    """
    The function writes the graph to a file given.
    :param file_path:
    :param graph:
    :return:
    """
    file = open(file_path, "w")
    first_line = str(graph.get_number_of_vertices()) + " " + str(graph.get_number_of_edges()) + "\n"
    file.write(first_line)
    for vertex in graph.parse_vertices():
        for outbound in graph.parse_outbound_neighbours(vertex):
            line = str(vertex) + " " + str(outbound) + " " + str(graph.get_edge_cost(vertex, outbound)) + "\n"
            file.write(line)
    file.close()


"""
FOR LAB 3
    Write a program that, given a graph with costs and two vertices, 
    finds a lowest cost walk between the given vertices, or prints a message 
    if there are negative cost cycles accessible from the starting vertex. 
    The program will use a matrix defined as d[x,k]=the cost of the lowest 
    cost walk from s to x and of length at most k, where s is the starting vertex.
"""


def minimum_cost_path(graph, start_vertex, end_vertex):
    """

    :param graph:
    :param start_vertex:
    :param end_vertex:
    :return:
    """
    number_of_vertices = graph.get_number_of_vertices()
    number_of_edges = graph.get_number_of_edges()
    distance = [[math.inf for x in range(number_of_edges+1)] for y in range(number_of_vertices)]
    predecessor = {}

    for vertex in graph.parse_vertices():
        predecessor[vertex] = -1

    distance[start_vertex][0] = 0

    #k is the number of steps for the maximum length of the low cost walk
    for k in range(1, number_of_edges+1):
        for vertex in graph.parse_vertices():
            distance[vertex][k] = distance[vertex][k-1]
            for prev_vertex in graph.parse_inbound_neighbours(vertex):
                if distance[prev_vertex][k-1] + graph.get_edge_cost(prev_vertex, vertex) < distance[vertex][k]:
                    distance[vertex][k] = distance[prev_vertex][k-1] + graph.get_edge_cost(prev_vertex, vertex)
                    predecessor[vertex] = prev_vertex

    #check for negative cost cycle
    for vertex in graph.parse_vertices():
        minimum_cost = distance[vertex][number_of_edges]
        for prev_vertex in graph.parse_inbound_neighbours(vertex):
            if distance[prev_vertex][number_of_edges] + graph.get_edge_cost(prev_vertex, vertex) < minimum_cost:
                raise Exception("Negative cost cycle.")

   #gets the path of the lowest cost walk
    path = []
    vertex = end_vertex
    path.append(vertex)

    while predecessor[vertex] != -1:
        path.append(predecessor[vertex])
        vertex = predecessor[vertex]

    return distance[end_vertex][number_of_edges], path








class Graph:
    """
    A directed graph, represented as two maps,
    using inbound neighbours and outbound neighbours.
    In order to implement the cost function, a map for the costs
    has been added to the graph.
    For ease of use, a set of all the vertices has been added,
    making the implementation of certain functionalities simpler.
    """
    def __init__(self, nr_vertices=0, nr_edges=0):
        self.__vertices = set()
        self.__outbound = dict()
        self.__inbound = dict()
        self.__cost = dict()
        for i in range(nr_vertices):
            self.add_vertex(i)
        for i in range(nr_edges):
            vertex1 = random.randint(0, nr_vertices-1)
            vertex2 = random.randint(0, nr_vertices-1)
            while self.is_edge(vertex1, vertex2):
                vertex1 = random.randint(0, nr_vertices-1)
                vertex2 = random.randint(0, nr_vertices-1)
            cost = random.randint(0, 100)
            self.add_edge(vertex1, vertex2, cost)

    def add_vertex(self, vertex):
        """
        The function adds a vertex to the graph
        it adds it to the set of vertices (.__vertices)
        it adds an empty set to the dictionary for outbound
           neighbours with the key being the vertex
        it adds an empty set to the dictionary for inbound
           neighbours with the key being the vertex
        it raises an error if the vertex already exists in the graph
        :param vertex:
        :return:
        """
        if self.is_vertex(vertex):
            raise ValueError("Vertex already exists.")
        self.__vertices.add(vertex)
        self.__outbound[vertex] = set()
        self.__inbound[vertex] = set()

    def is_vertex(self, vertex):
        """
        The function checks whether a vertex is in the graph
        it searches for the vertex in the set of vertices.
        :param vertex:
        :return:
        """
        if vertex in self.__vertices:
            return True
        else:
            return False

    def add_edge(self, vertex1, vertex2, cost):
        """
        The function adds an edge and its cost to the graph.
        To the set of outbound neighbours of vertex1, vertex2 is added
        To the set of inbound neighbours of vertex2, vertex1 is added
        To the dictionary that maps the cost of each edge, a key-value pair is added.
           the key is a tuple of the vertices (vertex1, vertex2)
           the value is the cost
        The function raises an error if the edge already exists in the graph
         or if the vertices do not exist in the graph.
        :param vertex1:
        :param vertex2:
        :param cost:
        :return:
        """
        if self.is_edge(vertex1, vertex2):
            raise ValueError("Edge already exists.")
        if not self.is_vertex(vertex1) or not self.is_vertex(vertex2):
            raise ValueError("Invalid vertices.")
        self.__outbound[vertex1].add(vertex2)
        self.__inbound[vertex2].add(vertex1)
        self.__cost[(vertex1, vertex2)] = cost

    def is_edge(self, vertex1, vertex2):
        """
        The function checks whether the edge with the vertices vertex1 and vertex2 exists
        the function checks in the outbound neighbours of vertex1 for vertex2 and the
          inbound neighbours of vertex2 for vertex1
        :param vertex1:
        :param vertex2:
        :return:
        """
        if vertex2 in self.__outbound[vertex1]:
            return True
        else:
            return False

    def get_number_of_vertices(self):
        """
        The function returns the number of vertices in the graph.
        :return:
        """
        return len(self.__vertices)

    def get_out_degree(self, vertex):
        """
        The function returns the out degree of a vertex
        the function raises an error if the vertex doesn't exist in the graph
        :param vertex:
        :return:
        """
        if not self.is_vertex(vertex):
            raise ValueError("Invalid vertex.")
        return len(self.__outbound[vertex])

    def get_in_degree(self, vertex):
        """
        The function returns the in degree of a vertex
        the function raises an error if the vertex doesn't exist in the graph
        :param vertex:
        :return:
        """
        if not self.is_vertex(vertex):
            raise ValueError("Invalid vertex.")
        return len(self.__inbound[vertex])

    def get_edge_cost(self, vertex1, vertex2):
        """
        The function returns the cost of a given edge
        the cost is taken from the .__cost dictionary
        an error is raised if the edge doesn't exist.
        :param vertex1:
        :param vertex2:
        :return:
        """
        if (vertex1, vertex2) not in self.__cost:
            raise ValueError("Invalid edge.")
        return self.__cost[(vertex1, vertex2)]

    def set_edge_cost(self, vertex1, vertex2, new_cost):
        """
        The function sets the cost of an edge with the value specified.
        an error is raised if the edge doesn't exist.
        :param vertex1:
        :param vertex2:
        :param new_cost:
        :return:
        """
        if (vertex1, vertex2) not in self.__cost:
            raise ValueError("Invalid edge.")
        self.__cost[(vertex1, vertex2)] = new_cost

    def get_number_of_edges(self):
        """
        The function returns the number of edges in the graph
        it uses the cost dictionary since it holds all of the edges
        :return:
        """
        return len(self.__cost)

    def parse_vertices(self):
        """
        The function returns an iterator to the set of vertices.
        :return:
        """
        for vertex in self.__vertices:
            yield vertex

    def parse_inbound_neighbours(self, vertex):
        """
        The function returns an iterator to the set of inbound
        neighbours of the vertex.
        An error is raised if the vertex doesn't exist in the graph.
        :param vertex:
        :return:
        """
        if not self.is_vertex(vertex):
            raise ValueError("Invalid vertex.")
        for inbound in self.__inbound[vertex]:
            yield inbound

    def parse_outbound_neighbours(self, vertex):
        """
        The function returns an iterator to the set of outbound
        neighbours of the vertex.
        An error is raised if the vertex doesn't exist in the graph.
        :param vertex:
        :return:
        """
        if not self.is_vertex(vertex):
            raise ValueError("Invalid vertex.")
        for outbound in self.__outbound[vertex]:
            yield outbound

    def parse_edges(self):
        """
        The function returns an iterator to the set of edges
        with the cost.
        :return:
        """
        for key, value in self.__cost.items():
            yield key[0], key[1], value

    def remove_edge(self, vertex1, vertex2):
        """;
        The function removes an edge from the graph
        it deletes the entry in the cost dictionary
        it removes vertex2 from vertex1's outbound neighbours
        it removes vertex1 from vertex2's inbound neighbours
        An error is raised if the edge didn't exist in the graph.
        :param vertex1:
        :param vertex2:
        :return:
        """
        if not (vertex1, vertex2) in self.__cost:
            raise ValueError("Invalid edge.")
        del self.__cost[(vertex1, vertex2)]
        self.__outbound[vertex1].remove(vertex2)
        self.__inbound[vertex2].remove(vertex1)

    def remove_vertex(self, vertex):
        """
        The function removes a vertex from the graph.
        It removes all the edges for which the vertex is either an
        inbound node or an outbound one.
        then deletes the key-value item from the dictionaries (.__inbound, .__outbound)
         with the key= vertex
        lastly the vertex is removed from the list of vertices.
        An error is raised if the vertex didn't exist in the graph.
        :param vertex:
        :return:
        """
        if not self.is_vertex(vertex):
            raise ValueError("Invalid vertex.")
        deleteus = list()
        for outbound in self.__outbound[vertex]:
            deleteus.append(outbound)
        for outbound in deleteus:
            self.remove_edge(vertex, outbound)
        deleteus = list()
        for inbound in self.__inbound[vertex]:
            deleteus.append(inbound)
        for inbound in deleteus:
            self.remove_edge(inbound, vertex)
        del self.__outbound[vertex]
        del self.__inbound[vertex]
        self.__vertices.remove(vertex)

    def copy(self):
        """
        The function returns a deepcopy of the graph.
        :return:
        """
        return copy.deepcopy(self)
