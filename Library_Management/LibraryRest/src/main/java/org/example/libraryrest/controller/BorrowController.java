package org.example.libraryrest.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.example.libraryrest.entity.Book;
import org.example.libraryrest.entity.Borrow;
import org.example.libraryrest.entity.MyUser;
import org.example.libraryrest.service.BookService;
import org.example.libraryrest.service.BorrowService;
import org.example.libraryrest.service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/borrow/")
public class BorrowController {
    @Autowired
    private BorrowService borrowService;
    @Autowired
    private BookService bookService;
    @Autowired
    private MyUserService myUserService;
    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("")
    public String borrow(@RequestParam("id") int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        MyUser u= myUserService.getUserByEmail(email);
        List<Long> bids=borrowService.getBooks(email);
        for(Long bid:bids){
            if(bid==id){
                return "Already Issued";
            }
        }
        Borrow b= new Borrow(email,id,new Date(),null,"PENDING","ISSUE");
        Borrow out=borrowService.saveBorrow(b);
        if(out==null){
            return "Error in issuing";
        }
        return "Pending Approval By Admin";
    }
    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping( "all/")
    public List<Book> allbooks() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        List<Long> bids=borrowService.getBooksStatus(email,"DONE");
//        System.out.println(bids.get(0));
        return bookService.getList(bids);
//        List<Book> books=new ArrayList<>();
//        for(Long bid:bids){
////            Book b=bookService.getBookById(bid);
////            System.out.println(b.toString());
//            books.add(bookService.getBookById(bid));
//        }
//        System.out.println(books);
//        return books;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("issuereq/")
    public List<Borrow> issuereq() {
        List<Borrow> borrow=borrowService.getBooksbyAction("PENDING","ISSUE");
        return borrow;

    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("returnreq/")
    public List<Borrow> returnreq() {
        List<Borrow> borrow=borrowService.getBooksbyAction("PENDING","RETURN");
        return borrow;

    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("approve")
    public String approve(@RequestParam("id") long id) {
        Borrow borrow=borrowService.getBorrowbyid(id);
        borrow.setStatus("DONE");
        borrowService.saveBorrow(borrow);
        return "Approved";
    }
    @GetMapping("approvereturn")
    public String approvereturn(@RequestParam("id") long id) {
        Borrow borrow=borrowService.getBorrowbyid(id);
        borrow.setStatus("DONE");
        borrow.setReturndate(new Date());
        MyUser myUser=myUserService.getUserByEmail(borrow.getEmail());
        Borrow b=borrowService.saveBorrow(borrow);
        int fine=generateFine(b.getBorrowdate(),b.getReturndate());
        myUser.setFine(myUser.getFine()+fine);
        myUserService.saveUser(myUser);
        return "Approved and generated a fine of  "+fine;
    }
    @GetMapping("return")
    public String returning(@RequestParam("id") long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        MyUser u= myUserService.getUserByEmail(email);
        Borrow borrow= borrowService.findByEmailAndBookId(email,id);
       borrow.setStatus("PENDING");
       borrow.setAction("RETURN");
       Borrow out=borrowService.saveBorrow(borrow);
        if(out==null){
            return "Error in Return";
        }
        return "Pending Return Approval By Admin";
    }
    public int generateFine(Date d1, Date d2) {
        // Calculate the difference in milliseconds
        long diffInMillies = d2.getTime() - d1.getTime();

        // Convert the difference to days
        long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        // Calculate the fine, assuming the fine is 2 units per day
        int fine = (int) diffInDays * 2;

        return fine;
    }
}
