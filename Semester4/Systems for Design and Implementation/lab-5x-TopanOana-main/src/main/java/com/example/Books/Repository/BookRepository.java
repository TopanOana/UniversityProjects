package com.example.Books.Repository;

import com.example.Books.Model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book,Long>, JpaSpecificationExecutor<Book> {

    Page<Book> findBooksByRatingGreaterThan(Double rating_gt, Pageable pageable);

    Page<Book> findByOrderByTitleAsc(Pageable pageable);
    Page<Book> findByOrderByTitleDesc(Pageable pageable);

    Page<Book> findByOrderByAuthorAsc(Pageable pageable);
    Page<Book> findByOrderByAuthorDesc(Pageable pageable);

    Page<Book> findByOrderByNrPagesAsc(Pageable pageable);
    Page<Book> findByOrderByNrPagesDesc(Pageable pageable);

    Page<Book> findByOrderByRatingAsc(Pageable pageable);
    Page<Book> findByOrderByRatingDesc(Pageable pageable);

    Page<Book> findAllByOrderById(Pageable pageable);

    Page<Book> findBooksByTitleContainsIgnoreCase(String input, Pageable pageable);
    Page<Book> findBooksByTitleStartsWithIgnoreCase(String input, Pageable pageable);



}
