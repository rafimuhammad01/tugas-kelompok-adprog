package com.c2.sisteminformasitugas.repository;

import com.c2.sisteminformasitugas.model.ToDoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToDoListRepository extends JpaRepository<ToDoList, Integer> {
    ToDoList findById(int id);
}   