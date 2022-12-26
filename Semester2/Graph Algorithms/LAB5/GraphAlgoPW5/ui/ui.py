from domain.graph import *


class UserInterface:
    def __init__(self):
        self.graph = None

    def print_menu(self):
        print("1. Create an empty graph.")
        print("2. Create a graph with n vertices.")
        print("3. Create a graph with n vertices and m random edges.")
        print("4. Read a graph from a file.")
        print("5. Write graph to a file.")
        print("6. Add a vertex.")
        print("7. Add an edge.")
        print("8. Remove a vertex.")
        print("9. Remove an edge.")
        print("10. Get the cost of an edge.")
        print("11. Set the cost of an edge.")
        print("12. Get the in degree of a vertex.")
        print("13. Get the out degree of a vertex.")
        print("14. Get the number of vertices.")
        print("15. Get the number of edges.")
        print("16. Print the list of vertices.")
        print("17. Print the list of outbound neighbours of a vertex.")
        print("18. Print the list of inbound neighbours of a vertex.")
        print("19. Print the list of edges.")
        print("20. Exit")
        print("21. 2-Approximation vertex cover")

    def create_an_empty_graph(self):
        self.graph = Graph()
        print("Created empty graph!")
        print()

    def create_a_graph_with_n_vertices(self):
        n = int(input("Number of vertices n = "))
        self.graph = Graph(n)
        print("Created a graph with n vertices!")
        print()

    def create_a_graph_with_n_vertices_and_m_random_edges(self):
        n = int(input("Number of vertices n = "))
        m = int(input("Number of edges m = "))
        self.graph = Graph(n, m)
        print("Created a graph with n vertices and m random edges!")
        print()

    def read_a_graph_from_a_file(self):
        path = input("File to read from: ")
        self.graph = read_from_file(path)
        print("Read a graph from the file!")
        print()

    def write_graph_to_a_file(self):
        path = input("File to write to: ")
        write_to_file(path, self.graph)
        print("Graph written to file!")
        print()

    def add_a_vertex(self):
        vertex = int(input("Vertex to add: "))
        self.graph.add_vertex(vertex)
        print("Vertex added to graph!")
        print()

    def add_an_edge(self):
        vertex1 = int(input("Vertex 1: "))
        vertex2 = int(input("Vertex 2: "))
        cost = int(input("Cost: "))
        self.graph.add_edge(vertex1, vertex2, cost)
        print("Edge added to graph!")
        print()

    def remove_a_vertex(self):
        vertex = int(input("Vertex to remove: "))
        self.graph.remove_vertex(vertex)
        print("Vertex removed from graph!")
        print()

    def remove_an_edge(self):
        vertex1 = int(input("Vertex 1: "))
        vertex2 = int(input("Vertex 2: "))
        self.graph.remove_edge(vertex1, vertex2)
        print("Edge removed from graph!")
        print()

    def get_cost_of_an_edge(self):
        vertex1 = int(input("Vertex 1: "))
        vertex2 = int(input("Vertex 2: "))
        cost = self.graph.get_edge_cost(vertex1, vertex2)
        print("Cost of edge: "+ str(cost))
        print()

    def set_cost_of_an_edge(self):
        vertex1 = int(input("Vertex 1: "))
        vertex2 = int(input("Vertex 2: "))
        new_cost = int(input("New cost: "))
        self.graph.set_edge_cost(vertex1, vertex2, new_cost)
        print("New cost of edge set!")
        print()

    def get_in_degree_of_a_vertex(self):
        vertex = int(input("Vertex: "))
        in_degree = self.graph.get_in_degree(vertex)
        print("The in degree is: " + str(in_degree))
        print()

    def get_out_degree_of_a_vertex(self):
        vertex = int(input("Vertex: "))
        out_degree = self.graph.get_out_degree(vertex)
        print("The out degree is: " + str(out_degree))
        print()

    def get_number_of_vertices(self):
        number = self.graph.get_number_of_vertices()
        print(str(number) + " vertices.")
        print()

    def get_number_of_edges(self):
        number = self.graph.get_number_of_edges()
        print(str(number) + " edges.")
        print()

    def print_vertices(self):
        for vertex in self.graph.parse_vertices():
            print(vertex, end=" ")
        print()

    def print_outbound_neighbours_of_a_vertex(self):
        vertex = int(input("Vertex: "))
        for outbound in self.graph.parse_outbound_neighbours(vertex):
            print(outbound, end=" ")
        print()

    def print_inbound_neighbours_of_a_vertex(self):
        vertex = int(input("Vertex: "))
        for inbound in self.graph.parse_inbound_neighbours(vertex):
            print(inbound, end=" ")
        print()

    def print_edges(self):
        for edge in self.graph.parse_edges():
            vertex1 = edge[0]
            vertex2 = edge[1]
            cost = edge[2]
            print("("+str(vertex1)+","+str(vertex2)+") - "+str(cost))

    def start(self):
        print("Welcome to the Graph Algorithm Practical Work 1:")
        while True:
            try:
                self.print_menu()
                option = input("Choose an option: ")
                if option == "20":
                    return
                elif option == "1":
                    self.create_an_empty_graph()
                elif option == "2":
                    self.create_a_graph_with_n_vertices()
                elif option == "3":
                    self.create_a_graph_with_n_vertices_and_m_random_edges()
                elif option == "4":
                    self.read_a_graph_from_a_file()
                elif option == "5":
                    self.write_graph_to_a_file()
                elif option == "6":
                    self.add_a_vertex()
                elif option == "7":
                    self.add_an_edge()
                elif option == "8":
                    self.remove_a_vertex()
                elif option == "9":
                    self.remove_an_edge()
                elif option == "10":
                    self.get_cost_of_an_edge()
                elif option == "11":
                    self.set_cost_of_an_edge()
                elif option == "12":
                    self.get_in_degree_of_a_vertex()
                elif option == "13":
                    self.get_out_degree_of_a_vertex()
                elif option == "14":
                    self.get_number_of_vertices()
                elif option == "15":
                    self.get_number_of_edges()
                elif option == "16":
                    self.print_vertices()
                elif option == "17":
                    self.print_outbound_neighbours_of_a_vertex()
                elif option == "18":
                    self.print_inbound_neighbours_of_a_vertex()
                elif option == "19":
                    self.print_edges()
                elif option == "21":
                    self.print_vertex_cover()                    
                else:
                    print("Unavailable option!")
            except ValueError as ve:
                print(str(ve))

    def print_vertex_cover(self):
        cover = vertex_cover(self.graph)
        string = "A vertex cover of this graph is: "
        for edge in cover:
            print("Edge: " + str(edge[0])+" - " + str(edge[1]))
        print()


