import src.infrastructure.book_repo
from src.infrastructure.book_repo import *
from src.infrastructure.client_repo import *
from src.infrastructure.undo_redo_repo import *
from src.infrastructure.rental_repo import *
from src.ui.ui import *

"""
for text files
book_repo = BookRepositoryTextFile("books.txt")
client_repo = ClientRepositoryTextFile("clients.txt")
rental_repo = RentalRepositoryTextFile("rentals.txt")
undo_redo_repo = UndoRedoRepo()

book_service = BookService(book_repo, undo_redo_repo, src.infrastructure.book_repo.BookRepositoryTextFile)
client_service = ClientService(client_repo, undo_redo_repo, src.infrastructure.client_repo.ClientRepositoryTextFile)
rental_service = RentalService(rental_repo, book_repo, client_repo, undo_redo_repo,
                               src.infrastructure.rental_repo.RentalRepositoryTextFile)

"""
"""
for in memory repositories
book_repo = BookRepository()
client_repo = ClientRepository()
rental_repo = RentalRepository()
undo_redo_repo = UndoRedoRepo()

book_service = BookService(book_repo, undo_redo_repo, src.infrastructure.book_repo.BookRepository)
client_service = ClientService(client_repo, undo_redo_repo, src.infrastructure.client_repo.ClientRepository)
rental_service = RentalService(rental_repo, book_repo, client_repo, undo_redo_repo,
                               src.infrastructure.rental_repo.RentalRepository)
book_service.initialise_list()
client_service.initialise_list()
rental_service.initialise_list()
"""

"""
for bin files
book_repo = BookRepositoryBinFile("books.bin")
client_repo = ClientRepositoryBinFile("clients.bin")
rental_repo = RentalRepositoryBinFile("rentals.bin")
undo_redo_repo = UndoRedoRepo()

book_service = BookService(book_repo, undo_redo_repo, src.infrastructure.book_repo.BookRepositoryBinFile)
client_service = ClientService(client_repo, undo_redo_repo, src.infrastructure.client_repo.ClientRepositoryBinFile)
rental_service = RentalService(rental_repo, book_repo, client_repo, undo_redo_repo,
                               src.infrastructure.rental_repo.RentalRepositoryBinFile)
book_service.initialise_list()
client_service.initialise_list()
rental_service.initialise_list()

"""

with open("settings.properties", "r") as read:
    repo_type = read.readline().strip().split(" ")[2]

    book_repo_file = read.readline().strip().split(" ")[2]
    book_repo_file = book_repo_file[1:len(book_repo_file)-1]

    client_repo_file = read.readline().strip().split(" ")[2]
    client_repo_file = client_repo_file[1:len(client_repo_file)-1]

    rental_repo_file = read.readline().strip().split(" ")[2]
    rental_repo_file = rental_repo_file[1:len(rental_repo_file)-1]

undo_redo_repo = UndoRedoRepo()

if repo_type == "inmemory":
    book_repo = BookRepository()
    client_repo = ClientRepository()
    rental_repo = RentalRepository()

    book_service = BookService(book_repo, undo_redo_repo, src.infrastructure.book_repo.BookRepository)
    client_service = ClientService(client_repo, undo_redo_repo, src.infrastructure.client_repo.ClientRepository)
    rental_service = RentalService(rental_repo, book_repo, client_repo, undo_redo_repo,
                                   src.infrastructure.rental_repo.RentalRepository)

    book_service.initialise_list()
    client_service.initialise_list()
    rental_service.initialise_list()

elif repo_type == "textfiles":
    book_repo = BookRepositoryTextFile("books.txt")
    client_repo = ClientRepositoryTextFile("clients.txt")
    rental_repo = RentalRepositoryTextFile("rentals.txt")
    book_service = BookService(book_repo, undo_redo_repo, src.infrastructure.book_repo.BookRepositoryTextFile)
    client_service = ClientService(client_repo, undo_redo_repo, src.infrastructure.client_repo.ClientRepositoryTextFile)
    rental_service = RentalService(rental_repo, book_repo, client_repo, undo_redo_repo,
                                   src.infrastructure.rental_repo.RentalRepositoryTextFile)


elif repo_type == "binfiles":
    book_repo = BookRepositoryBinFile("books.bin")
    client_repo = ClientRepositoryBinFile("clients.bin")
    rental_repo = RentalRepositoryBinFile("rentals.bin")
    book_service = BookService(book_repo, undo_redo_repo, src.infrastructure.book_repo.BookRepositoryBinFile)
    client_service = ClientService(client_repo, undo_redo_repo, src.infrastructure.client_repo.ClientRepositoryBinFile)
    rental_service = RentalService(rental_repo, book_repo, client_repo, undo_redo_repo,
                                   src.infrastructure.rental_repo.RentalRepositoryBinFile)



ui = UI(undo_redo_repo, book_repo, client_repo, rental_repo, book_service, client_service, rental_service)
ui.start()
