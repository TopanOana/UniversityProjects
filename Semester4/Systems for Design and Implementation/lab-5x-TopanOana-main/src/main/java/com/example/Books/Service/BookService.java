package com.example.Books.Service;

import com.example.Books.Exception.BookNotFoundException;
import com.example.Books.Exception.BookValidationException;
import com.example.Books.Model.*;
import com.example.Books.Model.DTO.BookCountDTO;
import com.example.Books.Repository.BookRepository;
import com.example.Books.Repository.UserInfoRepository;
import com.example.Books.Validation.ValidatorBook;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    @Autowired
    private BookRepository repository;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @PersistenceContext
    private EntityManager entityManager;


    public BookService(BookRepository repository){
        this.repository=repository;
    }

    public Page<BookCountDTO> getAllBooks(int page, int size){
        /*
        returns all books in the repo
         */
        return getBooksWithQuantity(page, size);
    }


    public Book getBookByID(Long id){
        /*
        gets a specific book from the repo with an id
         */
        return repository.findById(id).orElseThrow(()->new BookNotFoundException(id));
    }

    public Book addBookToRepository(Book newBook) throws BookValidationException {
        /*
        adds a book to the repository
         */

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserInfo userInfo =   userInfoRepository.findByUsername(username).get();
        newBook.setUser(userInfo);

        ValidatorBook validatorBook = new ValidatorBook();
        validatorBook.validate(newBook);
        return repository.save(newBook);
    }


    public Book updateBookInRepository(Long id, Book updatedBook){
        /*
        updating a book or adding a new one with a specific id
        i just used a lot of setters and getters
         */
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserInfo userInfo =   userInfoRepository.findByUsername(username).get();
        Book bookmmid = repository.findById(id).get();
        if ((bookmmid.getUser().getUsername().equals(username) && userInfo.getRoles().equals("USER")) || (userInfo.getRoles().equals("ADMIN") || userInfo.getRoles().equals("MODERATOR"))){
            ValidatorBook validatorBook = new ValidatorBook();
            validatorBook.validate(updatedBook);
            return repository.findById(id).map(book -> {
                book.setTitle(updatedBook.getTitle());
                book.setAuthor(updatedBook.getAuthor());
                book.setNrPages(updatedBook.getNrPages());
                book.setRating(updatedBook.getRating());
                book.setGenre(updatedBook.getGenre());
                return repository.save(book);
            }).orElseGet(() -> {
                updatedBook.setId(id);
                return repository.save(updatedBook);
            });
        }
        else
            throw new RuntimeException("bad request: user not allowed to update");


    }


    public void deleteBookInRepository(Long id){
        /*
        deletes a book in the repository
         */
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserInfo userInfo =   userInfoRepository.findByUsername(username).get();
        Book bookmmid = repository.findById(id).get();
        if ((bookmmid.getUser().getUsername().equals(username) && userInfo.getRoles().equals("USER")) || (userInfo.getRoles().equals("ADMIN") || userInfo.getRoles().equals("MODERATOR"))) {
            repository.deleteById(id);
        }
        else
            throw new RuntimeException("bad request: user not allowed to delete");
    }

    public Page<BookCountDTO> getBooksWithRatingGreaterThan(double rating, int page, int size){
        PageRequest pageable = PageRequest.of(page, size);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> booksQuantityCQ = criteriaBuilder.createQuery(Tuple.class);
        Root<Book> books = booksQuantityCQ.from(Book.class);
        Join<Book, Stock> join1 = books.join("stocks", JoinType.LEFT);
        Join<Book, UserInfo> join2 = books.join("user", JoinType.INNER);
        booksQuantityCQ.multiselect(
                books.get("id").alias("id"),
                books.get("title").alias("title"),
                books.get("author").alias("author"),
                books.get("nrPages").alias("nrPages"),
                books.get("rating").alias("rating"),
                books.get("genre").alias("genre"),
                criteriaBuilder.coalesce(criteriaBuilder.sum(join1.get("quantity")), 0).alias("count"),
                join2.get("username").alias("username")
        ).groupBy(
                books.get("id"),
                books.get("title"),
                books.get("author"),
                books.get("nrPages"),
                books.get("rating"),
                books.get("genre"),
                join2.get("username")
        ).where(criteriaBuilder.greaterThan(books.get("rating"),rating));
        TypedQuery<Tuple> typedQuery = entityManager.createQuery(booksQuantityCQ);
        List<BookCountDTO> results = typedQuery.setFirstResult(pageable.getPageNumber()* pageable.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .getResultList()
                .stream()
                .map(row->{
                    return new BookCountDTO((Long)row.get("id"), (String)row.get("title"),
                            (String)row.get("author"),
                            (int) row.get("nrPages"),
                            (double)row.get("rating"),
                            (String)row.get("genre"), (int)row.get("count"),
                            (String) row.get("username"));
                })
                .collect(Collectors.toList());

        long total = results.size();

        return new PageImpl<>(results, pageable, total);
    }

    public Page<BookCountDTO> getBooksSorted(int page, int size, String column, String order){
        PageRequest pageRequest = PageRequest.of(page, size);

        return findByOrderByColumn(pageRequest,column,order);
    }


    public Page<BookCountDTO> getBooksWithTitleInput(String input, int page, int size){
        PageRequest pageable = PageRequest.of(page, size);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> booksQuantityCQ = criteriaBuilder.createQuery(Tuple.class);
        Root<Book> books = booksQuantityCQ.from(Book.class);
        Join<Book, Stock> join1 = books.join("stocks", JoinType.LEFT);
        Join<Book, UserInfo> join2 = books.join("user", JoinType.INNER);
        booksQuantityCQ.multiselect(
                books.get("id").alias("id"),
                books.get("title").alias("title"),
                books.get("author").alias("author"),
                books.get("nrPages").alias("nrPages"),
                books.get("rating").alias("rating"),
                books.get("genre").alias("genre"),
                criteriaBuilder.coalesce(criteriaBuilder.sum(join1.get("quantity")), 0).alias("count"),
                join2.get("username").alias("username")
        ).groupBy(
                books.get("id"),
                books.get("title"),
                books.get("author"),
                books.get("nrPages"),
                books.get("rating"),
                books.get("genre"),
                join2.get("username")
        ).where(criteriaBuilder.like(books.get("title"),input+"%"));
        TypedQuery<Tuple> typedQuery = entityManager.createQuery(booksQuantityCQ);
        List<BookCountDTO> results = typedQuery.setFirstResult(pageable.getPageNumber()* pageable.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .getResultList()
                .stream()
                .map(row->{
                    return new BookCountDTO((Long)row.get("id"), (String)row.get("title"),
                            (String)row.get("author"),
                            (int) row.get("nrPages"),
                            (double)row.get("rating"),
                            (String)row.get("genre"), (int)row.get("count"),
                            (String) row.get("username"));
                })
                .collect(Collectors.toList());

        long total = results.size();

        return new PageImpl<>(results, pageable, total);

    }

    public Page<BookCountDTO> getBooksWithQuantity(int page, int size){
        PageRequest pageable = PageRequest.of(page, size);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> booksQuantityCQ = criteriaBuilder.createQuery(Tuple.class);
        Root<Book> books = booksQuantityCQ.from(Book.class);
        Join<Book, Stock> join1 = books.join("stocks", JoinType.LEFT);
        Join<Book, UserInfo> join2 = books.join("user", JoinType.INNER);
        booksQuantityCQ.multiselect(
                books.get("id").alias("id"),
                books.get("title").alias("title"),
                books.get("author").alias("author"),
                books.get("nrPages").alias("nrPages"),
                books.get("rating").alias("rating"),
                books.get("genre").alias("genre"),
                criteriaBuilder.coalesce(criteriaBuilder.sum(join1.get("quantity")), 0).alias("count"),
                join2.get("username").alias("username")
        ).groupBy(
                books.get("id"),
                books.get("title"),
                books.get("author"),
                books.get("nrPages"),
                books.get("rating"),
                books.get("genre"),
                join2.get("username")
        );
        TypedQuery<Tuple> typedQuery = entityManager.createQuery(booksQuantityCQ);
        List<BookCountDTO> results = typedQuery.setFirstResult(pageable.getPageNumber()* pageable.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .getResultList()
                .stream()
                .map(row->{
                    return new BookCountDTO((Long)row.get("id"), (String)row.get("title"),
                            (String)row.get("author"),
                            (int) row.get("nrPages"),
                            (double)row.get("rating"),
                            (String)row.get("genre"), (int)row.get("count"),
                            (String)row.get("username"));
                })
                .collect(Collectors.toList());
        CriteriaQuery<Long> countCQ = criteriaBuilder.createQuery(Long.class);
        Root<Book> book_count = countCQ.from(Book.class);
        countCQ.select(criteriaBuilder.countDistinct(book_count.get("id")));
        long total = entityManager.createQuery(countCQ).getSingleResult();

        return new PageImpl<>(results, pageable, total);

    }

    public Page<BookCountDTO> findByOrderByColumn(PageRequest pageable, String column, String order){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> booksQuantityCQ = criteriaBuilder.createQuery(Tuple.class);
        Root<Book> books = booksQuantityCQ.from(Book.class);
        Join<Book, Stock> join1 = books.join("stocks", JoinType.LEFT);
        Join<Book, UserInfo> join2 = books.join("user", JoinType.INNER);
        booksQuantityCQ.multiselect(
                books.get("id").alias("id"),
                books.get("title").alias("title"),
                books.get("author").alias("author"),
                books.get("nrPages").alias("nrPages"),
                books.get("rating").alias("rating"),
                books.get("genre").alias("genre"),
                criteriaBuilder.coalesce(criteriaBuilder.sum(join1.get("quantity")), 0).alias("count"),
                join2.get("username").alias("username")
        ).groupBy(
                books.get("id"),
                books.get("title"),
                books.get("author"),
                books.get("nrPages"),
                books.get("rating"),
                books.get("genre"),
                join2.get("username")
        );
        if(order.equalsIgnoreCase("asc"))
            booksQuantityCQ.orderBy(criteriaBuilder.asc(books.get(column)));
        else
            booksQuantityCQ.orderBy(criteriaBuilder.desc(books.get(column)));
        TypedQuery<Tuple> typedQuery = entityManager.createQuery(booksQuantityCQ);
        List<BookCountDTO> results = typedQuery.setFirstResult(pageable.getPageNumber()* pageable.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .getResultList()
                .stream()
                .map(row->{
                    return new BookCountDTO((Long)row.get("id"), (String)row.get("title"),
                            (String)row.get("author"),
                            (int) row.get("nrPages"),
                            (double)row.get("rating"),
                            (String)row.get("genre"), (int)row.get("count"),
                            (String)row.get("username"));
                })
                .collect(Collectors.toList());
        CriteriaQuery<Long> countCQ = criteriaBuilder.createQuery(Long.class);
        Root<Book> book_count = countCQ.from(Book.class);
        countCQ.select(criteriaBuilder.countDistinct(book_count.get("id")));
        long total = entityManager.createQuery(countCQ).getSingleResult();

        return new PageImpl<>(results, pageable, total);
    }

    public Book toUser(Long bookId, UserInfo userInfo){
        Book book = repository.findById(bookId).get();
        book.setUser(userInfo);
        repository.save(book);
        return book;
    }





}
