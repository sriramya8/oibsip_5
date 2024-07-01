package org.example.libraryrest.repository;

import org.example.libraryrest.entity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyUserRepository extends JpaRepository<MyUser, Long> {
    public MyUser findByEmail(String email);
    public List<MyUser> findByRole(String role);
}
