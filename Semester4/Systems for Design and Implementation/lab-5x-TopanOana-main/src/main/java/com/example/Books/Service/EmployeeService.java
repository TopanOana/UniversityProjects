package com.example.Books.Service;

import com.example.Books.Model.DTO.BookCountDTO;
import com.example.Books.Model.DTO.EmployeeDTO;
import com.example.Books.Model.Employee;
import com.example.Books.Model.Store;
import com.example.Books.Model.UserInfo;
import com.example.Books.Repository.EmployeeRepository;
import com.example.Books.Repository.UserInfoRepository;
import com.example.Books.Validation.ValidatorEmployee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @PersistenceContext
    EntityManager entityManager;


    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Page<EmployeeDTO> getAllEmployees(int page, int size) {
        /*
        returns all the employees in the repository
         */
        PageRequest pageable = PageRequest.of(page, size);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> employeesCQ = criteriaBuilder.createQuery(Tuple.class);
        Root<Employee> employeeRoot = employeesCQ.from(Employee.class);
        Join<Employee, UserInfo> join = employeeRoot.join("user", JoinType.INNER);

        employeesCQ.multiselect(
                employeeRoot.get("id").alias("id"),
                employeeRoot.get("firstName").alias("firstName"),
                employeeRoot.get("lastName").alias("lastName"),
                employeeRoot.get("phoneNumber").alias("phoneNumber"),
                employeeRoot.get("salary").alias("salary"),
                employeeRoot.get("fullTime").alias("fullTime"),
                employeeRoot.get("description").alias("description"),
                join.get("username").alias("username")
        ).groupBy(
                employeeRoot.get("id"),
                employeeRoot.get("firstName"),
                employeeRoot.get("lastName"),
                employeeRoot.get("phoneNumber"),
                employeeRoot.get("salary"),
                employeeRoot.get("fullTime"),
                employeeRoot.get("description"),
                join.get("username")
        );
        TypedQuery<Tuple> typedQuery = entityManager.createQuery(employeesCQ);
        List<EmployeeDTO> results = typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .getResultList()
                .stream()
                .map(row -> {
                    return new EmployeeDTO((Long) row.get("id"), (String) row.get("firstName"),
                            (String) row.get("lastName"),
                            (String) row.get("phoneNumber"),
                            (int) row.get("salary"),
                            (boolean) row.get("fullTime"), (String) row.get("description"),
                            (String) row.get("username"));
                })
                .collect(Collectors.toList());
        long total = results.size();

        return new PageImpl<>(results, pageable, total);
    }

    public Employee addEmployeeToRepository(Employee newEmployee) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserInfo userInfo = userInfoRepository.findByUsername(username).get();
        newEmployee.setUser(userInfo);
        ValidatorEmployee validatorEmployee = new ValidatorEmployee();
        validatorEmployee.validate(newEmployee);
        return employeeRepository.save(newEmployee);
    }

    public Employee getStoreIDByEmployeeID(Long id) {
        /*
        gets an employee by id
         */
        return employeeRepository.findById(id).get();
    }

    public Employee updateEmployeeInRepository(Long id, Employee updatedEmployee) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserInfo userInfo = userInfoRepository.findByUsername(username).get();
        Employee employeeaux = employeeRepository.findById(id).get();
        if ((userInfo.getRoles().equals("ADMIN") || userInfo.getRoles().equals("MODERATOR")) || (userInfo.getRoles().equals("USER") && userInfo.getUsername().equals(employeeaux.getUser().getUsername()))){
            ValidatorEmployee validatorEmployee = new ValidatorEmployee();
            validatorEmployee.validate(updatedEmployee);
            return employeeRepository.findById(id).map(employee -> {
                employee.setFirstName(updatedEmployee.getFirstName());
                employee.setLastName(updatedEmployee.getLastName());
                employee.setPhoneNumber(updatedEmployee.getPhoneNumber());
                employee.setSalary(updatedEmployee.getSalary());
                employee.setFullTime(updatedEmployee.isFullTime());
                employee.setStore(updatedEmployee.getStore());
                employee.setDescription(updatedEmployee.getDescription());
                return employeeRepository.save(employee);
            }).orElseGet(() -> {
                updatedEmployee.setId(id);
                return employeeRepository.save(updatedEmployee);
            });
        }
        else
            throw new RuntimeException("bad request: user cannot update employee");

    }

    public void deleteEmployeeInRepository(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserInfo userInfo = userInfoRepository.findByUsername(username).get();
        Employee employeeaux = employeeRepository.findById(id).get();
        if ((userInfo.getRoles().equals("ADMIN") || userInfo.getRoles().equals("MODERATOR")) || (userInfo.getRoles().equals("USER") && userInfo.getUsername().equals(employeeaux.getUser().getUsername()))) {
            employeeRepository.deleteById(id);
        }
        else
            throw new RuntimeException("bad request: user cannot delete employee");
    }


    public Employee getEmployeeByID(Long id) {
        return this.employeeRepository.findById(id).get();
    }


    public Page<EmployeeDTO> getSortedBy(int page, int size, String column, String order) {
        PageRequest pageRequest = PageRequest.of(page, size);
        PageRequest pageable = PageRequest.of(page, size);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> employeesCQ = criteriaBuilder.createQuery(Tuple.class);
        Root<Employee> employeeRoot = employeesCQ.from(Employee.class);
        Join<Employee, UserInfo> join = employeeRoot.join("user", JoinType.INNER);

        employeesCQ.multiselect(
                employeeRoot.get("id").alias("id"),
                employeeRoot.get("firstName").alias("firstName"),
                employeeRoot.get("lastName").alias("lastName"),
                employeeRoot.get("phoneNumber").alias("phoneNumber"),
                employeeRoot.get("salary").alias("salary"),
                employeeRoot.get("fullTime").alias("fullTime"),
                employeeRoot.get("description").alias("description"),
                join.get("username").alias("username")
        ).groupBy(
                employeeRoot.get("id"),
                employeeRoot.get("firstName"),
                employeeRoot.get("lastName"),
                employeeRoot.get("phoneNumber"),
                employeeRoot.get("salary"),
                employeeRoot.get("fullTime"),
                employeeRoot.get("description"),
                join.get("username")
        );
        if (order.equalsIgnoreCase("asc"))
            employeesCQ.orderBy(criteriaBuilder.asc(employeeRoot.get(column)));
        else employeesCQ.orderBy(criteriaBuilder.desc(employeeRoot.get(column)));
        TypedQuery<Tuple> typedQuery = entityManager.createQuery(employeesCQ);
        List<EmployeeDTO> results = typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .getResultList()
                .stream()
                .map(row -> {
                    return new EmployeeDTO((Long) row.get("id"), (String) row.get("firstName"),
                            (String) row.get("lastName"),
                            (String) row.get("phoneNumber"),
                            (int) row.get("salary"),
                            (boolean) row.get("fullTime"), (String) row.get("description"),
                            (String) row.get("username"));
                })
                .collect(Collectors.toList());
        long total = results.size();

        return new PageImpl<>(results, pageable, total);
    }

    public Employee toUser(Long employeeID, UserInfo userInfo){
        Employee employee = employeeRepository.findById(employeeID).get();
        employee.setUser(userInfo);
        employeeRepository.save(employee);
        return employee;
    }
}


