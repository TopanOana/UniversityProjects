package com.example.Books.Repository;

import com.example.Books.Model.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store,Long> {
    Page<Store> findAllByOrderById(Pageable pageable);
    Page<Store> findStoresByStoreNameStartsWithIgnoreCase(String input, Pageable pageable);

    Page<Store> findByOrderByStoreNameAsc(Pageable pageable);
    Page<Store> findByOrderByStoreNameDesc(Pageable pageable);

    Page<Store> findByOrderByAddressAsc(Pageable pageable);
    Page<Store> findByOrderByAddressDesc(Pageable pageable);

}
