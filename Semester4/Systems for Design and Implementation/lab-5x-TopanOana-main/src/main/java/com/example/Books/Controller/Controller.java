package com.example.Books.Controller;

//import com.example.Books.Exception.BookNotFoundException;
import com.example.Books.Exception.*;
import com.example.Books.Model.*;
//import com.example.Books.Repository.BookRepository;
import com.example.Books.Model.DTO.*;
import com.example.Books.Service.*;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api") //only for the server
public class Controller {

//    @Autowired
    @Autowired private final BookService bookService;

    @Autowired private final StoreService storeService;

    @Autowired private final EmployeeService employeeService;
    @Autowired private final StockService stockService;

    @Autowired private final StatService statService;
    @Autowired private final UserService userService;
    @Autowired private final JWTService jwtService;

    @Autowired private  final AuthenticationManager authenticationManager;

    @Autowired private final AdminService adminService;


    public Controller(StoreService storeService, BookService bookService, EmployeeService employeeService, StockService stockService, StatService statService, UserService userService, JWTService jwtService, AuthenticationManager authenticationManager, AdminService adminService) {
        this.bookService = bookService;
        this.storeService = storeService;
        this.employeeService = employeeService;
        this.stockService = stockService;
        this.statService = statService;
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.adminService = adminService;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/books")
    Page<BookCountDTO> getBooks(@Nullable @RequestParam("rating_gt") Double rating, @RequestParam int page, @RequestParam int size, @Nullable @RequestParam String column, @Nullable @RequestParam String order, @Nullable @RequestParam String input){
        /*
        the get mapping is for reading all the books in the repository or
        getting all the books in the repository with a rating greater than the one given
         */

        if(rating!=null)
            return bookService.getBooksWithRatingGreaterThan(rating,page,size);
        if(column!=null && order!=null)
            return bookService.getBooksSorted(page, size, column, order);
        if(input!=null)
            return bookService.getBooksWithTitleInput(input, page, size);
        return bookService.getAllBooks(page, size);
    }
    // end::get-aggregate-root[]

    @GetMapping("/books/totest")
    Page<BookCountDTO> testBook(@RequestParam int page, @RequestParam int size){
        return bookService.getBooksWithQuantity(page, size);
    }

    @GetMapping("/books/{id}")
    Book getBookByID(@PathVariable Long id){
        /*
        the get mapping that returns a book with a specific id in the repository
         */
        return bookService.getBookByID(id);
    }

    @PostMapping("/books")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR', 'USER')")
    Book addBook(@RequestBody Book newBook, @RequestHeader("Authorization") String authorizationHeader){
        /*
        the post mapping is for adding a new book to the repository
         */
        try{
            return bookService.addBookToRepository(newBook);
        }catch(BookValidationException ex)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }

    }

    @PutMapping("/books/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR', 'USER')")
    Book updateBook(@RequestBody Book updatedBook, @PathVariable Long id, @RequestHeader("Authorization") String authorizationHeader){
        /*
        the put mapping is for updating a book or adding a new one with a specific id
        i just used a lot of setters and getters
         */
        authorizationHeader = authorizationHeader.substring(7);
        try{
            String username = jwtService.extractUsername(authorizationHeader);

            return bookService.updateBookInRepository(id,updatedBook);
        }catch(BookValidationException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }

    }

    @DeleteMapping("/books/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR', 'USER')")
    void deleteBook(@PathVariable Long id,  @RequestHeader("Authorization") String authorizationHeader){
        /*
        the delete mapping is for removing a book from the repository
         */
        System.out.println("plang");
        authorizationHeader = authorizationHeader.substring(7);
        bookService.deleteBookInRepository(id);
    }



    @GetMapping("/stores")
    Page<StoreCountDTO> getAllStores(@Nullable @RequestParam String input, @RequestParam int page, @RequestParam int size, @Nullable @RequestParam String column, @Nullable @RequestParam String order){
        /*
        get mapping for reading all the stores in the repository
         */
        if(input!=null)
            return storeService.getStoresWithNameLike(input, page, size);
        if(column!=null && order!=null) {
            System.out.println("got to the sort");
            return storeService.getStoresSorted(page, size, column, order);
        }
        return storeService.getAllStores(page, size);

    }
//    @GetMapping("/stores/{id}")
//    List<Employee> getStoreEmployeesByID(@PathVariable Long id){
//        /*
//        gets a store with a specific id
//         */
//        return storeService.getStoreEmployeesByID(id);
//    }
    @GetMapping("/stores/{id}")
    Store getStoreByID(@PathVariable Long id){
        return storeService.getStoreByID(id);
    }



    @PostMapping("/stores")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR', 'USER')")
    Store addStore(@RequestBody Store newStore, @RequestHeader("Authorization") String authorizationHeader){
        /*
        post mapping for adding a new store to the repository
         */
        authorizationHeader = authorizationHeader.substring(7);
        try{
            return storeService.addStoreToRepository(newStore);
        }catch (StoreValidationException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }

    }



