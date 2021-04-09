package com.example.demo.repository;


import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAll();
    User findByUsername(String username);
    Optional<User> findById(Long id);

    //Добавляем пользователю новую роль "Оператор"
    @Transactional
    @Modifying
    @Query(value = "insert into users_roles(user_id, role_id) values(:id, 3)", nativeQuery = true)
    void setUserRole(@Param("id") Long id);
}
