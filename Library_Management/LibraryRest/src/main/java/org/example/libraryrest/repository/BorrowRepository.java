package org.example.libraryrest.repository;

import org.example.libraryrest.entity.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BorrowRepository extends JpaRepository<Borrow, Long> {
    @Query(value = "SELECT bookid FROM Borrow WHERE email = :email and status=:status", nativeQuery = true)
    public List<Long> getBookIdByEmailStatus(@Param("email") String email,String status);
    @Query(value="select * from Borrow where status=:status and action=:action", nativeQuery = true)
    public List<Borrow> getBookIdByAction(@Param("status")String status,@Param("action")String action);
    @Query(value = "SELECT bookid FROM Borrow WHERE email = :email", nativeQuery = true)
    public List<Long> getBookIdByEmail(@Param("email") String email);
    @Query(value="SELECT * FROM Borrow  where email=:email and bookid=:id",nativeQuery = true)
    public Borrow getBookByEmailAndId(String email,Long id);
}