    @PutMapping("/stores/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR', 'USER')")
    Store updateStore(@RequestBody Store updatedStore, @PathVariable Long id, @RequestHeader("Authorization") String authorizationHeader){
        /*
        put mapping for updating a store or adding a new store with a specific id
         */
        authorizationHeader = authorizationHeader.substring(7);
        try{
            if (updatedStore!=null)
                return storeService.updateStoreInRepository(id,updatedStore);
            return null;
        }catch(StoreValidationException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }

    }


    @DeleteMapping("/stores/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR', 'USER')")
    void deleteStore(@PathVariable Long id, @RequestHeader("Authorization") String authorizationHeader){
        /*
        delete mapping for removing a store from the repository
         */
        authorizationHeader = authorizationHeader.substring(7);
        storeService.deleteStoreByID(id);
    }


    @GetMapping("/employees")
    Page<EmployeeDTO> getAllEmployees(@RequestParam int page, @RequestParam int size, @Nullable @RequestParam String column, @Nullable @RequestParam String order){
        if (column!=null && order!=null)
            return employeeService.getSortedBy(page, size, column, order);
        else
            return employeeService.getAllEmployees(page, size);

    }

    @GetMapping("/employees/{id}")
    Employee getEmployeeStore(@PathVariable Long id){
        return employeeService.getStoreIDByEmployeeID(id);
    }


    @PostMapping("/employees")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR', 'USER')")
    Employee addEmployee(@RequestBody Employee employee, @RequestParam("storeID") Long storeID, @RequestHeader("Authorization") String authorizationHeader){
        authorizationHeader = authorizationHeader.substring(7);
        try{
            employee.setStore(storeService.getStoreByID(storeID));
            return employeeService.addEmployeeToRepository(employee);
        }catch(EmployeeValidationException | StoreNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }

    }

    @PostMapping("/stores/{id}/employees")
    List<Employee> addMultipleEmployees(@RequestBody List<Employee> employees, @PathVariable Long id){
        Store store = storeService.getStoreByID(id);
        for(Employee e: employees) {
            e.setStore(store);
            employeeService.addEmployeeToRepository(e);
        }
        return employees;
    }

