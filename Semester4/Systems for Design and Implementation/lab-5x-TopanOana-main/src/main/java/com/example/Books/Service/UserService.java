package com.example.Books.Service;

import com.example.Books.Model.*;
import com.example.Books.Model.DTO.UserInfoDTO;
import com.example.Books.Model.DTO.UserStatDTO;
import com.example.Books.Repository.UserInfoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PersistenceContext
    private EntityManager entityManager;

    public String addUser(UserInfo userInfo){
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));

        String confirmCode = RandomStringUtils.random(10,true, false);

        userInfo.setConfirmationCode(confirmCode);
        userInfo.setConfirmCodeSend(LocalDateTime.now());

        userInfoRepository.save(userInfo);
        return userInfo.getConfirmationCode();
    }

    public String verifyUser(UserInfo userInfo, String confirmationCode){
        UserInfo user = userInfoRepository.findByUsername(userInfo.getUsername()).get();
        if (confirmationCode.equals(user.getConfirmationCode()) && Duration.between(user.getConfirmCodeSend(), LocalDateTime.now()).toMinutes()<=10){
            userInfo.setVerified(true);
            user.setVerified(true);
            userInfoRepository.save(user);
            return "User verified";
        }
        else{
            return "User not verified";
        }
    }

    public UserService(UserInfoRepository userInfoRepository, PasswordEncoder passwordEncoder) {
        this.userInfoRepository = userInfoRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public UserInfo getUserByUsername(String username){
        return userInfoRepository.findByUsername(username).get();
    }

    public UserInfo getUserByID(Long id){
        return userInfoRepository.findById(id).get();
    }

    public UserInfoDTO getUserInfoDTO(Long id){
        UserInfo userInfo = userInfoRepository.findById(id).get();
        UserInfoDTO userInfoDTO = new UserInfoDTO(userInfo.getUsername(),
                userInfo.getEmail(),
                userInfo.getBio(),
                userInfo.getLocation(),
                userInfo.getAge());
        return userInfoDTO;
    }

    public List<UserInfo> gimmeAllDemBoys(){
        return this.userInfoRepository.findAll();
    }

    public Page<UserInfo> getAllUsers(int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        return userInfoRepository.findAll(pageRequest);
    }

    public UserInfo updatedUser(String roles, Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserInfo userInfo = userInfoRepository.findByUsername(username).get();
        if (userInfo.getRoles().equals("ADMIN")){
            UserInfo toUpdate = userInfoRepository.findById(id).get();
            if (roles.equals("ADMIN") || roles.equals("MODERATOR") || roles.equals("USER")){
                toUpdate.setRoles(roles);
                return userInfoRepository.save(toUpdate);
            }
            else
                throw new RuntimeException("bad request: role doesn't exist");
        }
        else throw new RuntimeException("bad request: user cannot update other users' roles");
    }

    public UserStatDTO getUserStats(String username){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> userBooksCQ = criteriaBuilder.createQuery(Long.class);
        Root<Book> books = userBooksCQ.from(Book.class);
        Join<Book, UserInfo> bookUserInfoJoin = books.join("user", JoinType.INNER);
        userBooksCQ.select(criteriaBuilder.countDistinct(books.get("id")))
                .where(criteriaBuilder.equal(bookUserInfoJoin.get("username"), username));
        TypedQuery<Long> booksResult = entityManager.createQuery(userBooksCQ);
        Long nrBooks = booksResult.getSingleResult();

        CriteriaQuery<Long> userEmployeesCQ = criteriaBuilder.createQuery(Long.class);
        Root<Employee> employees = userEmployeesCQ.from(Employee.class);
        Join<Employee, UserInfo> employeeUserInfoJoin = employees.join("user", JoinType.INNER);
        userEmployeesCQ.select(criteriaBuilder.countDistinct(employees.get("id")))
                .where(criteriaBuilder.equal(employeeUserInfoJoin.get("username"), username));
        TypedQuery<Long> employeesResult = entityManager.createQuery(userEmployeesCQ);
        Long nrEmployees = employeesResult.getSingleResult();

        CriteriaQuery<Long> userStoresCQ = criteriaBuilder.createQuery(Long.class);
        Root<Store> stores = userStoresCQ.from(Store.class);
        Join<Store, UserInfo> storesUserJoin = stores.join("user", JoinType.INNER);
        userStoresCQ.select(criteriaBuilder.countDistinct(stores.get("id")))
                .where(criteriaBuilder.equal(storesUserJoin.get("username"), username));
        TypedQuery<Long> storesResult = entityManager.createQuery(userStoresCQ);
        Long nrStores = storesResult.getSingleResult();

        CriteriaQuery<Long> userStocksCQ = criteriaBuilder.createQuery(Long.class);
        Root<Stock> stocks = userStocksCQ.from(Stock.class);
        Join<Stock, UserInfo> stockUserJoin = stocks.join("user", JoinType.INNER);
        userStocksCQ.select(criteriaBuilder.countDistinct(stocks.get("id")))
                .where(criteriaBuilder.equal(stockUserJoin.get("username"), username));
        TypedQuery<Long> stocksResult = entityManager.createQuery(userStocksCQ);
        Long nrStocks = stocksResult.getSingleResult();


        UserStatDTO toReturn = new UserStatDTO(nrBooks,nrEmployees,nrStores,nrStocks);
        return toReturn;
    }
}
