package com.example.Books.Repository;

import com.example.Books.Model.Stock;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {


    Page<Stock> getStocksByBookId(Long bookID, Pageable pageable);

    Page<Stock> getStocksByStoreId(Long storeID, Pageable pageable);


    Stock getStockByBookIdAndStoreId(Long bookID, Long storeID);

}
