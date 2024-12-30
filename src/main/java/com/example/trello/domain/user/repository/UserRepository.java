package com.example.trello.domain.user.repository;

import com.example.trello.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(@Param("email") String email);

    User findByEmail(String email);


}
