package org.example.libraryrest.service;

import org.example.libraryrest.entity.Borrow;
import org.example.libraryrest.repository.BookRepository;
import org.example.libraryrest.repository.BorrowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowService {
    @Autowired
    private BorrowRepository borrowRepository;

    public List<Long> getBooks(String email){

        List<Long> bookids=borrowRepository.getBookIdByEmail(email);
        return bookids;

    }
    public Borrow saveBorrow(Borrow borrow){
        return borrowRepository.save(borrow);
    }
    public List<Borrow> getBooksbyAction(String status,String action){
        List<Borrow> borrow=borrowRepository.getBookIdByAction(status,action);
        return borrow;
    }
    public Borrow getBorrowbyid(long id){
        return borrowRepository.getReferenceById(id);
    }
    public List<Long> getBooksStatus(String email,String status){
        return borrowRepository.getBookIdByEmailStatus(email,status);
    }
    public Borrow findByEmailAndBookId(String email,long id){
        return borrowRepository.getBookByEmailAndId(email,id);
    }
}