    @PutMapping("/employees/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR', 'USER')")
    Employee updateEmployee(@RequestBody Employee newEmployee, @RequestParam("storeID") Long storeID, @PathVariable Long id, @RequestHeader("Authorization") String authorizationHeader){
        authorizationHeader = authorizationHeader.substring(7);
        try{
            newEmployee.setStore(storeService.getStoreByID(storeID));
            return employeeService.updateEmployeeInRepository(id, newEmployee);
        }catch(EmployeeValidationException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(),ex);
        }

    }

    @DeleteMapping("/employees/{employeeId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR', 'USER')")
    void deleteEmployee(@PathVariable Long employeeId, @RequestHeader("Authorization") String authorizationHeader){
        authorizationHeader = authorizationHeader.substring(7);
        employeeService.deleteEmployeeInRepository(employeeId);
    }


    @GetMapping("/stocks")
    List<Stock> getAllStocks(){
        return stockService.getAllStocks();
    }

    @PostMapping("/stores/{id}/stock")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR', 'USER')")
    Stock addStock(@PathVariable Long id, @RequestBody Stock stock, @RequestHeader("Authorization") String authorizationHeader){
        authorizationHeader = authorizationHeader.substring(7);
        try{
            stock.setStore(storeService.getStoreByID(id));
            return this.stockService.addStockToRepository(stock);
        }catch(StockValidationException | StoreNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }


    }

    @GetMapping("/stores/{id}/stock")
    Page<StockDTO> getStocksFromStore (@PathVariable Long id, int page, int size){
//        return storeService.getStoreByID(id).getStocks();
//        System.out.println("got to controller");
        return stockService.getStockWithStoreID(id, page, size);
    }

    @GetMapping("/books/{id}/stock")
    Page<StockDTO> getStocksForBooks(@PathVariable Long id, int page, int size){
//        return bookService.getBookByID(id).getStocks();
        return stockService.getStockWithBookID(id, page, size);
    }

    @DeleteMapping("/stores/{id}/stock")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR', 'USER')")
    void deleteStockFromStore(@PathVariable Long id, @RequestParam("stockID") Long stockID, @RequestHeader("Authorization") String authorizationHeader){
        authorizationHeader = authorizationHeader.substring(7);
        this.stockService.deleteStockInRepository(stockID);
    }

    @PutMapping("/stores/{id}/stock/{stockID}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR', 'USER')")
    Stock updateStockInStore(@PathVariable Long id, @RequestBody Stock stock, @PathVariable Long stockID, @RequestHeader("Authorization") String authorizationHeader){
        authorizationHeader = authorizationHeader.substring(7);
        try{
            stock.setStore(storeService.getStoreByID(id));
            System.out.println("here i am ");
            return this.stockService.updateStockInRepository(stockID, stock);
        }catch(StockValidationException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }

    }

    @GetMapping("/stats/stores")
    Page<StoreStockDTO> getStatStoreStock(@RequestParam int page, @RequestParam int size){
//        return statService.getAllStoresByNumberOfBooks();
        return statService.getStoresSortedByNumberOfBooks(page,size);
    }

    @GetMapping("/stats/books")
    Page<BookStockDTO> getStatBookStock(@RequestParam int page, @RequestParam int size){
        return statService.getBooksSortedByStocksQuantity(page, size);
    }


    @GetMapping("/stocks/{id}")
    Stock getStock(@PathVariable Long id){
        return stockService.getStockByID(id);
    }


    @PostMapping("/users/register")
    public String addNewUser(@RequestBody UserInfo userInfo){
        return userService.addUser(userInfo);
    }

    @PutMapping("/users/register/verify/{code}")
    public String verifyUser(@PathVariable String code, @RequestBody UserInfo userInfo){
        return userService.verifyUser(userInfo,code);
    }


    @PostMapping("/users/authenticate")
    public String authenticate(@RequestBody AuthenticationRequest authenticationRequest){
        Authentication authentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        if (authentication.isAuthenticated()){
            return jwtService.generateToken(authenticationRequest.getUsername());
        }
        else{
            throw new UsernameNotFoundException("invalid user request");
        }

    }

    @GetMapping("/users/profile/{username}")
    public UserInfo getUserProfile(@PathVariable String username){
        UserInfo result = userService.getUserByUsername(username);
        if (result != null) {
            return result;
        }
        else
            throw new UsernameNotFoundException("Bad request: Username not found");
    }

    @PutMapping("/books/{bookID}/{userID}")
    public Book userToBook(@PathVariable Long bookID, @PathVariable Long userID){

        return bookService.toUser(bookID, userService.getUserByID(userID));

    }
    @PutMapping("/stores/{storeID}/{userID}")
    public Store userToStore(@PathVariable Long storeID, @PathVariable Long userID){
        return storeService.toUser(storeID, userService.getUserByID(userID));
    }

    @PutMapping("/stocks/{stockID}/{userID}")
    public Stock userToStock(@PathVariable Long stockID, @PathVariable Long userID){
        return stockService.toUser(stockID, userService.getUserByID(userID));
    }

    @PutMapping("/employees/{employeeID}/{userID}")
    public Employee userToEmployee(@PathVariable Long employeeID, @PathVariable Long userID){
        return employeeService.toUser(employeeID, userService.getUserByID(userID));
    }

    @GetMapping("/users/{id}")
    public UserInfo getUserProfile(@PathVariable Long id){
        return userService.getUserByID(id);
    }

    @GetMapping("/users/gimme")
    public List<UserInfo> getUsersPls(){
        return userService.gimmeAllDemBoys();
    }

    @GetMapping("/users/stats/{username}")
    public UserStatDTO getUserStats(@PathVariable String username){
        return userService.getUserStats(username);
    }

    @GetMapping("/users/admin/getAll")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Page<UserInfo> getAllUsersForAdmin(@RequestParam int page, @RequestParam int size, @RequestHeader("Authorization") String authorizationHeader){
        return userService.getAllUsers(page, size);
    }

    @PutMapping("/users/admin/update")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public UserInfo updateRoleForUserByAdmin(@RequestParam Long id, @RequestParam String roles, @RequestHeader("Authorization") String authorizationHeader){
        return userService.updatedUser(roles,id);
    }

    @GetMapping("/admin/checkRole")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String checkForAdminRole(@RequestHeader("Authorization") String authorizationHeader){
        return "Admin";
    }

    @DeleteMapping("/users/admin/bulkDelete")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String bulkDeleteFromDatabase(@RequestHeader("Authorization") String authorizationHeader){
        try{
            adminService.bulkDelete();
            return "Delete successful";
        }
        catch(Exception ex){
            throw new RuntimeException("bad request: "+ex.getMessage());
        }
    }

    @GetMapping("/users/admin/insert")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String insertIntoTableDatabase(@RequestParam String table, @RequestHeader("Authorization") String authorizationHeader){
        try{
            switch(table){
                case "STOCK":
                    adminService.populateStockDB();
                    return "Stock data added";
                case "EMPLOYEE":
                    adminService.populateEmployeeDB();
                    return "Employee data added";
                case "STORE":
                    adminService.populateStoreDB();
                    return "Store data added";
                case "BOOK":
                    adminService.populateBookDB();
                    return "Book data added";
                default:
                    throw new RuntimeException("table doesn't exist");
            }
        }
        catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

}

