package com.example.Books.Service;

import com.example.Books.Model.*;
import com.example.Books.Model.DTO.BookStockDTO;
import com.example.Books.Model.DTO.StoreStockDTO;
import com.example.Books.Repository.BookRepository;
import com.example.Books.Repository.StoreRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatService {
    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private BookRepository bookRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public StatService(StoreRepository repo, BookRepository bookRepository) {
        this.storeRepository = repo;
        this.bookRepository = bookRepository;
    }



     public List<StoreStockDTO> getAllStoresByNumberOfBooks(){
        List<StoreStockDTO> toSort = storeRepository.findAll().stream().map(this::convertDataIntoStoreStockDTO).collect(Collectors.toList());
        Collections.sort(toSort);
        return toSort;
    }

    public StoreStockDTO convertDataIntoStoreStockDTO(Store store){
        StoreStockDTO storeStockDTO = new StoreStockDTO();

        storeStockDTO.setStoreID(store.getId());
        storeStockDTO.setStoreName(store.getStoreName());
        storeStockDTO.setAddress(store.getAddress());
        storeStockDTO.setContactNumber(store.getContactNumber());
        storeStockDTO.setOpeningHour(store.getOpeningHour());
        storeStockDTO.setClosingHour(store.getClosingHour());

        storeStockDTO.setQuantity(store.getStocks().stream().map(stock -> {return stock.getQuantity();}).reduce(0, (a,b) -> a+b));

        return storeStockDTO;
    }

    BookStockDTO convertDataIntoBookStockDTO(Book book){
        BookStockDTO bookStockDTO = new BookStockDTO();

        bookStockDTO.setBookID(book.getId());
        bookStockDTO.setTitle(book.getTitle());
        bookStockDTO.setAuthor(book.getAuthor());
        bookStockDTO.setRating(book.getRating());
        bookStockDTO.setNrPages(book.getNrPages());
        bookStockDTO.setGenre(book.getGenre());

        bookStockDTO.setQuantity(book.getStocks().stream().map(stock -> {return stock.getQuantity();}).reduce(0, (a,b) -> a+b));

        return bookStockDTO;
    }

    public List<BookStockDTO> getAllBooksByNumber(){
        List<BookStockDTO> toSort = bookRepository.findAll().stream().map(this::convertDataIntoBookStockDTO).collect(Collectors.toList());
        Collections.sort(toSort);
        return toSort;
    }

    public Page<BookStockDTO> getBooksSortedByStocksQuantity(int page, int size)
    {
        PageRequest pageable = PageRequest.of(page, size);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Tuple> booksQuantityCQ = criteriaBuilder.createQuery(Tuple.class);
        Root<Stock> stocks = booksQuantityCQ.from(Stock.class);

        booksQuantityCQ.multiselect(
                        stocks.get("book").get("id").alias("book_id"),
                        criteriaBuilder.sum(criteriaBuilder.coalesce(stocks.get("quantity"),0)).alias("quantity")
                ).groupBy(stocks.get("book").get("id"))
                .orderBy(criteriaBuilder.desc(criteriaBuilder.sum(criteriaBuilder.coalesce(stocks.get("quantity"),0))));

        TypedQuery<Tuple> typedQuery = entityManager.createQuery(booksQuantityCQ);
        List<BookStockDTO> results = typedQuery.setFirstResult(pageable.getPageNumber()* pageable.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .getResultList()
                .stream()
                .map (row ->{
                    CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
                    Root<Book> book = criteriaQuery.from(Book.class);
                    criteriaQuery.select(book).where(criteriaBuilder.equal(book.get("id"), row.get("book_id")));
                    Book aux_book = entityManager.createQuery(criteriaQuery).getSingleResult();
                    return new BookStockDTO(aux_book.getId(), aux_book.getTitle(), aux_book.getAuthor(), aux_book.getNrPages(), aux_book.getRating(), aux_book.getGenre(), (Integer)row.get("quantity") );
                }).toList();
        CriteriaQuery<Long> countCQ = criteriaBuilder.createQuery(Long.class);
        Root<Stock> book_count = countCQ.from(Stock.class);
        countCQ.select(criteriaBuilder.countDistinct(book_count.get("book").get("id")));
        long total = entityManager.createQuery(countCQ).getSingleResult();

        return new PageImpl<>(results, pageable, total);
    }

    public Page<StoreStockDTO> getStoresSortedByNumberOfBooks(int page, int size){
        PageRequest pageable = PageRequest.of(page, size);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Tuple> storesQuantityCQ = criteriaBuilder.createQuery(Tuple.class);
        Root<Stock> stocks = storesQuantityCQ.from(Stock.class);


        storesQuantityCQ.multiselect(
                stocks.get("store").get("id").alias("store_id"),
                criteriaBuilder.sum(criteriaBuilder.coalesce(stocks.get("quantity"),0)).alias("quantity")
        ).groupBy(stocks.get("store").get("id"))
                .orderBy(criteriaBuilder.desc(criteriaBuilder.sum(criteriaBuilder.coalesce(stocks.get("quantity"),0))));

        TypedQuery<Tuple> typedQuery = entityManager.createQuery(storesQuantityCQ);
        List<StoreStockDTO> results = typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .getResultList()
                .stream()
                .map (row ->{
                    CriteriaQuery<Store> criteriaQuery = criteriaBuilder.createQuery(Store.class);
                    Root<Store> store = criteriaQuery.from(Store.class);
                    criteriaQuery.select(store).where(criteriaBuilder.equal(store.get("id"), row.get("store_id")));
                    Store aux_store = entityManager.createQuery(criteriaQuery).getSingleResult();
                    return new StoreStockDTO(aux_store.getId(), aux_store.getStoreName(), aux_store.getAddress(), aux_store.getContactNumber(), aux_store.getOpeningHour(), aux_store.getClosingHour(), (Integer)row.get("quantity") );
                }).toList();
        CriteriaQuery<Long> countCQ = criteriaBuilder.createQuery(Long.class);
        Root<Stock> store_count= countCQ.from(Stock.class);
        countCQ.select(criteriaBuilder.countDistinct(store_count.get("store").get("id")));
        long total = entityManager.createQuery(countCQ).getSingleResult();

        return new PageImpl<>(results, pageable, total);

    }


}
