package com.daja.waa_server_lab.repository;

import com.daja.waa_server_lab.entity.User;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.name LIKE %:name%")
    List<User> findByName(@Param("name") String name);

    @Query("SELECT u.id AS id, u.name AS name, COUNT(p) AS count " +
            "FROM User u JOIN u.posts p " +
            "GROUP BY u.id, u.name " +
            "HAVING COUNT(p) > 1")
    List<Tuple> findUsersWithPosts();
}
