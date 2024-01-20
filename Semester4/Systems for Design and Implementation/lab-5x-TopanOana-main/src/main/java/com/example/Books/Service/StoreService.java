package com.example.Books.Service;

import com.example.Books.Exception.StoreNotFoundException;
import com.example.Books.Exception.StoreValidationException;
import com.example.Books.Model.*;
import com.example.Books.Model.DTO.StoreCountDTO;
import com.example.Books.Repository.StoreRepository;
import com.example.Books.Repository.UserInfoRepository;
import com.example.Books.Validation.ValidatorStore;
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
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoreService {
    @Autowired
    private StoreRepository repository;

    @Autowired
    private UserInfoRepository userInfoRepository;
    @PersistenceContext
    EntityManager entityManager;

    public StoreService(StoreRepository repository){
        this.repository=repository;
    }


    public Page<StoreCountDTO> getAllStores(int page, int size){
        /*
        returns all stores in the repo
         */
        PageRequest pageable = PageRequest.of(page, size);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Tuple> storesQuantityCQ = criteriaBuilder.createQuery(Tuple.class);
        Root<Store> stores = storesQuantityCQ.from(Store.class);
        Join<Store, Stock> join = stores.join("stocks", JoinType.LEFT);
        Join<Store, UserInfo> join1 = stores.join("user", JoinType.INNER);
        Join<Store, Employee> join2 = stores.join("employees", JoinType.LEFT);

        storesQuantityCQ.multiselect(
                        stores.get("id").alias("id"),
                        stores.get("storeName").alias("storeName"),
                        stores.get("address").alias("address"),
                        stores.get("contactNumber").alias("contactNumber"),
                        stores.get("openingHour").alias("openingHour"),
                        stores.get("closingHour").alias("closingHour"),
                        criteriaBuilder.sum(criteriaBuilder.coalesce(join.get("quantity"),0)).alias("nrBooks"),
                        join1.get("username").alias("username"),
                        criteriaBuilder.count(criteriaBuilder.coalesce(join2.get("id"),0)).alias("nrEmployees")
                )
                .groupBy(
                        stores.get("id"),
                        stores.get("storeName"),
                        stores.get("address"),
                        stores.get("contactNumber"),
                        stores.get("openingHour"),
                        stores.get("closingHour"),
                        join1.get("username")
                );
        TypedQuery<Tuple> typedQuery = entityManager.createQuery(storesQuantityCQ);
        List<StoreCountDTO> results = typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .getResultList()
                .stream()
                .map (row ->{
                    return new StoreCountDTO((Long)row.get("id"),
                            (String)row.get("storeName"),
                            (String)row.get("address"),
                            (String)row.get("contactNumber"),
                            (int)row.get("openingHour"),
                            (int)row.get("closingHour"),
                            (String) row.get("username"),
                            (int)row.get("nrBooks"),
                            (Long)row.get("nrEmployees"));
                }).toList();
        CriteriaQuery<Long> countCQ = criteriaBuilder.createQuery(Long.class);
        Root<Store> store_count= countCQ.from(Store.class);
        countCQ.select(criteriaBuilder.countDistinct(store_count.get("id")));
        long total = entityManager.createQuery(countCQ).getSingleResult();

        return new PageImpl<>(results, pageable, total);

    }

    public List<Employee> getStoreEmployeesByID(Long id){
        /*
        gets a specific book from the repo with an id
         */

        return repository.findById(id).get().getEmployees().stream().collect(Collectors.toList());
    }

    public Store getStoreByID(Long id){
        return repository.findById(id).orElseThrow(() -> new StoreNotFoundException(id));
    }

    public Store addStoreToRepository(Store newStore) throws StoreValidationException {
        /*
        adds a store to the repository
         */
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserInfo userInfo = userInfoRepository.findByUsername(username).get();
        newStore.setUser(userInfo);
        ValidatorStore validatorStore = new ValidatorStore();
        validatorStore.validate(newStore);
        return repository.save(newStore);
    }

    public Store updateStoreInRepository(Long id, Store updatedStore){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserInfo userInfo = userInfoRepository.findByUsername(username).get();
        Store storeaux = repository.findById(id).get();
        if((userInfo.getRoles().equals("ADMIN") || userInfo.getRoles().equals("MODERATOR")) || ((userInfo.getRoles().equals("USER")) && storeaux.getUser().getUsername().equals(username))){
            ValidatorStore validatorStore = new ValidatorStore();
            validatorStore.validate(updatedStore);
            return repository.findById(id).map(store->{
                store.setStoreName(updatedStore.getStoreName());
                store.setAddress(updatedStore.getAddress());
                store.setContactNumber(updatedStore.getContactNumber());
                store.setOpeningHour(updatedStore.getOpeningHour());
                store.setClosingHour(updatedStore.getClosingHour());
                return repository.save(store);
            }).orElseGet(() ->{
                updatedStore.setId(id);
                return repository.save(updatedStore);
            });
        }
        else
            throw new RuntimeException("bad request: user cannot update store");


    }

    public void deleteStoreByID(Long id){
        /*
        deletes a store in the repository by id
         */
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserInfo userInfo = userInfoRepository.findByUsername(username).get();
        Store storeaux = repository.findById(id).get();
        if((userInfo.getRoles().equals("ADMIN") || userInfo.getRoles().equals("MODERATOR")) || ((userInfo.getRoles().equals("USER")) && storeaux.getUser().getUsername().equals(username))){
                repository.deleteById(id);
        }
        else
            throw new RuntimeException("bad request: user cannot delete store");
    }


    public Store addEmployeeToStore(Long id, Employee employee){
        return this.repository.findById(id).get().addEmployeeToStore(employee);
    }

    public Stock addStock(Long storeID, Stock stock){
        stock.setStore(getStoreByID(storeID));
        return repository.findById(storeID).get().addStockToStore(stock);
    }

    public List<Employee> addEmployeesToStore(Long storeID, List<Employee> employees){
        for (Employee e:employees){
            addEmployeeToStore(storeID, e);
        }
        return employees;
    }

    public Page<StoreCountDTO> getStoresWithNameLike(String input, int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        PageRequest pageable = PageRequest.of(page, size);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Tuple> storesQuantityCQ = criteriaBuilder.createQuery(Tuple.class);
        Root<Store> stores = storesQuantityCQ.from(Store.class);
        Join<Store, Stock> join = stores.join("stocks", JoinType.LEFT);
        Join<Store, UserInfo> join1 = stores.join("user", JoinType.INNER);
        Join<Store, Employee> join2 = stores.join("employees", JoinType.LEFT);


        storesQuantityCQ.multiselect(
                        stores.get("id").alias("id"),
                        stores.get("storeName").alias("storeName"),
                        stores.get("address").alias("address"),
                        stores.get("contactNumber").alias("contactNumber"),
                        stores.get("openingHour").alias("openingHour"),
                        stores.get("closingHour").alias("closingHour"),
                        criteriaBuilder.sum(criteriaBuilder.coalesce(join.get("quantity"),0)).alias("nrBooks"),
                        join1.get("username").alias("username"),
                        criteriaBuilder.count(criteriaBuilder.coalesce(join2.get("id"),0)).alias("nrEmployees")
                )
                .groupBy(
                        stores.get("id"),
                        stores.get("storeName"),
                        stores.get("address"),
                        stores.get("contactNumber"),
                        stores.get("openingHour"),
                        stores.get("closingHour"),
                        join1.get("username")
                )
                .where(criteriaBuilder.like(stores.get("storeName"), input+"%"));
        TypedQuery<Tuple> typedQuery = entityManager.createQuery(storesQuantityCQ);
        List<StoreCountDTO> results = typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .getResultList()
                .stream()
                .map (row ->{
                    return new StoreCountDTO((Long)row.get("id"),
                            (String)row.get("storeName"),
                            (String)row.get("address"),
                            (String)row.get("contactNumber"),
                            (int)row.get("openingHour"),
                            (int)row.get("closingHour"),
                            (String) row.get("username"),
                            (int)row.get("nrBooks"),
                            (Long)row.get("nrEmployees"));
                }).toList();
        long total = results.size();

        return new PageImpl<>(results, pageable, total);
    }

    public Page<StoreCountDTO> getStoresSorted(int page, int size, String column, String order){
        PageRequest pageable = PageRequest.of(page, size);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Tuple> storesQuantityCQ = criteriaBuilder.createQuery(Tuple.class);
        Root<Store> stores = storesQuantityCQ.from(Store.class);
        Join<Store, Stock> join = stores.join("stocks", JoinType.LEFT);

        Join<Store, UserInfo> join1 = stores.join("user", JoinType.INNER);
        Join<Store, Employee> join2 = stores.join("employees", JoinType.LEFT);

        storesQuantityCQ.multiselect(
                        stores.get("id").alias("id"),
                        stores.get("storeName").alias("storeName"),
                        stores.get("address").alias("address"),
                        stores.get("contactNumber").alias("contactNumber"),
                        stores.get("openingHour").alias("openingHour"),
                        stores.get("closingHour").alias("closingHour"),
                        criteriaBuilder.sum(criteriaBuilder.coalesce(join.get("quantity"),0)).alias("nrBooks"),
                        join1.get("username").alias("username"),
                        criteriaBuilder.count(criteriaBuilder.coalesce(join2.get("id"),0)).alias("nrEmployees")
                )
                .groupBy(
                        stores.get("id"),
                        stores.get("storeName"),
                        stores.get("address"),
                        stores.get("contactNumber"),
                        stores.get("openingHour"),
                        stores.get("closingHour"),
                        join1.get("username")
                );
        if(order.equalsIgnoreCase("asc"))
            storesQuantityCQ.orderBy(criteriaBuilder.asc(stores.get(column)));
        else storesQuantityCQ.orderBy(criteriaBuilder.desc(stores.get(column)));
        TypedQuery<Tuple> typedQuery = entityManager.createQuery(storesQuantityCQ);
        List<StoreCountDTO> results = typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .getResultList()
                .stream()
                .map (row ->{
                    return new StoreCountDTO((Long)row.get("id"),
                            (String)row.get("storeName"),
                            (String)row.get("address"),
                            (String)row.get("contactNumber"),
                            (int)row.get("openingHour"),
                            (int)row.get("closingHour"),
                            (String) row.get("username"),
                            (int)row.get("nrBooks"),
                            (Long)row.get("nrEmployees"));
                }).toList();
        CriteriaQuery<Long> countCQ = criteriaBuilder.createQuery(Long.class);
        Root<Store> store_count= countCQ.from(Store.class);
        countCQ.select(criteriaBuilder.countDistinct(store_count.get("id")));
        long total = entityManager.createQuery(countCQ).getSingleResult();

        return new PageImpl<>(results, pageable, total);
    }

    public Store toUser(Long storeID, UserInfo userInfo){
        Store store = repository.findById(storeID).get();
        store.setUser(userInfo);
        repository.save(store);
        return store;
    }


}
