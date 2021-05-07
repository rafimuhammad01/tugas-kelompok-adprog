package com.c2.sisteminformasitugas.service;

import com.c2.sisteminformasitugas.model.ToDoList;
import com.c2.sisteminformasitugas.model.User;
import com.c2.sisteminformasitugas.model.Tugas;

public interface ToDoListService {
    Iterable<ToDoList> getListToDoList();

    ToDoList createToDoList(Tugas tugas, User user);

    ToDoList getToDoList(int id);

    ToDoList updateToDoList(int id, ToDoList todolist);
}