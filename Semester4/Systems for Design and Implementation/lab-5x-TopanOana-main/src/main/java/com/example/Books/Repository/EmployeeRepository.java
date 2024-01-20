package com.example.Books.Repository;

import com.example.Books.Model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{

    Page<Employee> findAllByOrderById(Pageable pageable);
    Page<Employee> findByOrderByFirstNameAsc(Pageable pageable);
    Page<Employee> findByOrderByFirstNameDesc(Pageable pageable);

    Page<Employee> findByOrderByLastNameAsc(Pageable pageable);
    Page<Employee> findByOrderByLastNameDesc(Pageable pageable);

    Page<Employee> findByOrderBySalaryAsc(Pageable pageable);
    Page<Employee> findByOrderBySalaryDesc(Pageable pageable);

}
