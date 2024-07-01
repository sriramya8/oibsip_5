package org.example.libraryrest.service;

import org.example.libraryrest.entity.MyUser;
import org.example.libraryrest.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyUserService {
    @Autowired
    private MyUserRepository myUserRepository;

    public MyUser getUserByEmail(String email) {
        return myUserRepository.findByEmail(email);
    }
    public MyUser saveUser(MyUser myUser) {
        return myUserRepository.save(myUser);
    }
    public MyUser updateUser(MyUser myUser) {
        return myUserRepository.save(myUser);
    }
    public void deleteUser(MyUser myUser) {
        myUserRepository.delete(myUser);
    }
    public Integer numberOfStudents(){
        List<MyUser> myuser= myUserRepository.findByRole("STUDENT");
        return myuser.size();
    }

}
