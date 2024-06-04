package com.daja.waa_server_lab.repository;

import com.daja.waa_server_lab.entity.User;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u.id AS id, u.name AS name, COUNT(p) AS count " +
            "FROM User u JOIN u.posts p " +
            "GROUP BY u.id, u.name " +
            "HAVING COUNT(p) = :count")
    List<Tuple> findUsersWithPostsByCount(@Param("count") Integer count);

    @Query("SELECT DISTINCT u FROM User u JOIN u.posts p WHERE p.title LIKE %:title%")
    List<User> findUsersByPostTitle(@Param("title") String title);

    Optional<User> findUserByEmail(String email);
}
