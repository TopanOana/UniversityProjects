package com.example.Books.Service;

import com.example.Books.Exception.StockNotFoundException;
import com.example.Books.Exception.StockValidationException;
import com.example.Books.Model.Book;
import com.example.Books.Model.DTO.StockDTO;
import com.example.Books.Model.Stock;
import com.example.Books.Model.Store;
import com.example.Books.Model.UserInfo;
import com.example.Books.Repository.StockRepository;
import com.example.Books.Repository.UserInfoRepository;
import com.example.Books.Validation.ValidatorStock;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public StockService(StockRepository stockRepository){
        this.stockRepository=stockRepository;
    }

    public List<Stock> getAllStocks(){
        return stockRepository.findAll();
    }

    public Stock getStockByID(Long id){
       return stockRepository.findById(id).orElseThrow(() -> new StockNotFoundException(id));
    }

    public Stock addStockToRepository(Stock newStock){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserInfo userInfo = userInfoRepository.findByUsername(username).get();
        newStock.setUser(userInfo);
        ValidatorStock validatorStock = new ValidatorStock(stockRepository);
        validatorStock.validate(newStock);
        return stockRepository.save(newStock);
    }

    public Stock updateStockInRepository(Long id, Stock updatedStock){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserInfo userInfo = userInfoRepository.findByUsername(username).get();
        Stock stockaux = stockRepository.findById(id).get();
        if (userInfo.getRoles().equals("ADMIN") || userInfo.getRoles().equals("MODERATOR")
            || (username.equals(stockaux.getUser().getUsername()))){
            if(updatedStock.getQuantity()<1 || updatedStock.getQuantity()>1000)
                throw new StockValidationException("stock quantity invalid (quantity<1 || quantity>1000");
            return stockRepository.findById(id).map(stock->{
                stock.setBook(updatedStock.getBook());
                stock.setStore(updatedStock.getStore());
                stock.setQuantity(updatedStock.getQuantity());
                return stockRepository.save(stock);
            }).orElseGet(() -> {
                updatedStock.setId(id);
                return stockRepository.save(updatedStock);
            });
        }
        else
            throw new RuntimeException("bad request: user cannot update stock");

    }

    public void deleteStockInRepository(Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserInfo userInfo = userInfoRepository.findByUsername(username).get();
        Stock stockaux = stockRepository.findById(id).get();
        if (userInfo.getRoles().equals("ADMIN") || userInfo.getRoles().equals("MODERATOR")
                || (username.equals(stockaux.getUser().getUsername()))) {
            stockRepository.deleteById(id);
        }
        else
            throw new RuntimeException("bad request: user cannot delete stock");
    }

    public Page<StockDTO> getStockWithBookID(Long bookID, int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        PageRequest pageable = PageRequest.of(page, size);
//        System.out.println("got to stock service");
//        return stockRepository.findAll().stream().filter(obj -> Objects.equals(obj.getStore().getId(), storeID)).collect(Collectors.toList());
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> stocksStoreCQ = criteriaBuilder.createQuery(Tuple.class);
        Root<Stock> stockRoot = stocksStoreCQ.from(Stock.class);

        Join<Stock, UserInfo> join = stockRoot.join("user", JoinType.INNER);

        stocksStoreCQ.multiselect(
                stockRoot.get("id").alias("id"),
                stockRoot.get("store").alias("store"),
                stockRoot.get("book").alias("book"),
                stockRoot.get("quantity").alias("quantity"),
                join.get("username").alias("username")
        ).groupBy(
                stockRoot.get("id"),
                stockRoot.get("store"),
                stockRoot.get("book"),
                stockRoot.get("quantity"),
                join.get("username")
        ).where(criteriaBuilder.equal(stockRoot.get("book").get("id"),bookID));
        TypedQuery<Tuple> typedQuery = entityManager.createQuery(stocksStoreCQ);
        List<StockDTO> results = typedQuery.setFirstResult(page*size)
                .setMaxResults(size)
                .getResultList()
                .stream()
                .map(row->{
                    return new StockDTO((Long)row.get("id"),
                            (Store)row.get("store"),
                            (Book)row.get("book"),
                            (int)row.get("quantity"),
                            (String)row.get("username"));
                }).collect(Collectors.toList());
        long total = results.size();

        return new PageImpl<>(results, pageable, total);
    }

    public Page<StockDTO> getStockWithStoreID(Long storeID, int page, int size){
        PageRequest pageable = PageRequest.of(page, size);
//        System.out.println("got to stock service");
//        return stockRepository.findAll().stream().filter(obj -> Objects.equals(obj.getStore().getId(), storeID)).collect(Collectors.toList());
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> stocksStoreCQ = criteriaBuilder.createQuery(Tuple.class);
        Root<Stock> stockRoot = stocksStoreCQ.from(Stock.class);

        Join<Stock, UserInfo> join = stockRoot.join("user", JoinType.INNER);

        stocksStoreCQ.multiselect(
                stockRoot.get("id").alias("id"),
                stockRoot.get("store").alias("store"),
                stockRoot.get("book").alias("book"),
                stockRoot.get("quantity").alias("quantity"),
                join.get("username").alias("username")
        ).groupBy(
                stockRoot.get("id"),
                stockRoot.get("store"),
                stockRoot.get("book"),
                stockRoot.get("quantity"),
                join.get("username")
        ).where(criteriaBuilder.equal(stockRoot.get("store").get("id"),storeID));
        TypedQuery<Tuple> typedQuery = entityManager.createQuery(stocksStoreCQ);
        List<StockDTO> results = typedQuery.setFirstResult(page*size)
                .setMaxResults(size)
                .getResultList()
                .stream()
                .map(row->{
                    return new StockDTO((Long)row.get("id"),
                            (Store)row.get("store"),
                            (Book)row.get("book"),
                            (int)row.get("quantity"),
                            (String)row.get("username"));
                }).collect(Collectors.toList());
        long total = results.size();

        return new PageImpl<>(results, pageable, total);

    }

    public Stock toUser(Long stockID, UserInfo userInfo){
        Stock stock = stockRepository.findById(stockID).get();
        stock.setUser(userInfo);
        stockRepository.save(stock);
        return stock;
    }


}
