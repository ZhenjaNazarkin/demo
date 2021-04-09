package com.example.demo.repository;


import com.example.demo.entity.Request;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends CrudRepository<Request, Long> {

    List<Request> findAll();
    List<Request> findAllByStatus(String status);
    List<Request> findAllByUserName(String name);

    Optional<Request> findById(Long id);
}